package net.augustus.modules.movement;

import java.awt.Color;

import net.augustus.events.EventBlockBoundingBox;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventSendPacket;
import net.augustus.events.EventUpdate;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.ChatUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.PlayerUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Fly extends Module {
   public StringValue mode = new StringValue(1, "Mode", this, "Vanilla", new String[]{"Vanilla", "Motion", "Collide", "AirJump", "Verus", "Verus2", "SkycaveBad", "Teleport", "FurtherTeleport", "Test", "SilentACAbuse", "Karhu", "FoxAC", "VulcanGlide", "Grim", "Packet", "PolarTest"});
   public BooleanValue autoJump = new BooleanValue(2, "AutoJump", this, true);
   public BooleanValue sendOnGroundPacket = new BooleanValue(5, "OnGroundPacket", this, true);

   public DoubleValue speed = new DoubleValue(3, "Speed", this, 1.0, 0.1, 9.0, 1);
   public DoubleValue verusspeed = new DoubleValue(4, "Speed", this, 1.0, 0.1, 9.0, 1);
   public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);
   @EventTarget
   public void onWorld(EventWorld eventWorld) {
      if(onWorld.getBoolean()) {
         setToggled(false);
      }
   }
   public int count;
   boolean verusdmg = false;
   private double startY = 0.0;
   private double jumpGround;
   private double oldY;
   private double x;
   private double z;
   public boolean teleported = false;
   public Vec3 lastPos = new Vec3(0, 0, 0);
   public TimeHelper polarTimeHelper = new TimeHelper();

   public Fly() {
      super("Fly", new Color(123, 240, 156), Categorys.MOVEMENT);
   }

   @Override
   public void onDisable() {
      this.verusdmg = false;
      mc.thePlayer.capabilities.isFlying = false;
      mc.getTimer().timerSpeed = 1.0F;
      mc.thePlayer.setSpeedInAir(0.02F);
      String var1 = this.mode.getSelected();
      switch(var1) {
         case "Vanilla":
            mc.thePlayer.motionX = 0.0;
            mc.thePlayer.motionZ = 0.0;
            break;
         case "Verus":
            MoveUtil.setSpeed(0.0F);
      }
   }
   @Override
   public void onEnable() {
      super.onEnable();
      try {
         oldY = mc.thePlayer.posY;
         this.verusdmg = false;
         String var1 = this.mode.getSelected();
         switch (var1) {
            case "FoxAC": {
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 2.0D, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
               break;
            }
            case "Verus":
               PlayerUtil.verusdmg();
               break;
            case "Collide":
               this.startY = mc.thePlayer.posY;
            case "PolarTest": {
            	teleported = false;
            	mc.timer.timerSpeed = 0.5f;
            	polarTimeHelper.reset();
           	 lastPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            	break;
            }
         }

         this.jumpGround = 0.0;
      } catch (NullPointerException e) {
         e.printStackTrace();
      }
   }

   @EventTarget
   public void onEventBlockBoundingBox(EventBlockBoundingBox eventBlockBoundingBox) {
      if (this.mode.getSelected().equals("Collide")
         && eventBlockBoundingBox.getBlock() instanceof BlockAir
         && (double)eventBlockBoundingBox.getBlockPos().getY() < this.startY) {
         BlockPos blockPos = eventBlockBoundingBox.getBlockPos();
         eventBlockBoundingBox.setAxisAlignedBB(
            new AxisAlignedBB(
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ(),
                    blockPos.getX() + 1,
                    blockPos.getY() + 1,
                    blockPos.getZ() + 1
            )
         );
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      this.setDisplayName(super.getName() + " Â§8" + this.mode.getSelected());
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "Intave12": {
            if (mc.thePlayer.posY != (int) mc.thePlayer.posY || mc.thePlayer.motionY > 0)
               return;
            mc.thePlayer.onGround = true;
            mc.thePlayer.noClip = true;
            mc.thePlayer.motionY = 0;
            mc.gameSettings.keyBindJump.pressed = false;

            if(mc.thePlayer.ticksExisted % 2 == 0) {
               double multiplier = 0.01D;
               z = this.mc.thePlayer.posZ + MathHelper.cos((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
               x = this.mc.thePlayer.posX + -MathHelper.sin((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
               this.mc.thePlayer.setPosition(x, mc.thePlayer.posY, z);
            }
            break;
         }
         case "Karhu": {
            if(mc.thePlayer.fallDistance >= 0.05) {
               mc.thePlayer.motionY = 0.05;
               mc.thePlayer.fallDistance = 0;
            }
            break;
         }
         case "VulcanGlide": {
            mc.thePlayer.motionY = (mc.thePlayer.ticksExisted % 2 == 0) ? -0.17D : -0.1D;
            break;
         }
         case "Motion":
         case "FoxAC":
         case "SilentACAbuse": {
            mc.thePlayer.motionY = 0;
            if(mc.gameSettings.keyBindJump.pressed) {
               mc.thePlayer.motionY = speed.getValue();
            }
            if(mc.gameSettings.keyBindSneak.pressed) {
               mc.thePlayer.motionY = -speed.getValue();
            }
            if(MoveUtil.isMoving()) {
               MoveUtil.setSpeed((float) speed.getValue());
            } else {
               MoveUtil.setSpeed(0);
            }
            break;
         }
         case "Test": {
            MoveUtil.multiplyXZ(0);
            mc.thePlayer.motionY = 0;
            double multiplier = 0.01D;
            if(mc.thePlayer.ticksExisted % 3 == 0) {
               z = this.mc.thePlayer.posZ + MathHelper.cos((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
               x = this.mc.thePlayer.posX + -MathHelper.sin((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
            }
            this.mc.thePlayer.setPosition(x, this.oldY, z);
            if (this.mc.gameSettings.keyBindJump.pressed) {
               this.oldY += multiplier * this.speed.getValue();
            } else if (this.mc.gameSettings.keyBindSneak.pressed) {
               this.oldY -= multiplier * this.speed.getValue();
            }
            break;
         }
         case "Vanilla": {
            mc.thePlayer.capabilities.isFlying = true;
            mc.thePlayer.capabilities.setFlySpeed((float) this.speed.getValue());
            break;
         }
         case "Verus": {
            if (mc.thePlayer.hurtTime != 0) {
               this.verusdmg = true;
            }

            if (!this.verusdmg) {
               mc.thePlayer.motionZ = 0.0;
               mc.thePlayer.motionX = 0.0;
               mc.gameSettings.keyBindJump.pressed = false;
            }

            if (this.verusdmg) {
               mc.getTimer().timerSpeed = 0.3F;
               if (mc.gameSettings.keyBindJump.pressed) {
                  mc.thePlayer.motionY = 1.5;
               } else if (mc.gameSettings.keyBindSneak.pressed) {
                  mc.thePlayer.motionY = -1.5;
               } else {
                  mc.thePlayer.motionY = 0.0;
               }

               mc.thePlayer.onGround = true;
               MoveUtil.setSpeed((float) this.verusspeed.getValue());
            }
            break;
         }
         case "Teleport": {
            MoveUtil.multiplyXZ(0);
            mc.thePlayer.motionY = 0;
            double multiplier = 0.01D;
            z = this.mc.thePlayer.posZ + MathHelper.cos((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
            x = this.mc.thePlayer.posX + -MathHelper.sin((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
            this.mc.thePlayer.setPositionAndUpdate(x, this.oldY, z);
            if (this.mc.gameSettings.keyBindJump.pressed) {
               this.oldY += multiplier * this.speed.getValue();
            } else if (this.mc.gameSettings.keyBindSneak.pressed) {
               this.oldY -= multiplier * this.speed.getValue();
            }
            break;
         }
         case "Packet":
         case "FurtherTeleport": {
            MoveUtil.multiplyXZ(0);
            mc.thePlayer.motionY = 0;
            double multiplier = 1D;
            z = this.mc.thePlayer.posZ + MathHelper.cos((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
            x = this.mc.thePlayer.posX + -MathHelper.sin((float) MoveUtil.direction()) * multiplier * this.speed.getValue();
            this.mc.thePlayer.setPositionAndUpdate(x, this.oldY, z);
            if (this.mc.gameSettings.keyBindJump.pressed) {
               this.oldY += multiplier * this.speed.getValue();
            } else if (this.mc.gameSettings.keyBindSneak.pressed) {
               this.oldY -= multiplier * this.speed.getValue();
            }
            break;
         }
         case "Verus2": {
            double constantMotionValue = 0.42F;
            float constantMotionJumpGroundValue = 0.76F;
            if (mc.thePlayer.onGround) {
               this.jumpGround = mc.thePlayer.posY;
               mc.thePlayer.jump();
            }

            if (mc.thePlayer.posY > this.jumpGround + (double) constantMotionJumpGroundValue) {
               MoveUtil.setMotion(0.35, 45.0, mc.thePlayer.rotationYaw, true);
               mc.thePlayer.motionY = constantMotionValue;
               this.jumpGround = mc.thePlayer.posY;
            }
            break;
         }
         case "SkycaveBad": {
            if(mc.thePlayer.fallDistance >= 3.8) {
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer(true));
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - mc.thePlayer.motionY, mc.thePlayer.posZ);
               mc.thePlayer.motionY = 0.8;
               MoveUtil.setSpeed((float)speed.getValue());
               mc.thePlayer.fallDistance = 0;
            }
            break;
         }
         case "AirJump": {
            if (mc.gameSettings.keyBindJump.isPressed()) {
               if (!mc.thePlayer.onGround) {
                  mc.thePlayer.onGround = true;
                  if (this.sendOnGroundPacket.getBoolean()) {
                     mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer(true));
                  }
               }
            } else if (this.autoJump.getBoolean() && mc.thePlayer.motionY < -0.44 && !mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               if (this.sendOnGroundPacket.getBoolean()) {
                  mc.thePlayer.sendQueue.addToSendQueueDirect(new C03PacketPlayer(true));
               }
            }
         }
         break;
         case "PolarTest": {
        	 //檢查有沒有被傳送
        	 double xDiff = lastPos.xCoord > mc.thePlayer.posX ? lastPos.xCoord - mc.thePlayer.posX : mc.thePlayer.posX - lastPos.xCoord;
        	 double yDiff = lastPos.yCoord > mc.thePlayer.posY ? lastPos.yCoord - mc.thePlayer.posY : mc.thePlayer.posY - lastPos.yCoord;
        	 double zDiff = lastPos.zCoord > mc.thePlayer.posZ ? lastPos.zCoord - mc.thePlayer.posZ : mc.thePlayer.posZ - lastPos.zCoord;
        	 if(xDiff > 4 || yDiff > 4 || zDiff > 4) {
        		 polarTimeHelper.reset();
        		 teleported = true;
        	 }
        	 ChatUtil.sendChat(xDiff + "");
        	 ChatUtil.sendChat(yDiff + "");
        	 ChatUtil.sendChat(zDiff + "");
        	 lastPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        	 //如果傳送了就fly $$$
        	 if(teleported) {
        		 //fly code
        		 if(!polarTimeHelper.reached(4000)) {
        		 }else {
        			 mc.gameSettings.keyBindForward.pressed = false;
        			 polarTimeHelper.reset();
        			 this.toggle();
        		 }
        	 }
       	  break;
         }
      }

      if (mc.theWorld == null) {
         this.toggle();
      }
   }

   @EventTarget
   public void onEventReadPacket(EventReadPacket eventReadPacket) {
      Packet packet = eventReadPacket.getPacket();
      if (mode.getSelected().equals("VulcanSex")) {
         if (packet instanceof S02PacketChat) {
            if (((S02PacketChat) packet).getChatComponent().getFormattedText().contains("Vulcan")) {
               eventReadPacket.setCanceled(true);
            }
         }
      }
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      switch (mode.getSelected()) {
         case "Packet": {
            if(mc.thePlayer.ticksExisted % 2 == 1) {
               if (eventSendPacket.getPacket() instanceof C03PacketPlayer) {
                  ((C03PacketPlayer) eventSendPacket.getPacket()).setX(mc.thePlayer.posX + 1000);
                  ((C03PacketPlayer) eventSendPacket.getPacket()).setZ(mc.thePlayer.posZ + 1000);
               }
            }
            break;
         }
         case "Grim": {
            if (eventSendPacket.getPacket() instanceof C03PacketPlayer) {
               ((C03PacketPlayer) eventSendPacket.getPacket()).setX(mc.thePlayer.posX + 1000);
               ((C03PacketPlayer) eventSendPacket.getPacket()).setZ(mc.thePlayer.posZ + 1000);
            }
            break;
         }
         case "Test": {
            if (eventSendPacket.getPacket() instanceof C03PacketPlayer) {
               ((C03PacketPlayer)eventSendPacket.getPacket()).setOnGround(false);
            }
            break;
         }
         case "SilentACAbuse": {
            if (eventSendPacket.getPacket() instanceof C03PacketPlayer) {
               double newY = this.oldY - mc.thePlayer.posY;
               double newX = this.x - mc.thePlayer.posX;
               double newZ = this.z - mc.thePlayer.posZ;
               double diff = Math.sqrt(newX * newX + newY * newY + newZ * newZ);
               boolean should = diff >= 8;
               if(!should) {
                  eventSendPacket.setCanceled(true);
               }
               if(should) {
                  x = mc.thePlayer.posX;
                  oldY = mc.thePlayer.posY;
                  z = mc.thePlayer.posZ;
               }
            }
            break;
         }

      }
   }
}
