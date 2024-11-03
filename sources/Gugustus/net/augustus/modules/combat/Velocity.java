package net.augustus.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import net.augustus.events.*;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.RayTraceUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Velocity extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   private final TimeHelper timeDelay = new TimeHelper();
   private final ArrayList<Packet> packets = new ArrayList<>();
   public StringValue mode = new StringValue(1, "Mode", this, "Basic", new String[]{"Basic", "Legit", "PushGround", "Push", "Intave", "IntaveTest", "Reverse", "Spoof", "Test", "Grim", "BuzzReverse", "TickZero"});
   public DoubleValue XZValue = new DoubleValue(2, "XZVelocity", this, 20.0, 0.0, 100.0, 0);
   public BooleanValue legitexploit1 = new BooleanValue(7, "LegitExploitCombat", this, false);
   public BooleanValue legitexploit3 = new BooleanValue(7, "LegitExploitOnlyMove", this, false);
   public DoubleValue legitexploit2 = new DoubleValue(2, "LegitExploitPacket", this, 10.0, 1.0, 50.0, 0);
   
   public DoubleValue YValue = new DoubleValue(3, "YVelocity", this, 20.0, 0.0, 100.0, 0);
   public DoubleValue XZValueIntave = new DoubleValue(5, "XZVelocity", this, 0.6, -1.0, 1.0, 2);
   public BooleanValue jumpIntave = new BooleanValue(7, "Jump", this, false);
   public BooleanValue ignoreExplosion = new BooleanValue(4, "Explosion", this, true);
   public DoubleValue pushXZ = new DoubleValue(7, "Push", this, 1.1, 0.01, 20.0, 2);
   public DoubleValue pushStart = new DoubleValue(9, "PushStart", this, 9.0, 1.0, 10.0, 0);
   public DoubleValue pushEnd = new DoubleValue(10, "PushEnd", this, 2.0, 1.0, 10.0, 0);
   public DoubleValue reverseStart = new DoubleValue(12, "ReverseStart", this, 9.0, 1.0, 10.0, 0);
   public DoubleValue karhuStart = new DoubleValue(69, "TickZero", this, 4, 1, 10, 0);
   public BooleanValue tickZeroY = new BooleanValue(69, "TickZeroY", this, false);
   public BooleanValue reverseStrafe = new BooleanValue(13, "ReverseStrafe", this, false);
   public BooleanValue pushOnGround = new BooleanValue(10, "OnGround", this, false);
   public BooleanValue hitBug = new BooleanValue(11, "HitBug", this, false);
   public Vec3 position = new Vec3(0.0, 0.0, 0.0);
   private int counter = 0;
   private int grimTCancel = 0;
   private boolean grimFlag;
   private double posY;
   private double posZ;
   private double posX;
   public boolean blockVelocity, isWorking;
   public int ticks, updates;
   
   private Minecraft mc = Minecraft.getMinecraft();
   
   public int sentC03Packets = 0;
   private boolean shouldBlockPackets;

   
   public Velocity() {
      super("Velocity", new Color(83, 102, 109, 255), Categorys.COMBAT);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.shouldBlockPackets = false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.shouldBlockPackets = false;
   }
   
   @Override
   public void onPreDisable() {
      this.resetPackets();
   }
   
   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      int count = 0;
      if(this.mode.getSelected().equalsIgnoreCase("Legit")) {
//    	  if(mc.thePlayer.hurtTime > 8) {
//    		  if(mc.thePlayer.onGround) {
//    			  mc.thePlayer.jump();
//    		  }
//    	  }
      }
      if(this.mode.getSelected().equalsIgnoreCase("Legit")) {
      for(Packet p : this.packets) {
         if (p instanceof C03PacketPlayer) {
            ++count;
         }
      }
      
      this.sentC03Packets = count;
      if (this.legitexploit1.getBoolean() && mc.thePlayer.hurtTime >= 0) {
         if ((double)count > this.legitexploit2.getValue() || BlockUtil.isScaffoldToggled()) {
            this.shouldBlockPackets = false;
         }
      } else if ((double)count <= this.legitexploit2.getValue() && !BlockUtil.isScaffoldToggled()) {
         this.shouldBlockPackets = true;
      } else {
         this.shouldBlockPackets = false;
         this.resetPackets();
      }

      if (!((double)count <= this.legitexploit2.getValue()) || BlockUtil.isScaffoldToggled()) {
         this.shouldBlockPackets = false;
         this.resetPackets();
      } else if (!this.legitexploit1.getBoolean()) {
         this.shouldBlockPackets = true;
      }
   }
   }
   
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
	      Packet packet = eventSendPacket.getPacket();
	      if (this.legitexploit1.getBoolean() && mc.thePlayer.hurtTime >= 0) {
	         if (packet instanceof C02PacketUseEntity) {
	            this.shouldBlockPackets = false;
	            this.resetPackets();
	         } else if (packet instanceof C03PacketPlayer && mm.killAura.isToggled() && mm.killAura.target != null) {
	            EntityLivingBase entityLivingBase = mm.killAura.target;
	            if (entityLivingBase instanceof EntityPlayer) {
	               EntityPlayer player = (EntityPlayer)entityLivingBase;
	               double[] predictedTarget = PlayerUtil.predictPosition(player, (int)Math.max(this.legitexploit2.getValue(), 0.0));
	               double[] predictedMe = PlayerUtil.predictPosition(mc.thePlayer, (int)Math.max(this.legitexploit2.getValue(), 0.0));
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

	      if (mc.theWorld != null && this.shouldBlockPackets && this.timeHelper.reached((long)0)) {
	         if (this.legitexploit3.getBoolean() && mc.thePlayer.hurtTime >= 0) {
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


   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      if (this.mode.getSelected().equalsIgnoreCase("IntaveTest")) {
         posX = mc.thePlayer.posX;
         posY = mc.thePlayer.posY;
         posZ = mc.thePlayer.posZ;
      }
      
     
      
      if (this.mode.getSelected().equalsIgnoreCase("Intave")) {
    	  if (packet instanceof S12PacketEntityVelocity) {
              S12PacketEntityVelocity packets = (S12PacketEntityVelocity)packet;
              
              if(packets.getEntityID() == mc.thePlayer.getEntityId()) {
              	isWorking = true;
              	
              	if(mc.thePlayer.isBurning()) {
              		
              	}
              	
      			if(mc.objectMouseOver.entityHit != null && mc.thePlayer.hurtTime == 9 && !mc.thePlayer.isBurning() && jumpIntave.getBoolean()) {
                      mc.thePlayer.movementInput.jump = true;
                      ticks++;
                  }
              }
          }
       }
      if (this.mode.getSelected().equalsIgnoreCase("Grim")) {
         if (!mm.blockFly.isToggled()/* && !mm.scaffoldWalk.isToggled() && !mm.newScaffold.isToggled()*/) {
            Packet p = eventReadPacket.getPacket();
            if (p instanceof S12PacketEntityVelocity) {
               if (((S12PacketEntityVelocity) p).getEntityID() == mc.thePlayer.getEntityId()) {
                  eventReadPacket.setCanceled(true);
                  grimTCancel = 6;
               }
            }
            if (p instanceof S32PacketConfirmTransaction) {
               eventReadPacket.setCanceled(true);
               grimTCancel--;
            }
         }
      }
      if (this.mode.getSelected().equalsIgnoreCase("Basic")) {
         Packet p = eventReadPacket.getPacket();
         if (p instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)p).getEntityID() == mc.thePlayer.getEntityId()) {
            if (!(this.XZValue.getValue() > 0.0) && !(this.YValue.getValue() > 0.0)) {
               eventReadPacket.setCanceled(true);
            } else {
               ((S12PacketEntityVelocity)p).setMotionX((int)((double)((S12PacketEntityVelocity)p).getMotionX() * this.XZValue.getValue() / 100.0));
               ((S12PacketEntityVelocity)p).setMotionY((int)((double)((S12PacketEntityVelocity)p).getMotionY() * this.YValue.getValue() / 100.0));
               ((S12PacketEntityVelocity)p).setMotionZ((int)((double)((S12PacketEntityVelocity)p).getMotionZ() * this.XZValue.getValue() / 100.0));
            }
         }

         if (p instanceof S27PacketExplosion && this.ignoreExplosion.getBoolean()) {
            if (!(this.XZValue.getValue() > 0.0) && !(this.YValue.getValue() > 0.0)) {
               eventReadPacket.setCanceled(true);
            } else {
               ((S27PacketExplosion)p).setField_149152_f((float)((int)((double)((S27PacketExplosion)p).getField_149152_f() * this.XZValue.getValue() / 100.0)));
               ((S27PacketExplosion)p).setField_149153_g((float)((int)((double)((S27PacketExplosion)p).getField_149152_f() * this.YValue.getValue() / 100.0)));
               ((S27PacketExplosion)p).setField_149159_h((float)((int)((double)((S27PacketExplosion)p).getField_149152_f() * this.XZValue.getValue() / 100.0)));
            }
         }
      }

      if (packet instanceof S29PacketSoundEffect && this.hitBug.getBoolean()) {
         S29PacketSoundEffect soundEffect = (S29PacketSoundEffect)packet;
         if (soundEffect.getSoundName().equalsIgnoreCase("game.player.hurt") || soundEffect.getSoundName().equalsIgnoreCase("game.player.die")) {
            eventReadPacket.setCanceled(true);
         }
      }

      if (packet instanceof S12PacketEntityVelocity && this.mode.getSelected().equals("Spoof")) {
         S12PacketEntityVelocity s12PacketEntityVelocity = (S12PacketEntityVelocity)packet;
         if (s12PacketEntityVelocity.getEntityID() == mc.thePlayer.getEntityId()) {
            eventReadPacket.setCanceled(true);
            mc.getNetHandler()
               .addToSendQueue(
                  new C03PacketPlayer.C04PacketPlayerPosition(
                     mc.thePlayer.posX + (double)s12PacketEntityVelocity.getMotionX() / 8000.0,
                     mc.thePlayer.posY + (double)s12PacketEntityVelocity.getMotionY() / 8000.0,
                     mc.thePlayer.posZ + (double)s12PacketEntityVelocity.getMotionZ() / 8000.0,
                     false
                  )
               );
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

   @EventTarget
   public void onSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      switch (mode.getSelected()) {
         case "IntaveTest": {
            if(mc.thePlayer.hurtTime > 7 && packet instanceof C0FPacketConfirmTransaction) {
               eventSendPacket.setCanceled(true);
            }
            break;
         }
         case "Grim2": {
            if(mc.thePlayer.hurtTime != 0) {
               ((C03PacketPlayer) packet).setY(mc.thePlayer.posY - 2);
            }
            break;
         }
      }
   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "BuzzReverse": {
            try {
               if (mc.thePlayer.hurtTime == 7)
                  MoveUtil.multiplyXZ(-1.0D);
            } catch (Exception exception) {}
            break;
         }
         case "Legit":
            if (mc.thePlayer.hurtTime > 0 && mm.killAura.isToggled() && mm.killAura.target != null) {
               ArrayList<Vec3> vec3s = new ArrayList<>();
               HashMap<Vec3, Integer> map = new HashMap<>();
               Vec3 playerPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
               Vec3 onlyForward = PlayerUtil.getPredictedPos(false, mm.killAura.target, 1.0F, 0.0F).add(playerPos);
               Vec3 strafeLeft = PlayerUtil.getPredictedPos(false, mm.killAura.target, 1.0F, 1.0F).add(playerPos);
               Vec3 strafeRight = PlayerUtil.getPredictedPos(false, mm.killAura.target, 1.0F, -1.0F).add(playerPos);
               map.put(onlyForward, 0);
               map.put(strafeLeft, 1);
               map.put(strafeRight, -1);
               vec3s.add(onlyForward);
               vec3s.add(strafeLeft);
               vec3s.add(strafeRight);
               Vec3 targetVec = new Vec3(mm.killAura.target.posX, mm.killAura.target.posY, mm.killAura.target.posZ);
               vec3s.sort(Comparator.comparingDouble(targetVec::distanceXZTo));
               if (!mc.thePlayer.movementInput.sneak) {
                  System.out.println(map.get(vec3s.get(0)));
                  mc.thePlayer.movementInput.moveStrafe = (float)map.get(vec3s.get(0)).intValue();
               }
    
            }
            break;
         case "Intave":
            if (this.jumpIntave.getBoolean() && mc.thePlayer.hurtTime == 9 && mc.thePlayer.onGround && this.counter++ % 2 == 0) {
               mc.thePlayer.movementInput.jump = true;
            }
  
            break;
         case "Test":
            if (mc.thePlayer.hurtTime > 2) {
               MoveUtil.setSpeed(0.01F);
               if (mc.thePlayer.hurtTime == 9 && mc.thePlayer.onGround) {
                  mc.thePlayer.movementInput.jump = true;
               }
            }
            break;
      }
   }

   @EventTarget
   public void onEventPostMotion(EventPostMotion eventPostMotion) {
      if (this.mode.getSelected().equalsIgnoreCase("IntaveTest") && mc.thePlayer.hurtTime == 8) {
         mc.thePlayer.setPosition(posX, posY, posZ);
      }
      if (this.mode.getSelected().equals("Reverse")
         && this.reverseStrafe.getBoolean()
         && (double)mc.thePlayer.hurtTime <= this.reverseStart.getValue()
         && mc.thePlayer.hurtTime > 0) {
         MoveUtil.strafe();
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      this.setDisplayName(this.getName() + this.mode.getSelected());
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "Grim2": {
            if(mc.thePlayer.hurtTime != 0) {
               mc.thePlayer.setPosition(mc.thePlayer.lastTickPosX, mc.thePlayer.lastTickPosY, mc.thePlayer.lastTickPosZ);
            }
            break;
         }
         case "TickZero": {
            if(mc.thePlayer.hurtTime == karhuStart.getValue()) {
               MoveUtil.multiplyXZ(0);
               mc.thePlayer.motionY = tickZeroY.getBoolean() ? 0 : mc.thePlayer.motionY;
            }
            break;
         }
         case "PushGround":
            this.pushGround();
            break;
         case "Push":
            this.push();
            break;
         case "Reverse":
            this.reverse();
         case "Intave":
 			blockVelocity = true;
 			
 			if(mc.objectMouseOver == null) {
 				return;
 			}
 			
 			if(mc.objectMouseOver.entityHit != null && mc.thePlayer.hurtTime == 9 && !mc.thePlayer.isBurning() && jumpIntave.getBoolean()) {
                 mc.thePlayer.movementInput.jump = true;
                 ticks++;
                 break;
             }
      }
   }
   private void reverse() {
      if ((double)mc.thePlayer.hurtTime == this.reverseStart.getValue()) {
         mc.thePlayer.motionX *= -1.0;
         mc.thePlayer.motionZ *= -1.0;
         if (this.reverseStrafe.getBoolean() && (double)mc.thePlayer.hurtTime <= this.reverseStart.getValue() && mc.thePlayer.hurtTime > 0) {
            MoveUtil.strafe();
         }
      }
   }

   private void push() {
      if ((double)mc.thePlayer.hurtTime <= Math.max(this.pushStart.getValue(), this.pushEnd.getValue())
         && (double)mc.thePlayer.hurtTime >= Math.min(this.pushStart.getValue(), this.pushEnd.getValue())) {
         mc.thePlayer.moveFlying(0.0F, 0.98F, (float)(this.pushXZ.getValue() / 100.0));
         if (this.pushOnGround.getBoolean()) {
            mc.thePlayer.onGround = true;
         }
      }
   }

   private void pushGround() {
      if (mc.thePlayer.hurtTime > 0) {
         mc.thePlayer.onGround = true;
      }
   }
}
