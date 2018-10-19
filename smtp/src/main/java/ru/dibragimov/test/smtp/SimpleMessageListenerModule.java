package ru.dibragimov.test.smtp;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SimpleMessageListenerModule {
    @Provides
    @Singleton
    SimpleMessageListenerService provideSimpleMessageListenerService(
            TelegramRestClientService telegramService
    ) {
        return new SimpleMessageListenerService(telegramService);
    }

}
