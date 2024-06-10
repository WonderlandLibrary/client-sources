/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public class MouseFilter
/*  4:   */ {
/*  5:   */   private float field_76336_a;
/*  6:   */   private float field_76334_b;
/*  7:   */   private float field_76335_c;
/*  8:   */   private static final String __OBFID = "CL_00001500";
/*  9:   */   
/* 10:   */   public float smooth(float par1, float par2)
/* 11:   */   {
/* 12:15 */     this.field_76336_a += par1;
/* 13:16 */     par1 = (this.field_76336_a - this.field_76334_b) * par2;
/* 14:17 */     this.field_76335_c += (par1 - this.field_76335_c) * 0.5F;
/* 15:19 */     if (((par1 > 0.0F) && (par1 > this.field_76335_c)) || ((par1 < 0.0F) && (par1 < this.field_76335_c))) {
/* 16:21 */       par1 = this.field_76335_c;
/* 17:   */     }
/* 18:24 */     this.field_76334_b += par1;
/* 19:25 */     return par1;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.MouseFilter
 * JD-Core Version:    0.7.0.1
 */