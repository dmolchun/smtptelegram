package ru.dibragimov.test.smtp;

import dagger.Component;
import org.subethamail.smtp.helper.SimpleMessageListener;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        SMTPServerServiceModule.class,
        SimpleMessageListenerModule.class,
        TelegramRestClientServiceModule.class
})
public interface SmtpComponent {
    SMTPServerService withSmtp();
}
