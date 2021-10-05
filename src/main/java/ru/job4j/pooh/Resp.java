package ru.job4j.pooh;

/**
 * 2. Тестовое задание - проект "Pooh JMS" [#268841]
 * Уровень : 3. Мидл Категория : 3.1. Multithreading
 * Топик : 3.1.7. Контрольные вопросы
 * Resp - ответ от сервиса.
 *
 * @author SlartiBartFast-art
 * @version 01
 * @since 17.09.2021
 */
public class Resp {
    private final String text;
    private final int status;

    public Resp(String text, int status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public int status() {
        return status;
    }

    @Override
    public String toString() {
        return "Resp{"
                + "text='" + text + '\''
                + ", status=" + status
                + '}';
    }
}
