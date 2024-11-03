package vestige.handler.packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.FinalPacketSendEvent;
import vestige.util.IMinecraft;
import vestige.util.network.PacketUtil;

public class PacketBlinkHandler implements IMinecraft {
   private final CopyOnWriteArrayList<Packet> packetsQueue = new CopyOnWriteArrayList();
   private boolean blinkingMove;
   private boolean blinkingPing;
   private boolean blinkingOther;
   private boolean clearedPackets;

   public PacketBlinkHandler() {
      Flap.instance.getEventManager().register(this);
   }

   @Listener(20)
   public void onFinalSend(FinalPacketSendEvent event) {
      if (mc.thePlayer != null && mc.thePlayer.ticksExisted >= 5) {
         this.clearedPackets = false;
      } else if (!this.clearedPackets) {
         this.packetsQueue.clear();
         this.stopAll();
         this.clearedPackets = true;
      }

      Packet packet = event.getPacket();
      if (!event.isCancelled()) {
         if (this.isMove(packet)) {
            if (this.blinkingMove) {
               event.setCancelled(true);
               this.packetsQueue.add(packet);
            }
         } else if (this.isPing(packet)) {
            if (this.blinkingPing) {
               event.setCancelled(true);
               this.packetsQueue.add(packet);
            }
         } else if (this.blinkingOther) {
            event.setCancelled(true);
            this.packetsQueue.add(packet);
         }
      }

   }

   public void startBlinkingMove() {
      this.blinkingMove = true;
   }

   public void releaseMove() {
      if (!this.packetsQueue.isEmpty()) {
         ArrayList<Packet> toRemove = new ArrayList();
         Iterator var2 = this.packetsQueue.iterator();

         Packet p;
         while(var2.hasNext()) {
            p = (Packet)var2.next();
            if (this.isMove(p)) {
               PacketUtil.sendPacketNoEvent(p);
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (Packet)var2.next();
               this.packetsQueue.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void stopBlinkingMove() {
      this.releaseMove();
      this.blinkingMove = false;
   }

   public void startBlinkingPing() {
      this.blinkingPing = true;
   }

   public void releasePing() {
      if (!this.packetsQueue.isEmpty()) {
         ArrayList<Packet> toRemove = new ArrayList();
         Iterator var2 = this.packetsQueue.iterator();

         Packet p;
         while(var2.hasNext()) {
            p = (Packet)var2.next();
            if (this.isPing(p)) {
               PacketUtil.sendPacketNoEvent(p);
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (Packet)var2.next();
               this.packetsQueue.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void stopBlinkingPing() {
      this.releasePing();
      this.blinkingPing = false;
   }

   public void startBlinkingOther() {
      this.blinkingOther = true;
   }

   public void releaseOther() {
      if (!this.packetsQueue.isEmpty()) {
         ArrayList<Packet> toRemove = new ArrayList();
         Iterator var2 = this.packetsQueue.iterator();

         Packet p;
         while(var2.hasNext()) {
            p = (Packet)var2.next();
            if (!this.isMove(p) && !this.isPing(p)) {
               PacketUtil.sendPacketNoEvent(p);
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (Packet)var2.next();
               this.packetsQueue.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void stopBlinkingOther() {
      this.releaseOther();
      this.blinkingOther = false;
   }

   public void clearMove() {
      if (!this.packetsQueue.isEmpty()) {
         ArrayList<Packet> toRemove = new ArrayList();
         Iterator var2 = this.packetsQueue.iterator();

         Packet p;
         while(var2.hasNext()) {
            p = (Packet)var2.next();
            if (this.isMove(p)) {
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (Packet)var2.next();
               this.packetsQueue.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void clearPing() {
      if (!this.packetsQueue.isEmpty()) {
         ArrayList<Packet> toRemove = new ArrayList();
         Iterator var2 = this.packetsQueue.iterator();

         Packet p;
         while(var2.hasNext()) {
            p = (Packet)var2.next();
            if (this.isPing(p)) {
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (Packet)var2.next();
               this.packetsQueue.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void clearOther() {
      if (!this.packetsQueue.isEmpty()) {
         ArrayList<Packet> toRemove = new ArrayList();
         Iterator var2 = this.packetsQueue.iterator();

         Packet p;
         while(var2.hasNext()) {
            p = (Packet)var2.next();
            if (!this.isMove(p) && !this.isPing(p)) {
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (Packet)var2.next();
               this.packetsQueue.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void startBlinkingAll() {
      this.blinkingMove = true;
      this.blinkingPing = true;
      this.blinkingOther = true;
   }

   public void releaseAll() {
      if (!this.packetsQueue.isEmpty()) {
         Iterator var1 = this.packetsQueue.iterator();

         while(var1.hasNext()) {
            Packet packet = (Packet)var1.next();
            PacketUtil.sendPacketNoEvent(packet);
         }

         this.packetsQueue.clear();
      }

   }

   public void stopAll() {
      this.releaseAll();
      this.blinkingMove = false;
      this.blinkingPing = false;
      this.blinkingOther = false;
   }

   public boolean isBlinking() {
      return this.blinkingMove || this.blinkingPing || this.blinkingOther;
   }

   public boolean isBlinkingAll() {
      return this.blinkingMove && this.blinkingPing && this.blinkingOther;
   }

   public boolean isMove(Packet p) {
      return p instanceof C03PacketPlayer || p instanceof C0BPacketEntityAction;
   }

   public boolean isPing(Packet p) {
      return p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive;
   }

   public CopyOnWriteArrayList<Packet> getPacketsQueue() {
      return this.packetsQueue;
   }

   public boolean isBlinkingMove() {
      return this.blinkingMove;
   }

   public boolean isBlinkingPing() {
      return this.blinkingPing;
   }

   public boolean isBlinkingOther() {
      return this.blinkingOther;
   }

   public boolean isClearedPackets() {
      return this.clearedPackets;
   }
}
