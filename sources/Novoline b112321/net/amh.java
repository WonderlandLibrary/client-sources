package net;

import com.google.gson.JsonObject;
import net.optifine.PlayerItemModel;
import net.optifine.PlayerItemParser;

public class amh {
   public static PlayerItemModel a(JsonObject var0) {
      return PlayerItemParser.parseItemModel(var0);
   }
}
