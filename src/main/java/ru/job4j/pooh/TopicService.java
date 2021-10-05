package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 2. Тестовое задание - проект "Pooh JMS" [#268841]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Отправитель посылает сообщение с указанием темы.
 * <p>
 * Получатель читает первое сообщение и удаляет его из очереди.
 * <p>
 * Для каждого потребителя в режиме "topic" должна быть уникальная очередь
 * потребления в отличии от режима "queue", где очереди для всех клиентов одна и та же.
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 17.09.2021
 */
public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> concurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if ("POST".equals(req.method())) {
            var qn = req.queue();
            var hm = req.param(req.keyMap());
            if (concurrentHashMap.get(qn) == null) {
                ConcurrentLinkedQueue<String> c = new ConcurrentLinkedQueue<>();
                c.add(hm);
                concurrentHashMap.putIfAbsent(qn, c);
            } else {
                concurrentHashMap.get(qn).add(hm); /* put */
            }
            resp = new Resp(hm, 1);
        }
        if ("GET".equals(req.method())) {
            var qn = req.queue();
            var clq = concurrentHashMap.get(qn);
            resp = new Resp(concurrentHashMap.get(qn).poll(), -1); /* extract */
            System.out.println(resp.text());
        }
        return resp;
    }
}
