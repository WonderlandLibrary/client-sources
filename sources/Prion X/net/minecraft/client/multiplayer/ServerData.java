package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;













public class ServerData
{
  public String serverName;
  public String serverIP;
  public String populationInfo;
  public String serverMOTD;
  public long pingToServer;
  public int version = 47;
  

  public String gameVersion = "1.8";
  public boolean field_78841_f;
  public String playerList;
  private ServerResourceMode resourceMode;
  private String serverIcon;
  private static final String __OBFID = "CL_00000890";
  
  public ServerData(String p_i1193_1_, String p_i1193_2_)
  {
    resourceMode = ServerResourceMode.PROMPT;
    serverName = p_i1193_1_;
    serverIP = p_i1193_2_;
  }
  



  public NBTTagCompound getNBTCompound()
  {
    NBTTagCompound var1 = new NBTTagCompound();
    var1.setString("name", serverName);
    var1.setString("ip", serverIP);
    
    if (serverIcon != null)
    {
      var1.setString("icon", serverIcon);
    }
    
    if (resourceMode == ServerResourceMode.ENABLED)
    {
      var1.setBoolean("acceptTextures", true);
    }
    else if (resourceMode == ServerResourceMode.DISABLED)
    {
      var1.setBoolean("acceptTextures", false);
    }
    
    return var1;
  }
  
  public ServerResourceMode getResourceMode()
  {
    return resourceMode;
  }
  
  public void setResourceMode(ServerResourceMode mode)
  {
    resourceMode = mode;
  }
  



  public static ServerData getServerDataFromNBTCompound(NBTTagCompound nbtCompound)
  {
    ServerData var1 = new ServerData(nbtCompound.getString("name"), nbtCompound.getString("ip"));
    
    if (nbtCompound.hasKey("icon", 8))
    {
      var1.setBase64EncodedIconData(nbtCompound.getString("icon"));
    }
    
    if (nbtCompound.hasKey("acceptTextures", 1))
    {
      if (nbtCompound.getBoolean("acceptTextures"))
      {
        var1.setResourceMode(ServerResourceMode.ENABLED);
      }
      else
      {
        var1.setResourceMode(ServerResourceMode.DISABLED);
      }
      
    }
    else {
      var1.setResourceMode(ServerResourceMode.PROMPT);
    }
    
    return var1;
  }
  



  public String getBase64EncodedIconData()
  {
    return serverIcon;
  }
  
  public void setBase64EncodedIconData(String icon)
  {
    serverIcon = icon;
  }
  
  public void copyFrom(ServerData serverDataIn)
  {
    serverIP = serverIP;
    serverName = serverName;
    setResourceMode(serverDataIn.getResourceMode());
    serverIcon = serverIcon;
  }
  
  public static enum ServerResourceMode
  {
    ENABLED("ENABLED", 0, "enabled"), 
    DISABLED("DISABLED", 1, "disabled"), 
    PROMPT("PROMPT", 2, "prompt");
    
    private final IChatComponent motd;
    private static final ServerResourceMode[] $VALUES = { ENABLED, DISABLED, PROMPT };
    private static final String __OBFID = "CL_00001833";
    
    private ServerResourceMode(String p_i1053_1_, int p_i1053_2_, String p_i1053_3_)
    {
      motd = new ChatComponentTranslation("addServer.resourcePack." + p_i1053_3_, new Object[0]);
    }
    
    public IChatComponent getMotd()
    {
      return motd;
    }
  }
}
