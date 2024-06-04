package net.minecraft.server.integrated;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ThreadLanServerPing;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldServerMulti;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import optifine.Reflector;
import optifine.ReflectorClass;
import optifine.ReflectorMethod;
import optifine.WorldServerOF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer
{
  private static final Logger logger = ;
  
  private final Minecraft mc;
  
  private final WorldSettings theWorldSettings;
  private boolean isGamePaused;
  private boolean isPublic;
  private ThreadLanServerPing lanServerPing;
  
  public IntegratedServer(Minecraft mcIn)
  {
    super(mcIn.getProxy(), new File(mcDataDir, USER_CACHE_FILE.getName()));
    mc = mcIn;
    theWorldSettings = null;
  }
  
  public IntegratedServer(Minecraft mcIn, String folderName, String worldName, WorldSettings settings)
  {
    super(new File(mcDataDir, "saves"), mcIn.getProxy(), new File(mcDataDir, USER_CACHE_FILE.getName()));
    setServerOwner(mcIn.getSession().getUsername());
    setFolderName(folderName);
    setWorldName(worldName);
    setDemo(mcIn.isDemo());
    canCreateBonusChest(settings.isBonusChestEnabled());
    setBuildLimit(256);
    setConfigManager(new IntegratedPlayerList(this));
    mc = mcIn;
    theWorldSettings = (isDemo() ? DemoWorldServer.demoWorldSettings : settings);
  }
  
  protected net.minecraft.command.ServerCommandManager createNewCommandManager()
  {
    return new IntegratedServerCommandManager();
  }
  
  protected void loadAllWorlds(String p_71247_1_, String p_71247_2_, long seed, WorldType type, String p_71247_6_)
  {
    convertMapIfNeeded(p_71247_1_);
    ISaveHandler var7 = getActiveAnvilConverter().getSaveLoader(p_71247_1_, true);
    setResourcePackFromWorld(getFolderName(), var7);
    WorldInfo var8 = var7.loadWorldInfo();
    
    if (Reflector.DimensionManager.exists())
    {
      WorldServer var9 = isDemo() ? (WorldServer)new DemoWorldServer(this, var7, var8, 0, theProfiler).init() : (WorldServer)new WorldServerOF(this, var7, var8, 0, theProfiler).init();
      var9.initialize(theWorldSettings);
      Integer[] var10 = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
      Integer[] arr$ = var10;
      int len$ = var10.length;
      
      for (int i$ = 0; i$ < len$; i$++)
      {
        int dim = arr$[i$].intValue();
        WorldServer world = dim == 0 ? var9 : (WorldServer)new WorldServerMulti(this, var7, dim, var9, theProfiler).init();
        world.addWorldAccess(new WorldManager(this, world));
        
        if (!isSinglePlayer())
        {
          world.getWorldInfo().setGameType(getGameType());
        }
        
        if (Reflector.EventBus.exists())
        {
          Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { world });
        }
      }
      
      getConfigurationManager().setPlayerManager(new WorldServer[] { var9 });
      
      if (var9.getWorldInfo().getDifficulty() == null)
      {
        setDifficultyForAllWorlds(mc.gameSettings.difficulty);
      }
    }
    else
    {
      worldServers = new WorldServer[3];
      timeOfLastDimensionTick = new long[worldServers.length][100];
      setResourcePackFromWorld(getFolderName(), var7);
      
      if (var8 == null)
      {
        var8 = new WorldInfo(theWorldSettings, p_71247_2_);
      }
      else
      {
        var8.setWorldName(p_71247_2_);
      }
      
      for (int var16 = 0; var16 < worldServers.length; var16++)
      {
        byte var17 = 0;
        
        if (var16 == 1)
        {
          var17 = -1;
        }
        
        if (var16 == 2)
        {
          var17 = 1;
        }
        
        if (var16 == 0)
        {
          if (isDemo())
          {
            worldServers[var16] = ((WorldServer)new DemoWorldServer(this, var7, var8, var17, theProfiler).init());
          }
          else
          {
            worldServers[var16] = ((WorldServer)new WorldServerOF(this, var7, var8, var17, theProfiler).init());
          }
          
          worldServers[var16].initialize(theWorldSettings);
        }
        else
        {
          worldServers[var16] = ((WorldServer)new WorldServerMulti(this, var7, var17, worldServers[0], theProfiler).init());
        }
        
        worldServers[var16].addWorldAccess(new WorldManager(this, worldServers[var16]));
      }
      
      getConfigurationManager().setPlayerManager(worldServers);
      
      if (worldServers[0].getWorldInfo().getDifficulty() == null)
      {
        setDifficultyForAllWorlds(mc.gameSettings.difficulty);
      }
    }
    
    initialWorldChunkLoad();
  }
  


  protected boolean startServer()
    throws IOException
  {
    logger.info("Starting integrated minecraft server version 1.8");
    setOnlineMode(true);
    setCanSpawnAnimals(true);
    setCanSpawnNPCs(true);
    setAllowPvp(true);
    setAllowFlight(true);
    logger.info("Generating keypair");
    setKeyPair(net.minecraft.util.CryptManager.generateKeyPair());
    

    if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists())
    {
      Object inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
      
      if (!Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this }))
      {
        return false;
      }
    }
    
    loadAllWorlds(getFolderName(), getWorldName(), theWorldSettings.getSeed(), theWorldSettings.getTerrainType(), theWorldSettings.getWorldName());
    setMOTD(getServerOwner() + " - " + worldServers[0].getWorldInfo().getWorldName());
    
    if (Reflector.FMLCommonHandler_handleServerStarting.exists())
    {
      Object inst = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
      
      if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == Boolean.TYPE)
      {
        return Reflector.callBoolean(inst, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
      }
      
      Reflector.callVoid(inst, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
    }
    
    return true;
  }
  



  public void tick()
  {
    boolean var1 = isGamePaused;
    isGamePaused = ((Minecraft.getMinecraft().getNetHandler() != null) && (Minecraft.getMinecraft().isGamePaused()));
    
    if ((!var1) && (isGamePaused))
    {
      logger.info("Saving and pausing game...");
      getConfigurationManager().saveAllPlayerData();
      saveAllWorlds(false);
    }
    
    if (isGamePaused)
    {
      Queue var10 = futureTaskQueue;
      Queue var3 = futureTaskQueue;
      
      synchronized (futureTaskQueue)
      {
        while (!futureTaskQueue.isEmpty())
        {
          try
          {
            if (Reflector.FMLCommonHandler_callFuture.exists())
            {
              Reflector.callVoid(Reflector.FMLCommonHandler_callFuture, new Object[] { (FutureTask)futureTaskQueue.poll() });
            }
            else
            {
              ((FutureTask)futureTaskQueue.poll()).run();
            }
          }
          catch (Throwable var8)
          {
            logger.fatal(var8);
          }
        }
      }
    }
    

    super.tick();
    
    if (mc.gameSettings.renderDistanceChunks != getConfigurationManager().getViewDistance())
    {
      logger.info("Changing view distance to {}, from {}", new Object[] { Integer.valueOf(mc.gameSettings.renderDistanceChunks), Integer.valueOf(getConfigurationManager().getViewDistance()) });
      getConfigurationManager().setViewDistance(mc.gameSettings.renderDistanceChunks);
    }
    
    if (mc.theWorld != null)
    {
      WorldInfo var101 = worldServers[0].getWorldInfo();
      WorldInfo var11 = mc.theWorld.getWorldInfo();
      
      if ((!var101.isDifficultyLocked()) && (var11.getDifficulty() != var101.getDifficulty()))
      {
        logger.info("Changing difficulty to {}, from {}", new Object[] { var11.getDifficulty(), var101.getDifficulty() });
        setDifficultyForAllWorlds(var11.getDifficulty());
      }
      else if ((var11.isDifficultyLocked()) && (!var101.isDifficultyLocked()))
      {
        logger.info("Locking difficulty to {}", new Object[] { var11.getDifficulty() });
        WorldServer[] var4 = worldServers;
        int var5 = var4.length;
        
        for (int var6 = 0; var6 < var5; var6++)
        {
          WorldServer var7 = var4[var6];
          
          if (var7 != null)
          {
            var7.getWorldInfo().setDifficultyLocked(true);
          }
        }
      }
    }
  }
  

  public boolean canStructuresSpawn()
  {
    return false;
  }
  
  public WorldSettings.GameType getGameType()
  {
    return theWorldSettings.getGameType();
  }
  



  public EnumDifficulty getDifficulty()
  {
    return mc.theWorld == null ? mc.gameSettings.difficulty : mc.theWorld.getWorldInfo().getDifficulty();
  }
  



  public boolean isHardcore()
  {
    return theWorldSettings.getHardcoreEnabled();
  }
  
  public File getDataDirectory()
  {
    return mc.mcDataDir;
  }
  
  public boolean isDedicatedServer()
  {
    return false;
  }
  



  protected void finalTick(CrashReport report)
  {
    mc.crashed(report);
  }
  



  public CrashReport addServerInfoToCrashReport(CrashReport report)
  {
    report = super.addServerInfoToCrashReport(report);
    report.getCategory().addCrashSectionCallable("Type", new Callable()
    {
      public String call()
      {
        return "Integrated Server (map_client.txt)";
      }
    });
    report.getCategory().addCrashSectionCallable("Is Modded", new Callable()
    {
      public String call()
      {
        String var1 = net.minecraft.client.ClientBrandRetriever.getClientModName();
        
        if (!var1.equals("vanilla"))
        {
          return "Definitely; Client brand changed to '" + var1 + "'";
        }
        

        var1 = getServerModName();
        return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : !var1.equals("vanilla") ? "Definitely; Server brand changed to '" + var1 + "'" : "Probably not. Jar signature remains and both client + server brands are untouched.";
      }
      
    });
    return report;
  }
  
  public void setDifficultyForAllWorlds(EnumDifficulty difficulty)
  {
    super.setDifficultyForAllWorlds(difficulty);
    
    if (mc.theWorld != null)
    {
      mc.theWorld.getWorldInfo().setDifficulty(difficulty);
    }
  }
  
  public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper)
  {
    super.addServerStatsToSnooper(playerSnooper);
    playerSnooper.addClientStat("snooper_partner", mc.getPlayerUsageSnooper().getUniqueID());
  }
  



  public boolean isSnooperEnabled()
  {
    return Minecraft.getMinecraft().isSnooperEnabled();
  }
  



  public String shareToLAN(WorldSettings.GameType type, boolean allowCheats)
  {
    try
    {
      int var6 = -1;
      
      try
      {
        var6 = HttpUtil.getSuitableLanPort();
      }
      catch (IOException localIOException1) {}
      



      if (var6 <= 0)
      {
        var6 = 25564;
      }
      
      getNetworkSystem().addLanEndpoint(null, var6);
      logger.info("Started on " + var6);
      isPublic = true;
      lanServerPing = new ThreadLanServerPing(getMOTD(), var6);
      lanServerPing.start();
      getConfigurationManager().func_152604_a(type);
      getConfigurationManager().setCommandsAllowedForAll(allowCheats);
      return var6;
    }
    catch (IOException var61) {}
    
    return null;
  }
  




  public void stopServer()
  {
    super.stopServer();
    
    if (lanServerPing != null)
    {
      lanServerPing.interrupt();
      lanServerPing = null;
    }
  }
  



  public void initiateShutdown()
  {
    com.google.common.util.concurrent.Futures.getUnchecked(addScheduledTask(new Runnable()
    {
      public void run()
      {
        ArrayList var1 = com.google.common.collect.Lists.newArrayList(getConfigurationManager().playerEntityList);
        Iterator var2 = var1.iterator();
        
        while (var2.hasNext())
        {
          EntityPlayerMP var3 = (EntityPlayerMP)var2.next();
          getConfigurationManager().playerLoggedOut(var3);
        }
      }
    }));
    super.initiateShutdown();
    
    if (lanServerPing != null)
    {
      lanServerPing.interrupt();
      lanServerPing = null;
    }
  }
  
  public void func_175592_a()
  {
    func_175585_v();
  }
  



  public boolean getPublic()
  {
    return isPublic;
  }
  



  public void setGameType(WorldSettings.GameType gameMode)
  {
    getConfigurationManager().func_152604_a(gameMode);
  }
  



  public boolean isCommandBlockEnabled()
  {
    return true;
  }
  
  public int getOpPermissionLevel()
  {
    return 4;
  }
}
