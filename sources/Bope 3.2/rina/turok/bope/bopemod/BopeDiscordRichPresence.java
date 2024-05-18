package rina.turok.bope.bopemod;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class BopeDiscordRichPresence {
   public final DiscordRPC discord_rpc;
   public DiscordRichPresence discord_presence;
   public final Minecraft mc = Minecraft.getMinecraft();
   public String detail_option_1;
   public String detail_option_2;
   public String detail_option_3;
   public String detail_option_4;
   public String state_option_1;
   public String state_option_2;
   public String state_option_3;
   public String state_option_4;

   public BopeDiscordRichPresence(String tag) {
      this.discord_rpc = DiscordRPC.INSTANCE;
      this.discord_presence = new DiscordRichPresence();
      this.detail_option_1 = "";
      this.detail_option_2 = "";
      this.detail_option_3 = "";
      this.detail_option_4 = "";
      this.state_option_1 = "";
      this.state_option_2 = "";
      this.state_option_3 = "";
      this.state_option_4 = "";
   }

   public void stop() {
      this.discord_rpc.Discord_Shutdown();
   }

   public void run() {
      this.discord_presence = new DiscordRichPresence();
      DiscordEventHandlers handler_ = new DiscordEventHandlers();
      this.discord_rpc.Discord_Initialize("722173364227014756", handler_, true, "");
      this.discord_presence.largeImageText = "B.O.P.E 0.3";
      this.discord_presence.largeImageKey = "splash";
      (new Thread(() -> {
         while(!Thread.currentThread().isInterrupted()) {
            try {
               if (this.mc.world == null) {
                  this.detail_option_1 = "";
                  this.detail_option_2 = "main menu";
               } else {
                  if (this.mc.player != null) {
                     this.detail_option_1 = this.mc.player.getName() + " - ";
                  } else {
                     this.detail_option_1 = "";
                  }

                  if (this.mc.isIntegratedServerRunning()) {
                     this.detail_option_2 = "survival";
                  } else {
                     this.detail_option_2 = this.mc.getCurrentServerData().serverIP;
                  }
               }

               String detail = this.detail_option_1 + this.detail_option_2 + this.detail_option_3 + this.detail_option_4;
               String state = this.state_option_1 + this.state_option_2 + this.state_option_3 + this.state_option_4;
               this.discord_rpc.Discord_RunCallbacks();
               this.discord_presence.details = detail;
               this.discord_presence.state = state;
               this.discord_rpc.Discord_UpdatePresence(this.discord_presence);
            } catch (Exception var4) {
               var4.printStackTrace();
            }

            try {
               Thread.sleep(4000L);
            } catch (InterruptedException var3) {
               var3.printStackTrace();
            }
         }

      }, "RPC-Callback-Handler")).start();
   }

   public String set(String presume) {
      return " " + presume;
   }
}
