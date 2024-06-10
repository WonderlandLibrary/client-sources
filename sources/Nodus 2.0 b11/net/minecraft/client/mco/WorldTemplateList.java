/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonArray;
/*  4:   */ import com.google.gson.JsonElement;
/*  5:   */ import com.google.gson.JsonIOException;
/*  6:   */ import com.google.gson.JsonObject;
/*  7:   */ import com.google.gson.JsonParser;
/*  8:   */ import com.google.gson.JsonSyntaxException;
/*  9:   */ import java.util.ArrayList;
/* 10:   */ import java.util.Iterator;
/* 11:   */ import java.util.List;
/* 12:   */ import net.minecraft.util.ValueObject;
/* 13:   */ 
/* 14:   */ public class WorldTemplateList
/* 15:   */   extends ValueObject
/* 16:   */ {
/* 17:   */   public List field_148782_a;
/* 18:   */   private static final String __OBFID = "CL_00001175";
/* 19:   */   
/* 20:   */   public static WorldTemplateList func_148781_a(String p_148781_0_)
/* 21:   */   {
/* 22:20 */     WorldTemplateList var1 = new WorldTemplateList();
/* 23:21 */     var1.field_148782_a = new ArrayList();
/* 24:   */     try
/* 25:   */     {
/* 26:25 */       JsonParser var2 = new JsonParser();
/* 27:26 */       JsonObject var3 = var2.parse(p_148781_0_).getAsJsonObject();
/* 28:28 */       if (var3.get("templates").isJsonArray())
/* 29:   */       {
/* 30:30 */         Iterator var4 = var3.get("templates").getAsJsonArray().iterator();
/* 31:32 */         while (var4.hasNext()) {
/* 32:34 */           var1.field_148782_a.add(WorldTemplate.func_148783_a(((JsonElement)var4.next()).getAsJsonObject()));
/* 33:   */         }
/* 34:   */       }
/* 35:   */     }
/* 36:   */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 37:47 */     return var1;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.WorldTemplateList
 * JD-Core Version:    0.7.0.1
 */