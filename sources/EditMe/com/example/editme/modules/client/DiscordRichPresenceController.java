package com.example.editme.modules.client;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.example.editme.EditmeMod;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.Objects;
import net.minecraft.client.multiplayer.ServerData;

@Module.Info(
   name = "DiscordRPC",
   category = Module.Category.CLIENT
)
public class DiscordRichPresenceController extends Module {
   private Setting showIP = this.register(SettingsManager.b("Show IP", true));
   private static String details = "";
   private static final DiscordRPC rpc;
   private static String playerName;
   public static DiscordRichPresence presence;
   private boolean started;
   private static String state = "";
   private static boolean hasStarted;

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         playerName = mc.field_71439_g.func_70005_c_();
      }

      if ((Boolean)this.showIP.getValue()) {
         try {
            state = ((ServerData)Objects.requireNonNull(mc.func_147104_D())).field_78845_b;
         } catch (Exception var2) {
            state = "my main menu";
         }
      } else {
         state = "a server";
      }

   }

   private static void lambda$start$0(int var0, String var1) {
      EditmeMod.log.info("Discord RPC disconnected");
   }

   private static void setRpcFromSettingsNonInt() {
      while(!Thread.currentThread().isInterrupted()) {
         try {
            rpc.Discord_RunCallbacks();
            String var0 = " | ";
            presence.details = details;
            presence.state = state;
            presence.largeImageKey = "editme";
            presence.largeImageText = "EDITME";
            EditmeMod.log.info(String.valueOf((new StringBuilder()).append("Discord RPC player icon set to ").append(playerName.toLowerCase())));
            presence.smallImageKey = playerName.toLowerCase();
            presence.smallImageText = playerName.toLowerCase();
            rpc.Discord_UpdatePresence(presence);
         } catch (Exception var2) {
            var2.printStackTrace();
         }

         try {
            Thread.sleep(4000L);
         } catch (InterruptedException var1) {
            var1.printStackTrace();
         }
      }

   }

   static {
      rpc = DiscordRPC.INSTANCE;
      presence = new DiscordRichPresence();
      hasStarted = false;
   }

   private static void setRpcFromSettings() {
      presence.details = details;
      presence.state = state;
      presence.largeImageKey = "editme";
      presence.largeImageText = "EDITME";
      EditmeMod.log.info(String.valueOf((new StringBuilder()).append("Discord RPC player icon set to ").append(playerName.toLowerCase())));
      presence.smallImageKey = playerName.toLowerCase();
      presence.smallImageText = playerName.toLowerCase();
      rpc.Discord_UpdatePresence(presence);
   }

   public static void start() {
      EditmeMod.log.info("Starting Discord RPC");
      if (!hasStarted) {
         hasStarted = true;
         DiscordEventHandlers var0 = new DiscordEventHandlers();
         var0.disconnected = DiscordRichPresenceController::lambda$start$0;
         rpc.Discord_Initialize("715582355171180554", var0, true, "");
         presence.startTimestamp = System.currentTimeMillis() / 1000L;
         setRpcFromSettings();
         (new Thread(DiscordRichPresenceController::setRpcFromSettingsNonInt, "Discord-RPC-Callback-Handler")).start();
         EditmeMod.log.info("Discord RPC initialised successfully");
      }
   }

   public void onEnable() {
      playerName = mc.func_110432_I().func_111285_a();
      details = "Supporting trans right on";
      start();
   }
}
