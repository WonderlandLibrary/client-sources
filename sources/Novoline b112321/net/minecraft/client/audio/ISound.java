package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound$AttenuationType;
import net.minecraft.util.ResourceLocation;

public interface ISound {
   ResourceLocation getSoundLocation();

   boolean canRepeat();

   int getRepeatDelay();

   float getVolume();

   float getPitch();

   float getXPosF();

   float getYPosF();

   float getZPosF();

   ISound$AttenuationType getAttenuationType();
}
