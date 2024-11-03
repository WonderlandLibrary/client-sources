package net.augustus.modules.misc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;
import net.augustus.events.EventReadPacket;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.utils.ChatUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S38PacketPlayerListItem;

public class StaffDetector extends Module {
   private final ArrayList<UUID> playersInRound = new ArrayList<>();
   public BooleanValue isInRound = new BooleanValue(1, "IsInRound", this, true);
   private WorldClient worldClient;

   public StaffDetector() {
      super("StaffDetector", new Color(80, 162, 181), Categorys.MISC);
   }

   @Override
   public void onEnable() {
      if (mc.theWorld != null) {
         this.worldClient = mc.theWorld;
         this.playersInRound.clear();
      }
   }

   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      if (packet instanceof S38PacketPlayerListItem) {
         S38PacketPlayerListItem playerListItem = (S38PacketPlayerListItem)packet;
         switch(playerListItem.getAction()) {
            case UPDATE_LATENCY:
               if (!this.isInRound.getBoolean()) {
                  this.playersInRound.clear();
               }

               for(S38PacketPlayerListItem.AddPlayerData addPlayerData : playerListItem.getPlayers()) {
                  if (!mc.getNetHandler().getPlayerMapInfo().containsKey(addPlayerData.getProfile().getId())
                     && !this.playersInRound.contains(addPlayerData.getProfile().getId())) {
                     ChatUtil.sendChat("Staff detectecd: " + addPlayerData.getProfile().getName());
                  }
               }
               break;
            case ADD_PLAYER:
               for(S38PacketPlayerListItem.AddPlayerData addPlayerData : playerListItem.getPlayers()) {
                  if (!this.playersInRound.contains(addPlayerData.getProfile().getId()) && addPlayerData.getDisplayName() == null) {
                     this.playersInRound.add(addPlayerData.getProfile().getId());
                  }
               }
         }
      }

      if (packet instanceof S08PacketPlayerPosLook) {
         if (this.worldClient != null && this.worldClient != mc.theWorld) {
            this.playersInRound.clear();
         }

         this.worldClient = mc.theWorld;
      }
   }
}
