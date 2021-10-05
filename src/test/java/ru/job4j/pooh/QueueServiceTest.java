package ru.job4j.pooh;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class QueueServiceTest {
    @Test
    public void whenPostThenGetQueue() {
        var queueService = new QueueService();
       // queueService.get(String name, ConcurrentLinkedQueue<String> concurrentLinkedQueue).poll()
        Map<String, String> params = new HashMap<>();
        params.put("temperature", "18");
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", params)
        );
        /* Забираем данные из очереди weather. Режим queue */
        var result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        System.out.println("___ " + result.toString());
        assertThat(result.text(), is("18"));
    }
}