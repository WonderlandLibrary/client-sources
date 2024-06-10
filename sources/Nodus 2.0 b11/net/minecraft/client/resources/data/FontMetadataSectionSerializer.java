/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonDeserializationContext;
/*  4:   */ import com.google.gson.JsonElement;
/*  5:   */ import com.google.gson.JsonObject;
/*  6:   */ import com.google.gson.JsonParseException;
/*  7:   */ import java.lang.reflect.Type;
/*  8:   */ import net.minecraft.util.JsonUtils;
/*  9:   */ import org.apache.commons.lang3.Validate;
/* 10:   */ 
/* 11:   */ public class FontMetadataSectionSerializer
/* 12:   */   extends BaseMetadataSectionSerializer
/* 13:   */ {
/* 14:   */   private static final String __OBFID = "CL_00001109";
/* 15:   */   
/* 16:   */   public FontMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
/* 17:   */   {
/* 18:17 */     JsonObject var4 = par1JsonElement.getAsJsonObject();
/* 19:18 */     float[] var5 = new float[256];
/* 20:19 */     float[] var6 = new float[256];
/* 21:20 */     float[] var7 = new float[256];
/* 22:21 */     float var8 = 1.0F;
/* 23:22 */     float var9 = 0.0F;
/* 24:23 */     float var10 = 0.0F;
/* 25:25 */     if (var4.has("characters"))
/* 26:   */     {
/* 27:27 */       if (!var4.get("characters").isJsonObject()) {
/* 28:29 */         throw new JsonParseException("Invalid font->characters: expected object, was " + var4.get("characters"));
/* 29:   */       }
/* 30:32 */       JsonObject var11 = var4.getAsJsonObject("characters");
/* 31:34 */       if (var11.has("default"))
/* 32:   */       {
/* 33:36 */         if (!var11.get("default").isJsonObject()) {
/* 34:38 */           throw new JsonParseException("Invalid font->characters->default: expected object, was " + var11.get("default"));
/* 35:   */         }
/* 36:41 */         JsonObject var12 = var11.getAsJsonObject("default");
/* 37:42 */         var8 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var12, "width", var8);
/* 38:43 */         Validate.inclusiveBetween(Float.valueOf(0.0F), Float.valueOf(3.4028235E+38F), Float.valueOf(var8), "Invalid default width", new Object[0]);
/* 39:44 */         var9 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var12, "spacing", var9);
/* 40:45 */         Validate.inclusiveBetween(Float.valueOf(0.0F), Float.valueOf(3.4028235E+38F), Float.valueOf(var9), "Invalid default spacing", new Object[0]);
/* 41:46 */         var10 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var12, "left", var9);
/* 42:47 */         Validate.inclusiveBetween(Float.valueOf(0.0F), Float.valueOf(3.4028235E+38F), Float.valueOf(var10), "Invalid default left", new Object[0]);
/* 43:   */       }
/* 44:50 */       for (int var18 = 0; var18 < 256; var18++)
/* 45:   */       {
/* 46:52 */         JsonElement var13 = var11.get(Integer.toString(var18));
/* 47:53 */         float var14 = var8;
/* 48:54 */         float var15 = var9;
/* 49:55 */         float var16 = var10;
/* 50:57 */         if (var13 != null)
/* 51:   */         {
/* 52:59 */           JsonObject var17 = JsonUtils.getJsonElementAsJsonObject(var13, "characters[" + var18 + "]");
/* 53:60 */           var14 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var17, "width", var8);
/* 54:61 */           Validate.inclusiveBetween(Float.valueOf(0.0F), Float.valueOf(3.4028235E+38F), Float.valueOf(var14), "Invalid width", new Object[0]);
/* 55:62 */           var15 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var17, "spacing", var9);
/* 56:63 */           Validate.inclusiveBetween(Float.valueOf(0.0F), Float.valueOf(3.4028235E+38F), Float.valueOf(var15), "Invalid spacing", new Object[0]);
/* 57:64 */           var16 = JsonUtils.getJsonObjectFloatFieldValueOrDefault(var17, "left", var10);
/* 58:65 */           Validate.inclusiveBetween(Float.valueOf(0.0F), Float.valueOf(3.4028235E+38F), Float.valueOf(var16), "Invalid left", new Object[0]);
/* 59:   */         }
/* 60:68 */         var5[var18] = var14;
/* 61:69 */         var6[var18] = var15;
/* 62:70 */         var7[var18] = var16;
/* 63:   */       }
/* 64:   */     }
/* 65:74 */     return new FontMetadataSection(var5, var7, var6);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getSectionName()
/* 69:   */   {
/* 70:82 */     return "font";
/* 71:   */   }
/* 72:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.FontMetadataSectionSerializer
 * JD-Core Version:    0.7.0.1
 */