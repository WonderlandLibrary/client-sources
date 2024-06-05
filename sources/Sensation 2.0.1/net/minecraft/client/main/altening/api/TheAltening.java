package net.minecraft.client.main.altening.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.client.main.altening.api.data.AccountData;
import net.minecraft.client.main.altening.api.data.LicenseData;
import net.minecraft.client.main.altening.api.utils.HttpUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class TheAltening extends HttpUtils {

    private final String apiKey;
    private final String endpoint = "http://api.thealtening.com/v1/";
    private final Logger logger = Logger.getLogger("TheAltening");
    private final Gson gson = new Gson();

    public TheAltening(String apiKey) {
        this.apiKey = apiKey;
    }

    public LicenseData getLicenseData() {
        try {
            System.setProperty("http.agent", "chrome");
            final String connectionData = connect(String.format("http://api.thealtening.com/v1/license?token=%s", apiKey));
            return gson.fromJson(connectionData, LicenseData.class);
        } catch (IOException e) {
            if (e.getMessage().contains("401")) {
                logger.info("Invalid API Key provided");
            } else {
                logger.info("Failed to communicate with the website. Try again later");

            }
            return null;
        }
    }

    public AccountData getAccountData() {
        try {
            final String connectionData = connect(String.format("http://api.thealtening.com/v1/generate?info=true&token=%s", apiKey));
            return gson.fromJson(connectionData, AccountData.class);
        } catch (IOException e) {
            if (e.getMessage().contains("401")) {
                logger.info("Invalid API Key provided");
            } else {
                logger.info("Failed to communicate with the website. Try again later");

            }
            return null;
        }
    }

    public boolean isPrivate(String token) {
        try {
            final String connectionData = connect(endpoint + "private?acctoken=" + token + "&token=" + apiKey);
            final JsonObject jsonObject = gson.fromJson(connectionData, JsonObject.class);

            return jsonObject != null && jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
        } catch (IOException e) {
            if (e.getMessage().contains("401")) {
                logger.info("Invalid API Key provided");
            } else {
                logger.info("Failed to communicate with the website. Try again later");

            }
            return false;
        }
    }

    public boolean isFavorite(String token) {
        try {
            final String connectionData = connect(endpoint + "favorite?acctoken=" + token + "&token=" + apiKey);
            final JsonObject jsonObject = gson.fromJson(connectionData, JsonObject.class);
            return jsonObject != null && jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
        } catch (IOException e) {
            if (e.getMessage().contains("401")) {
                logger.info("Invalid API Key provided");
            } else {
                logger.info("Failed to communicate with the website. Try again later");

            }
            return false;
        }
    }

    public static class Asynchronous {
        private TheAltening theAltening;

        public Asynchronous(TheAltening theAltening) {
            this.theAltening = theAltening;
        }

        public CompletableFuture<LicenseData> getLicenseData() {
            return CompletableFuture.supplyAsync(theAltening::getLicenseData);
        }

        public CompletableFuture<AccountData> getAccountData() {
            return CompletableFuture.supplyAsync(theAltening::getAccountData);
        }

        public CompletableFuture<Boolean> isPrivate(String token) {
            return CompletableFuture.supplyAsync(() -> theAltening.isPrivate(token));
        }

        public CompletableFuture<Boolean> isFavorite(String token) {
            return CompletableFuture.supplyAsync(() -> theAltening.isFavorite(token));
        }
    }
}
