package ru.dibragimov.test.smtp;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class SMTPServerServiceModule {
    @Provides
    @Singleton
    SMTPServerService provideSMTPServerService(
            SimpleMessageListenerService listener,
            @Named("smtp_enabled") boolean enabled,
            @Named("smtp_host") String hostName,
            @Named("smtp_port") Integer port
    ) {
        return new SMTPServerService(listener, enabled, hostName, port);
    }

    @Provides
    @Named("smtp_enabled")
    boolean provideSmtpEnabled() {
        return true;
    }

    @Provides
    @Named("smtp_host")
    String provideSmtpHost() {
        return "localhost";
    }

    @Provides
    @Named("smtp_port")
    Integer provideSmtpPort() {
        return 2222;
    }
}
