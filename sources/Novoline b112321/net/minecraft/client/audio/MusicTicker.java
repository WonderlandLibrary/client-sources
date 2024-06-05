package net.minecraft.client.audio;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker$MusicType;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;

public class MusicTicker implements ITickable {
   private final Random rand = new Random();
   private final Minecraft mc;
   private ISound currentMusic;
   private int timeUntilNextMusic = 100;

   public MusicTicker(Minecraft var1) {
      this.mc = var1;
   }

   public void update() {
      MusicTicker$MusicType var1 = this.mc.getAmbientMusicType();
      if(this.currentMusic != null) {
         if(!var1.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, var1.getMinDelay() / 2);
         }

         if(!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
            this.currentMusic = null;
            this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, var1.getMinDelay(), var1.getMaxDelay()), this.timeUntilNextMusic);
         }
      }

      if(this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
         this.func_181558_a(var1);
      }

   }

   public void func_181558_a(MusicTicker$MusicType var1) {
      this.currentMusic = PositionedSoundRecord.create(var1.getMusicLocation());
      this.mc.getSoundHandler().playSound(this.currentMusic);
      this.timeUntilNextMusic = Integer.MAX_VALUE;
   }

   public void func_181557_a() {
      if(this.currentMusic != null) {
         this.mc.getSoundHandler().stopSound(this.currentMusic);
         this.currentMusic = null;
         this.timeUntilNextMusic = 0;
      }

   }
}
