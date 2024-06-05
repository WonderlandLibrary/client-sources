package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public interface ISound {
   ResourceLocation getSoundLocation();

   float getVolume();

   ISound.AttenuationType getAttenuationType();

   float getXPosF();

   float getZPosF();

   boolean canRepeat();

   int getRepeatDelay();

   float getYPosF();

   float getPitch();

   public static enum AttenuationType {
      private final int type;
      private static final ISound.AttenuationType[] $VALUES = new ISound.AttenuationType[]{NONE, LINEAR};
      NONE("NONE", 0, 0);

      private static final String __OBFID = "CL_00001126";
      private static final ISound.AttenuationType[] ENUM$VALUES = new ISound.AttenuationType[]{NONE, LINEAR};
      LINEAR("LINEAR", 1, 2);

      private AttenuationType(String var3, int var4, int var5) {
         this.type = var5;
      }

      public int getTypeInt() {
         return this.type;
      }
   }
}
