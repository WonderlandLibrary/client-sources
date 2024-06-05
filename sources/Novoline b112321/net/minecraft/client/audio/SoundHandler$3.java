package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundList$SoundEntry$Type;

// $FF: synthetic class
class SoundHandler$3 {
   static final int[] $SwitchMap$net$minecraft$client$audio$SoundList$SoundEntry$Type = new int[SoundList$SoundEntry$Type.values().length];

   static {
      try {
         $SwitchMap$net$minecraft$client$audio$SoundList$SoundEntry$Type[SoundList$SoundEntry$Type.FILE.ordinal()] = 1;
      } catch (NoSuchFieldError var2) {
         ;
      }

      try {
         $SwitchMap$net$minecraft$client$audio$SoundList$SoundEntry$Type[SoundList$SoundEntry$Type.SOUND_EVENT.ordinal()] = 2;
      } catch (NoSuchFieldError var1) {
         ;
      }

   }
}
