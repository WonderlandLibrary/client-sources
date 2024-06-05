package net.minecraft.server.integrated;

import com.google.common.util.concurrent.Futures;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import net.aOO;
import net.aQG;
import net.aia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedPlayerList;
import net.minecraft.server.integrated.IntegratedServerCommandManager;
import net.minecraft.util.CryptManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.Reflector;
import net.optifine.WorldServerOF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Minecraft mc;
   private final WorldSettings theWorldSettings;
   private boolean isGamePaused;
   private boolean isPublic;
   private ThreadLanServerPing lanServerPing;

   public IntegratedServer(Minecraft var1) {
      super(var1.getProxy(), new File(var1.mcDataDir, USER_CACHE_FILE.getName()));
      this.mc = var1;
      this.theWorldSettings = null;
   }

   public IntegratedServer(Minecraft var1, String var2, String var3, WorldSettings var4) {
      super(new File(var1.mcDataDir, "saves"), var1.getProxy(), new File(var1.mcDataDir, USER_CACHE_FILE.getName()));
      this.setServerOwner(var1.getSession().getUsername());
      this.setFolderName(var2);
      this.setWorldName(var3);
      this.setDemo(var1.isDemo());
      this.canCreateBonusChest(var4.isBonusChestEnabled());
      this.setBuildLimit(256);
      this.setConfigManager(new IntegratedPlayerList(this));
      this.mc = var1;
      this.theWorldSettings = this.isDemo()?DemoWorldServer.demoWorldSettings:var4;
   }

   protected ServerCommandManager createNewCommandManager() {
      return new IntegratedServerCommandManager();
   }

   protected void loadAllWorlds(String var1, String var2, long var3, WorldType var5, String var6) {
      this.convertMapIfNeeded(var1);
      ISaveHandler var7 = this.getActiveAnvilConverter().getSaveLoader(var1, true);
      this.setResourcePackFromWorld(this.getFolderName(), var7);
      WorldInfo var8 = var7.loadWorldInfo();
      if(Reflector.DimensionManager.exists()) {
         WorldServer var9 = this.isDemo()?(WorldServer)(new DemoWorldServer(this, var7, var8, 0, this.theProfiler)).init():(WorldServer)(new WorldServerOF(this, var7, var8, 0, this.theProfiler)).init();
         var9.initialize(this.theWorldSettings);
         Integer[] var10 = (Integer[])((Integer[])Reflector.f(Reflector.ae, new Object[0]));
         int var11 = var10.length;

         for(int var12 = 0; var12 < var11; ++var12) {
            int var13 = var10[var12].intValue();
            var9.addWorldAccess(new WorldManager(this, var9));
            if(!this.isSinglePlayer()) {
               var9.getWorldInfo().setGameType(this.getGameType());
            }

            if(Reflector.EventBus.exists()) {
               Reflector.a(Reflector.bP, new Object[]{var9});
            }
         }

         this.getConfigurationManager().setPlayerManager(new WorldServer[]{var9});
         if(var9.getWorldInfo().getDifficulty() == null) {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
         }
      } else {
         this.worldServers = new WorldServer[3];
         this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
         this.setResourcePackFromWorld(this.getFolderName(), var7);
         var8 = new WorldInfo(this.theWorldSettings, var2);

         for(int var16 = 0; var16 < this.worldServers.length; ++var16) {
            byte var17 = 0;
            if(var16 == 1) {
               var17 = -1;
            }

            if(var16 == 2) {
               var17 = 1;
            }

            if(this.isDemo()) {
               this.worldServers[var16] = (WorldServer)(new DemoWorldServer(this, var7, var8, var17, this.theProfiler)).init();
            } else {
               this.worldServers[var16] = (WorldServer)(new WorldServerOF(this, var7, var8, var17, this.theProfiler)).init();
            }

            this.worldServers[var16].initialize(this.theWorldSettings);
            this.worldServers[var16].addWorldAccess(new WorldManager(this, this.worldServers[var16]));
         }

         this.getConfigurationManager().setPlayerManager(this.worldServers);
         if(this.worldServers[0].getWorldInfo().getDifficulty() == null) {
            this.setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
         }
      }

      this.initialWorldChunkLoad();
   }

   protected boolean startServer() {
      LOGGER.info("Starting integrated minecraft server version 1.8.8");
      this.setOnlineMode(true);
      this.setCanSpawnAnimals(true);
      this.setCanSpawnNPCs(true);
      this.setAllowPvp(true);
      this.setAllowFlight(true);
      LOGGER.info("Generating keypair");
      this.setKeyPair(CryptManager.generateKeyPair());
      if(Reflector.O.d()) {
         Object var1 = Reflector.f(Reflector.dk, new Object[0]);
         if(!Reflector.d(var1, Reflector.O, new Object[]{this})) {
            return false;
         }
      }

      this.loadAllWorlds(this.getFolderName(), this.getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getWorldName());
      this.setMOTD(this.getServerOwner() + " - " + this.worldServers[0].getWorldInfo().getWorldName());
      if(Reflector.dt.d()) {
         Object var2 = Reflector.f(Reflector.dk, new Object[0]);
         if(Reflector.dt.b() == Boolean.TYPE) {
            return Reflector.d(var2, Reflector.dt, new Object[]{this});
         }

         Reflector.g(var2, Reflector.dt, new Object[]{this});
      }

      return true;
   }

   public void tick() {
      // $FF: Couldn't be decompiled
   }

   public boolean canStructuresSpawn() {
      return false;
   }

   public WorldSettings$GameType getGameType() {
      return this.theWorldSettings.getGameType();
   }

   public void setGameType(WorldSettings$GameType var1) {
      this.getConfigurationManager().setGameType(var1);
   }

   public EnumDifficulty getDifficulty() {
      return this.mc.world == null?this.mc.gameSettings.difficulty:this.mc.world.getWorldInfo().getDifficulty();
   }

   public boolean isHardcore() {
      return this.theWorldSettings.getHardcoreEnabled();
   }

   public boolean func_181034_q() {
      return true;
   }

   public boolean func_183002_r() {
      return true;
   }

   public File getDataDirectory() {
      return this.mc.mcDataDir;
   }

   public boolean func_181035_ah() {
      return false;
   }

   public boolean isDedicatedServer() {
      return false;
   }

   protected void finalTick(CrashReport var1) {
      this.mc.crashed(var1);
   }

   public CrashReport addServerInfoToCrashReport(CrashReport var1) {
      var1 = super.addServerInfoToCrashReport(var1);
      var1.getCategory().addCrashSectionCallable("Type", new aia(this));
      var1.getCategory().addCrashSectionCallable("Is Modded", new aQG(this));
      return var1;
   }

   public void setDifficultyForAllWorlds(EnumDifficulty var1) {
      super.setDifficultyForAllWorlds(var1);
      if(this.mc.world != null) {
         this.mc.world.getWorldInfo().setDifficulty(var1);
      }

   }

   public void addServerStatsToSnooper(PlayerUsageSnooper var1) {
      super.addServerStatsToSnooper(var1);
      var1.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
   }

   public boolean isSnooperEnabled() {
      return Minecraft.getInstance().isSnooperEnabled();
   }

   public String shareToLAN(WorldSettings$GameType var1, boolean var2) {
      try {
         int var3 = -1;

         try {
            var3 = HttpUtil.getSuitableLanPort();
         } catch (IOException var5) {
            ;
         }

         var3 = 25564;
         this.getNetworkSystem().addLanEndpoint((InetAddress)null, var3);
         LOGGER.info("Started on " + var3);
         this.isPublic = true;
         this.lanServerPing = new ThreadLanServerPing(this.getMOTD(), var3 + "");
         this.lanServerPing.start();
         this.getConfigurationManager().setGameType(var1);
         this.getConfigurationManager().setCommandsAllowedForAll(var2);
         return var3 + "";
      } catch (IOException var6) {
         return null;
      }
   }

   public void stopServer() {
      super.stopServer();
      if(this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }

   }

   public void initiateShutdown() {
      Futures.getUnchecked(this.addScheduledTask(new aOO(this)));
      super.initiateShutdown();
      if(this.lanServerPing != null) {
         this.lanServerPing.interrupt();
         this.lanServerPing = null;
      }

   }

   public void setStaticInstance() {
      this.setInstance();
   }

   public boolean getPublic() {
      return this.isPublic;
   }

   public boolean isCommandBlockEnabled() {
      return true;
   }

   public int getOpPermissionLevel() {
      return 4;
   }
}
