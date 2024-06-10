/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonElement;
/*  4:   */ import com.google.gson.JsonIOException;
/*  5:   */ import com.google.gson.JsonObject;
/*  6:   */ import com.google.gson.JsonParser;
/*  7:   */ import com.google.gson.JsonSyntaxException;
/*  8:   */ import net.minecraft.util.ValueObject;
/*  9:   */ 
/* 10:   */ public class ValueObjectSubscription
/* 11:   */   extends ValueObject
/* 12:   */ {
/* 13:   */   public long field_148790_a;
/* 14:   */   public int field_148789_b;
/* 15:   */   private static final String __OBFID = "CL_00001172";
/* 16:   */   
/* 17:   */   public static ValueObjectSubscription func_148788_a(String p_148788_0_)
/* 18:   */   {
/* 19:17 */     ValueObjectSubscription var1 = new ValueObjectSubscription();
/* 20:   */     try
/* 21:   */     {
/* 22:21 */       JsonParser var2 = new JsonParser();
/* 23:22 */       JsonObject var3 = var2.parse(p_148788_0_).getAsJsonObject();
/* 24:23 */       var1.field_148790_a = var3.get("startDate").getAsLong();
/* 25:24 */       var1.field_148789_b = var3.get("daysLeft").getAsInt();
/* 26:   */     }
/* 27:   */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 28:35 */     return var1;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.ValueObjectSubscription
 * JD-Core Version:    0.7.0.1
 */