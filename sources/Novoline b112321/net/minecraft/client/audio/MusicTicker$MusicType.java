package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public enum MusicTicker$MusicType {
   MENU(new ResourceLocation("minecraft:music.menu"), 20, 600),
   GAME(new ResourceLocation("minecraft:music.game"), 12000, 24000),
   CREATIVE(new ResourceLocation("minecraft:music.game.creative"), 1200, 3600),
   CREDITS(new ResourceLocation("minecraft:music.game.end.credits"), Integer.MAX_VALUE, Integer.MAX_VALUE),
   NETHER(new ResourceLocation("minecraft:music.game.nether"), 1200, 3600),
   END_BOSS(new ResourceLocation("minecraft:music.game.end.dragon"), 0, 0),
   END(new ResourceLocation("minecraft:music.game.end"), 6000, 24000);

   private final ResourceLocation musicLocation;
   private final int minDelay;
   private final int maxDelay;
   private static final MusicTicker$MusicType[] $VALUES = new MusicTicker$MusicType[]{MENU, GAME, CREATIVE, CREDITS, NETHER, END_BOSS, END};

   private MusicTicker$MusicType(ResourceLocation var3, int var4, int var5) {
      this.musicLocation = var3;
      this.minDelay = var4;
      this.maxDelay = var5;
   }

   public ResourceLocation getMusicLocation() {
      return this.musicLocation;
   }

   public int getMinDelay() {
      return this.minDelay;
   }

   public int getMaxDelay() {
      return this.maxDelay;
   }
}
