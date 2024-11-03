package xyz.cucumber.base.utils.cfgs;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SettingsUtils {
   public static String[] getFonts() {
      return new String[]{"rb-m", "rb-r", "Comforta", "Orbitron", "Volte", "Minecraft", "Bebas", "Mitr"};
   }

   public static void setSession(String currContent) {
      JsonParser parser = new JsonParser();
      JsonObject json = (JsonObject)parser.parse(currContent);
   }
}
