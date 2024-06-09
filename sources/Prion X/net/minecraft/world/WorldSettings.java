package net.minecraft.world;

import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.storage.WorldInfo;














public final class WorldSettings
{
  private final long seed;
  private final GameType theGameType;
  private final boolean mapFeaturesEnabled;
  private final boolean hardcoreEnabled;
  private final WorldType terrainType;
  private boolean commandsAllowed;
  private boolean bonusChestEnabled;
  private String worldName;
  private static final String __OBFID = "CL_00000147";
  
  public WorldSettings(long seedIn, GameType gameType, boolean enableMapFeatures, boolean hardcoreMode, WorldType worldTypeIn)
  {
    worldName = "";
    seed = seedIn;
    theGameType = gameType;
    mapFeaturesEnabled = enableMapFeatures;
    hardcoreEnabled = hardcoreMode;
    terrainType = worldTypeIn;
  }
  
  public WorldSettings(WorldInfo info)
  {
    this(info.getSeed(), info.getGameType(), info.isMapFeaturesEnabled(), info.isHardcoreModeEnabled(), info.getTerrainType());
  }
  



  public WorldSettings enableBonusChest()
  {
    bonusChestEnabled = true;
    return this;
  }
  



  public WorldSettings enableCommands()
  {
    commandsAllowed = true;
    return this;
  }
  
  public WorldSettings setWorldName(String name)
  {
    worldName = name;
    return this;
  }
  



  public boolean isBonusChestEnabled()
  {
    return bonusChestEnabled;
  }
  



  public long getSeed()
  {
    return seed;
  }
  



  public GameType getGameType()
  {
    return theGameType;
  }
  



  public boolean getHardcoreEnabled()
  {
    return hardcoreEnabled;
  }
  



  public boolean isMapFeaturesEnabled()
  {
    return mapFeaturesEnabled;
  }
  
  public WorldType getTerrainType()
  {
    return terrainType;
  }
  



  public boolean areCommandsAllowed()
  {
    return commandsAllowed;
  }
  



  public static GameType getGameTypeById(int id)
  {
    return GameType.getByID(id);
  }
  
  public String getWorldName()
  {
    return worldName;
  }
  
  public static enum GameType
  {
    NOT_SET("NOT_SET", 0, -1, ""), 
    SURVIVAL("SURVIVAL", 1, 0, "survival"), 
    CREATIVE("CREATIVE", 2, 1, "creative"), 
    ADVENTURE("ADVENTURE", 3, 2, "adventure"), 
    SPECTATOR("SPECTATOR", 4, 3, "spectator");
    
    int id;
    String name;
    private static final GameType[] $VALUES = { NOT_SET, SURVIVAL, CREATIVE, ADVENTURE, SPECTATOR };
    private static final String __OBFID = "CL_00000148";
    
    private GameType(String p_i1956_1_, int p_i1956_2_, int typeId, String nameIn)
    {
      id = typeId;
      name = nameIn;
    }
    
    public int getID()
    {
      return id;
    }
    
    public String getName()
    {
      return name;
    }
    
    public void configurePlayerCapabilities(PlayerCapabilities capabilities)
    {
      if (this == CREATIVE)
      {
        allowFlying = true;
        isCreativeMode = true;
        disableDamage = true;
      }
      else if (this == SPECTATOR)
      {
        allowFlying = true;
        isCreativeMode = false;
        disableDamage = true;
        isFlying = true;
      }
      else
      {
        allowFlying = false;
        isCreativeMode = false;
        disableDamage = false;
        isFlying = false;
      }
      
      allowEdit = (!isAdventure());
    }
    
    public boolean isAdventure()
    {
      return (this == ADVENTURE) || (this == SPECTATOR);
    }
    
    public boolean isCreative()
    {
      return this == CREATIVE;
    }
    
    public boolean isSurvivalOrAdventure()
    {
      return (this == SURVIVAL) || (this == ADVENTURE);
    }
    
    public static GameType getByID(int idIn)
    {
      GameType[] var1 = values();
      int var2 = var1.length;
      
      for (int var3 = 0; var3 < var2; var3++)
      {
        GameType var4 = var1[var3];
        
        if (id == idIn)
        {
          return var4;
        }
      }
      
      return SURVIVAL;
    }
    
    public static GameType getByName(String p_77142_0_)
    {
      GameType[] var1 = values();
      int var2 = var1.length;
      
      for (int var3 = 0; var3 < var2; var3++)
      {
        GameType var4 = var1[var3];
        
        if (name.equals(p_77142_0_))
        {
          return var4;
        }
      }
      
      return SURVIVAL;
    }
  }
}
