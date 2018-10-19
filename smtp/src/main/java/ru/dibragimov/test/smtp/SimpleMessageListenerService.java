package ru.dibragimov.test.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.helper.SimpleMessageListener;
import ru.dibragimov.test.api.Message;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SMTP server listener to intercept and process emails
 */
public class SimpleMessageListenerService implements SimpleMessageListener {

    private Logger logger = LoggerFactory.getLogger(SimpleMessageListenerService.class.getName());

    TelegramRestClientService telegramService;

    @Inject
    public SimpleMessageListenerService(TelegramRestClientService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public boolean accept(String from, String recipient) {
        return true;
    }

    @Override
    public void deliver(String from, String recipient, InputStream data) {
        Session session = Session.getDefaultInstance(new Properties());
        try {
            MimeMessage mimeMessage = new MimeMessage(session, data);

            logger.info("Process message from " + from);
            telegramService.sendMessage(new Message(
                    mimeMessage.getSubject(),
                    from,
                    recipient,
                    mimeMessage.getContent().toString()
            ));
        } catch (IOException | MessagingException e) {
            logger.info("Error processing email from " + from + " to " + recipient, e);
        }
    }
}