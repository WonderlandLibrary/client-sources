package net.minecraft.world.storage;

import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.WorldType;

public class WorldInfo
{
  public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
  
  private long randomSeed;
  
  private WorldType terrainType;
  
  private String generatorOptions;
  
  private int spawnX;
  
  private int spawnY;
  
  private int spawnZ;
  
  private long totalTime;
  
  private long worldTime;
  
  private long lastTimePlayed;
  
  private long sizeOnDisk;
  
  private NBTTagCompound playerTag;
  
  private int dimension;
  
  private String levelName;
  
  private int saveVersion;
  
  private int cleanWeatherTime;
  
  private boolean raining;
  
  private int rainTime;
  
  private boolean thundering;
  
  private int thunderTime;
  
  private WorldSettings.GameType theGameType;
  
  private boolean mapFeaturesEnabled;
  
  private boolean hardcore;
  
  private boolean allowCommands;
  
  private boolean initialized;
  
  private EnumDifficulty difficulty;
  
  private boolean difficultyLocked;
  
  private double borderCenterX;
  
  private double borderCenterZ;
  
  private double borderSize;
  
  private long borderSizeLerpTime;
  
  private double borderSizeLerpTarget;
  
  private double borderSafeZone;
  
  private double borderDamagePerBlock;
  
  private int borderWarningDistance;
  
  private int borderWarningTime;
  
  private GameRules theGameRules;
  private static final String __OBFID = "CL_00000587";
  
  protected WorldInfo()
  {
    terrainType = WorldType.DEFAULT;
    generatorOptions = "";
    borderCenterX = 0.0D;
    borderCenterZ = 0.0D;
    borderSize = 6.0E7D;
    borderSizeLerpTime = 0L;
    borderSizeLerpTarget = 0.0D;
    borderSafeZone = 5.0D;
    borderDamagePerBlock = 0.2D;
    borderWarningDistance = 5;
    borderWarningTime = 15;
    theGameRules = new GameRules();
  }
  
  public WorldInfo(NBTTagCompound nbt)
  {
    terrainType = WorldType.DEFAULT;
    generatorOptions = "";
    borderCenterX = 0.0D;
    borderCenterZ = 0.0D;
    borderSize = 6.0E7D;
    borderSizeLerpTime = 0L;
    borderSizeLerpTarget = 0.0D;
    borderSafeZone = 5.0D;
    borderDamagePerBlock = 0.2D;
    borderWarningDistance = 5;
    borderWarningTime = 15;
    theGameRules = new GameRules();
    randomSeed = nbt.getLong("RandomSeed");
    
    if (nbt.hasKey("generatorName", 8))
    {
      String var2 = nbt.getString("generatorName");
      terrainType = WorldType.parseWorldType(var2);
      
      if (terrainType == null)
      {
        terrainType = WorldType.DEFAULT;
      }
      else if (terrainType.isVersioned())
      {
        int var3 = 0;
        
        if (nbt.hasKey("generatorVersion", 99))
        {
          var3 = nbt.getInteger("generatorVersion");
        }
        
        terrainType = terrainType.getWorldTypeForGeneratorVersion(var3);
      }
      
      if (nbt.hasKey("generatorOptions", 8))
      {
        generatorOptions = nbt.getString("generatorOptions");
      }
    }
    
    theGameType = WorldSettings.GameType.getByID(nbt.getInteger("GameType"));
    
    if (nbt.hasKey("MapFeatures", 99))
    {
      mapFeaturesEnabled = nbt.getBoolean("MapFeatures");
    }
    else
    {
      mapFeaturesEnabled = true;
    }
    
    spawnX = nbt.getInteger("SpawnX");
    spawnY = nbt.getInteger("SpawnY");
    spawnZ = nbt.getInteger("SpawnZ");
    totalTime = nbt.getLong("Time");
    
    if (nbt.hasKey("DayTime", 99))
    {
      worldTime = nbt.getLong("DayTime");
    }
    else
    {
      worldTime = totalTime;
    }
    
    lastTimePlayed = nbt.getLong("LastPlayed");
    sizeOnDisk = nbt.getLong("SizeOnDisk");
    levelName = nbt.getString("LevelName");
    saveVersion = nbt.getInteger("version");
    cleanWeatherTime = nbt.getInteger("clearWeatherTime");
    rainTime = nbt.getInteger("rainTime");
    raining = nbt.getBoolean("raining");
    thunderTime = nbt.getInteger("thunderTime");
    thundering = nbt.getBoolean("thundering");
    hardcore = nbt.getBoolean("hardcore");
    
    if (nbt.hasKey("initialized", 99))
    {
      initialized = nbt.getBoolean("initialized");
    }
    else
    {
      initialized = true;
    }
    
    if (nbt.hasKey("allowCommands", 99))
    {
      allowCommands = nbt.getBoolean("allowCommands");
    }
    else
    {
      allowCommands = (theGameType == WorldSettings.GameType.CREATIVE);
    }
    
    if (nbt.hasKey("Player", 10))
    {
      playerTag = nbt.getCompoundTag("Player");
      dimension = playerTag.getInteger("Dimension");
    }
    
    if (nbt.hasKey("GameRules", 10))
    {
      theGameRules.readGameRulesFromNBT(nbt.getCompoundTag("GameRules"));
    }
    
    if (nbt.hasKey("Difficulty", 99))
    {
      difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
    }
    
    if (nbt.hasKey("DifficultyLocked", 1))
    {
      difficultyLocked = nbt.getBoolean("DifficultyLocked");
    }
    
    if (nbt.hasKey("BorderCenterX", 99))
    {
      borderCenterX = nbt.getDouble("BorderCenterX");
    }
    
    if (nbt.hasKey("BorderCenterZ", 99))
    {
      borderCenterZ = nbt.getDouble("BorderCenterZ");
    }
    
    if (nbt.hasKey("BorderSize", 99))
    {
      borderSize = nbt.getDouble("BorderSize");
    }
    
    if (nbt.hasKey("BorderSizeLerpTime", 99))
    {
      borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
    }
    
    if (nbt.hasKey("BorderSizeLerpTarget", 99))
    {
      borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
    }
    
    if (nbt.hasKey("BorderSafeZone", 99))
    {
      borderSafeZone = nbt.getDouble("BorderSafeZone");
    }
    
    if (nbt.hasKey("BorderDamagePerBlock", 99))
    {
      borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
    }
    
    if (nbt.hasKey("BorderWarningBlocks", 99))
    {
      borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
    }
    
    if (nbt.hasKey("BorderWarningTime", 99))
    {
      borderWarningTime = nbt.getInteger("BorderWarningTime");
    }
  }
  
  public WorldInfo(WorldSettings settings, String name)
  {
    terrainType = WorldType.DEFAULT;
    generatorOptions = "";
    borderCenterX = 0.0D;
    borderCenterZ = 0.0D;
    borderSize = 6.0E7D;
    borderSizeLerpTime = 0L;
    borderSizeLerpTarget = 0.0D;
    borderSafeZone = 5.0D;
    borderDamagePerBlock = 0.2D;
    borderWarningDistance = 5;
    borderWarningTime = 15;
    theGameRules = new GameRules();
    populateFromWorldSettings(settings);
    levelName = name;
    difficulty = DEFAULT_DIFFICULTY;
    initialized = false;
  }
  
  public void populateFromWorldSettings(WorldSettings settings)
  {
    randomSeed = settings.getSeed();
    theGameType = settings.getGameType();
    mapFeaturesEnabled = settings.isMapFeaturesEnabled();
    hardcore = settings.getHardcoreEnabled();
    terrainType = settings.getTerrainType();
    generatorOptions = settings.getWorldName();
    allowCommands = settings.areCommandsAllowed();
  }
  
  public WorldInfo(WorldInfo p_i2159_1_)
  {
    terrainType = WorldType.DEFAULT;
    generatorOptions = "";
    borderCenterX = 0.0D;
    borderCenterZ = 0.0D;
    borderSize = 6.0E7D;
    borderSizeLerpTime = 0L;
    borderSizeLerpTarget = 0.0D;
    borderSafeZone = 5.0D;
    borderDamagePerBlock = 0.2D;
    borderWarningDistance = 5;
    borderWarningTime = 15;
    theGameRules = new GameRules();
    randomSeed = randomSeed;
    terrainType = terrainType;
    generatorOptions = generatorOptions;
    theGameType = theGameType;
    mapFeaturesEnabled = mapFeaturesEnabled;
    spawnX = spawnX;
    spawnY = spawnY;
    spawnZ = spawnZ;
    totalTime = totalTime;
    worldTime = worldTime;
    lastTimePlayed = lastTimePlayed;
    sizeOnDisk = sizeOnDisk;
    playerTag = playerTag;
    dimension = dimension;
    levelName = levelName;
    saveVersion = saveVersion;
    rainTime = rainTime;
    raining = raining;
    thunderTime = thunderTime;
    thundering = thundering;
    hardcore = hardcore;
    allowCommands = allowCommands;
    initialized = initialized;
    theGameRules = theGameRules;
    difficulty = difficulty;
    difficultyLocked = difficultyLocked;
    borderCenterX = borderCenterX;
    borderCenterZ = borderCenterZ;
    borderSize = borderSize;
    borderSizeLerpTime = borderSizeLerpTime;
    borderSizeLerpTarget = borderSizeLerpTarget;
    borderSafeZone = borderSafeZone;
    borderDamagePerBlock = borderDamagePerBlock;
    borderWarningTime = borderWarningTime;
    borderWarningDistance = borderWarningDistance;
  }
  



  public NBTTagCompound getNBTTagCompound()
  {
    NBTTagCompound var1 = new NBTTagCompound();
    updateTagCompound(var1, playerTag);
    return var1;
  }
  



  public NBTTagCompound cloneNBTCompound(NBTTagCompound nbt)
  {
    NBTTagCompound var2 = new NBTTagCompound();
    updateTagCompound(var2, nbt);
    return var2;
  }
  
  private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt)
  {
    nbt.setLong("RandomSeed", randomSeed);
    nbt.setString("generatorName", terrainType.getWorldTypeName());
    nbt.setInteger("generatorVersion", terrainType.getGeneratorVersion());
    nbt.setString("generatorOptions", generatorOptions);
    nbt.setInteger("GameType", theGameType.getID());
    nbt.setBoolean("MapFeatures", mapFeaturesEnabled);
    nbt.setInteger("SpawnX", spawnX);
    nbt.setInteger("SpawnY", spawnY);
    nbt.setInteger("SpawnZ", spawnZ);
    nbt.setLong("Time", totalTime);
    nbt.setLong("DayTime", worldTime);
    nbt.setLong("SizeOnDisk", sizeOnDisk);
    nbt.setLong("LastPlayed", net.minecraft.server.MinecraftServer.getCurrentTimeMillis());
    nbt.setString("LevelName", levelName);
    nbt.setInteger("version", saveVersion);
    nbt.setInteger("clearWeatherTime", cleanWeatherTime);
    nbt.setInteger("rainTime", rainTime);
    nbt.setBoolean("raining", raining);
    nbt.setInteger("thunderTime", thunderTime);
    nbt.setBoolean("thundering", thundering);
    nbt.setBoolean("hardcore", hardcore);
    nbt.setBoolean("allowCommands", allowCommands);
    nbt.setBoolean("initialized", initialized);
    nbt.setDouble("BorderCenterX", borderCenterX);
    nbt.setDouble("BorderCenterZ", borderCenterZ);
    nbt.setDouble("BorderSize", borderSize);
    nbt.setLong("BorderSizeLerpTime", borderSizeLerpTime);
    nbt.setDouble("BorderSafeZone", borderSafeZone);
    nbt.setDouble("BorderDamagePerBlock", borderDamagePerBlock);
    nbt.setDouble("BorderSizeLerpTarget", borderSizeLerpTarget);
    nbt.setDouble("BorderWarningBlocks", borderWarningDistance);
    nbt.setDouble("BorderWarningTime", borderWarningTime);
    
    if (difficulty != null)
    {
      nbt.setByte("Difficulty", (byte)difficulty.getDifficultyId());
    }
    
    nbt.setBoolean("DifficultyLocked", difficultyLocked);
    nbt.setTag("GameRules", theGameRules.writeGameRulesToNBT());
    
    if (playerNbt != null)
    {
      nbt.setTag("Player", playerNbt);
    }
  }
  



  public long getSeed()
  {
    return randomSeed;
  }
  



  public int getSpawnX()
  {
    return spawnX;
  }
  



  public int getSpawnY()
  {
    return spawnY;
  }
  



  public int getSpawnZ()
  {
    return spawnZ;
  }
  
  public long getWorldTotalTime()
  {
    return totalTime;
  }
  



  public long getWorldTime()
  {
    return worldTime;
  }
  
  public long getSizeOnDisk()
  {
    return sizeOnDisk;
  }
  



  public NBTTagCompound getPlayerNBTTagCompound()
  {
    return playerTag;
  }
  



  public void setSpawnX(int p_76058_1_)
  {
    spawnX = p_76058_1_;
  }
  



  public void setSpawnY(int p_76056_1_)
  {
    spawnY = p_76056_1_;
  }
  



  public void setSpawnZ(int p_76087_1_)
  {
    spawnZ = p_76087_1_;
  }
  
  public void incrementTotalWorldTime(long p_82572_1_)
  {
    totalTime = p_82572_1_;
  }
  



  public void setWorldTime(long p_76068_1_)
  {
    worldTime = p_76068_1_;
  }
  
  public void setSpawn(BlockPos spawnPoint)
  {
    spawnX = spawnPoint.getX();
    spawnY = spawnPoint.getY();
    spawnZ = spawnPoint.getZ();
  }
  



  public String getWorldName()
  {
    return levelName;
  }
  
  public void setWorldName(String p_76062_1_)
  {
    levelName = p_76062_1_;
  }
  



  public int getSaveVersion()
  {
    return saveVersion;
  }
  



  public void setSaveVersion(int p_76078_1_)
  {
    saveVersion = p_76078_1_;
  }
  



  public long getLastTimePlayed()
  {
    return lastTimePlayed;
  }
  
  public int func_176133_A()
  {
    return cleanWeatherTime;
  }
  
  public void func_176142_i(int p_176142_1_)
  {
    cleanWeatherTime = p_176142_1_;
  }
  



  public boolean isThundering()
  {
    return thundering;
  }
  



  public void setThundering(boolean p_76069_1_)
  {
    thundering = p_76069_1_;
  }
  



  public int getThunderTime()
  {
    return thunderTime;
  }
  



  public void setThunderTime(int p_76090_1_)
  {
    thunderTime = p_76090_1_;
  }
  



  public boolean isRaining()
  {
    return raining;
  }
  



  public void setRaining(boolean p_76084_1_)
  {
    raining = p_76084_1_;
  }
  



  public int getRainTime()
  {
    return rainTime;
  }
  



  public void setRainTime(int p_76080_1_)
  {
    rainTime = p_76080_1_;
  }
  



  public WorldSettings.GameType getGameType()
  {
    return theGameType;
  }
  



  public boolean isMapFeaturesEnabled()
  {
    return mapFeaturesEnabled;
  }
  
  public void setMapFeaturesEnabled(boolean enabled)
  {
    mapFeaturesEnabled = enabled;
  }
  



  public void setGameType(WorldSettings.GameType type)
  {
    theGameType = type;
  }
  



  public boolean isHardcoreModeEnabled()
  {
    return hardcore;
  }
  
  public void setHardcore(boolean hardcoreIn)
  {
    hardcore = hardcoreIn;
  }
  
  public WorldType getTerrainType()
  {
    return terrainType;
  }
  
  public void setTerrainType(WorldType p_76085_1_)
  {
    terrainType = p_76085_1_;
  }
  
  public String getGeneratorOptions()
  {
    return generatorOptions;
  }
  



  public boolean areCommandsAllowed()
  {
    return allowCommands;
  }
  
  public void setAllowCommands(boolean allow)
  {
    allowCommands = allow;
  }
  



  public boolean isInitialized()
  {
    return initialized;
  }
  



  public void setServerInitialized(boolean initializedIn)
  {
    initialized = initializedIn;
  }
  



  public GameRules getGameRulesInstance()
  {
    return theGameRules;
  }
  
  public double func_176120_C()
  {
    return borderCenterX;
  }
  
  public double func_176126_D()
  {
    return borderCenterZ;
  }
  
  public double func_176137_E()
  {
    return borderSize;
  }
  
  public void func_176145_a(double p_176145_1_)
  {
    borderSize = p_176145_1_;
  }
  
  public long func_176134_F()
  {
    return borderSizeLerpTime;
  }
  
  public void func_176135_e(long p_176135_1_)
  {
    borderSizeLerpTime = p_176135_1_;
  }
  
  public double func_176132_G()
  {
    return borderSizeLerpTarget;
  }
  
  public void func_176118_b(double p_176118_1_)
  {
    borderSizeLerpTarget = p_176118_1_;
  }
  
  public void func_176141_c(double p_176141_1_)
  {
    borderCenterZ = p_176141_1_;
  }
  
  public void func_176124_d(double p_176124_1_)
  {
    borderCenterX = p_176124_1_;
  }
  
  public double func_176138_H()
  {
    return borderSafeZone;
  }
  
  public void func_176129_e(double p_176129_1_)
  {
    borderSafeZone = p_176129_1_;
  }
  
  public double func_176140_I()
  {
    return borderDamagePerBlock;
  }
  
  public void func_176125_f(double p_176125_1_)
  {
    borderDamagePerBlock = p_176125_1_;
  }
  
  public int func_176131_J()
  {
    return borderWarningDistance;
  }
  
  public int func_176139_K()
  {
    return borderWarningTime;
  }
  
  public void func_176122_j(int p_176122_1_)
  {
    borderWarningDistance = p_176122_1_;
  }
  
  public void func_176136_k(int p_176136_1_)
  {
    borderWarningTime = p_176136_1_;
  }
  
  public EnumDifficulty getDifficulty()
  {
    return difficulty;
  }
  
  public void setDifficulty(EnumDifficulty newDifficulty)
  {
    difficulty = newDifficulty;
  }
  
  public boolean isDifficultyLocked()
  {
    return difficultyLocked;
  }
  
  public void setDifficultyLocked(boolean locked)
  {
    difficultyLocked = locked;
  }
  



  public void addToCrashReport(CrashReportCategory category)
  {
    category.addCrashSectionCallable("Level seed", new Callable()
    {
      private static final String __OBFID = "CL_00000588";
      
      public String call() {
        return String.valueOf(getSeed());
      }
    });
    category.addCrashSectionCallable("Level generator", new Callable()
    {
      private static final String __OBFID = "CL_00000589";
      
      public String call() {
        return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(terrainType.getWorldTypeID()), terrainType.getWorldTypeName(), Integer.valueOf(terrainType.getGeneratorVersion()), Boolean.valueOf(mapFeaturesEnabled) });
      }
    });
    category.addCrashSectionCallable("Level generator options", new Callable()
    {
      private static final String __OBFID = "CL_00000590";
      
      public String call() {
        return generatorOptions;
      }
    });
    category.addCrashSectionCallable("Level spawn location", new Callable()
    {
      private static final String __OBFID = "CL_00000591";
      
      public String call() {
        return CrashReportCategory.getCoordinateInfo(spawnX, spawnY, spawnZ);
      }
    });
    category.addCrashSectionCallable("Level time", new Callable()
    {
      private static final String __OBFID = "CL_00000592";
      
      public String call() {
        return String.format("%d game time, %d day time", new Object[] { Long.valueOf(totalTime), Long.valueOf(worldTime) });
      }
    });
    category.addCrashSectionCallable("Level dimension", new Callable()
    {
      private static final String __OBFID = "CL_00000593";
      
      public String call() {
        return String.valueOf(dimension);
      }
    });
    category.addCrashSectionCallable("Level storage version", new Callable()
    {
      private static final String __OBFID = "CL_00000594";
      
      public String call() {
        String var1 = "Unknown?";
        
        try
        {
          switch (saveVersion)
          {
          case 19132: 
            var1 = "McRegion";
            break;
          
          case 19133: 
            var1 = "Anvil";
          }
          
        }
        catch (Throwable localThrowable) {}
        


        return String.format("0x%05X - %s", new Object[] { Integer.valueOf(saveVersion), var1 });
      }
    });
    category.addCrashSectionCallable("Level weather", new Callable()
    {
      private static final String __OBFID = "CL_00000595";
      
      public String call() {
        return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(rainTime), Boolean.valueOf(raining), Integer.valueOf(thunderTime), Boolean.valueOf(thundering) });
      }
    });
    category.addCrashSectionCallable("Level game mode", new Callable()
    {
      private static final String __OBFID = "CL_00000597";
      
      public String call() {
        return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { theGameType.getName(), Integer.valueOf(theGameType.getID()), Boolean.valueOf(hardcore), Boolean.valueOf(allowCommands) });
      }
    });
  }
}
