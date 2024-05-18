package net.minecraft.world.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.WorldType;

public class DerivedWorldInfo
  extends WorldInfo
{
  private final WorldInfo theWorldInfo;
  private static final String __OBFID = "CL_00000584";
  
  public DerivedWorldInfo(WorldInfo p_i2145_1_)
  {
    theWorldInfo = p_i2145_1_;
  }
  



  public NBTTagCompound getNBTTagCompound()
  {
    return theWorldInfo.getNBTTagCompound();
  }
  



  public NBTTagCompound cloneNBTCompound(NBTTagCompound nbt)
  {
    return theWorldInfo.cloneNBTCompound(nbt);
  }
  



  public long getSeed()
  {
    return theWorldInfo.getSeed();
  }
  



  public int getSpawnX()
  {
    return theWorldInfo.getSpawnX();
  }
  



  public int getSpawnY()
  {
    return theWorldInfo.getSpawnY();
  }
  



  public int getSpawnZ()
  {
    return theWorldInfo.getSpawnZ();
  }
  
  public long getWorldTotalTime()
  {
    return theWorldInfo.getWorldTotalTime();
  }
  



  public long getWorldTime()
  {
    return theWorldInfo.getWorldTime();
  }
  
  public long getSizeOnDisk()
  {
    return theWorldInfo.getSizeOnDisk();
  }
  



  public NBTTagCompound getPlayerNBTTagCompound()
  {
    return theWorldInfo.getPlayerNBTTagCompound();
  }
  



  public String getWorldName()
  {
    return theWorldInfo.getWorldName();
  }
  



  public int getSaveVersion()
  {
    return theWorldInfo.getSaveVersion();
  }
  



  public long getLastTimePlayed()
  {
    return theWorldInfo.getLastTimePlayed();
  }
  



  public boolean isThundering()
  {
    return theWorldInfo.isThundering();
  }
  



  public int getThunderTime()
  {
    return theWorldInfo.getThunderTime();
  }
  



  public boolean isRaining()
  {
    return theWorldInfo.isRaining();
  }
  



  public int getRainTime()
  {
    return theWorldInfo.getRainTime();
  }
  



  public WorldSettings.GameType getGameType()
  {
    return theWorldInfo.getGameType();
  }
  



  public void setSpawnX(int p_76058_1_) {}
  



  public void setSpawnY(int p_76056_1_) {}
  



  public void setSpawnZ(int p_76087_1_) {}
  



  public void incrementTotalWorldTime(long p_82572_1_) {}
  


  public void setWorldTime(long p_76068_1_) {}
  


  public void setSpawn(BlockPos spawnPoint) {}
  


  public void setWorldName(String p_76062_1_) {}
  


  public void setSaveVersion(int p_76078_1_) {}
  


  public void setThundering(boolean p_76069_1_) {}
  


  public void setThunderTime(int p_76090_1_) {}
  


  public void setRaining(boolean p_76084_1_) {}
  


  public void setRainTime(int p_76080_1_) {}
  


  public boolean isMapFeaturesEnabled()
  {
    return theWorldInfo.isMapFeaturesEnabled();
  }
  



  public boolean isHardcoreModeEnabled()
  {
    return theWorldInfo.isHardcoreModeEnabled();
  }
  
  public WorldType getTerrainType()
  {
    return theWorldInfo.getTerrainType();
  }
  


  public void setTerrainType(WorldType p_76085_1_) {}
  

  public boolean areCommandsAllowed()
  {
    return theWorldInfo.areCommandsAllowed();
  }
  


  public void setAllowCommands(boolean allow) {}
  

  public boolean isInitialized()
  {
    return theWorldInfo.isInitialized();
  }
  



  public void setServerInitialized(boolean initializedIn) {}
  



  public GameRules getGameRulesInstance()
  {
    return theWorldInfo.getGameRulesInstance();
  }
  
  public EnumDifficulty getDifficulty()
  {
    return theWorldInfo.getDifficulty();
  }
  
  public void setDifficulty(EnumDifficulty newDifficulty) {}
  
  public boolean isDifficultyLocked()
  {
    return theWorldInfo.isDifficultyLocked();
  }
  
  public void setDifficultyLocked(boolean locked) {}
}
