package ru.dibragimov.test.smtp;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class TelegramRestClientServiceModule {

    @Provides
    @Singleton
    public TelegramRestClientService providesTelegramRestClientService(
            @Named("telegram_service_url") String telegramServiceUrl
    ) {
        return new TelegramRestClientService(telegramServiceUrl);
    }

    @Provides
    @Named("telegram_service_url")
    String provideTelegramServiceUrl() {
        return "localhost:8080/send";
    }
}
