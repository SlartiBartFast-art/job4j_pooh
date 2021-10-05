package ru.job4j.pooh;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2. Тестовое задание - проект "Pooh JMS" [#268841]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Для реализации QueueService и TopicService нужно использовать многопоточные коллекции.
 * Важно. Для реализации QueueService и TopicService вам хватить методов putIfAbsent и get.
 * В коде не надо писать проверку на if (map.contains()) - это не атомарная операция.
 * computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)
 * Если указанный ключ еще не связан со значением, пытается вычислить его значение с помощью заданной
 * функции сопоставления и вводит его в эту карту, если null.
 * ConcurrentLinkedQueue<String> boolean	add(E e) Вставляет указанный элемент в конец этой очереди.
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 17.09.2021
 */
public class CASMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
        String name = "weather";

        /* add if empty */
        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());

        /* put */
        queue.get(name).add("value");

        /* extract */
        var text = queue.get(name).poll();
        /* was */
        // var text = queue.get(name, emptyQueue()).poll();
    }
}
