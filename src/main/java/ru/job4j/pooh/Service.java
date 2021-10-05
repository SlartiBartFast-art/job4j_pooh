package ru.job4j.pooh;

/**
 * 2. Тестовое задание - проект "Pooh JMS" [#268841]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Сервисы.
 * @author SlartiBartFast-art
 * @version 01
 * @since 17.09.2021
 */
public interface Service {
    Resp process(Req req);
}
