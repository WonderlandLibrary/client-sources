package vestige.handler.packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.PacketSendEvent;
import vestige.util.IMinecraft;
import vestige.util.network.PacketUtil;

public class PacketDelayHandler implements IMinecraft {
   private long pingDelay;
   private long otherDelay;
   private boolean delayingPing;
   private boolean delayingOther;
   private boolean clearedPackets;
   private final CopyOnWriteArrayList<DelayedPacket> delayedPackets = new CopyOnWriteArrayList();

   public PacketDelayHandler() {
      Flap.instance.getEventManager().register(this);
   }

   @Listener(10)
   public void onSend(PacketSendEvent event) {
      if (mc.thePlayer != null && mc.thePlayer.ticksExisted >= 5) {
         this.clearedPackets = false;
      } else if (!this.clearedPackets) {
         this.delayedPackets.clear();
         this.stopAll();
         this.clearedPackets = true;
      }

      if (!event.isCancelled()) {
         if (this.isPing(event.getPacket())) {
            if (this.delayingPing) {
               event.setCancelled(true);
               this.delayedPackets.add(new DelayedPacket(event.getPacket()));
            }
         } else if (this.delayingOther) {
            event.setCancelled(true);
            this.delayedPackets.add(new DelayedPacket(event.getPacket()));
         }
      }

   }

   @Listener(10)
   public void onMotion(MotionEvent event) {
      if (!this.delayedPackets.isEmpty()) {
         ArrayList<DelayedPacket> toRemove = new ArrayList();
         Iterator var3 = this.delayedPackets.iterator();

         DelayedPacket p;
         while(var3.hasNext()) {
            p = (DelayedPacket)var3.next();
            if (this.isPing(p.getPacket())) {
               if (p.getTimer().getTimeElapsed() >= this.pingDelay) {
                  toRemove.add(p);
                  PacketUtil.sendPacketFinal(p.getPacket());
               }
            } else if (p.getTimer().getTimeElapsed() >= this.otherDelay) {
               toRemove.add(p);
               PacketUtil.sendPacketFinal(p.getPacket());
            }
         }

         if (!toRemove.isEmpty()) {
            var3 = toRemove.iterator();

            while(var3.hasNext()) {
               p = (DelayedPacket)var3.next();
               this.delayedPackets.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void startDelayingPing(long delay) {
      this.delayingPing = true;
      this.pingDelay = delay;
   }

   public void stopDelayingPing() {
      if (!this.delayedPackets.isEmpty()) {
         ArrayList<DelayedPacket> toRemove = new ArrayList();
         Iterator var2 = this.delayedPackets.iterator();

         DelayedPacket p;
         while(var2.hasNext()) {
            p = (DelayedPacket)var2.next();
            if (this.isPing(p.getPacket())) {
               PacketUtil.sendPacketFinal(p.getPacket());
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (DelayedPacket)var2.next();
               this.delayedPackets.remove(p);
            }
         }

         toRemove.clear();
      }

      this.delayingPing = false;
      this.pingDelay = 0L;
   }

   public void clearPing() {
      if (!this.delayedPackets.isEmpty()) {
         ArrayList<DelayedPacket> toRemove = new ArrayList();
         Iterator var2 = this.delayedPackets.iterator();

         DelayedPacket p;
         while(var2.hasNext()) {
            p = (DelayedPacket)var2.next();
            if (this.isPing(p.getPacket())) {
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (DelayedPacket)var2.next();
               this.delayedPackets.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void startDelayingOther(long delay) {
      this.delayingOther = true;
      this.otherDelay = delay;
   }

   public void stopDelayingOther() {
      if (!this.delayedPackets.isEmpty()) {
         ArrayList<DelayedPacket> toRemove = new ArrayList();
         Iterator var2 = this.delayedPackets.iterator();

         DelayedPacket p;
         while(var2.hasNext()) {
            p = (DelayedPacket)var2.next();
            if (!this.isPing(p.getPacket())) {
               PacketUtil.sendPacketFinal(p.getPacket());
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (DelayedPacket)var2.next();
               this.delayedPackets.remove(p);
            }
         }

         toRemove.clear();
      }

      this.delayingOther = false;
      this.otherDelay = 0L;
   }

   public void clearOther() {
      if (!this.delayedPackets.isEmpty()) {
         ArrayList<DelayedPacket> toRemove = new ArrayList();
         Iterator var2 = this.delayedPackets.iterator();

         DelayedPacket p;
         while(var2.hasNext()) {
            p = (DelayedPacket)var2.next();
            if (!this.isPing(p.getPacket())) {
               toRemove.add(p);
            }
         }

         if (!toRemove.isEmpty()) {
            var2 = toRemove.iterator();

            while(var2.hasNext()) {
               p = (DelayedPacket)var2.next();
               this.delayedPackets.remove(p);
            }
         }

         toRemove.clear();
      }

   }

   public void releaseAll() {
      if (!this.delayedPackets.isEmpty()) {
         Iterator var1 = this.delayedPackets.iterator();

         while(var1.hasNext()) {
            DelayedPacket p = (DelayedPacket)var1.next();
            PacketUtil.sendPacketFinal(p.getPacket());
         }

         this.delayedPackets.clear();
      }

   }

   public void stopAll() {
      this.releaseAll();
      this.delayingPing = false;
      this.delayingOther = false;
   }

   public boolean isPing(Packet p) {
      return p instanceof C0FPacketConfirmTransaction || p instanceof C00PacketKeepAlive;
   }

   public long getPingDelay() {
      return this.pingDelay;
   }

   public long getOtherDelay() {
      return this.otherDelay;
   }

   public boolean isDelayingPing() {
      return this.delayingPing;
   }

   public boolean isDelayingOther() {
      return this.delayingOther;
   }

   public boolean isClearedPackets() {
      return this.clearedPackets;
   }

   public CopyOnWriteArrayList<DelayedPacket> getDelayedPackets() {
      return this.delayedPackets;
   }
}
