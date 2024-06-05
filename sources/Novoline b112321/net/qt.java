package net;

import com.google.gson.JsonElement;
import net.optifine.PlayerConfiguration;
import net.optifine.PlayerConfigurationParser;

public class qt {
   public static PlayerConfiguration a(PlayerConfigurationParser var0, JsonElement var1) {
      return var0.parsePlayerConfiguration(var1);
   }
}
