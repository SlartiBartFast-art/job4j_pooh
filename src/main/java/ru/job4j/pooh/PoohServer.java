package ru.job4j.pooh;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2. Тестовое задание - проект "Pooh JMS" [#268841]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Каркас сервера
 * Этот класс считает данные из запроса и отправляет их обработно.
 * Для проверки запустить класс +
 * ввести в терминеле curl -X POST -d "text=13" http://localhost:9000/queue/weather
 * <p>
 * 1) Создайте репозиторий job4j_pooh.
 * 2) Допишите классы Req, Resp, QueueService, TopicService. Класс PoohServer трогать не нужно.
 * 3) В коде не должно быть синхронизации. Внутри Service нужно использовать коллекции
 * из класса concurrency.
 * 4) Загрузите код. Оставьте ссылку на коммит.
 * modes.put("queue", new QueueService());  - очередь входящих запросов
 * modes.put("topic", new TopicService()); - очередь ответов от сервера
 * //пулл потоков выполнения
 * ExecutorService pool = Executors.newFixedThreadPool(
 * Runtime.getRuntime().availableProcessors() - по колличеству ядер в процессоре
 * <p>
 * try (ServerSocket server = new ServerSocket(9000)) {  - слушать сервер по порту9000
 * while (!server.isClosed()) { - пока не закрото соединение
 * Socket socket = server.accept();  - создать сокет
 * pool.execute(() -> { //пул потоков
 * try (OutputStream out = socket.getOutputStream();  - запись
 * InputStream input = socket.getInputStream()) {  - чтение
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 17.09.2021
 */
public class PoohServer {
    private final HashMap<String, Service> modes = new HashMap<>();

    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                pool.execute(() -> {
                    try (OutputStream out = socket.getOutputStream();
                         InputStream input = socket.getInputStream()) {
                        byte[] buff = new byte[1_000_000];
                        var total = input.read(buff);
                        var content = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                        var req = Req.of(content);
                        var resp = modes.get(req.mode()).process(req);
                        out.write(("HTTP/1.1 " + resp.status() + " OK\r\n").getBytes());
                        out.write(resp.text().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PoohServer().start();
    }
}
