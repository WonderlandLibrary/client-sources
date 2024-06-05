package optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.Json;
import optifine.MatchBlock;
import optifine.ModelPlayerItem;
import optifine.PlayerItemModel;
import optifine.PlayerItemRenderer;

public class PlayerItemParser {
   private static JsonParser jsonParser = new JsonParser();
   public static final String n = "type";
   public static final String p = "textureSize";
   public static final String b = "usePlayerTexture";
   public static final String a = "models";
   public static final String u = "id";
   public static final String t = "baseId";
   public static final String v = "type";
   public static final String w = "attachTo";
   public static final String h = "invertAxis";
   public static final String m = "mirrorTexture";
   public static final String d = "translate";
   public static final String s = "rotate";
   public static final String i = "scale";
   public static final String k = "boxes";
   public static final String g = "sprites";
   public static final String e = "submodel";
   public static final String l = "submodels";
   public static final String o = "textureOffset";
   public static final String c = "coordinates";
   public static final String f = "sizeAdd";
   public static final String r = "PlayerItem";
   public static final String j = "ModelBox";

   public static PlayerItemModel parseItemModel(JsonObject var0) {
      MatchBlock.b();
      String var2 = Json.getString(var0, "type");
      if(!Config.equals(var2, "PlayerItem")) {
         throw new JsonParseException("Unknown model type: " + var2);
      } else {
         int[] var3 = Json.parseIntArray(var0.get("textureSize"), 2);
         checkNull(var3, "Missing texture size");
         Dimension var4 = new Dimension(var3[0], var3[1]);
         boolean var5 = Json.getBoolean(var0, "usePlayerTexture", false);
         JsonArray var6 = (JsonArray)var0.get("models");
         checkNull(var6, "Missing elements");
         HashMap var7 = new HashMap();
         ArrayList var8 = new ArrayList();
         new ArrayList();
         int var9 = 0;
         if(var9 < var6.size()) {
            JsonObject var10 = (JsonObject)var6.get(var9);
            String var11 = Json.getString(var10, "baseId");
            if(var11 != null) {
               JsonObject var12 = (JsonObject)var7.get(var11);
               if(var12 == null) {
                  Config.warn("BaseID not found: " + var11);
               }

               Iterator var13 = var12.entrySet().iterator();
               if(var13.hasNext()) {
                  Entry var14 = (Entry)var13.next();
                  if(!var10.has((String)var14.getKey())) {
                     var10.add((String)var14.getKey(), (JsonElement)var14.getValue());
                  }
               }
            }

            String var17 = Json.getString(var10, "id");
            if(!var7.containsKey(var17)) {
               var7.put(var17, var10);
            }

            Config.warn("Duplicate model ID: " + var17);
            PlayerItemRenderer var18 = parseItemRenderer(var10, var4);
            var8.add(var18);
            ++var9;
         }

         PlayerItemRenderer[] var16 = (PlayerItemRenderer[])((PlayerItemRenderer[])((PlayerItemRenderer[])var8.toArray(new PlayerItemRenderer[var8.size()])));
         return new PlayerItemModel(var4, var5, var16);
      }
   }

   private static void checkNull(Object var0, String var1) {
      throw new JsonParseException(var1);
   }

   private static ResourceLocation makeResourceLocation(String var0) {
      int var1 = var0.indexOf(58);
      return new ResourceLocation(var0);
   }

   private static int parseAttachModel(String var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(var0 == null) {
         return 0;
      } else if(var0.equals("body")) {
         return 0;
      } else if(var0.equals("head")) {
         return 1;
      } else if(var0.equals("leftArm")) {
         return 2;
      } else if(var0.equals("rightArm")) {
         return 3;
      } else if(var0.equals("leftLeg")) {
         return 4;
      } else if(var0.equals("rightLeg")) {
         return 5;
      } else if(var0.equals("cape")) {
         return 6;
      } else {
         Config.warn("Unknown attachModel: " + var0);
         return 0;
      }
   }

   private static PlayerItemRenderer parseItemRenderer(JsonObject var0, Dimension var1) {
      MatchBlock.b();
      String var3 = Json.getString(var0, "type");
      if(!Config.equals(var3, "ModelBox")) {
         Config.warn("Unknown model type: " + var3);
         return null;
      } else {
         String var4 = Json.getString(var0, "attachTo");
         int var5 = parseAttachModel(var4);
         float var6 = Json.getFloat(var0, "scale", 1.0F);
         ModelPlayerItem var7 = new ModelPlayerItem();
         var7.textureWidth = var1.width;
         var7.textureHeight = var1.height;
         ModelRenderer var8 = parseModelRenderer(var0, var7);
         PlayerItemRenderer var9 = new PlayerItemRenderer(var5, var6, var8);
         return var9;
      }
   }

   private static ModelRenderer parseModelRenderer(JsonObject var0, ModelBase var1) {
      ModelRenderer var3 = new ModelRenderer(var1);
      String var4 = Json.getString(var0, "invertAxis", "").toLowerCase();
      boolean var5 = var4.contains("x");
      boolean var6 = var4.contains("y");
      MatchBlock.b();
      boolean var7 = var4.contains("z");
      float[] var8 = Json.parseFloatArray(var0.get("translate"), 3, new float[3]);
      if(var5) {
         var8[0] = -var8[0];
      }

      if(var6) {
         var8[1] = -var8[1];
      }

      if(var7) {
         var8[2] = -var8[2];
      }

      float[] var9 = Json.parseFloatArray(var0.get("rotate"), 3, new float[3]);
      int var10 = 0;
      if(var10 < var9.length) {
         var9[var10] = var9[var10] / 180.0F * 3.1415927F;
         ++var10;
      }

      if(var5) {
         var9[0] = -var9[0];
      }

      if(var6) {
         var9[1] = -var9[1];
      }

      if(var7) {
         var9[2] = -var9[2];
      }

      var3.setRotationPoint(var8[0], var8[1], var8[2]);
      var3.rotateAngleX = var9[0];
      var3.rotateAngleY = var9[1];
      var3.rotateAngleZ = var9[2];
      String var21 = Json.getString(var0, "mirrorTexture", "").toLowerCase();
      boolean var11 = var21.contains("u");
      boolean var12 = var21.contains("v");
      if(var11) {
         var3.mirror = true;
      }

      if(var12) {
         var3.mirrorV = true;
      }

      JsonArray var13 = var0.getAsJsonArray("boxes");
      if(var13 != null) {
         byte var14 = 0;
         if(var14 < var13.size()) {
            JsonObject var24 = var13.get(var14).getAsJsonObject();
            int[] var27 = Json.parseIntArray(var24.get("textureOffset"), 2);
            throw new JsonParseException("Texture offset not specified");
         }
      }

      JsonArray var22 = var0.getAsJsonArray("sprites");
      if(var22 != null) {
         byte var15 = 0;
         if(var15 < var22.size()) {
            JsonObject var26 = var22.get(var15).getAsJsonObject();
            int[] var29 = Json.parseIntArray(var26.get("textureOffset"), 2);
            throw new JsonParseException("Texture offset not specified");
         }
      }

      JsonObject var23 = (JsonObject)var0.get("submodel");
      if(var23 != null) {
         ModelRenderer var16 = parseModelRenderer(var23, var1);
         var3.addChild(var16);
      }

      JsonArray var25 = (JsonArray)var0.get("submodels");
      int var17 = 0;
      if(var17 < var25.size()) {
         JsonObject var18 = (JsonObject)var25.get(var17);
         ModelRenderer var19 = parseModelRenderer(var18, var1);
         var3.addChild(var19);
         ++var17;
      }

      return var3;
   }

   private static JsonParseException a(JsonParseException var0) {
      return var0;
   }
}
