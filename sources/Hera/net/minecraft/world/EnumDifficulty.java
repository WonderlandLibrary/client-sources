/*    */ package net.minecraft.world;
/*    */ 
/*    */ public enum EnumDifficulty
/*    */ {
/*  5 */   PEACEFUL(0, "options.difficulty.peaceful"),
/*  6 */   EASY(1, "options.difficulty.easy"),
/*  7 */   NORMAL(2, "options.difficulty.normal"),
/*  8 */   HARD(3, "options.difficulty.hard");
/*    */   static {
/* 10 */     difficultyEnums = new EnumDifficulty[(values()).length];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     byte b;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     int i;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     EnumDifficulty[] arrayOfEnumDifficulty;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 36 */     for (i = (arrayOfEnumDifficulty = values()).length, b = 0; b < i; ) { EnumDifficulty enumdifficulty = arrayOfEnumDifficulty[b];
/*    */       
/* 38 */       difficultyEnums[enumdifficulty.difficultyId] = enumdifficulty;
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   private static final EnumDifficulty[] difficultyEnums;
/*    */   private final int difficultyId;
/*    */   private final String difficultyResourceKey;
/*    */   
/*    */   EnumDifficulty(int difficultyIdIn, String difficultyResourceKeyIn) {
/*    */     this.difficultyId = difficultyIdIn;
/*    */     this.difficultyResourceKey = difficultyResourceKeyIn;
/*    */   }
/*    */   
/*    */   public int getDifficultyId() {
/*    */     return this.difficultyId;
/*    */   }
/*    */   
/*    */   public static EnumDifficulty getDifficultyEnum(int p_151523_0_) {
/*    */     return difficultyEnums[p_151523_0_ % difficultyEnums.length];
/*    */   }
/*    */   
/*    */   public String getDifficultyResourceKey() {
/*    */     return this.difficultyResourceKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\EnumDifficulty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */