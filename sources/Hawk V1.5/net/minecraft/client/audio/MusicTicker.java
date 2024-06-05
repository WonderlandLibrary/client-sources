package net.minecraft.client.audio;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MusicTicker implements IUpdatePlayerListBox {
   private static final String __OBFID = "CL_00001138";
   private int timeUntilNextMusic = 100;
   private final Minecraft mc;
   private ISound currentMusic;
   private final Random rand = new Random();

   public void update() {
      MusicTicker.MusicType var1 = this.mc.getAmbientMusicType();
      if (this.currentMusic != null) {
         if (!var1.getMusicLocation().equals(this.currentMusic.getSoundLocation())) {
            this.mc.getSoundHandler().stopSound(this.currentMusic);
            this.timeUntilNextMusic = MathHelper.getRandomIntegerInRange(this.rand, 0, var1.getMinDelay() / 2);
         }

         if (!this.mc.getSoundHandler().isSoundPlaying(this.currentMusic)) {
            this.currentMusic = null;
            this.timeUntilNextMusic = Math.min(MathHelper.getRandomIntegerInRange(this.rand, var1.getMinDelay(), var1.getMaxDelay()), this.timeUntilNextMusic);
         }
      }

      if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
         this.currentMusic = PositionedSoundRecord.createPositionedSoundRecord(var1.getMusicLocation());
         this.mc.getSoundHandler().playSound(this.currentMusic);
         this.timeUntilNextMusic = Integer.MAX_VALUE;
      }

   }

   public MusicTicker(Minecraft var1) {
      this.mc = var1;
   }

   public static enum MusicType {
      CREATIVE("CREATIVE", 2, new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
      MENU("MENU", 0, new ResourceLocation("minecraft:music.menu"), 20, 600);

      private final ResourceLocation musicLocation;
      GAME("GAME", 1, new ResourceLocation("minecraft:music.game"), 12000, 24000),
      END("END", 6, new ResourceLocation("minecraft:music.game.end"), 6000, 24000);

      private final int maxDelay;
      private final int minDelay;
      private static final MusicTicker.MusicType[] $VALUES = new MusicTicker.MusicType[]{MENU, GAME, CREATIVE, CREDITS, NETHER, END_BOSS, END};
      private static final String __OBFID = "CL_00001139";
      CREDITS("CREDITS", 3, new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE),
      END_BOSS("END_BOSS", 5, new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
      NETHER("NETHER", 4, new ResourceLocation("minecraft:music.game.nether"), 1200, 3600);

      private static final MusicTicker.MusicType[] ENUM$VALUES = new MusicTicker.MusicType[]{MENU, GAME, CREATIVE, CREDITS, NETHER, END_BOSS, END};

      public int getMaxDelay() {
         return this.maxDelay;
      }

      public ResourceLocation getMusicLocation() {
         return this.musicLocation;
      }

      private MusicType(String var3, int var4, ResourceLocation var5, int var6, int var7) {
         this.musicLocation = var5;
         this.minDelay = var6;
         this.maxDelay = var7;
      }

      public int getMinDelay() {
         return this.minDelay;
      }
   }
}
