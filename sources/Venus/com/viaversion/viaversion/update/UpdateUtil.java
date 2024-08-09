/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
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
import java.net.URL;
import java.util.Locale;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class UpdateUtil {
    private static final String PREFIX = "\u00a7a\u00a7l[ViaVersion] \u00a7a";
    private static final String URL = "https://api.spiget.org/v2/resources/";
    private static final int PLUGIN = 19254;
    private static final String LATEST_VERSION = "/versions/latest";

    public static void sendUpdateMessage(UUID uUID) {
        Via.getPlatform().runAsync(() -> UpdateUtil.lambda$sendUpdateMessage$1(uUID));
    }

    public static void sendUpdateMessage() {
        Via.getPlatform().runAsync(UpdateUtil::lambda$sendUpdateMessage$3);
    }

    private static @Nullable String getUpdateMessage(boolean bl) {
        Version version;
        if (Via.getPlatform().getPluginVersion().equals("${version}")) {
            return "You are using a debug/custom version, consider updating.";
        }
        String string = UpdateUtil.getNewestVersion();
        if (string == null) {
            if (bl) {
                return "Could not check for updates, check your connection.";
            }
            return null;
        }
        try {
            version = new Version(Via.getPlatform().getPluginVersion());
        } catch (IllegalArgumentException illegalArgumentException) {
            return "You are using a custom version, consider updating.";
        }
        Version version2 = new Version(string);
        if (version.compareTo(version2) < 0) {
            return "There is a newer plugin version available: " + version2 + ", you're on: " + version;
        }
        if (bl && version.compareTo(version2) != 0) {
            String string2 = version.getTag().toLowerCase(Locale.ROOT);
            if (string2.startsWith("dev") || string2.startsWith("snapshot")) {
                return "You are running a development version of the plugin, please report any bugs to GitHub.";
            }
            return "You are running a newer version of the plugin than is released!";
        }
        return null;
    }

    private static @Nullable String getNewestVersion() {
        try {
            JsonObject jsonObject;
            String string;
            URL uRL = new URL("https://api.spiget.org/v2/resources/19254/versions/latest?" + System.currentTimeMillis());
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.addRequestProperty("User-Agent", "ViaVersion " + Via.getPlatform().getPluginVersion() + " " + Via.getPlatform().getPlatformName());
            httpURLConnection.setDoOutput(false);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            bufferedReader.close();
            try {
                jsonObject = GsonUtil.getGson().fromJson(stringBuilder.toString(), JsonObject.class);
            } catch (JsonParseException jsonParseException) {
                jsonParseException.printStackTrace();
                return null;
            }
            return jsonObject.get("name").getAsString();
        } catch (IOException iOException) {
            return null;
        }
    }

    private static void lambda$sendUpdateMessage$3() {
        String string = UpdateUtil.getUpdateMessage(true);
        if (string != null) {
            Via.getPlatform().runSync(() -> UpdateUtil.lambda$null$2(string));
        }
    }

    private static void lambda$null$2(String string) {
        Via.getPlatform().getLogger().warning(string);
    }

    private static void lambda$sendUpdateMessage$1(UUID uUID) {
        String string = UpdateUtil.getUpdateMessage(false);
        if (string != null) {
            Via.getPlatform().runSync(() -> UpdateUtil.lambda$null$0(uUID, string));
        }
    }

    private static void lambda$null$0(UUID uUID, String string) {
        Via.getPlatform().sendMessage(uUID, PREFIX + string);
    }
}

