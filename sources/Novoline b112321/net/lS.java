package net;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import net.WL;
import net.av2;
import net.d3;
import net.gZ;
import net.rE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class lS {
   private static gZ d = gZ.g();
   private static Minecraft b = Minecraft.getMinecraft();
   private static av2 a = (av2)d.d().b(av2.class);
   private static boolean e;
   private static boolean c;

   public static boolean a(WL var0) {
      String[] var1 = d3.e();
      return !b.isSingleplayer() && a.A() != null?a.A().equals(var0):false;
   }

   public static boolean a(rE var0) {
      return a.m().equals(var0);
   }

   public static void a(ServerData var0) {
      String[] var1 = d3.e();
      if(var0.serverIP.toLowerCase().contains("hypixel.net") && !a("hypixel") && !c) {
         e = true;
      }

      e = false;
      a.a(WL.NONE);
   }

   public static boolean c() {
      String[] var0 = d3.e();
      return e || b.isSingleplayer();
   }

   public static boolean b() {
      String[] var0 = d3.e();
      return b.getCurrentServerData() != null && b.getCurrentServerData().serverIP.toLowerCase().contains("blocksmc");
   }

   public static boolean a() {
      String[] var0 = d3.e();
      return b.getCurrentServerData() != null && b.getCurrentServerData().serverIP.toLowerCase().contains("cubecraft");
   }

   public static boolean g() {
      String[] var0 = d3.e();
      return b.getCurrentServerData() != null && b.getCurrentServerData().serverIP.toLowerCase().contains("mineman");
   }

   public static boolean a(String var0) {
      d3.e();
      Path var2 = Paths.get(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts", new String[0]);
      if(Files.notExists(var2, new LinkOption[0])) {
         return false;
      } else {
         Path var10000 = var2;

         try {
            return Files.lines(var10000).anyMatch(lS::lambda$hostModified$0);
         } catch (IOException var4) {
            b.getNetHandler().getNetworkManager().closeChannel(new ChatComponentText(EnumChatFormatting.RED + "Connection error! Contact staff"));
            return true;
         }
      }
   }

   public static void a(boolean var0) {
      c = var0;
   }

   public static boolean f() {
      return c;
   }

   public static int d() {
      return a.z() / 20;
   }

   public static int e() {
      return a.z();
   }

   private static boolean lambda$hostModified$0(String var0, String var1) {
      return var1.toLowerCase().contains(var0);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
