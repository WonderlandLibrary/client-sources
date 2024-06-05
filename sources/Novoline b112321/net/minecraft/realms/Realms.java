package net.minecraft.realms;

import com.google.common.util.concurrent.ListenableFuture;
import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.Session;
import net.minecraft.world.WorldSettings$GameType;

public class Realms {
   public static boolean isTouchScreen() {
      return Minecraft.getInstance().gameSettings.touchscreen;
   }

   public static Proxy getProxy() {
      return Minecraft.getInstance().getProxy();
   }

   public static String k() {
      Session var0 = Minecraft.getInstance().getSession();
      return null;
   }

   public static String f() {
      Session var0 = Minecraft.getInstance().getSession();
      return null;
   }

   public static long currentTimeMillis() {
      return Minecraft.getSystemTime();
   }

   public static String getSessionId() {
      return Minecraft.getInstance().getSession().getSessionID();
   }

   public static String getUUID() {
      return Minecraft.getInstance().getSession().getPlayerID();
   }

   public static String getName() {
      return Minecraft.getInstance().getSession().getUsername();
   }

   public static String uuidToName(String var0) {
      return Minecraft.getInstance().getSessionService().fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(var0), (String)null), false).getName();
   }

   public static void setScreen(RealmsScreen var0) {
      Minecraft.getInstance().displayGuiScreen(var0.getProxy());
   }

   public static String getGameDirectoryPath() {
      return Minecraft.getInstance().mcDataDir.getAbsolutePath();
   }

   public static int survivalId() {
      return WorldSettings$GameType.SURVIVAL.getID();
   }

   public static int creativeId() {
      return WorldSettings$GameType.CREATIVE.getID();
   }

   public static int adventureId() {
      return WorldSettings$GameType.ADVENTURE.getID();
   }

   public static int spectatorId() {
      return WorldSettings$GameType.SPECTATOR.getID();
   }

   public static void setConnectedToRealms(boolean var0) {
      Minecraft.getInstance().func_181537_a(var0);
   }

   public static ListenableFuture downloadResourcePack(String var0, String var1) {
      ListenableFuture var2 = Minecraft.getInstance().getResourcePackRepository().downloadResourcePack(var0, var1);
      return var2;
   }

   public static void clearResourcePack() {
      Minecraft.getInstance().getResourcePackRepository().func_148529_f();
   }
}
