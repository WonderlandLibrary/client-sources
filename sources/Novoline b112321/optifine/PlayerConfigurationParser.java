package optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.HttpPipeline;
import optifine.Json;
import optifine.MatchBlock;
import optifine.PlayerConfiguration;
import optifine.PlayerItemModel;
import optifine.PlayerItemParser;

public class PlayerConfigurationParser {
   private String player = null;
   public static final String b = "items";
   public static final String a = "type";
   public static final String c = "active";

   public PlayerConfigurationParser(String var1) {
      this.player = var1;
   }

   public PlayerConfiguration parsePlayerConfiguration(JsonElement var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         throw new JsonParseException("JSON object is null, player: " + this.player);
      } else {
         JsonObject var3 = (JsonObject)var1;
         PlayerConfiguration var4 = new PlayerConfiguration();
         JsonArray var5 = (JsonArray)var3.get("items");
         int var6 = 0;
         if(var6 < var5.size()) {
            JsonObject var7 = (JsonObject)var5.get(var6);
            boolean var8 = Json.getBoolean(var7, "active", true);
            String var9 = Json.getString(var7, "type");
            if(var9 == null) {
               Config.warn("Item type is null, player: " + this.player);
            }

            String var10 = Json.getString(var7, "model");
            if(var10 == null) {
               var10 = "items/" + var9 + "/model.cfg";
            }

            PlayerItemModel var11 = this.downloadModel(var10);
            if(!var11.isUsePlayerTexture()) {
               String var12 = Json.getString(var7, "texture");
               if(var12 == null) {
                  var12 = "items/" + var9 + "/users/" + this.player + ".png";
               }

               BufferedImage var13 = this.downloadTextureImage(var12);
               var11.setTextureImage(var13);
               ResourceLocation var14 = new ResourceLocation("net.optifine.net", var12);
               var11.setTextureLocation(var14);
            }

            var4.addPlayerItemModel(var11);
            ++var6;
         }

         return var4;
      }
   }

   private BufferedImage downloadTextureImage(String var1) {
      String var2 = "http://s.optifine.net/" + var1;

      try {
         byte[] var3 = HttpPipeline.get(var2, Minecraft.getMinecraft().getProxy());
         BufferedImage var4 = ImageIO.read(new ByteArrayInputStream(var3));
         return var4;
      } catch (IOException var5) {
         Config.warn("Error loading item texture " + var1 + ": " + var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      }
   }

   private PlayerItemModel downloadModel(String var1) {
      String var2 = "http://s.optifine.net/" + var1;

      try {
         byte[] var3 = HttpPipeline.get(var2, Minecraft.getMinecraft().getProxy());
         String var4 = new String(var3, "ASCII");
         JsonParser var5 = new JsonParser();
         JsonObject var6 = (JsonObject)var5.parse(var4);
         new PlayerItemParser();
         PlayerItemModel var8 = PlayerItemParser.parseItemModel(var6);
         return var8;
      } catch (Exception var9) {
         Config.warn("Error loading item model " + var1 + ": " + var9.getClass().getName() + ": " + var9.getMessage());
         return null;
      }
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
