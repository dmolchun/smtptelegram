package ru.dibragimov.test.telegram;

import dagger.Module;
import dagger.Provides;
import ru.dibragimov.test.telegram.db.EmailRepository;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class TelegramServiceModule {
    @Provides
    @Singleton
    TelegramService provideTelegramService(
            @Named("token") String token,
            @Named("bot_name") String botName
    ) {
        return new TelegramService(new EmailRepository(), token, botName);
    }

    @Provides
    @Named("token")
    String provideToken() {
        return "677589704:AAFhnDeA4jFt8szospAcT8pOR4fx5hsYwgY";
    }

    @Provides
    @Named("bot_name")
    String provideBotName() {
        return "TestSmtpBot";
    }
}
