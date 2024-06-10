/*   1:    */ package net.minecraft.client.resources.data;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.gson.JsonArray;
/*   5:    */ import com.google.gson.JsonDeserializationContext;
/*   6:    */ import com.google.gson.JsonElement;
/*   7:    */ import com.google.gson.JsonObject;
/*   8:    */ import com.google.gson.JsonParseException;
/*   9:    */ import com.google.gson.JsonPrimitive;
/*  10:    */ import com.google.gson.JsonSerializationContext;
/*  11:    */ import com.google.gson.JsonSerializer;
/*  12:    */ import java.lang.reflect.Type;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import net.minecraft.util.JsonUtils;
/*  15:    */ import org.apache.commons.lang3.Validate;
/*  16:    */ 
/*  17:    */ public class AnimationMetadataSectionSerializer
/*  18:    */   extends BaseMetadataSectionSerializer
/*  19:    */   implements JsonSerializer
/*  20:    */ {
/*  21:    */   private static final String __OBFID = "CL_00001107";
/*  22:    */   
/*  23:    */   public AnimationMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
/*  24:    */   {
/*  25: 23 */     ArrayList var4 = Lists.newArrayList();
/*  26: 24 */     JsonObject var5 = JsonUtils.getJsonElementAsJsonObject(par1JsonElement, "metadata section");
/*  27: 25 */     int var6 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "frametime", 1);
/*  28: 27 */     if (var6 != 1) {
/*  29: 29 */       Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(2147483647), Integer.valueOf(var6), "Invalid default frame time", new Object[0]);
/*  30:    */     }
/*  31: 34 */     if (var5.has("frames")) {
/*  32:    */       try
/*  33:    */       {
/*  34: 38 */         JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var5, "frames");
/*  35: 40 */         for (int var8 = 0; var8 < var7.size(); var8++)
/*  36:    */         {
/*  37: 42 */           JsonElement var9 = var7.get(var8);
/*  38: 43 */           AnimationFrame var10 = parseAnimationFrame(var8, var9);
/*  39: 45 */           if (var10 != null) {
/*  40: 47 */             var4.add(var10);
/*  41:    */           }
/*  42:    */         }
/*  43:    */       }
/*  44:    */       catch (ClassCastException var11)
/*  45:    */       {
/*  46: 53 */         throw new JsonParseException("Invalid animation->frames: expected array, was " + var5.get("frames"), var11);
/*  47:    */       }
/*  48:    */     }
/*  49: 57 */     int var12 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "width", -1);
/*  50: 58 */     int var8 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var5, "height", -1);
/*  51: 60 */     if (var12 != -1) {
/*  52: 62 */       Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(2147483647), Integer.valueOf(var12), "Invalid width", new Object[0]);
/*  53:    */     }
/*  54: 65 */     if (var8 != -1) {
/*  55: 67 */       Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(2147483647), Integer.valueOf(var8), "Invalid height", new Object[0]);
/*  56:    */     }
/*  57: 70 */     return new AnimationMetadataSection(var4, var12, var8, var6);
/*  58:    */   }
/*  59:    */   
/*  60:    */   private AnimationFrame parseAnimationFrame(int par1, JsonElement par2JsonElement)
/*  61:    */   {
/*  62: 75 */     if (par2JsonElement.isJsonPrimitive()) {
/*  63: 77 */       return new AnimationFrame(JsonUtils.getJsonElementIntegerValue(par2JsonElement, "frames[" + par1 + "]"));
/*  64:    */     }
/*  65: 79 */     if (par2JsonElement.isJsonObject())
/*  66:    */     {
/*  67: 81 */       JsonObject var3 = JsonUtils.getJsonElementAsJsonObject(par2JsonElement, "frames[" + par1 + "]");
/*  68: 82 */       int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var3, "time", -1);
/*  69: 84 */       if (var3.has("time")) {
/*  70: 86 */         Validate.inclusiveBetween(Integer.valueOf(1), Integer.valueOf(2147483647), Integer.valueOf(var4), "Invalid frame time", new Object[0]);
/*  71:    */       }
/*  72: 89 */       int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var3, "index");
/*  73: 90 */       Validate.inclusiveBetween(Integer.valueOf(0), Integer.valueOf(2147483647), Integer.valueOf(var5), "Invalid frame index", new Object[0]);
/*  74: 91 */       return new AnimationFrame(var5, var4);
/*  75:    */     }
/*  76: 95 */     return null;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public JsonElement serialize(AnimationMetadataSection par1AnimationMetadataSection, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/*  80:    */   {
/*  81:101 */     JsonObject var4 = new JsonObject();
/*  82:102 */     var4.addProperty("frametime", Integer.valueOf(par1AnimationMetadataSection.getFrameTime()));
/*  83:104 */     if (par1AnimationMetadataSection.getFrameWidth() != -1) {
/*  84:106 */       var4.addProperty("width", Integer.valueOf(par1AnimationMetadataSection.getFrameWidth()));
/*  85:    */     }
/*  86:109 */     if (par1AnimationMetadataSection.getFrameHeight() != -1) {
/*  87:111 */       var4.addProperty("height", Integer.valueOf(par1AnimationMetadataSection.getFrameHeight()));
/*  88:    */     }
/*  89:114 */     if (par1AnimationMetadataSection.getFrameCount() > 0)
/*  90:    */     {
/*  91:116 */       JsonArray var5 = new JsonArray();
/*  92:118 */       for (int var6 = 0; var6 < par1AnimationMetadataSection.getFrameCount(); var6++) {
/*  93:120 */         if (par1AnimationMetadataSection.frameHasTime(var6))
/*  94:    */         {
/*  95:122 */           JsonObject var7 = new JsonObject();
/*  96:123 */           var7.addProperty("index", Integer.valueOf(par1AnimationMetadataSection.getFrameIndex(var6)));
/*  97:124 */           var7.addProperty("time", Integer.valueOf(par1AnimationMetadataSection.getFrameTimeSingle(var6)));
/*  98:125 */           var5.add(var7);
/*  99:    */         }
/* 100:    */         else
/* 101:    */         {
/* 102:129 */           var5.add(new JsonPrimitive(Integer.valueOf(par1AnimationMetadataSection.getFrameIndex(var6))));
/* 103:    */         }
/* 104:    */       }
/* 105:133 */       var4.add("frames", var5);
/* 106:    */     }
/* 107:136 */     return var4;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getSectionName()
/* 111:    */   {
/* 112:144 */     return "animation";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 116:    */   {
/* 117:149 */     return serialize((AnimationMetadataSection)par1Obj, par2Type, par3JsonSerializationContext);
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.AnimationMetadataSectionSerializer
 * JD-Core Version:    0.7.0.1
 */