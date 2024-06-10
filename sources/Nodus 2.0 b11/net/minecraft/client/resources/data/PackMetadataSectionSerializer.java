/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonDeserializationContext;
/*  4:   */ import com.google.gson.JsonElement;
/*  5:   */ import com.google.gson.JsonObject;
/*  6:   */ import com.google.gson.JsonSerializationContext;
/*  7:   */ import com.google.gson.JsonSerializer;
/*  8:   */ import java.lang.reflect.Type;
/*  9:   */ import net.minecraft.util.JsonUtils;
/* 10:   */ 
/* 11:   */ public class PackMetadataSectionSerializer
/* 12:   */   extends BaseMetadataSectionSerializer
/* 13:   */   implements JsonSerializer
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00001113";
/* 16:   */   
/* 17:   */   public PackMetadataSection deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
/* 18:   */   {
/* 19:17 */     JsonObject var4 = par1JsonElement.getAsJsonObject();
/* 20:18 */     String var5 = JsonUtils.getJsonObjectStringFieldValue(var4, "description");
/* 21:19 */     int var6 = JsonUtils.getJsonObjectIntegerFieldValue(var4, "pack_format");
/* 22:20 */     return new PackMetadataSection(var5, var6);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public JsonElement serialize(PackMetadataSection par1PackMetadataSection, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 26:   */   {
/* 27:25 */     JsonObject var4 = new JsonObject();
/* 28:26 */     var4.addProperty("pack_format", Integer.valueOf(par1PackMetadataSection.getPackFormat()));
/* 29:27 */     var4.addProperty("description", par1PackMetadataSection.getPackDescription());
/* 30:28 */     return var4;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getSectionName()
/* 34:   */   {
/* 35:36 */     return "pack";
/* 36:   */   }
/* 37:   */   
/* 38:   */   public JsonElement serialize(Object par1Obj, Type par2Type, JsonSerializationContext par3JsonSerializationContext)
/* 39:   */   {
/* 40:41 */     return serialize((PackMetadataSection)par1Obj, par2Type, par3JsonSerializationContext);
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.PackMetadataSectionSerializer
 * JD-Core Version:    0.7.0.1
 */