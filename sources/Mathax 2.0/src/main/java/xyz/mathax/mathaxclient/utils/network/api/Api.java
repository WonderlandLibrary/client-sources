package xyz.mathax.mathaxclient.utils.network.api;

import org.json.JSONObject;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.network.Http;

import java.io.File;

public class Api {
    private static final File FOLDER = new File(MatHax.FOLDER, "API");

    public static Account loggedAccount = null;

    public static String token = "";

    public static void login(String usernameOrEmail, String password) {
        if (usernameOrEmail.isBlank() || password.isBlank()) {
            return;
        }

        String loginURL = String.format("accounts/login?usernameOrEmail=%s&password=%s", usernameOrEmail, password);
        login(getJSON(loginURL));
    }

    public static void login(String token) {
        if (token.isBlank()) {
            return;
        }

        String loginURL = String.format("accounts/login?token=%s", token);
        login(getJSON(loginURL));
    }

    public static void login(JSONObject json) {
        if (json == null) {
            return;
        }

        if (json.has("error")) {
            MatHax.LOG.error(json.getString("error"));
            return;
        }

        token = json.getString("token");
        loggedAccount = new Account(token);
    }

    public static JSONObject getVersions() {
        return getJSON("versions/metadata.json");
    }

    public static JSONObject getCapes() {
        return getJSON("capes/metadata.json");
    }

    public static JSONObject getJSON(String URL, String bearer) {
        String response = Http.get(MatHax.API_URL + URL).bearer(bearer).sendString();
        if (response == null) {
            return null;
        }

        return new JSONObject(response);
    }

    public static JSONObject getJSON(String URL) {
        return getJSON(URL, "");
    }

    public static void save() {
        JSONObject json = new JSONObject();
        json.put("token", token);
        JSONUtils.saveJSON(json, new File(FOLDER, "Account.json"));
    }

    public static void load() {
        File file = new File(FOLDER, "Account.json");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            return;
        }

        JSONObject json = JSONUtils.loadJSON(file);
        if (json != null && json.has("token")) {
            token = json.getString("token");
            login(token);
        }
    }
}
