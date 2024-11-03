package net.augustus.utils;

import java.util.ArrayList;
import java.util.Locale;
import net.augustus.Augustus;
import net.augustus.events.EventSendPacket;
import me.jDev.xenza.files.FileManager;
import net.augustus.utils.interfaces.MC;
import net.lenni0451.eventapi.manager.EventManager;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;

public class ClickRecordingUtil implements MC {
   private final ArrayList<Integer> linkedList = new ArrayList<>();
   private boolean isDigging = false;
   private long lastTime = System.currentTimeMillis();

   public void startRecording() {
      this.linkedList.clear();
      this.isDigging = false;
      EventManager.register(this);
      this.lastTime = System.currentTimeMillis();
   }

   public void stopRecording() {
      EventManager.unregister(this);
      FileManager<Integer> fileManager = new FileManager<>();
      fileManager.saveFile(Augustus.getInstance().getName().toLowerCase(Locale.ROOT) + "/clickpattern", "ClickingPattern.json", this.linkedList);
      this.linkedList.clear();
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      if (packet instanceof C07PacketPlayerDigging) {
         C07PacketPlayerDigging digging = (C07PacketPlayerDigging)packet;
         if (digging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
            this.isDigging = true;
         }

         if (digging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK
            || digging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
            this.isDigging = false;
         }
      }

      if (packet instanceof C0APacketAnimation) {
         if (mc.thePlayer.isSneaking() && !this.isDigging) {
            int diff = (int)(System.currentTimeMillis() - this.lastTime);
            this.linkedList.add(diff);
         }

         this.lastTime = System.currentTimeMillis();
      }
   }
}
