/*  1:   */ package net.minecraft.client.audio;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonArray;
/*  4:   */ import com.google.gson.JsonDeserializationContext;
/*  5:   */ import com.google.gson.JsonDeserializer;
/*  6:   */ import com.google.gson.JsonElement;
/*  7:   */ import com.google.gson.JsonObject;
/*  8:   */ import java.lang.reflect.Type;
/*  9:   */ import java.util.List;
/* 10:   */ import net.minecraft.util.JsonUtils;
/* 11:   */ import org.apache.commons.lang3.Validate;
/* 12:   */ 
/* 13:   */ public class SoundListSerializer
/* 14:   */   implements JsonDeserializer
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00001124";
/* 17:   */   
/* 18:   */   public SoundList deserialize(JsonElement p_148578_1_, Type p_148578_2_, JsonDeserializationContext p_148578_3_)
/* 19:   */   {
/* 20:18 */     JsonObject var4 = JsonUtils.getJsonElementAsJsonObject(p_148578_1_, "entry");
/* 21:19 */     SoundList var5 = new SoundList();
/* 22:20 */     var5.func_148572_a(JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var4, "replace", false));
/* 23:21 */     SoundCategory var6 = SoundCategory.func_147154_a(JsonUtils.getJsonObjectStringFieldValueOrDefault(var4, "category", SoundCategory.MASTER.getCategoryName()));
/* 24:22 */     var5.func_148571_a(var6);
/* 25:23 */     Validate.notNull(var6, "Invalid category", new Object[0]);
/* 26:25 */     if (var4.has("sounds"))
/* 27:   */     {
/* 28:27 */       JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var4, "sounds");
/* 29:29 */       for (int var8 = 0; var8 < var7.size(); var8++)
/* 30:   */       {
/* 31:31 */         JsonElement var9 = var7.get(var8);
/* 32:32 */         SoundList.SoundEntry var10 = new SoundList.SoundEntry();
/* 33:34 */         if (JsonUtils.jsonElementTypeIsString(var9))
/* 34:   */         {
/* 35:36 */           var10.func_148561_a(JsonUtils.getJsonElementStringValue(var9, "sound"));
/* 36:   */         }
/* 37:   */         else
/* 38:   */         {
/* 39:40 */           JsonObject var11 = JsonUtils.getJsonElementAsJsonObject(var9, "sound");
/* 40:41 */           var10.func_148561_a(JsonUtils.getJsonObjectStringFieldValue(var11, "name"));
/* 41:43 */           if (var11.has("type"))
/* 42:   */           {
/* 43:45 */             SoundList.SoundEntry.Type var12 = SoundList.SoundEntry.Type.func_148580_a(JsonUtils.getJsonObjectStringFieldValue(var11, "type"));
/* 44:46 */             Validate.notNull(var12, "Invalid type", new Object[0]);
/* 45:47 */             var10.func_148562_a(var12);
/* 46:   */           }
/* 47:52 */           if (var11.has("volume"))
/* 48:   */           {
/* 49:54 */             float var13 = JsonUtils.getJsonObjectFloatFieldValue(var11, "volume");
/* 50:55 */             Validate.isTrue(var13 > 0.0F, "Invalid volume", new Object[0]);
/* 51:56 */             var10.func_148553_a(var13);
/* 52:   */           }
/* 53:59 */           if (var11.has("pitch"))
/* 54:   */           {
/* 55:61 */             float var13 = JsonUtils.getJsonObjectFloatFieldValue(var11, "pitch");
/* 56:62 */             Validate.isTrue(var13 > 0.0F, "Invalid pitch", new Object[0]);
/* 57:63 */             var10.func_148559_b(var13);
/* 58:   */           }
/* 59:66 */           if (var11.has("weight"))
/* 60:   */           {
/* 61:68 */             int var14 = JsonUtils.getJsonObjectIntegerFieldValue(var11, "weight");
/* 62:69 */             Validate.isTrue(var14 > 0, "Invalid weight", new Object[0]);
/* 63:70 */             var10.func_148554_a(var14);
/* 64:   */           }
/* 65:73 */           if (var11.has("stream")) {
/* 66:75 */             var10.func_148557_a(JsonUtils.getJsonObjectBooleanFieldValue(var11, "stream"));
/* 67:   */           }
/* 68:   */         }
/* 69:79 */         var5.func_148570_a().add(var10);
/* 70:   */       }
/* 71:   */     }
/* 72:83 */     return var5;
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.audio.SoundListSerializer
 * JD-Core Version:    0.7.0.1
 */