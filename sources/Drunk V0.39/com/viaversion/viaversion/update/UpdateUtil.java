/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.update;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.update.Version;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public class UpdateUtil {
    private static final String PREFIX = "\u00a7a\u00a7l[ViaVersion] \u00a7a";
    private static final String URL = "https://api.spiget.org/v2/resources/";
    private static final int PLUGIN = 19254;
    private static final String LATEST_VERSION = "/versions/latest";

    public static void sendUpdateMessage(UUID uuid) {
        Via.getPlatform().runAsync(() -> {
            String message = UpdateUtil.getUpdateMessage(false);
            if (message == null) return;
            Via.getPlatform().runSync(() -> Via.getPlatform().sendMessage(uuid, PREFIX + message));
        });
    }

    public static void sendUpdateMessage() {
        Via.getPlatform().runAsync(() -> {
            String message = UpdateUtil.getUpdateMessage(true);
            if (message == null) return;
            Via.getPlatform().runSync(() -> Via.getPlatform().getLogger().warning(message));
        });
    }

    private static @Nullable String getUpdateMessage(boolean console) {
        Version current;
        if (Via.getPlatform().getPluginVersion().equals("${version}")) {
            return "You are using a debug/custom version, consider updating.";
        }
        String newestString = UpdateUtil.getNewestVersion();
        if (newestString == null) {
            if (!console) return null;
            return "Could not check for updates, check your connection.";
        }
        try {
            current = new Version(Via.getPlatform().getPluginVersion());
        }
        catch (IllegalArgumentException e) {
            return "You are using a custom version, consider updating.";
        }
        Version newest = new Version(newestString);
        if (current.compareTo(newest) < 0) {
            return "There is a newer plugin version available: " + newest + ", you're on: " + current;
        }
        if (!console) return null;
        if (current.compareTo(newest) == 0) return null;
        String tag = current.getTag().toLowerCase(Locale.ROOT);
        if (tag.startsWith("dev")) return "You are running a development version of the plugin, please report any bugs to GitHub.";
        if (!tag.startsWith("snapshot")) return "You are running a newer version of the plugin than is released!";
        return "You are running a development version of the plugin, please report any bugs to GitHub.";
    }

    private static @Nullable String getNewestVersion() {
        try {
            String input;
            URL url = new URL("https://api.spiget.org/v2/resources/19254/versions/latest?" + System.currentTimeMillis());
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "ViaVersion " + Via.getPlatform().getPluginVersion() + " " + Via.getPlatform().getPlatformName());
            connection.setDoOutput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while ((input = br.readLine()) != null) {
                builder.append(input);
            }
            br.close();
            try {
                JsonObject statistics = GsonUtil.getGson().fromJson(builder.toString(), JsonObject.class);
                return statistics.get("name").getAsString();
            }
            catch (JsonParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        catch (MalformedURLException e) {
            return null;
        }
        catch (IOException e) {
            return null;
        }
    }
}

