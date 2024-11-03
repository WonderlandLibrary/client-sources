package net.augustus.modules.player;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventSendPacket;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.RayTraceUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class FakeLag extends Module {
   private final ArrayList<Packet> packets = new ArrayList<>();
   private final TimeHelper timeHelper = new TimeHelper();
   public DoubleValue startDelay = new DoubleValue(1, "StartDelay", this, 500.0, 0.0, 2000.0, 0);
   public DoubleValue lagDuration = new DoubleValue(2, "LagPackets", this, 10.0, 1.0, 50.0, 0);
   public BooleanValue onlyMove = new BooleanValue(3, "OnlyMove", this, false);
   public BooleanValue combat = new BooleanValue(4, "Combat", this, false);
   public int sentC03Packets = 0;
   private boolean shouldBlockPackets;

   public FakeLag() {
      super("FakeLag", Color.red, Categorys.PLAYER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.shouldBlockPackets = false;
   }

   @Override
   public void onPreDisable() {
      this.resetPackets();
   }

   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      int count = 0;

      for(Packet p : this.packets) {
         if (p instanceof C03PacketPlayer) {
            ++count;
         }
      }

      this.sentC03Packets = count;
      if (this.combat.getBoolean()) {
         if ((double)count > this.lagDuration.getValue() || BlockUtil.isScaffoldToggled()) {
            this.shouldBlockPackets = false;
         }
      } else if ((double)count <= this.lagDuration.getValue() && !BlockUtil.isScaffoldToggled()) {
         this.shouldBlockPackets = true;
      } else {
         this.shouldBlockPackets = false;
         this.resetPackets();
      }

      if (!((double)count <= this.lagDuration.getValue()) || BlockUtil.isScaffoldToggled()) {
         this.shouldBlockPackets = false;
         this.resetPackets();
      } else if (!this.combat.getBoolean()) {
         this.shouldBlockPackets = true;
      }
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      if (this.combat.getBoolean()) {
         if (packet instanceof C02PacketUseEntity) {
            this.shouldBlockPackets = false;
            this.resetPackets();
         } else if (packet instanceof C03PacketPlayer && mm.killAura.isToggled() && mm.killAura.target != null) {
            EntityLivingBase entityLivingBase = mm.killAura.target;
            if (entityLivingBase instanceof EntityPlayer) {
               EntityPlayer player = (EntityPlayer)entityLivingBase;
               double[] predictedTarget = PlayerUtil.predictPosition(player, (int)Math.max(this.lagDuration.getValue(), 0.0));
               double[] predictedMe = PlayerUtil.predictPosition(mc.thePlayer, (int)Math.max(this.lagDuration.getValue(), 0.0));
               MovingObjectPosition movingObjectPosition = RayTraceUtil.rayCast(4.0F);
               Vec3 positionEyes = mc.thePlayer.getPositionEyes(1.0F);
               Vec3 positionEyesServer = mc.thePlayer.getSeverPosition().addVector(0.0, (double)mc.thePlayer.getEyeHeight(), 0.0);
               Vec3 bestHitVec = RotationUtil.getBestHitVec(player);
               if (!this.shouldBlockPackets
                  && player.hurtTime < 3
                  && positionEyes.distanceTo(bestHitVec) > 2.9
                  && positionEyes.distanceTo(bestHitVec) < 3.3
                  && positionEyes.distanceTo(bestHitVec) < positionEyesServer.distanceTo(bestHitVec)) {
                  this.shouldBlockPackets = true;
               }
            }
         }
      }

      if (mc.theWorld != null && this.shouldBlockPackets && this.timeHelper.reached((long)this.startDelay.getValue())) {
         if (this.onlyMove.getBoolean()) {
            if (packet instanceof C03PacketPlayer && !this.packets.contains(packet)) {
               this.packets.add(packet);
               eventSendPacket.setCanceled(true);
            }
         } else if (!this.packets.contains(packet)) {
            this.packets.add(packet);
            eventSendPacket.setCanceled(true);
         }
      }
   }

   private void resetPackets() {
      if (mc.thePlayer != null) {
         if (!this.packets.isEmpty()) {
            this.packets.forEach(packet -> mc.thePlayer.sendQueue.addToSendQueueDirect(packet));
            this.packets.clear();
         }
      } else {
         this.packets.clear();
      }
   }
}
