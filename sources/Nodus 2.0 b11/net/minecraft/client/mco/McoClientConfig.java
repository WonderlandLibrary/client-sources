/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ import java.net.Proxy;
/*  4:   */ 
/*  5:   */ public class McoClientConfig
/*  6:   */ {
/*  7:   */   private static Proxy field_148686_a;
/*  8:   */   private static final String __OBFID = "CL_00001157";
/*  9:   */   
/* 10:   */   public static Proxy func_148685_a()
/* 11:   */   {
/* 12:12 */     return field_148686_a;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static void func_148684_a(Proxy p_148684_0_)
/* 16:   */   {
/* 17:17 */     if (field_148686_a == null) {
/* 18:19 */       field_148686_a = p_148684_0_;
/* 19:   */     }
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.McoClientConfig
 * JD-Core Version:    0.7.0.1
 */