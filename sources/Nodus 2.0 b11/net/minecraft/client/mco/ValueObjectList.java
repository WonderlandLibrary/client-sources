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
/* 14:   */ public class ValueObjectList
/* 15:   */   extends ValueObject
/* 16:   */ {
/* 17:   */   public List field_148772_a;
/* 18:   */   private static final String __OBFID = "CL_00001169";
/* 19:   */   
/* 20:   */   public static ValueObjectList func_148771_a(String p_148771_0_)
/* 21:   */   {
/* 22:21 */     ValueObjectList var1 = new ValueObjectList();
/* 23:22 */     var1.field_148772_a = new ArrayList();
/* 24:   */     try
/* 25:   */     {
/* 26:26 */       JsonParser var2 = new JsonParser();
/* 27:27 */       JsonObject var3 = var2.parse(p_148771_0_).getAsJsonObject();
/* 28:29 */       if (var3.get("servers").isJsonArray())
/* 29:   */       {
/* 30:31 */         JsonArray var4 = var3.get("servers").getAsJsonArray();
/* 31:32 */         Iterator var5 = var4.iterator();
/* 32:34 */         while (var5.hasNext()) {
/* 33:36 */           var1.field_148772_a.add(McoServer.func_148802_a(((JsonElement)var5.next()).getAsJsonObject()));
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:   */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 38:49 */     return var1;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.ValueObjectList
 * JD-Core Version:    0.7.0.1
 */