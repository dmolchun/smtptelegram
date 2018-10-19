package ru.dibragimov.test.telegram;

public class TelegramApp {
    public static void main(String[] args) {
        DaggerTelegramComponent.builder().build().withTelegramService().init();
    }
}
