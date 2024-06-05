package net.optifine;

import net.acE;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.ClearWater;
import net.optifine.Config;
import net.optifine.MatchBlock;

public class WorldServerOF extends WorldServer {
   private MinecraftServer mcServer;

   public WorldServerOF(MinecraftServer var1, ISaveHandler var2, WorldInfo var3, int var4, Profiler var5) {
      super(var1, var2, var3, var4, var5);
      this.mcServer = var1;
   }

   public void tick() {
      MatchBlock.b();
      super.tick();
      if(!Config.aQ()) {
         this.fixWorldTime();
      }

      if(Config.waterOpacityChanged) {
         Config.waterOpacityChanged = false;
         ClearWater.updateWaterOpacity(Config.getGameSettings(), this);
      }

   }

   protected void updateWeather() {
      acE[] var1 = MatchBlock.b();
      if(!Config.isWeatherEnabled()) {
         this.fixWorldWeather();
      }

      super.updateWeather();
   }

   private void fixWorldWeather() {
      acE[] var1 = MatchBlock.b();
      if(this.worldInfo.isRaining() || this.worldInfo.isThundering()) {
         this.worldInfo.setRainTime(0);
         this.worldInfo.setRaining(false);
         this.setRainStrength(0.0F);
         this.worldInfo.setThunderTime(0);
         this.worldInfo.setThundering(false);
         this.setThunderStrength(0.0F);
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(2, 0.0F));
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(7, 0.0F));
         this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S2BPacketChangeGameState(8, 0.0F));
      }

   }

   private void fixWorldTime() {
      acE[] var1 = MatchBlock.b();
      if(this.worldInfo.getGameType().getID() == 1) {
         long var2 = this.getWorldTime();
         long var4 = var2 % 24000L;
         if(Config.bd()) {
            if(var4 <= 1000L) {
               this.setWorldTime(var2 - var4 + 1001L);
            }

            if(var4 >= 11000L) {
               this.setWorldTime(var2 - var4 + 24001L);
            }
         }

         if(Config.a_()) {
            if(var4 <= 14000L) {
               this.setWorldTime(var2 - var4 + 14001L);
            }

            if(var4 >= 22000L) {
               this.setWorldTime(var2 - var4 + 24000L + 14001L);
            }
         }
      }

   }
}
