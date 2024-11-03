package vestige.util.misc;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import vestige.util.IMinecraft;

public class AudioUtil implements IMinecraft {
   public static void buttonClick() {
      mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   public static void playSound(String sound) {
      playSound(sound, 1.0F, 1.0F);
   }

   public static void playSound(String sound, float volume, float pitch) {
      mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation(sound), pitch));
   }
}
