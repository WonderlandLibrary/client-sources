package net;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class tT {
   public static PositionedSoundRecord a(ResourceLocation var0, float var1) {
      return PositionedSoundRecord.create(var0, var1);
   }

   public static PositionedSoundRecord a(ResourceLocation var0) {
      return PositionedSoundRecord.create(var0);
   }

   public static PositionedSoundRecord a(ResourceLocation var0, float var1, float var2, float var3) {
      return PositionedSoundRecord.create(var0, var1, var2, var3);
   }
}
