package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class PositionedSound implements ISound {
   private static final String __OBFID = "CL_00001116";
   protected float pitch = 1.0F;
   protected boolean repeat = false;
   protected int repeatDelay = 0;
   protected float zPosF;
   protected float xPosF;
   protected ISound.AttenuationType attenuationType;
   protected float yPosF;
   protected float volume = 1.0F;
   protected final ResourceLocation positionedSoundLocation;

   public int getRepeatDelay() {
      return this.repeatDelay;
   }

   public float getVolume() {
      return this.volume;
   }

   public float getPitch() {
      return this.pitch;
   }

   public ResourceLocation getSoundLocation() {
      return this.positionedSoundLocation;
   }

   public float getZPosF() {
      return this.zPosF;
   }

   public boolean canRepeat() {
      return this.repeat;
   }

   protected PositionedSound(ResourceLocation var1) {
      this.attenuationType = ISound.AttenuationType.LINEAR;
      this.positionedSoundLocation = var1;
   }

   public float getYPosF() {
      return this.yPosF;
   }

   public ISound.AttenuationType getAttenuationType() {
      return this.attenuationType;
   }

   public float getXPosF() {
      return this.xPosF;
   }
}
