/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Lists;
/*  4:   */ import com.google.gson.JsonArray;
/*  5:   */ import com.google.gson.JsonDeserializationContext;
/*  6:   */ import com.google.gson.JsonElement;
/*  7:   */ import com.google.gson.JsonObject;
/*  8:   */ import com.google.gson.JsonParseException;
/*  9:   */ import java.lang.reflect.Type;
/* 10:   */ import java.util.ArrayList;
/* 11:   */ import net.minecraft.util.JsonUtils;
/* 12:   */ 
/* 13:   */ public class TextureMetadataSectionSerializer
/* 14:   */   extends BaseMetadataSectionSerializer
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00001115";
/* 17:   */   
/* 18:   */   public TextureMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
/* 19:   */   {
/* 20:19 */     JsonObject var4 = par1JsonElement.getAsJsonObject();
/* 21:20 */     boolean var5 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "blur", false);
/* 22:21 */     boolean var6 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "clamp", false);
/* 23:22 */     ArrayList var7 = Lists.newArrayList();
/* 24:24 */     if (var4.has("mipmaps")) {
/* 25:   */       try
/* 26:   */       {
/* 27:28 */         JsonArray var8 = var4.getAsJsonArray("mipmaps");
/* 28:30 */         for (int var9 = 0; var9 < var8.size(); var9++)
/* 29:   */         {
/* 30:32 */           JsonElement var10 = var8.get(var9);
/* 31:34 */           if (var10.isJsonPrimitive()) {
/* 32:   */             try
/* 33:   */             {
/* 34:38 */               var7.add(Integer.valueOf(var10.getAsInt()));
/* 35:   */             }
/* 36:   */             catch (NumberFormatException var12)
/* 37:   */             {
/* 38:42 */               throw new JsonParseException("Invalid texture->mipmap->" + var9 + ": expected number, was " + var10, var12);
/* 39:   */             }
/* 40:45 */           } else if (var10.isJsonObject()) {
/* 41:47 */             throw new JsonParseException("Invalid texture->mipmap->" + var9 + ": expected number, was " + var10);
/* 42:   */           }
/* 43:   */         }
/* 44:   */       }
/* 45:   */       catch (ClassCastException var13)
/* 46:   */       {
/* 47:53 */         throw new JsonParseException("Invalid texture->mipmaps: expected array, was " + var4.get("mipmaps"), var13);
/* 48:   */       }
/* 49:   */     }
/* 50:57 */     return new TextureMetadataSection(var5, var6, var7);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getSectionName()
/* 54:   */   {
/* 55:65 */     return "texture";
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.TextureMetadataSectionSerializer
 * JD-Core Version:    0.7.0.1
 */