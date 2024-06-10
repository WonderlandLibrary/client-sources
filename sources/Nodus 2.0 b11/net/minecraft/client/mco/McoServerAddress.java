/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonElement;
/*  4:   */ import com.google.gson.JsonIOException;
/*  5:   */ import com.google.gson.JsonObject;
/*  6:   */ import com.google.gson.JsonParser;
/*  7:   */ import com.google.gson.JsonSyntaxException;
/*  8:   */ import net.minecraft.util.ValueObject;
/*  9:   */ 
/* 10:   */ public class McoServerAddress
/* 11:   */   extends ValueObject
/* 12:   */ {
/* 13:   */   public String field_148770_a;
/* 14:   */   private static final String __OBFID = "CL_00001168";
/* 15:   */   
/* 16:   */   public static McoServerAddress func_148769_a(String p_148769_0_)
/* 17:   */   {
/* 18:16 */     JsonParser var1 = new JsonParser();
/* 19:17 */     McoServerAddress var2 = new McoServerAddress();
/* 20:   */     try
/* 21:   */     {
/* 22:21 */       JsonObject var3 = var1.parse(p_148769_0_).getAsJsonObject();
/* 23:22 */       var2.field_148770_a = var3.get("address").getAsString();
/* 24:   */     }
/* 25:   */     catch (JsonIOException localJsonIOException) {}catch (JsonSyntaxException localJsonSyntaxException) {}
/* 26:33 */     return var2;
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.McoServerAddress
 * JD-Core Version:    0.7.0.1
 */