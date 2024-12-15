package com.alan.clients.util.dynamic;

import com.alan.clients.util.dynamic.impl.DynamicResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DynamicAPI {
    private static final Gson GSON = new Gson();
    private static final String ACCOUNT_API_URL = "http://alts.russia-games.eu/api/v1/accounts";
    private static final String LOGIN_API_URL = "http://alts.russia-games.eu/api/v1/login";
    public static String token = "api-token";

    /**
     * Requests all available accounts from the API.
     *
     * @return An array of {@link DynamicResult} objects.
     */
    public static DynamicResult[] requestAvailableAccounts() {
        try {
            // Set up the connection.
            HttpURLConnection connection = (HttpURLConnection) new URL(ACCOUNT_API_URL).openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestMethod("POST");

            // Write the request.
            JsonObject object = new JsonObject();
            object.addProperty("api-token", token);
            connection.setDoOutput(true);
            connection.getOutputStream().write(GSON.toJson(object).getBytes());
            connection.connect();

            // Read the response.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; builder.append(line)) ;

            // Parse the response.
            object = JsonParser.parseString(builder.toString()).getAsJsonObject();
            JsonArray array = object.get("accounts").getAsJsonArray();
            DynamicResult[] results = new DynamicResult[array.size()];
            if (results.length == 0) {
                return null;
            }

            // Convert the response to a DynamicResult array.
            for (int i = 0; i < array.size(); i++) {
                JsonObject data = array.get(i).getAsJsonObject();
                DynamicResult result = GSON.fromJson(data, DynamicResult.class);
                results[i] = result;
            }

            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Request a specific account from the API.
     *
     * @param id The ID of the account.
     * @return The access token for the account.
     */
    public static String requestAccessToken(int id) {
        try {
            // Set up the connection.
            HttpURLConnection connection = (HttpURLConnection) new URL(LOGIN_API_URL).openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestMethod("POST");

            // Write the request.
            JsonObject object = new JsonObject();
            object.addProperty("api-token", token);
            object.addProperty("id", id);
            connection.setDoOutput(true);
            connection.getOutputStream().write(GSON.toJson(object).getBytes());
            connection.connect();

            // Read the response.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; builder.append(line)) ;

            // Parse the response.
            object = JsonParser.parseString(builder.toString()).getAsJsonObject();
            if (object.has("access_token")) {
                return object.get("access_token").getAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
