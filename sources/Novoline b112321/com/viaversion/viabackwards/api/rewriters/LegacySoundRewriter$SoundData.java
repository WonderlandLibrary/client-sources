package com.viaversion.viabackwards.api.rewriters;

public final class LegacySoundRewriter$SoundData {
   private final int replacementSound;
   private final boolean changePitch;
   private final float newPitch;
   private final boolean added;

   public LegacySoundRewriter$SoundData(int var1, boolean var2, float var3, boolean var4) {
      this.replacementSound = var1;
      this.changePitch = var2;
      this.newPitch = var3;
      this.added = var4;
   }

   public int getReplacementSound() {
      return this.replacementSound;
   }

   public boolean isChangePitch() {
      return this.changePitch;
   }

   public float getNewPitch() {
      return this.newPitch;
   }

   public boolean isAdded() {
      return this.added;
   }
}
