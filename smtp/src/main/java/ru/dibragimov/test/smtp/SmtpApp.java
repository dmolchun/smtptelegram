package ru.dibragimov.test.smtp;

public class SmtpApp {
    private SmtpComponent smtpComponent;

    public static void main(String[] args) {
        new SmtpApp();
    }

    public SmtpApp() {
        smtpComponent = DaggerSmtpComponent.builder().build();
        smtpComponent.withSmtp().start();
    }
}
