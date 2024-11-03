package com.viaversion.viaversion.update;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
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
   private static final String PREFIX = "§a§l[ViaVersion] §a";
   private static final String URL = "https://update.viaversion.com";
   private static final String PLUGIN = "/ViaVersion/";

   public static void sendUpdateMessage(UUID uuid) {
      Via.getPlatform().runAsync(() -> {
         String message = getUpdateMessage(false);
         if (message != null) {
            Via.getPlatform().runSync(() -> Via.getPlatform().sendMessage(uuid, "§a§l[ViaVersion] §a" + message));
         }
      });
   }

   public static void sendUpdateMessage() {
      Via.getPlatform().runAsync(() -> {
         String message = getUpdateMessage(true);
         if (message != null) {
            Via.getPlatform().runSync(() -> Via.getPlatform().getLogger().warning(message));
         }
      });
   }

   @Nullable
   private static String getUpdateMessage(boolean console) {
      if (Via.getPlatform().getPluginVersion().equals("${version}")) {
         return "You are using a debug/custom version, consider updating.";
      } else {
         String newestString = getNewestVersion();
         if (newestString == null) {
            return console ? "Could not check for updates, check your connection." : null;
         } else {
            Version current;
            try {
               current = new Version(Via.getPlatform().getPluginVersion());
            } catch (IllegalArgumentException var5) {
               return "You are using a custom version, consider updating.";
            }

            Version newest = new Version(newestString);
            if (current.compareTo(newest) < 0) {
               return "There is a newer plugin version available: " + newest + ", you're on: " + current;
            } else if (console && current.compareTo(newest) != 0) {
               String tag = current.getTag().toLowerCase(Locale.ROOT);
               return !tag.startsWith("dev") && !tag.startsWith("snapshot")
                  ? "You are running a newer version of the plugin than is released!"
                  : "You are running a development version of the plugin, please report any bugs to GitHub.";
            } else {
               return null;
            }
         }
      }
   }

   @Nullable
   private static String getNewestVersion() {
      try {
         URL url = new URL("https://update.viaversion.com/ViaVersion/");
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setUseCaches(false);
         connection.addRequestProperty("User-Agent", "ViaVersion " + Via.getPlatform().getPluginVersion() + " " + Via.getPlatform().getPlatformName());
         connection.addRequestProperty("Accept", "application/json");
         connection.setDoOutput(true);
         BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
         StringBuilder builder = new StringBuilder();

         String input;
         while ((input = br.readLine()) != null) {
            builder.append(input);
         }

         br.close();

         JsonObject statistics;
         try {
            statistics = GsonUtil.getGson().fromJson(builder.toString(), JsonObject.class);
         } catch (JsonParseException var7) {
            var7.printStackTrace();
            return null;
         }

         return statistics.get("name").getAsString();
      } catch (IOException var8) {
         return null;
      }
   }
}
