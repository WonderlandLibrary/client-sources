package cc.novoline.utils;

import cc.novoline.Novoline;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.utils.Channels;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ServerUtils {
   private static Novoline novoline = Novoline.getInstance();
   private static Minecraft mc = Minecraft.getInstance();
   private static ClickGUI clickGUI = (ClickGUI)novoline.getModuleManager().getModule(ClickGUI.class);
   private static boolean hypixel;
   private static boolean fakeHypixel;

   public static boolean serverIs(Servers var0) {
      String[] var1 = Timer.e();
      return !mc.isSingleplayer() && clickGUI.getCurrentServer() != null?clickGUI.getCurrentServer().equals(var0):false;
   }

   public static boolean channelIs(Channels var0) {
      return clickGUI.getChannel().equals(var0);
   }

   public static void checkHypixel(ServerData var0) {
      String[] var1 = Timer.e();
      if(var0.serverIP.toLowerCase().contains("hypixel.net") && !hostModified("hypixel") && !fakeHypixel) {
         hypixel = true;
      }

      hypixel = false;
      clickGUI.setCurrentServer(Servers.NONE);
   }

   public static boolean isHypixel() {
      String[] var0 = Timer.e();
      return hypixel || mc.isSingleplayer();
   }

   public static boolean b() {
      String[] var0 = Timer.e();
      return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("blocksmc");
   }

   public static boolean a() {
      String[] var0 = Timer.e();
      return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("cubecraft");
   }

   public static boolean g() {
      String[] var0 = Timer.e();
      return mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP.toLowerCase().contains("mineman");
   }

   public static boolean hostModified(String var0) {
      Timer.e();
      Path var2 = Paths.get(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts", new String[0]);
      if(Files.notExists(var2, new LinkOption[0])) {
         return false;
      } else {
         Path var10000 = var2;

         try {
            return Files.lines(var10000).anyMatch(ServerUtils::lambda$hostModified$0);
         } catch (IOException var4) {
            mc.getNetHandler().getNetworkManager().closeChannel(new ChatComponentText(EnumChatFormatting.RED + "Connection error! Contact staff"));
            return true;
         }
      }
   }

   public static void setFakeHypixel(boolean var0) {
      fakeHypixel = var0;
   }

   public static boolean isFakeHypixel() {
      return fakeHypixel;
   }

   public static int inGameSeconds() {
      return clickGUI.getTicks() / 20;
   }

   public static int inGameTicks() {
      return clickGUI.getTicks();
   }

   private static boolean lambda$hostModified$0(String var0, String var1) {
      return var1.toLowerCase().contains(var0);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
