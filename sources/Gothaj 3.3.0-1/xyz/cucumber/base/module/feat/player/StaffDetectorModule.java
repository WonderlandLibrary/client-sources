package xyz.cucumber.base.module.feat.player;

import java.util.Arrays;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventReceivePacket;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.StringSettings;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Detects staff who joined",
   name = "Staff Detector"
)
public class StaffDetectorModule extends Mod {
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"QPlay", "BlocksMC", "SG"});
   private BooleanSettings autoLobby = new BooleanSettings("Auto HUB", true);
   private StringSettings lobbyCommand = new StringSettings("Command", () -> this.autoLobby.isEnabled(), "/hub");
   private boolean detected = false;
   private String[] qplay = new String[]{
      "Profikk",
      "_Spetty_",
      "Ayessha",
      "ItzTadeas",
      "_razorRalfcz",
      "Dastrokk",
      "Linuuus",
      "KwenT_",
      "M1che_",
      "GodLikeKubiss",
      "SeptunLover",
      "Veverka14",
      "DeKoN_CZ",
      "H0t_Mamka",
      "Hrib",
      "Majulka",
      "DarkBanan",
      "iArthurr_",
      "matyraptycz",
      "stepanpanik",
      "Sunnyyyyyyy",
      "RuzovaTabletkaa",
      "dulisek231"
   };

   public StaffDetectorModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.autoLobby, this.lobbyCommand});
   }

   @EventListener
   public void onRecieve(EventReceivePacket e) {
      this.detected = false;
      if (e.getPacket() instanceof S14PacketEntity) {
         S14PacketEntity packet = (S14PacketEntity)e.getPacket();
         if (!this.detected) {
            Entity player = packet.getEntity(this.mc.theWorld);
            if (player == null) {
               Client.INSTANCE.getCommandManager().sendChatMessage("Â§cStaff detected: Â§7Unknown");
               if (this.autoLobby.isEnabled()) {
                  this.mc.thePlayer.sendChatMessage(this.lobbyCommand.getString());
               }

               return;
            }

            if (Arrays.asList(this.qplay).contains(player.getName())) {
               Client.INSTANCE.getCommandManager().sendChatMessage("Â§cStaff detected: Â§7" + player.getName());
               if (this.autoLobby.isEnabled()) {
                  this.mc.thePlayer.sendChatMessage(this.lobbyCommand.getString());
               }

               this.detected = true;
            }
         }
      } else if (e.getPacket() instanceof S1DPacketEntityEffect) {
         S1DPacketEntityEffect packet = (S1DPacketEntityEffect)e.getPacket();
         if (!this.detected) {
            Entity playerx = this.mc.theWorld.getEntityByID(packet.getEntityId());
            if (playerx == null) {
               Client.INSTANCE.getCommandManager().sendChatMessage("Â§cStaff detected: Â§7Unknown");
               if (this.autoLobby.isEnabled()) {
                  this.mc.thePlayer.sendChatMessage(this.lobbyCommand.getString());
               }

               return;
            }

            Client.INSTANCE.getCommandManager().sendChatMessage("Â§cStaff detected: Â§7" + playerx.getName());
            if (this.autoLobby.isEnabled()) {
               this.mc.thePlayer.sendChatMessage(this.lobbyCommand.getString());
            }

            this.detected = true;
         }
      }
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
      this.detected = false;
   }
}
