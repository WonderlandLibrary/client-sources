/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import com.google.common.collect.Sets;
/*  4:   */ import com.google.gson.JsonDeserializationContext;
/*  5:   */ import com.google.gson.JsonElement;
/*  6:   */ import com.google.gson.JsonObject;
/*  7:   */ import com.google.gson.JsonParseException;
/*  8:   */ import java.lang.reflect.Type;
/*  9:   */ import java.util.HashSet;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import java.util.Map.Entry;
/* 12:   */ import java.util.Set;
/* 13:   */ import net.minecraft.client.resources.Language;
/* 14:   */ import net.minecraft.util.JsonUtils;
/* 15:   */ 
/* 16:   */ public class LanguageMetadataSectionSerializer
/* 17:   */   extends BaseMetadataSectionSerializer
/* 18:   */ {
/* 19:   */   private static final String __OBFID = "CL_00001111";
/* 20:   */   
/* 21:   */   public LanguageMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
/* 22:   */   {
/* 23:21 */     JsonObject var4 = par1JsonElement.getAsJsonObject();
/* 24:22 */     HashSet var5 = Sets.newHashSet();
/* 25:23 */     Iterator var6 = var4.entrySet().iterator();
/* 26:   */     String var8;
/* 27:   */     String var10;
/* 28:   */     String var11;
/* 29:   */     boolean var12;
/* 30:   */     do
/* 31:   */     {
/* 32:31 */       if (!var6.hasNext()) {
/* 33:33 */         return new LanguageMetadataSection(var5);
/* 34:   */       }
/* 35:36 */       Map.Entry var7 = (Map.Entry)var6.next();
/* 36:37 */       var8 = (String)var7.getKey();
/* 37:38 */       JsonObject var9 = JsonUtils.getJsonElementAsJsonObject((JsonElement)var7.getValue(), "language");
/* 38:39 */       var10 = JsonUtils.getJsonObjectStringFieldValue(var9, "region");
/* 39:40 */       var11 = JsonUtils.getJsonObjectStringFieldValue(var9, "name");
/* 40:41 */       var12 = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var9, "bidirectional", false);
/* 41:43 */       if (var10.isEmpty()) {
/* 42:45 */         throw new JsonParseException("Invalid language->'" + var8 + "'->region: empty value");
/* 43:   */       }
/* 44:48 */       if (var11.isEmpty()) {
/* 45:50 */         throw new JsonParseException("Invalid language->'" + var8 + "'->name: empty value");
/* 46:   */       }
/* 47:53 */     } while (var5.add(new Language(var8, var10, var11, var12)));
/* 48:55 */     throw new JsonParseException("Duplicate language->'" + var8 + "' defined");
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String getSectionName()
/* 52:   */   {
/* 53:63 */     return "language";
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.LanguageMetadataSectionSerializer
 * JD-Core Version:    0.7.0.1
 */