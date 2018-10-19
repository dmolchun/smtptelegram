package ru.dibragimov.test.telegram.db;

import java.util.HashSet;
import java.util.Set;

public class EmailHolder {
    private String email;
    private Set<Long> chatIdList = new HashSet<>();

    public EmailHolder() {
    }

    public EmailHolder(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Set<Long> getChatIdList() {
        return chatIdList;
    }
}
