package ru.dibragimov.test.telegram;

import ru.dibragimov.test.api.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/")
public class TelegramServiceRest {
    TelegramService telegramService;

    public TelegramServiceRest(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @POST
    @Consumes("application/json")
    public void processMessage(Message message) {
        telegramService.sendMessage(message);
    }
}
