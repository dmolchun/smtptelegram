package ru.dibragimov.test.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.smtp.helper.SimpleMessageListenerAdapter;
import org.subethamail.smtp.server.SMTPServer;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * SMTP server to process emails
 */

public class SMTPServerService {
    private Logger logger = LoggerFactory.getLogger(SMTPServerService.class.getName());

    private SMTPServer smtpServer;

    SimpleMessageListenerService listener;

    private boolean enabled = false;
    private String hostName;
    private Integer port;

    @Inject
    SMTPServerService(
            SimpleMessageListenerService listener,
            @Named("smtp_enabled") boolean enabled,
            @Named("smtp_host") String hostName,
            @Named("smtp_port") Integer port

    ) {
        this.listener = listener;
        this.enabled = enabled;
        this.hostName = hostName;
        this.port = port;
    }

    public void start() {
        if (enabled) {
            smtpServer = new SMTPServer(new SimpleMessageListenerAdapter(listener));
            smtpServer.setHostName(hostName);
            smtpServer.setPort(port);
            smtpServer.start();
            logger.info("****** SMTP Server is running for domain " + smtpServer.getHostName() + " on port " + smtpServer.getPort());
        } else {
            logger.info("****** SMTP Server NOT ENABLED by settings ");
        }
    }

    public void stop() {
        if (enabled) {
            logger.info("****** Stopping SMTP Server for domain " + smtpServer.getHostName() + " on port " + smtpServer.getPort());
            smtpServer.stop();
        }
    }
}
