package ru.dibragimov.test.telegram;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.dibragimov.test.api.Message;
import ru.dibragimov.test.telegram.db.EmailHolder;
import ru.dibragimov.test.telegram.db.EmailRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Service to operate with telegram API
 */
public class TelegramService {
    private Logger logger = LoggerFactory.getLogger(TelegramService.class.getName());


    private EmailRepository emailRepository;
    private EmailBot emailBot;

    private String token;
    private String botName;

    @Inject
    public TelegramService(EmailRepository emailRepository,
                           String token,
                           String botName) {
        this.emailRepository = emailRepository;
        this.token = token;
        this.botName = botName;
    }

    public void init() {
        try {
            ApiContextInitializer.init();
            TelegramBotsApi botsApi = new TelegramBotsApi();
            emailBot = new EmailBot(this);
            botsApi.registerBot(emailBot);

        } catch (TelegramApiRequestException e) {
            logger.error("Error initializing Telegram bot", e);
        }
    }

    /**
     * Save new connection email-chatId to repository
     *
     * @return sting with result message
     */
    public String register(String email, Long chatId) {
        EmailHolder holder = Optional.ofNullable(emailRepository.getOneEager(email)).orElse(new EmailHolder(email));
        if (holder.getChatIdList().contains(chatId)) {
            return "Already registered";
        }
        holder.getChatIdList().add(chatId);
        saveToRepo(holder);
        return "Successfully registered";
    }

    /**
     * Remove connection email-chatId from repository
     *
     * @return sting with result message
     */
    public String deregister(String email, Long chatId) {
        EmailHolder holder = emailRepository.getOneEager(email);
        if (holder != null && holder.getChatIdList().removeIf(chatId::equals)) {
            saveToRepo(holder);
            return "Successfully deregistered";
        }
        return "Not registered";
    }

    /**
     * Save EmailHolder to repository
     *
     * @param holder - holder to save
     */
    private void saveToRepo(EmailHolder holder) {
        if (holder.getChatIdList().isEmpty()) {
            emailRepository.delete(holder);
        } else {
            emailRepository.save(holder);
        }
    }

    /**
     * Send messages for registered Telegram users
     */
    public void sendMessage(Message message) {
        String toAddress = message.getTo();
        if (emailRepository.existsById(toAddress)) {
            String telegramMessage = message.toTelegramString();
            emailRepository.getOneEager(toAddress).getChatIdList()
                    .forEach(chatId -> sendMessageToChat(telegramMessage, chatId));
        }
    }

    /**
     * Send message to concrete chat
     *
     * @param message - string message
     * @param chatId  - Telegram chat Id
     */
    private void sendMessageToChat(String message, Long chatId) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId).setText(message);
        try {
            emailBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.error("Error sending message for chatId " + chatId, e);
        }
    }

    public String getToken() {
        return token;
    }

    public String getBotName() {
        return botName;
    }
}
