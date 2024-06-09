package exhibition.module.impl.movement;

import exhibition.Client;
import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.event.impl.EventMove;
import exhibition.management.notifications.user.Notifications;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.module.impl.player.Scaffold;
import exhibition.util.Timer;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class Fly extends Module {
   private static String SPEED = "SPEED";
   private static final String MODE = "MODE";
   private String BYPASS = "BLORP";
   private String BOOST = "BOOST";
   private String JUMP = "JUMP";
   private Timer jumpDelay = new Timer();
   private Timer boostDelay = new Timer();
   private Timer kickTimer = new Timer();
   private double flyHeight;
   private double startY;
   private int stupidAutisticTickCounting;
   private int zoom;

   public Fly(ModuleData data) {
      super(data);
      this.settings.put(this.BYPASS, new Setting(this.BYPASS, false, "Blorps you in a zorp at blips and chitz. (Hypixel Fly)"));
      this.settings.put(this.JUMP, new Setting(this.JUMP, false, "You do a white man jump before launching off."));
      this.settings.put(SPEED, new Setting(SPEED, 1.5F, "Movement speed.", 0.25D, 1.0D, 5.0D));
      this.settings.put(this.BOOST, new Setting(this.BOOST, 2.0F, "Boost speed. 0 = no boost.", 0.25D, 0.0D, 3.0D));
      this.settings.put("MODE", new Setting("MODE", new Options("Fly Mode", "Motion", new String[]{"Vanilla", "AntiKick", "Glide", "Motion"}), "Fly method."));
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public void updateFlyHeight() {
      double h = 1.0D;
      AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().expand(0.0625D, 0.0625D, 0.0625D);

      for(this.flyHeight = 0.0D; this.flyHeight < mc.thePlayer.posY; this.flyHeight += h) {
         AxisAlignedBB nextBox = box.offset(0.0D, -this.flyHeight, 0.0D);
         if (mc.theWorld.checkBlockCollision(nextBox)) {
            if (h < 0.0625D) {
               break;
            }

            this.flyHeight -= h;
            h /= 2.0D;
         }
      }

   }

   public void goToGround() {
      if (this.flyHeight <= 300.0D) {
         double minY = mc.thePlayer.posY - this.flyHeight;
         if (minY > 0.0D) {
            double y = mc.thePlayer.posY;

            C03PacketPlayer.C04PacketPlayerPosition packet;
            while(y > minY) {
               y -= 8.0D;
               if (y < minY) {
                  y = minY;
               }

               packet = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
               mc.thePlayer.sendQueue.addToSendQueue(packet);
            }

            y = minY;

            while(y < mc.thePlayer.posY) {
               y += 8.0D;
               if (y > mc.thePlayer.posY) {
                  y = mc.thePlayer.posY;
               }

               packet = new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true);
               mc.thePlayer.sendQueue.addToSendQueue(packet);
            }

         }
      }
   }

   public void onEnable() {
      if (mc.thePlayer != null) {
         this.stupidAutisticTickCounting = -1;
         mc.timer.timerSpeed = 1.0F;
         this.startY = mc.thePlayer.posY;
         Module[] modules = new Module[]{(Module)Client.getModuleManager().get(Speed.class), (Module)Client.getModuleManager().get(LongJump.class), (Module)Client.getModuleManager().get(Scaffold.class)};
         Module[] var2 = modules;
         int var3 = modules.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Module module = var2[var4];
            if (module.isEnabled()) {
               module.toggle();
               Notifications.getManager().post("Movement Check", "Disabled extra modules.", 250L, Notifications.Type.NOTIFY);
            }
         }

         if (((Boolean)((Setting)this.settings.get(this.BYPASS)).getValue()).booleanValue()) {
            if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically && ((Boolean)((Setting)this.settings.get(this.JUMP)).getValue()).booleanValue() && this.jumpDelay.delay(2500.0F)) {
               mc.thePlayer.motionY = 0.412274355D;
               this.jumpDelay.reset();
            }

            this.zoom = 30;
         }

      }
   }

   public void onDisable() {
      super.onDisable();
      if (mc.thePlayer != null) {
         this.stupidAutisticTickCounting = -1;
         mc.timer.timerSpeed = 1.0F;
         mc.thePlayer.capabilities.isFlying = false;
         mc.thePlayer.capabilities.allowFlying = false;
      }
   }

   @RegisterEvent(
      events = {EventMove.class, EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      boolean hypickle = ((Boolean)((Setting)this.settings.get(this.BYPASS)).getValue()).booleanValue();
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate em = (EventMotionUpdate)event;
         double speed = Math.max((double)((Number)((Setting)this.settings.get(SPEED)).getValue()).floatValue(), getBaseMoveSpeed());
         if (em.isPre()) {
            BlockPos pos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5D, mc.thePlayer.posZ);
            if (hypickle && mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
               em.setGround(false);
            }

            this.setSuffix(((Options)((Setting)this.settings.get("MODE")).getValue()).getSelected());
            mc.thePlayer.fallDistance = 0.0F;
            String var7 = ((Options)((Setting)this.settings.get("MODE")).getValue()).getSelected();
            byte var8 = -1;
            switch(var7.hashCode()) {
            case -1984451626:
               if (var7.equals("Motion")) {
                  var8 = 2;
               }
               break;
            case -419998488:
               if (var7.equals("AntiKick")) {
                  var8 = 3;
               }
               break;
            case 68891525:
               if (var7.equals("Glide")) {
                  var8 = 0;
               }
               break;
            case 1897755483:
               if (var7.equals("Vanilla")) {
                  var8 = 1;
               }
            }

            switch(var8) {
            case 0:
               boolean shouldBlock = mc.thePlayer.posY + 0.1D >= this.startY && mc.gameSettings.keyBindJump.getIsKeyPressed();
               if (mc.thePlayer.isSneaking()) {
                  mc.thePlayer.motionY = -0.4000000059604645D;
               } else if (mc.gameSettings.keyBindJump.getIsKeyPressed() && !shouldBlock) {
                  mc.thePlayer.motionY = 0.4000000059604645D;
               } else {
                  mc.thePlayer.motionY = -0.01D;
               }
               break;
            case 1:
               mc.thePlayer.capabilities.isFlying = true;
               mc.thePlayer.capabilities.allowFlying = true;
               break;
            case 2:
               if (mc.thePlayer.movementInput.jump) {
                  mc.thePlayer.motionY = speed * 0.6D;
               } else if (mc.thePlayer.movementInput.sneak) {
                  mc.thePlayer.motionY = -speed * 0.6D;
               } else {
                  mc.thePlayer.motionY = 0.0D;
               }
               break;
            case 3:
               if (mc.thePlayer.movementInput.jump) {
                  mc.thePlayer.motionY = 0.4D;
               } else if (mc.thePlayer.movementInput.sneak) {
                  mc.thePlayer.motionY = -0.4D;
               } else {
                  mc.thePlayer.motionY = 0.0D;
               }

               this.updateFlyHeight();
               mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
               if (this.flyHeight <= 290.0D && this.kickTimer.delay(500.0F) || this.flyHeight > 290.0D && this.kickTimer.delay(100.0F)) {
                  this.goToGround();
                  this.kickTimer.reset();
               }
            }
         }
      }

      if (event instanceof EventMove) {
         EventMove em = (EventMove)event;
         String mode = ((Options)((Setting)this.settings.get("MODE")).getValue()).getSelected();
         if (!mode.equalsIgnoreCase("Vanilla")) {
            double speed = (double)((Number)((Setting)this.settings.get(SPEED)).getValue()).floatValue();
            if (hypickle) {
               speed = getBaseMoveSpeed();
               if (mc.thePlayer.motionY != 0.412274355D) {
                  em.setY(mc.thePlayer.motionY = 0.0D);
               }

               if (this.boostDelay.delay(10000.0F)) {
                  this.boostDelay.reset();
               }
            }

            float boost = ((Number)((Setting)this.settings.get(this.BOOST)).getValue()).floatValue();
            if (this.zoom > 0 && boost > 0.0F && !this.boostDelay.delay(5000.0F)) {
               mc.timer.timerSpeed = 1.0F + boost;
               if (this.zoom < 10) {
                  float percent = (float)(this.zoom / 10);
                  if ((double)percent > 0.5D) {
                     percent = 1.0F;
                  }

                  mc.timer.timerSpeed = 1.0F + boost * percent;
               }
            } else {
               mc.timer.timerSpeed = 1.0F;
            }

            --this.zoom;
            double forward = (double)mc.thePlayer.movementInput.moveForward;
            double strafe = (double)mc.thePlayer.movementInput.moveStrafe;
            float yaw = mc.thePlayer.rotationYaw;
            if (forward == 0.0D && strafe == 0.0D) {
               em.setX(0.0D);
               em.setZ(0.0D);
            } else {
               if (forward != 0.0D) {
                  if (strafe > 0.0D) {
                     yaw += (float)(forward > 0.0D ? -45 : 45);
                  } else if (strafe < 0.0D) {
                     yaw += (float)(forward > 0.0D ? 45 : -45);
                  }

                  strafe = 0.0D;
                  if (forward > 0.0D) {
                     forward = 1.0D;
                  } else if (forward < 0.0D) {
                     forward = -1.0D;
                  }
               }

               em.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
               em.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
            }
         }

         if (hypickle && mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ)).getBlock() == Blocks.air) {
            ++this.stupidAutisticTickCounting;
            if (this.stupidAutisticTickCounting == 1) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 9.51753852431554E-13D, mc.thePlayer.posZ);
            }

            if (this.stupidAutisticTickCounting == 2) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 9.51753852765344E-13D, mc.thePlayer.posZ);
            }

            if (this.stupidAutisticTickCounting == 3) {
               mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 9.0350771E-13D, mc.thePlayer.posZ);
               this.stupidAutisticTickCounting = 0;
            }
         }
      }

   }
}
