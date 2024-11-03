package net.augustus.modules.misc;

import java.awt.Color;
import java.util.ArrayList;

import io.netty.buffer.Unpooled;
import net.augustus.events.*;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.notify.GeneralNotifyManager;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.custompackets.CustomC00PacketKeepAlive;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.status.client.C01PacketPing;

public class Disabler extends Module {
   private final ArrayList<CustomC00PacketKeepAlive> keepAlivePackets = new ArrayList<>();
   private final int counter = 0;
   public BooleanValue pingSpoof = new BooleanValue(0, "PingSpoof", this, false);
   public BooleanValue royalPixels = new BooleanValue(8, "RoyalPixels", this, false);
   public BooleanValue minemenStrafe = new BooleanValue(9, "MinemenStrafe", this, false);
   public BooleanValue universoCraft = new BooleanValue(7, "UniversoCraft", this, false);
   public BooleanValue spectate = new BooleanValue(0, "Spectate", this, false);
   public BooleanValue keepAlive = new BooleanValue(2, "C00PacketKeepAlive", this, false);
   public BooleanValue ping = new BooleanValue(3, "C01PacketPing", this, false);
   public BooleanValue entityAction = new BooleanValue(4, "C0BPacketEntityAction", this, false);
   public BooleanValue playerAbilities = new BooleanValue(5, "C13PacketPlayerAbilities", this, false);
   public BooleanValue confirmTransaction = new BooleanValue(7, "C0FPacketConfirmTransaction", this, false);
   public BooleanValue ncpTimer = new BooleanValue(132, "NCPTimerSemi", this, false);
   //public BooleanValue vulcanStrafe = new BooleanValue(7, "VulcanStrafe", this, false);
   //public BooleanValue vulcanCombat = new BooleanValue(7, "VulcanAutoclicker", this, false);
   //public BooleanValue spamSprint = new BooleanValue(7, "SpamSprint", this, false);
   public BooleanValue hac = new BooleanValue(696969, "HAC", this, false);
   public BooleanValue noSprint = new BooleanValue(7, "NoSprint", this, false);
   public BooleanValue blcSpoof = new BooleanValue(7, "BLC-Spoof", this, false);
   public BooleanValue aac5 = new BooleanValue(7, "AAC5", this, false);
   public BooleanValue antiVanillaEumel = new BooleanValue(7, "NoFlag", this, false);
   public BooleanValue debug = new BooleanValue(1536, "Debug", this, false);

   public DoubleValue delay = new DoubleValue(1, "Delay", this, 1000.0, 1.0, 4000.0, 0);
   public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);
   @EventTarget
   public void onWorld(EventWorld eventWorld) {
      if(onWorld.getBoolean()) {
         setToggled(false);
      }
   }
   private boolean disabling;

   public Disabler() {
      super("Disabler", new Color(73, 127, 163), Categorys.MISC);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.keepAlivePackets.clear();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (!this.keepAlivePackets.isEmpty()) {
         this.keepAlivePackets.clear();
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      if (ncpTimer.getBoolean()) {
         mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.017, mc.thePlayer.posZ);
         mc.thePlayer.motionY = 0.019;
      }
      if (hac.getBoolean()) {
         if(mc.thePlayer.ticksExisted % 10 == 0) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 11, mc.thePlayer.posZ, mc.thePlayer.cameraYaw, mc.thePlayer.cameraPitch, true));
         }
      }
      if (aac5.getBoolean()) {
         if (!mc.isIntegratedServerRunning()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 1.0E159, mc.thePlayer.posZ + 10, 0, 0, true));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, 0, 0, true));
         }
      }
      if (this.royalPixels.getBoolean()) {
         C13PacketPlayerAbilities capabilities = new C13PacketPlayerAbilities();
         capabilities.setAllowFlying(true);
         capabilities.setFlying(true);
         mc.thePlayer.sendQueue.addToSendQueue(capabilities);
      }
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      if (this.pingSpoof.getBoolean() && mc.thePlayer != null && !this.keepAlivePackets.isEmpty()) {
         ArrayList<CustomC00PacketKeepAlive> toRemove = new ArrayList<>();

         for(CustomC00PacketKeepAlive packet : this.keepAlivePackets) {
            if (packet.getTime() < System.currentTimeMillis()) {
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C00PacketKeepAlive(packet.getKey()));
               toRemove.add(packet);
            }
         }

         this.keepAlivePackets.removeIf(toRemove::contains);
      }
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      if (this.universoCraft.getBoolean()) {
         if (packet instanceof net.minecraft.network.play.server.S07PacketRespawn) {
            this.disabling = true;
         } else if (packet instanceof net.minecraft.network.play.client.C02PacketUseEntity) {
            this.disabling = false;
         } else if (packet instanceof net.minecraft.network.play.client.C03PacketPlayer && mc.thePlayer.ticksExisted <= 10) {
            this.disabling = true;
         } else if (packet instanceof C0FPacketConfirmTransaction && this.disabling && mc.thePlayer.ticksExisted < 350) {
            ((C0FPacketConfirmTransaction)packet).setUid((mc.thePlayer.ticksExisted % 2 == 0) ? Short.MIN_VALUE : Short.MAX_VALUE);
         }
      }
      if (this.noSprint.getBoolean() && packet instanceof C0BPacketEntityAction) {
         if(((C0BPacketEntityAction)packet).getAction() == C0BPacketEntityAction.Action.START_SPRINTING) {
            eventSendPacket.setCanceled(true);
         }
      }
      if (this.pingSpoof.getBoolean() && packet instanceof C00PacketKeepAlive) {
         C00PacketKeepAlive c00PacketKeepAlive = (C00PacketKeepAlive)packet;
         this.keepAlivePackets
            .add(
               new CustomC00PacketKeepAlive(
                  c00PacketKeepAlive.getKey(), (long)((double)System.currentTimeMillis() + this.delay.getValue() + (double)RandomUtil.nextLong(0L, 200L))
               )
            );
         eventSendPacket.setCanceled(true);
      }

      if (this.minemenStrafe.getBoolean() && packet instanceof C0FPacketConfirmTransaction && mc.thePlayer.ticksExisted % 3 == 0) {
         eventSendPacket.setCanceled(true);
      }

      if (packet instanceof C00PacketKeepAlive && this.keepAlive.getBoolean()) {
         eventSendPacket.setCanceled(true);
      }

      if (packet instanceof C01PacketPing && this.ping.getBoolean()) {
         eventSendPacket.setCanceled(true);
      }

      if (packet instanceof C0BPacketEntityAction && this.entityAction.getBoolean()) {
         eventSendPacket.setCanceled(true);
      }

      if (packet instanceof C13PacketPlayerAbilities && this.playerAbilities.getBoolean()) {
         eventSendPacket.setCanceled(true);
      }

      if (packet instanceof C0FPacketConfirmTransaction && this.confirmTransaction.getBoolean()) {
         eventSendPacket.setCanceled(true);
      }

      if (packet instanceof C03PacketPlayer && this.spectate.getBoolean()) {
         mc.thePlayer.sendQueue.addToSendQueue(new C18PacketSpectate(mc.thePlayer.getUniqueID()));
      }

      if (packet instanceof C17PacketCustomPayload && this.blcSpoof.getBoolean()) {
         final C17PacketCustomPayload c17 = (C17PacketCustomPayload) eventSendPacket.getPacket();
         if (c17.getChannelName().equals("MC|Brand")) {
            eventSendPacket.setPacket(new C17PacketCustomPayload(c17.getChannelName(), new PacketBuffer(Unpooled.buffer()).writeString("blc")));
         }
      }
   }
   @EventTarget
   public void onRecv(EventReadPacket event) {
      if(antiVanillaEumel.getBoolean()) {
         if (event.getPacket() instanceof S08PacketPlayerPosLook && mc.thePlayer.ticksExisted > 20) {
            //PlayerUtil.sendChat("Sexed: " + mc.thePlayer.ticksExisted);
            double x = ((S08PacketPlayerPosLook) event.getPacket()).getX() - mc.thePlayer.posX;
            double y = ((S08PacketPlayerPosLook) event.getPacket()).getY() - mc.thePlayer.posY;
            double z = ((S08PacketPlayerPosLook) event.getPacket()).getZ() - mc.thePlayer.posZ;
            double diff = Math.sqrt(x * x + y * y + z * z);
            if (diff <= 8.0D) {
               if(debug.getBoolean()) {
                  PlayerUtil.sendChat("sex");
               }
               event.setCanceled(true);
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer.C06PacketPlayerPosLook(((S08PacketPlayerPosLook) event.getPacket()).getX(), ((S08PacketPlayerPosLook) event.getPacket()).getY(), ((S08PacketPlayerPosLook) event.getPacket()).getZ(), ((S08PacketPlayerPosLook) event.getPacket()).getYaw(), ((S08PacketPlayerPosLook) event.getPacket()).getPitch(), true));
               //Client.INSTANCE.getNotificationManager().postNotification("Silent Flag!", NotificationType.INFO);
            }
         }
      }
   }
}
