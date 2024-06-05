package net;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings$GameType;

public class amr {
   public static GameProfile l(NetworkPlayerInfo var0) {
      return var0.getGameProfile();
   }

   public static WorldSettings$GameType b(NetworkPlayerInfo var0) {
      return var0.getGameType();
   }

   public static boolean c(NetworkPlayerInfo var0) {
      return var0.hasLocationSkin();
   }

   public static ScorePlayerTeam g(NetworkPlayerInfo var0) {
      return var0.getPlayerTeam();
   }

   public static void a(NetworkPlayerInfo var0, IChatComponent var1) {
      var0.setDisplayName(var1);
   }

   public static IChatComponent f(NetworkPlayerInfo var0) {
      return var0.getDisplayName();
   }

   public static ResourceLocation i(NetworkPlayerInfo var0) {
      return var0.getLocationSkin();
   }

   public static int j(NetworkPlayerInfo var0) {
      return var0.getResponseTime();
   }

   public static long e(NetworkPlayerInfo var0) {
      return var0.k();
   }

   public static int h(NetworkPlayerInfo var0) {
      return var0.h();
   }

   public static void b(NetworkPlayerInfo var0, long var1) {
      var0.c(var1);
   }

   public static void a(NetworkPlayerInfo var0, long var1) {
      var0.a(var1);
   }

   public static long k(NetworkPlayerInfo var0) {
      return var0.g();
   }

   public static void b(NetworkPlayerInfo var0, int var1) {
      var0.b(var1);
   }

   public static void a(NetworkPlayerInfo var0, int var1) {
      var0.c(var1);
   }

   public static void c(NetworkPlayerInfo var0, long var1) {
      var0.b(var1);
   }

   public static int a(NetworkPlayerInfo var0) {
      return var0.e();
   }

   public static long d(NetworkPlayerInfo var0) {
      return var0.o();
   }
}
