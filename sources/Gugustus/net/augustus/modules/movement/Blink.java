package net.augustus.modules.movement;

import net.augustus.events.EventReadPacket;
import net.augustus.events.EventSendPacket;
import net.augustus.events.EventTick;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.combat.KillAura;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.skid.wurst.EntityFakePlayer;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S23PacketBlockChange;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Blink extends Module {
   public final DoubleValue autoDisable = new DoubleValue(2, "AutoDisable", this, 0.0, 0.0, 100.0, 0);
   public final DoubleValue pulse = new DoubleValue(2, "Pulse", this, 0.0, 0.0, 100.0, 0);
   public final BooleanValue noAura = new BooleanValue(137248, "NoAura", this, false);
   public final BooleanValue fakePlayerEnabled = new BooleanValue(23627, "FakePlayer", this, false);
   private final ArrayList<Packet> packets = new ArrayList<>();
   private final HashMap<S14PacketEntity, Long> spackets = new HashMap<>();
   public StringValue mode = new StringValue(1, "Mode", this, "OnlyMovement", new String[]{"OnlyMovement", "Players", "C0F", "All"});
   public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);
   @EventTarget
   public void onWorld(EventWorld eventWorld) {
      if(onWorld.getBoolean()) {
         setToggled(false);
      }
   }
   private int counter = 0;
   public EntityFakePlayer fakePlayer;

   public Blink() {
      super("Blink", new Color(75, 5, 161), Categorys.MOVEMENT);
   }

   @Override
   public void onEnable() {
      if (mc.theWorld != null && mc.thePlayer != null) {
         if(fakePlayerEnabled.getBoolean()) {
            fakePlayer = new EntityFakePlayer();
         }
         this.packets.clear();
         this.counter = 0;
      }
   }
   @Override
   public void onPreDisable() {
      for(Map.Entry<S14PacketEntity, Long> set : spackets.entrySet()) {
         set.getKey().processPacket(mc.thePlayer.sendQueue);
      }
      spackets.clear();
      try {
         fakePlayer.despawn();
      } catch (NullPointerException ignored) {}
      this.resetPackets();
   }
   @EventTarget
   public void onEventRecvPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      //this.setDisplayName(this.getName() + " §8ServerBlocks");
      if (this.mode.getSelected().equals("Players")) {
         if(packet instanceof S14PacketEntity) {
            if(((S14PacketEntity)packet).getEntityId() != mc.thePlayer.getEntityId()) {
               spackets.put((S14PacketEntity) packet, System.currentTimeMillis());
               eventReadPacket.setCanceled(true);
            }
         }
         checkS14Packets();
      }
   }
   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      //this.setDisplayName(this.getName() + " §8" + this.counter);
      if(!(noAura.getBoolean() && KillAura.target != null)) {
         if (this.autoDisable.getValue() > 0.0 && (double) this.counter > this.autoDisable.getValue()) {
            this.resetPackets();
            this.toggle();
         }

         if (mc.thePlayer != null) {
            if (this.mode.getSelected().equals("OnlyMovement")) {
               if (packet instanceof C03PacketPlayer) {
                  this.packets.add(packet);
                  eventSendPacket.setCanceled(true);
               }
            } else if (mode.getSelected().equalsIgnoreCase("All")) {
               this.packets.add(packet);
               eventSendPacket.setCanceled(true);
            } else if (mode.getSelected().equalsIgnoreCase("C0F") && packet instanceof C0FPacketConfirmTransaction){
               this.packets.add(packet);
               eventSendPacket.setCanceled(true);
            }

            if (packet instanceof C03PacketPlayer) {
               ++this.counter;
            }
         }
         if(pulse.getValue() != 0 && pulse.getValue() < counter) {
            resetPackets();
         }
      } else {
         resetPackets();
      }
   }

   private void resetPackets() {
      counter = 0;
      try {
         this.packets.forEach(mc.thePlayer.sendQueue::addToSendQueueDirect);
         fakePlayer.resetPosition();
      } catch (NullPointerException ignored) {} catch (Exception var2) {
         System.err.println("Error Blink");
      }

      this.packets.clear();
   }
   private void checkS14Packets() {
      try {
         S14PacketEntity toremove = null;
         for (Map.Entry<S14PacketEntity, Long> set : spackets.entrySet()) {
            if (set.getValue().longValue() + 1000 < System.currentTimeMillis()) {
               set.getKey().processPacket(mc.thePlayer.sendQueue);
               toremove = set.getKey();
            }
         }
         if (toremove != null) {
            spackets.remove(toremove);
         }
//         for(S23PacketBlockChange s23 : spackets) {
//            mc.theWorld.invalidateRegionAndSetBlock(s23.getBlockPosition(), s23.getBlockState());
//         }
      } catch (
              Exception var2) {
         var2.printStackTrace();
         //System.err.println("Error SBlink");
      }
   }
}
