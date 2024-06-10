/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ public class ColorizerFoliage
/*  4:   */ {
/*  5: 6 */   private static int[] foliageBuffer = new int[65536];
/*  6:   */   private static final String __OBFID = "CL_00000135";
/*  7:   */   
/*  8:   */   public static void setFoliageBiomeColorizer(int[] par0ArrayOfInteger)
/*  9:   */   {
/* 10:11 */     foliageBuffer = par0ArrayOfInteger;
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static int getFoliageColor(double par0, double par2)
/* 14:   */   {
/* 15:19 */     par2 *= par0;
/* 16:20 */     int var4 = (int)((1.0D - par0) * 255.0D);
/* 17:21 */     int var5 = (int)((1.0D - par2) * 255.0D);
/* 18:22 */     return foliageBuffer[(var5 << 8 | var4)];
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static int getFoliageColorPine()
/* 22:   */   {
/* 23:30 */     return 6396257;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static int getFoliageColorBirch()
/* 27:   */   {
/* 28:38 */     return 8431445;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static int getFoliageColorBasic()
/* 32:   */   {
/* 33:43 */     return 4764952;
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.ColorizerFoliage
 * JD-Core Version:    0.7.0.1
 */