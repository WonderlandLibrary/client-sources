package net.minecraft.realms;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings.GameType;

public class Realms
{
  private static final String __OBFID = "CL_00001892";
  
  public Realms() {}
  
  public static boolean isTouchScreen()
  {
    return getMinecraftgameSettings.touchscreen;
  }
  
  public static java.net.Proxy getProxy()
  {
    return Minecraft.getMinecraft().getProxy();
  }
  
  public static String sessionId()
  {
    Session var0 = Minecraft.getMinecraft().getSession();
    return var0 == null ? null : var0.getSessionID();
  }
  
  public static String userName()
  {
    Session var0 = Minecraft.getMinecraft().getSession();
    return var0 == null ? null : var0.getUsername();
  }
  
  public static long currentTimeMillis()
  {
    return Minecraft.getSystemTime();
  }
  
  public static String getSessionId()
  {
    return Minecraft.getMinecraft().getSession().getSessionID();
  }
  
  public static String getName()
  {
    return Minecraft.getMinecraft().getSession().getUsername();
  }
  
  public static String uuidToName(String p_uuidToName_0_)
  {
    return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(com.mojang.util.UUIDTypeAdapter.fromString(p_uuidToName_0_), null), false).getName();
  }
  
  public static void setScreen(RealmsScreen p_setScreen_0_)
  {
    Minecraft.getMinecraft().displayGuiScreen(p_setScreen_0_.getProxy());
  }
  
  public static String getGameDirectoryPath()
  {
    return getMinecraftmcDataDir.getAbsolutePath();
  }
  
  public static int survivalId()
  {
    return WorldSettings.GameType.SURVIVAL.getID();
  }
  
  public static int creativeId()
  {
    return WorldSettings.GameType.CREATIVE.getID();
  }
  
  public static int adventureId()
  {
    return WorldSettings.GameType.ADVENTURE.getID();
  }
}
