package ru.dibragimov.test.smtp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import ru.dibragimov.test.api.Message;

import java.io.IOException;

import static org.apache.http.HttpHeaders.USER_AGENT;

public class TelegramRestClientService {

    private String telegramServiceUrl;

    public TelegramRestClientService(String telegramServiceUrl) {
        this.telegramServiceUrl = telegramServiceUrl;
    }

    public void sendMessage(Message message) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(telegramServiceUrl);

        post.setHeader("User-Agent", USER_AGENT);

        ObjectMapper mapper = new ObjectMapper();

        StringEntity entity = new StringEntity(mapper.writeValueAsString(message));
        entity.setContentType("application/json");
        post.setEntity(entity);

        HttpResponse response = client.execute(post);

    }
}
