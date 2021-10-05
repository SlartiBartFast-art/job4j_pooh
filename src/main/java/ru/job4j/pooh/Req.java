package ru.job4j.pooh;

import java.util.HashMap;
import java.util.Map;

/**
 * 2. Тестовое задание - проект "Pooh JMS" [#268841]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Req - класс, служить для парсинга входящего сообщения.
 * private final String method; //method - GET или POST. Он указывает на тип запроса.
 * private final String mode; //mode - указывает на режим работы: queue или topic.
 * private final String queue; // queue -имя очереди
 * private final Map<String, String> params; //text - содержимое запроса.
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 17.09.2021
 */
public class Req {
    private final String method;
    private final String mode;
    private final String queue;
    private final Map<String, String> params;

    public Req(String method, String mode, String queue, Map<String, String> params) {
        this.method = method;
        this.mode = mode;
        this.queue = queue;
        this.params = params;
    }

    /**
     * Принимает строку и парсит ее под Объект Req
     * 2 вида + дальше они расходятся по Очередь/Топик
     *
     * @param content String value
     * @return Req Object
     * curl -X POST -d "temperature=18" http://localhost:9000/queue/weather
     * curl -X GET http://localhost:9000/queue/weather
     */
    public static Req of(String content) {
        Map<String, String> stringMap = new HashMap<>();
        var spl = content.split(" ");
        if (content.contains("POST")) {
            var f = spl[1].split("/");
            var s = spl[spl.length - 1].split("\n");
            var arr = s[s.length - 1].split("=");
            stringMap.put(arr[0], arr[1]);
            return new Req(spl[0], f[1], f[2], stringMap);
        }
        if (content.contains("GET")) {
            var f = spl[1].split("/");
            var s = spl[spl.length - 1].split("\n");
            var arr = s[s.length - 1].split("=");
            return new Req(spl[0], f[1], f[2], null);

        }
        return new Req(null, null, null, null);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String queue() {
        return this.queue;
    }

    public String param(String key) {
        if (key == null) {
            return null;
        }
        return params.get(key);
    }

    public String keyMap() {
        if (params.isEmpty()) {
            return null;
        }
        return params.keySet().iterator().next();
    }

    @Override
    public String toString() {
        return "Req{"
                + "method='" + method + '\''
                + ", mode='" + mode + '\''
                + ", queue='" + queue + '\''
                + ", params=" + params
                + '}';
    }
}
