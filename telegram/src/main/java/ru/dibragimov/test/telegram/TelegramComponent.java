package ru.dibragimov.test.telegram;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        TelegramServiceModule.class
})
public interface TelegramComponent {
    TelegramService withTelegramService();
}
