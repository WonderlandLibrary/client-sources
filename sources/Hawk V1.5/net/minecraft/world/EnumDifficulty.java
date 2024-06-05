package net.minecraft.world;

public enum EnumDifficulty {
   PEACEFUL("PEACEFUL", 0, 0, "options.difficulty.peaceful");

   private final String difficultyResourceKey;
   HARD("HARD", 3, 3, "options.difficulty.hard");

   private final int difficultyId;
   private static final EnumDifficulty[] ENUM$VALUES = new EnumDifficulty[]{PEACEFUL, EASY, NORMAL, HARD};
   private static final String __OBFID = "CL_00001510";
   EASY("EASY", 1, 1, "options.difficulty.easy"),
   NORMAL("NORMAL", 2, 2, "options.difficulty.normal");

   private static final EnumDifficulty[] difficultyEnums = new EnumDifficulty[values().length];
   private static final EnumDifficulty[] $VALUES = new EnumDifficulty[]{PEACEFUL, EASY, NORMAL, HARD};

   private EnumDifficulty(String var3, int var4, int var5, String var6) {
      this.difficultyId = var5;
      this.difficultyResourceKey = var6;
   }

   public String getDifficultyResourceKey() {
      return this.difficultyResourceKey;
   }

   static {
      EnumDifficulty[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumDifficulty var3 = var0[var2];
         difficultyEnums[var3.difficultyId] = var3;
      }

   }

   public int getDifficultyId() {
      return this.difficultyId;
   }

   public static EnumDifficulty getDifficultyEnum(int var0) {
      return difficultyEnums[var0 % difficultyEnums.length];
   }
}
