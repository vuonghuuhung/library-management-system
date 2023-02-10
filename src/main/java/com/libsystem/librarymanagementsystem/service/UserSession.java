package com.libsystem.librarymanagementsystem.service;

import com.libsystem.librarymanagementsystem.model.User;

import java.util.HashSet;
import java.util.Set;

public final class UserSession {

    private static UserSession instance;
    private static User user;

    private UserSession(User user) {
        UserSession.user = user;
    }

    public static UserSession getInstace(User user) {
        if(instance == null) {
            instance = new UserSession(user);
        }
        return instance;
    }

    public static User getUser() {
        return user;
    }

    public void cleanUserSession() {
        user = null;// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + user.getUsername() +
                '}';
    }
}
