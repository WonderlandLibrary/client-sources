/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ public class ColorizerGrass
/*  4:   */ {
/*  5: 6 */   private static int[] grassBuffer = new int[65536];
/*  6:   */   private static final String __OBFID = "CL_00000138";
/*  7:   */   
/*  8:   */   public static void setGrassBiomeColorizer(int[] par0ArrayOfInteger)
/*  9:   */   {
/* 10:11 */     grassBuffer = par0ArrayOfInteger;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static int getGrassColor(double par0, double par2)
/* 14:   */   {
/* 15:19 */     par2 *= par0;
/* 16:20 */     int var4 = (int)((1.0D - par0) * 255.0D);
/* 17:21 */     int var5 = (int)((1.0D - par2) * 255.0D);
/* 18:22 */     return grassBuffer[(var5 << 8 | var4)];
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.ColorizerGrass
 * JD-Core Version:    0.7.0.1
 */