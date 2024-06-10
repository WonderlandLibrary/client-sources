/*  1:   */ package net.minecraft.potion;
/*  2:   */ 
/*  3:   */ public class PotionHealth
/*  4:   */   extends Potion
/*  5:   */ {
/*  6:   */   private static final String __OBFID = "CL_00001527";
/*  7:   */   
/*  8:   */   public PotionHealth(int par1, boolean par2, int par3)
/*  9:   */   {
/* 10: 9 */     super(par1, par2, par3);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public boolean isInstant()
/* 14:   */   {
/* 15:17 */     return true;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean isReady(int par1, int par2)
/* 19:   */   {
/* 20:25 */     return par1 >= 1;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.potion.PotionHealth
 * JD-Core Version:    0.7.0.1
 */