package net.minecraft.realms;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings;

public class Realms {
   private static final String __OBFID = "CL_00001892";

   public static String uuidToName(String var0) {
      return Minecraft.getMinecraft().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(var0), (String)null), false).getName();
   }

   public static int creativeId() {
      return WorldSettings.GameType.CREATIVE.getID();
   }

   public static String userName() {
      Session var0 = Minecraft.getMinecraft().getSession();
      return var0 == null ? null : var0.getUsername();
   }

   public static boolean isTouchScreen() {
      return Minecraft.getMinecraft().gameSettings.touchscreen;
   }

   public static String getSessionId() {
      return Minecraft.getMinecraft().getSession().getSessionID();
   }

   public static void setScreen(RealmsScreen var0) {
      Minecraft.getMinecraft().displayGuiScreen(var0.getProxy());
   }

   public static String getName() {
      return Minecraft.getMinecraft().getSession().getUsername();
   }

   public static int adventureId() {
      return WorldSettings.GameType.ADVENTURE.getID();
   }

   public static Proxy getProxy() {
      return Minecraft.getMinecraft().getProxy();
   }

   public static String sessionId() {
      Session var0 = Minecraft.getMinecraft().getSession();
      return var0 == null ? null : var0.getSessionID();
   }

   public static String getGameDirectoryPath() {
      return Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
   }

   public static long currentTimeMillis() {
      return Minecraft.getSystemTime();
   }

   public static int survivalId() {
      return WorldSettings.GameType.SURVIVAL.getID();
   }
}
