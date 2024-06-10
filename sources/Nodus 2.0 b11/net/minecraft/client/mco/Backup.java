/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonElement;
/*  4:   */ import com.google.gson.JsonObject;
/*  5:   */ import java.util.Date;
/*  6:   */ import net.minecraft.util.ValueObject;
/*  7:   */ 
/*  8:   */ public class Backup
/*  9:   */   extends ValueObject
/* 10:   */ {
/* 11:   */   public String field_148780_a;
/* 12:   */   public Date field_148778_b;
/* 13:   */   public long field_148779_c;
/* 14:   */   private static final String __OBFID = "CL_00001164";
/* 15:   */   
/* 16:   */   public static Backup func_148777_a(JsonElement p_148777_0_)
/* 17:   */   {
/* 18:17 */     JsonObject var1 = p_148777_0_.getAsJsonObject();
/* 19:18 */     Backup var2 = new Backup();
/* 20:   */     try
/* 21:   */     {
/* 22:22 */       var2.field_148780_a = var1.get("backupId").getAsString();
/* 23:23 */       var2.field_148778_b = new Date(Long.parseLong(var1.get("lastModifiedDate").getAsString()));
/* 24:24 */       var2.field_148779_c = Long.parseLong(var1.get("size").getAsString());
/* 25:   */     }
/* 26:   */     catch (IllegalArgumentException localIllegalArgumentException) {}
/* 27:31 */     return var2;
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.Backup
 * JD-Core Version:    0.7.0.1
 */