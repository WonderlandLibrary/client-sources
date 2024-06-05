package net.minecraft.world;

public enum EnumDifficulty {
   PEACEFUL(0, "options.difficulty.peaceful"),
   EASY(1, "options.difficulty.easy"),
   NORMAL(2, "options.difficulty.normal"),
   HARD(3, "options.difficulty.hard");

   private static final EnumDifficulty[] difficultyEnums = new EnumDifficulty[values().length];
   private final int difficultyId;
   private final String difficultyResourceKey;

   private EnumDifficulty(int var3, String var4) {
      this.difficultyId = var3;
      this.difficultyResourceKey = var4;
   }

   public int getDifficultyId() {
      return this.difficultyId;
   }

   public static EnumDifficulty getDifficultyEnum(int var0) {
      return difficultyEnums[var0 % difficultyEnums.length];
   }

   public String getDifficultyResourceKey() {
      return this.difficultyResourceKey;
   }

   static {
      for(EnumDifficulty var11 : values()) {
         difficultyEnums[var11.difficultyId] = var11;
      }

   }
}
