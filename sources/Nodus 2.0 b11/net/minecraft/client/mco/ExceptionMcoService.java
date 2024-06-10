/*  1:   */ package net.minecraft.client.mco;
/*  2:   */ 
/*  3:   */ public class ExceptionMcoService
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   public final int field_148831_a;
/*  7:   */   public final String field_148829_b;
/*  8:   */   public final int field_148830_c;
/*  9:   */   private static final String __OBFID = "CL_00001177";
/* 10:   */   
/* 11:   */   public ExceptionMcoService(int par1, String par2Str, int par3)
/* 12:   */   {
/* 13:12 */     super(par2Str);
/* 14:13 */     this.field_148831_a = par1;
/* 15:14 */     this.field_148829_b = par2Str;
/* 16:15 */     this.field_148830_c = par3;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:20 */     return "Realms: " + this.field_148829_b;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.mco.ExceptionMcoService
 * JD-Core Version:    0.7.0.1
 */