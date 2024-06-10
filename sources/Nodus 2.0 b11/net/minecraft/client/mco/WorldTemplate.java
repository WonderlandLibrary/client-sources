/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonElement;
/*  4:   */ import com.google.gson.JsonObject;
/*  5:   */ import net.minecraft.util.ValueObject;
/*  6:   */ 
/*  7:   */ public class WorldTemplate
/*  8:   */   extends ValueObject
/*  9:   */ {
/* 10:   */   public String field_148787_a;
/* 11:   */   public String field_148785_b;
/* 12:   */   public String field_148786_c;
/* 13:   */   public String field_148784_d;
/* 14:   */   private static final String __OBFID = "CL_00001174";
/* 15:   */   
/* 16:   */   public static WorldTemplate func_148783_a(JsonObject p_148783_0_)
/* 17:   */   {
/* 18:16 */     WorldTemplate var1 = new WorldTemplate();
/* 19:   */     try
/* 20:   */     {
/* 21:20 */       var1.field_148787_a = p_148783_0_.get("id").getAsString();
/* 22:21 */       var1.field_148785_b = p_148783_0_.get("name").getAsString();
/* 23:22 */       var1.field_148786_c = p_148783_0_.get("version").getAsString();
/* 24:23 */       var1.field_148784_d = p_148783_0_.get("author").getAsString();
/* 25:   */     }
/* 26:   */     catch (IllegalArgumentException localIllegalArgumentException) {}
/* 27:30 */     return var1;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.WorldTemplate
 * JD-Core Version:    0.7.0.1
 */