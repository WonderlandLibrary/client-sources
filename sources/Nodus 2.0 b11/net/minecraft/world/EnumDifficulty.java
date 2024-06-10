/*  1:   */ package net.minecraft.world;
/*  2:   */ 
/*  3:   */ public enum EnumDifficulty
/*  4:   */ {
/*  5: 5 */   PEACEFUL(0, "options.difficulty.peaceful"),  EASY(1, "options.difficulty.easy"),  NORMAL(2, "options.difficulty.normal"),  HARD(3, "options.difficulty.hard");
/*  6:   */   
/*  7:   */   private static final EnumDifficulty[] difficultyEnums;
/*  8:   */   private final int difficultyId;
/*  9:   */   private final String difficultyResourceKey;
/* 10:   */   private static final String __OBFID = "CL_00001510";
/* 11:   */   
/* 12:   */   private EnumDifficulty(int p_i45312_3_, String p_i45312_4_)
/* 13:   */   {
/* 14:16 */     this.difficultyId = p_i45312_3_;
/* 15:17 */     this.difficultyResourceKey = p_i45312_4_;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public int getDifficultyId()
/* 19:   */   {
/* 20:22 */     return this.difficultyId;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static EnumDifficulty getDifficultyEnum(int p_151523_0_)
/* 24:   */   {
/* 25:27 */     return difficultyEnums[(p_151523_0_ % difficultyEnums.length)];
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getDifficultyResourceKey()
/* 29:   */   {
/* 30:32 */     return this.difficultyResourceKey;
/* 31:   */   }
/* 32:   */   
/* 33:   */   static
/* 34:   */   {
/* 35: 9 */     difficultyEnums = new EnumDifficulty[values().length];
/* 36:   */     
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:36 */     EnumDifficulty[] var0 = values();
/* 63:37 */     int var1 = var0.length;
/* 64:39 */     for (int var2 = 0; var2 < var1; var2++)
/* 65:   */     {
/* 66:41 */       EnumDifficulty var3 = var0[var2];
/* 67:42 */       difficultyEnums[var3.difficultyId] = var3;
/* 68:   */     }
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.EnumDifficulty
 * JD-Core Version:    0.7.0.1
 */