package ru.job4j.pooh;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * очередь входящих запросов
 * Resp - ответ от сервиса.
 * 2. Тестовое задание - проект "Pooh JMS" [#268841 #204529]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Отправитель посылает сообщение с указанием очереди.
 * new Req("POST", "queue", "weather", params)
 * Map<String, String> params = new HashMap<>();
 * ConcurrentLinkedQueue - FIFO
 * в случае необходимости вывода в виде temperature=18 вместо 18
 * поменять  c.add(hm);  на c.add(req.keyMap() + "=" + hm);
 * Для выбора метода GET или POST используйте константы "GET".equals(req.method()),
 * а не на оборот. req.method.equals("GET").
 * Для реализации QueueService и TopicService вам хватить методов putIfAbsent и get.
 * Для реализации QueueService и TopicService нужно использовать многопоточные коллекции.
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 01.10.2021
 */
public class QueueService implements Service {
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
