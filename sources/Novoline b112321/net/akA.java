package net;

import net.minecraft.network.NetworkSystem;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings$GameType;

public class akA {
   private static int b;

   public static boolean j(IntegratedServer var0) {
      return var0.getPublic();
   }

   public static void m(IntegratedServer var0) {
      var0.stopServer();
   }

   public static void l(IntegratedServer var0) {
      var0.startServerThread();
   }

   public static boolean f(IntegratedServer var0) {
      return var0.serverIsInRunLoop();
   }

   public static String e(IntegratedServer var0) {
      return var0.getUserMessage();
   }

   public static NetworkSystem g(IntegratedServer var0) {
      return var0.getNetworkSystem();
   }

   public static boolean k(IntegratedServer var0) {
      return var0.isAnvilFileSet();
   }

   public static void c(IntegratedServer var0) {
      var0.initiateShutdown();
   }

   public static void a(IntegratedServer var0) {
      var0.setStaticInstance();
   }

   public static String d(IntegratedServer var0) {
      return var0.getServerOwner();
   }

   public static int i(IntegratedServer var0) {
      return var0.getTickCounter();
   }

   public static PlayerUsageSnooper h(IntegratedServer var0) {
      return var0.getPlayerUsageSnooper();
   }

   public static WorldServer a(IntegratedServer var0, int var1) {
      return var0.worldServerForDimension(var1);
   }

   public static String a(IntegratedServer var0, WorldSettings$GameType var1, boolean var2) {
      return var0.shareToLAN(var1, var2);
   }

   public static ServerConfigurationManager b(IntegratedServer var0) {
      return var0.getConfigurationManager();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 38;
   }

   static {
      if(b() != 0) {
         b(126);
      }

   }
}
