/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonObject
 */
package com.thealtening.api.retriever;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thealtening.api.TheAlteningException;
import com.thealtening.api.response.Account;
import com.thealtening.api.response.License;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface DataRetriever {
    public static final Logger LOGGER = Logger.getLogger("The Altening");
    public static final Gson gson = new Gson();
    public static final String BASE_URL = "http://api.thealtening.com/v1/";
    public static final String LICENCE_URL = "http://api.thealtening.com/v1/license?token=";
    public static final String GENERATE_URL = "http://api.thealtening.com/v1/generate?info=true&token=";
    public static final String PRIVATE_ACC_URL = "http://api.thealtening.com/v1/private?acctoken=";
    public static final String FAVORITE_ACC_URL = "http://api.thealtening.com/v1/favorite?acctoken=";

    public License getLicense();

    public Account getAccount();

    public boolean isPrivate(String var1) throws TheAlteningException;

    public boolean isFavorite(String var1) throws TheAlteningException;

    public void updateKey(String var1);

    default public JsonObject retrieveData(String url) throws TheAlteningException {
        JsonObject jsonObject;
        String response;
        try {
            response = this.connect(url);
            jsonObject = (JsonObject)gson.fromJson(response, JsonObject.class);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while reading retrieved data from the website");
            throw new TheAlteningException("IO", e.getCause());
        }
        if (jsonObject == null) {
            LOGGER.log(Level.SEVERE, "Error while parsing website's response");
            throw new TheAlteningException("JSON", "Parsing error: \n" + response);
        }
        if (jsonObject.has("error") && jsonObject.has("errorMessage")) {
            LOGGER.log(Level.SEVERE, "The website returned, type: " + jsonObject.get("error").getAsString() + ". Details:" + jsonObject.get("errorMessage").getAsString());
            throw new TheAlteningException("Connection", "Bad response");
        }
        return jsonObject;
    }

    default public boolean isSuccess(JsonObject jsonObject) {
        return jsonObject.has("success") && jsonObject.get("success").getAsBoolean();
    }

    default public String connect(String link) throws IOException {
        Charset encoding;
        StringBuilder stringBuilder = new StringBuilder();
        URLConnection connection = new URL(link).openConnection();
        InputStream connectionStream = connection.getInputStream();
        String encodingId = connection.getContentEncoding();
        try {
            encoding = encodingId == null ? StandardCharsets.UTF_8 : Charset.forName(encodingId);
        }
        catch (UnsupportedCharsetException ex) {
            encoding = StandardCharsets.UTF_8;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connectionStream, encoding));){
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }
        return stringBuilder.toString();
    }
}

