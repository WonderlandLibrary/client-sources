/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import com.google.gson.JsonElement;
/*  4:   */ import com.google.gson.JsonObject;
/*  5:   */ import net.minecraft.util.ValueObject;
/*  6:   */ 
/*  7:   */ public class PendingInvite
/*  8:   */   extends ValueObject
/*  9:   */ {
/* 10:   */   public String field_148776_a;
/* 11:   */   public String field_148774_b;
/* 12:   */   public String field_148775_c;
/* 13:   */   private static final String __OBFID = "CL_00001170";
/* 14:   */   
/* 15:   */   public static PendingInvite func_148773_a(JsonObject p_148773_0_)
/* 16:   */   {
/* 17:15 */     PendingInvite var1 = new PendingInvite();
/* 18:   */     try
/* 19:   */     {
/* 20:19 */       var1.field_148776_a = p_148773_0_.get("invitationId").getAsString();
/* 21:20 */       var1.field_148774_b = p_148773_0_.get("worldName").getAsString();
/* 22:21 */       var1.field_148775_c = p_148773_0_.get("worldOwnerName").getAsString();
/* 23:   */     }
/* 24:   */     catch (Exception localException) {}
/* 25:28 */     return var1;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.PendingInvite
 * JD-Core Version:    0.7.0.1
 */