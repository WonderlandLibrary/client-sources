package com.alan.clients.util.account.impl;

import com.alan.clients.util.account.Account;
import com.alan.clients.util.account.AccountType;
import com.alan.clients.util.account.auth.MicrosoftLogin;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

@Getter
@Setter
public class MicrosoftAccount extends Account {
    private String refreshToken;

    public MicrosoftAccount(String name, String uuid, String accessToken, String refreshToken) {
        super(AccountType.MICROSOFT, name, uuid, accessToken);
        this.refreshToken = refreshToken;
    }

    public static MicrosoftAccount create() {
        MicrosoftAccount account = new MicrosoftAccount("", "", "", "");
        Consumer<String> consumer = (String refreshToken) -> {
            account.setRefreshToken(refreshToken);
            account.login();
        };

        MicrosoftLogin.getRefreshToken(consumer);
        return account;
    }

    @Override
    public boolean login() {
        if (refreshToken.isEmpty()) return super.login();

        MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        if (!loginData.isGood()) {
            return false;
        }

        this.setName(loginData.username);
        this.setUuid(loginData.uuid);
        this.setAccessToken(loginData.mcToken);
        this.setRefreshToken(loginData.newRefreshToken);
        return super.login();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && !refreshToken.isEmpty();
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = super.toJson();
        object.addProperty("refreshToken", refreshToken);
        return object;
    }

    @Override
    public void parseJson(JsonObject object) {
        super.parseJson(object);

        if (object.has("refreshToken")) {
            refreshToken = object.get("refreshToken").getAsString();
        }
    }
}
