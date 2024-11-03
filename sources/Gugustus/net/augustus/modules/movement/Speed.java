package net.augustus.modules.movement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.augustus.Augustus;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventPostMotion;
import net.augustus.events.EventPreMotion;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventSendPacket;
import net.augustus.events.EventSilentMove;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.SigmaBlockUtils;
import net.augustus.utils.SigmaMoveUtils;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class Speed extends Module {
   public StringValue mode = new StringValue(
      1, "Mode", this, "GroundStrafe", new String[]{"Vanilla", "VanillaHop", "LegitHop", "LegitAbuse", "Matrix", "TeleportAbuse", "NCP", "Test", "Verus", "NoCheatMinus", "GamerCheatOG", "GamerCheatBHop", "CheatmineSafe", "CheatmineRage", "NCPRace", "NCPWtf", "NCPLow", "GroundStrafe", "Strafe", "FixedStrafe", "IntaveStrafe", "GrimTest"}
   );
   public DoubleValue vanillaSpeed = new DoubleValue(2, "Speed", this, 1.0, 0.0, 10.0, 2);
   public DoubleValue vanillaHeight = new DoubleValue(3, "Height", this, 0.2, 0.01, 0.42, 2);
   public DoubleValue dmgSpeed = new DoubleValue(4, "DMGSpeed", this, 1.0, 0.0, 2.0, 2);
   public BooleanValue damageBoost = new BooleanValue(5, "DamageBoost", this, false);
   public BooleanValue strafe = new BooleanValue(6, "Strafe", this, false);
   private int tickCounter;
   private int tickCounter2;
   private int ticks = 0;
   private float speedYaw;
   private boolean wasOnGround;
   public int stage = 0;
   public boolean collided = false;
   public double stair = 0D;
   private double speed = 0D;
   private boolean lessSlow = false;
   public double less = 0D;
   private TimeHelper timer = new TimeHelper();
   private TimeHelper lastCheck = new TimeHelper();
   private boolean shouldslow;
   private boolean polared;
   private boolean first;
   private double lastDist;
   private int offGround;
   private int motionDelay;
   private boolean groundBoost = false;
   private int groundTicks = 0;
   public ArrayList<Packet> packets = new ArrayList<Packet>();

   public Speed() {
      super("Speed", new Color(74, 133, 93), Categorys.MOVEMENT);
   }

   @Override
   public void onEnable() {
      this.lastDist = 0.0D;
      first = true;
      boolean player = (mc.thePlayer == null);
      this.collided = !player && mc.thePlayer.isCollidedHorizontally;
      this.lessSlow = false;
      if (mc.thePlayer != null)
         this.speed = SigmaMoveUtils.defaultSpeed();
      this.less = 0.0D;
      stage = 2;

      mc.getTimer().timerSpeed = 1.0F;
      if (mc.thePlayer != null) {
         mc.thePlayer.jumpMovementFactor = 0.02F;
         mc.thePlayer.setSpeedInAir(0.02F);
         mc.thePlayer.setSpeedOnGround(0.1F);
      }

      this.tickCounter2 = -2;
      this.tickCounter = 0;
      this.ticks = 0;
   }

   @Override
   public void onDisable() {
      mc.getTimer().timerSpeed = 1.0F;
      if (mc.thePlayer != null) {
         mc.thePlayer.jumpMovementFactor = 0.02F;
         mc.thePlayer.setSpeedInAir(0.02F);
         mc.thePlayer.setSpeedOnGround(0.1F);
      }

      this.tickCounter2 = -2;
      this.tickCounter = 0;
   }
   @EventTarget
   public void onSend(EventSendPacket eventSendPacket) {}
   @EventTarget
   public void onRecv(EventReadPacket e) {
      if(e.getPacket() instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)e.getPacket();
         if (this.lastCheck.reached(300L)) {
            pac.yaw = mc.thePlayer.rotationYaw;
            pac.pitch = mc.thePlayer.rotationPitch;
         }
         stage = -4;
         this.lastCheck.reset();
      }
   }
   @EventTarget
   public void onEventPostMotion(EventPostMotion eventPostMotion) {
      if(!mc.thePlayer.onGround) {
         offGround++;
      } else {
         offGround = 0;
      }
      switch (mode.getSelected()) {
         case "CheatmineSafe": {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.motionY = 0.38;
               MoveUtil.strafe();
            }
            break;
         }
         case "CheatmineRage": {
            if (mc.thePlayer.onGround) {
               mc.thePlayer.jump();
               mc.thePlayer.motionY = 0.35;
            }
            break;
         }
      }
   }
   @EventTarget
   public void onEventPreMotion(EventPreMotion eventPreMotion) {
       	if(mm.arrayList.mode.getSelected().equalsIgnoreCase("Default")) {
 	      this.setDisplayName(this.getName() + "  " + this.mode.getSelected());
  	 }else {
  		 this.setDisplayName(this.getName() + this.mode.getSelected());
  	 }
      this.speedYaw = mm.targetStrafe.target != null && mm.targetStrafe.isToggled()
         ? mm.targetStrafe.moveYaw
         : Augustus.getInstance().getYawPitchHelper().realYaw;
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "GroundStrafe": {
            groundStrafe();
            break;
         }
         case "Strafe": {
            strafe();
            break;
         }
         case "FixedStrafe": {
            fixedstrafe();
            break;
         }
         case "NCPLow": {
            ncplow();
            break;
         }
         case "NCPWtf": {
            ncpwtf();
            break;
         }
         case "NCPRace": {
            ncprace();
            break;
         }
         case "GamerCheatBHop": {
            gamercheatbhop();
            break;
         }
         case "GamerCheatOG": {
            gamercheatog();
            break;
         }
         case "NoCheatMinus": {
            nocheatminus();
            break;
         }
         case "Verus":
            verus();
            break;
         case "Vanilla":
            vonground();
            break;
         case "VanillaHop":
            vbhop();
            break;
         case "LegitAbuse": {
            legitAbuse();
            break;
         }
         case "TeleportAbuse": {
            teleportAbuse();
            break;
         }
         case "Matrix":
            matrix();
            break;
         case "Test":
            test();
            break;
         case "IntaveStrafe":
            iacStrafe();
            break;
         case "GrimTest": {
        	 if(isMoving()) {
        	 }
        	 break;
         }
      }
   }

   private void iacStrafe() {
      mc.gameSettings.keyBindJump.pressed = true;
      if (this.offGround >= 10 && this.offGround % 5 == 0) {
         MoveUtil.strafe();
      }
   }

   private void groundStrafe() {
      if (mc.thePlayer.onGround) {
         mc.thePlayer.jump();
         MoveUtil.strafe();
      }
   }

   private void strafe() {
      if (mc.thePlayer.onGround) {
         mc.thePlayer.jump();
      }
      MoveUtil.strafe();
   }

   private void fixedstrafe() {
      if (mc.thePlayer.onGround) {
         mc.thePlayer.jump();
      }
      MoveUtil.setSpeed((float) SigmaMoveUtils.defaultSpeed());
   }

   private void ncpwtf() {
      boolean hasSpeed = mc.thePlayer.isPotionActive(Potion.moveSpeed);
      double speedAmplifier = 0;
      if(hasSpeed)
         speedAmplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
      MoveUtil.strafe();

      mc.thePlayer.motionX *= 0.0045 + 1 + speedAmplifier * 0.02F;
      mc.thePlayer.motionZ *= 0.0045 + 1 + speedAmplifier * 0.02F;

      boolean pushDown = true;

      if(mc.thePlayer.onGround && isMoving() && !mc.gameSettings.keyBindJump.pressed) {
         float boost = (float) (speedAmplifier * 0.065F);
         mc.thePlayer.jump();
         if(!pushDown) return;
         mc.timer.timerSpeed = 1.405F;
         MoveUtil.strafe((mc.thePlayer.ticksExisted % 10 > 7 ? 0.4F : 0.325F) + boost);
         mc.thePlayer.motionX *= 0.01 + 1 + speedAmplifier * 0.175F;
         mc.thePlayer.motionZ *= 0.01 + 1 + speedAmplifier * 0.175F;
         mc.thePlayer.cameraPitch = 0;
         mc.thePlayer.cameraYaw = 0;
      } else if (!mc.thePlayer.onGround && mc.thePlayer.motionY > 0.3 && !mc.gameSettings.keyBindJump.pressed) {
         mc.timer.timerSpeed = 0.85F;
         mc.thePlayer.motionY = -0.42;
         mc.thePlayer.posY -= 0.45;
         mc.thePlayer.cameraPitch = 0;
      }

      mc.thePlayer.stepHeight = 0.5F;

      MoveUtil.strafe();
   }

   private void ncprace() {
      if (mc.thePlayer.onGround) {
         mc.thePlayer.jump();
         mc.timer.timerSpeed = 1.2F;
         MoveUtil.multiplyXZ(1.0708D);
         mc.thePlayer.moveStrafing *= 2.0F;
         return;
      }
      mc.timer.timerSpeed = 0.98F;
      mc.thePlayer.jumpMovementFactor = 0.0265F;
   }

   private void gamercheatbhop() {
      MoveUtil.setSpeed(0.6F);
      if (mc.thePlayer.isCollidedVertically) {
         MoveUtil.setSpeed(0.2F);
         mc.thePlayer.motionY = 0.35D;
      }
   }

   private void gamercheatog() {
      double y1;
      MoveUtil.setSpeed(0.56F);
      mc.thePlayer.motionY = 0.0D;
      if (mc.thePlayer.ticksExisted % 3 == 0) {
         double d = mc.thePlayer.posY - 1.0E-10D;
         mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, d, mc.thePlayer.posZ, true));
      }
      y1 = mc.thePlayer.posY + 1.0E-10D;
      mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);
   }

   private void nocheatminus() {
      if (mc.thePlayer.moveForward == 0.0F && mc.thePlayer.moveStrafing == 0.0F)
         this.speed = SigmaMoveUtils.defaultSpeed();
      if (stage == 1 && mc.thePlayer.isCollidedVertically && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         this.speed = 0.25D + SigmaMoveUtils.defaultSpeed() - 0.01D;
      } else if (!SigmaBlockUtils.isInLiquid() && stage == 2 && mc.thePlayer.isCollidedVertically && SigmaMoveUtils.isOnGround(0.001D) && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         mc.thePlayer.motionY = 0.4D;
         //em.setY(0.4D);
         mc.thePlayer.jump();
         this.speed *= 2.149D;
      } else if (stage == 3) {
         double difference = 0.66D * (this.lastDist - SigmaMoveUtils.defaultSpeed());
         this.speed = this.lastDist - difference;
      } else {
         List<AxisAlignedBB> collidingList = mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D));
         if ((collidingList.size() > 0 || mc.thePlayer.isCollidedVertically) &&
                 stage > 0)
            if (1.35D * SigmaMoveUtils.defaultSpeed() - 0.01D > this.speed) {
               stage = 0;
            } else {
               stage = (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F) ? 1 : 0;
            }
         this.speed = this.lastDist - this.lastDist / 159.0D;
      }
      this.speed = Math.max(this.speed, SigmaMoveUtils.defaultSpeed());
      if (stage > 0) {
         if (SigmaBlockUtils.isInLiquid())
            this.speed = 0.1D;
         MoveUtil.setSpeed((float) speed);
      }
      if (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)
         stage++;
   }

   private void verus() {
      MoveUtil.setSpeed2(0.292F);
      if (this.damageBoost.getBoolean() && mc.thePlayer.hurtTime != 0 && mc.thePlayer.fallDistance < 3.0F) {
         MoveUtil.setSpeed2((double)((float)this.dmgSpeed.getValue()));
      } else {
         MoveUtil.setSpeed2(0.292F);
      }

      if (this.canJump()) {
         mc.thePlayer.jump();
      } else {
         mc.thePlayer.jumpMovementFactor = 0.1F;
      }
   }

   private void vonground() {
      if (this.isMoving()) {
         MoveUtil.setSpeed((float)(0.1 * this.vanillaSpeed.getValue()), this.strafe.getBoolean());
      }
   }

   private void vbhop() {
      if (this.canJump()) {
         mc.thePlayer.motionY = this.vanillaHeight.getValue();
         if (this.strafe.getBoolean()) {
            MoveUtil.strafe();
         }
      } else if (this.isMoving()) {
         MoveUtil.setSpeed((float)(0.1 * this.vanillaSpeed.getValue()), this.strafe.getBoolean());
      }
   }

   private void legitAbuse() {
      if(isMoving()) {
         if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
         }
         KeyBinding[] gameSettings = {mc.gameSettings.keyBindForward, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindBack, mc.gameSettings.keyBindLeft};
         int[] down = {0};
         Arrays.<KeyBinding>stream(gameSettings).forEach(keyBinding -> down[0] = down[0] + (keyBinding.isKeyDown() ? 1 : 0));
         boolean active = (down[0] == 1);
         if (!active)
            return;
         double increase = mc.thePlayer.onGround ? 0.0026000750109401644D : 5.199896488849598E-4D;
         double yaw = MoveUtil.direction();
         mc.thePlayer.motionX += -MathHelper.sin((float) yaw) * increase;
         mc.thePlayer.motionZ += MathHelper.cos((float) yaw) * increase;
      }
   }

   private void teleportAbuse() {
      if(mc.thePlayer.onGround) {
         mc.thePlayer.jump();
         if(!first) {
            mc.timer.timerSpeed = 30F;
         } else {
            mc.timer.timerSpeed = 5F;
         }
         first = false;
      } else {
         mc.timer.timerSpeed = 0.3F;
      }
   }

   private void matrix() {
      ++this.tickCounter;
      if (mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0) {
         return;
      }

      if (MoveUtil.isMoving()) {
         return;
      }

      mc.thePlayer.motionY -= 0.009999F;
      if (mc.thePlayer.onGround) {
         this.tickCounter = 0;
         MoveUtil.strafeMatrix();
      } else if (mc.thePlayer.movementInput.moveForward > 0.0F && mc.thePlayer.movementInput.moveStrafe != 0.0F) {
         mc.thePlayer.setSpeedInAir(0.02F);
      } else {
         mc.thePlayer.setSpeedInAir(0.0208F);
      }
   }

   private void test() {
      if (mc.thePlayer.onGround) {
         this.tickCounter = 0;
      }

      if (MoveUtil.isMoving() && mm.killAura.target == null && !mm.longJump.isToggled()) {
         if (mc.thePlayer.isUsingItem()) {
            if (mc.thePlayer.isBlocking()) {
               mc.getTimer().timerSpeed = 1.23F;
            } else if (mc.getTimer().timerSpeed > 1.0F) {
               mc.getTimer().timerSpeed = 1.0F;
            }
         } else {
            mc.getTimer().timerSpeed = 1.23F;
         }
      } else if (mc.getTimer().timerSpeed > 1.0F) {
         mc.getTimer().timerSpeed = 1.0F;
      }

      if (mc.thePlayer.hurtTime > 8
              && !mm.longJump.isToggled()
              && !mc.thePlayer.isBurning()
              && mc.thePlayer.fallDistance < 2.0F
              && !mc.thePlayer.isPotionActive(Potion.wither)
              && !mc.thePlayer.isPotionActive(Potion.poison)) {
         MoveUtil.addSpeed(0.4F, false);
      }

      ++this.tickCounter;
   }

   private void ncplow() {
      if (isMoving()) {
         if (mc.gameSettings.keyBindJump.pressed)
            return;
         if (mc.thePlayer.onGround) {
            this.motionDelay++;
            this.motionDelay %= 3;
            if (this.motionDelay == 0) {
               mc.thePlayer.motionY += 0.18000000715255737D;
               mc.thePlayer.motionX *= 1.2000000476837158D;
               mc.thePlayer.motionZ *= 1.2000000476837158D;
            }
         }
         if (!mc.thePlayer.onGround) {
            mc.thePlayer.motionX *= 1.0499999523162842D;
            mc.thePlayer.motionZ *= 1.0499999523162842D;
         }
         mc.thePlayer.speedInAir = 0.022F;
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      String var2 = this.mode.getSelected();
      byte var3 = -1;
      switch(var2.hashCode()) {
         case 77115:
            if (var2.equals("NCP")) {
               var3 = 0;
            }
         default:
            switch(var3) {
               case 0:
                  if (MoveUtil.isMoving()) {
                     if (mc.thePlayer.onGround) {
                        mc.thePlayer.jump();
                        mc.thePlayer.motionX *= 1.01;
                        mc.thePlayer.motionZ *= 1.01;
                        mc.thePlayer.setSpeedInAir(0.022F);
                     }

                     mc.thePlayer.motionY -= 9.9999E-4;
                     MoveUtil.strafe();
                  } else {
                     mc.thePlayer.motionX = 0.0;
                     mc.thePlayer.motionZ = 0.0;
                  }

                  mc.getTimer().timerSpeed = 1.0865F;
            }
      }
   }

   @EventTarget
   public void onEventSilentMove(EventSilentMove eventSilentMove) {
      String var2 = this.mode.getSelected();
      switch(var2) {
         case "Matrix2":
         case "LegitHop":
         case "Matrix":
            if (this.isMoving()) {
               mc.thePlayer.movementInput.jump = true;
            }
      }
   }

   @EventTarget
   public void onEventMove(EventMove eventMove) {
      if (mc.thePlayer.fallDistance > 4.0F && !this.mode.getSelected().equals("LegitHop")) {
         eventMove.setCanceled(true);
      }

      if (!BlockUtil.isScaffoldToggled()) {
         eventMove.setYaw(
            mm.targetStrafe.target != null && mm.targetStrafe.isToggled()
               ? mm.targetStrafe.moveYaw
               : (
                  mm.killAura.isToggled() && mm.killAura.target != null && mm.killAura.moveFix.getBoolean()
                     ? mc.thePlayer.rotationYaw
                     : Augustus.getInstance().getYawPitchHelper().realYaw
               )
         );
      }
   }

   @EventTarget
   public void onEventJump(EventJump eventJump) {
      if (!BlockUtil.isScaffoldToggled()) {
         eventJump.setYaw(
            mm.targetStrafe.target != null && mm.targetStrafe.isToggled()
               ? mm.targetStrafe.moveYaw
               : (
                  mm.killAura.isToggled() && mm.killAura.target != null && mm.killAura.moveFix.getBoolean()
                     ? mc.thePlayer.rotationYaw
                     : Augustus.getInstance().getYawPitchHelper().realYaw
               )
         );
      }
   }

   private boolean isMoving() {
      return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F && !mc.thePlayer.isCollidedHorizontally;
   }

   private boolean canJump() {
      return this.isMoving() && mc.thePlayer.onGround;
   }
}
