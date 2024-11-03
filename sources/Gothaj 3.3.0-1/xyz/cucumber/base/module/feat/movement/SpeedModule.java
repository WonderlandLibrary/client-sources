package xyz.cucumber.base.module.feat.movement;

import god.buddy.aot.BCompiler;
import java.util.LinkedList;
import net.minecraft.network.Packet;
import net.minecraft.potion.Potion;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventJump;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.MOVEMENT,
   description = "Allows you to walk faster",
   name = "Speed",
   key = 0
)
public class SpeedModule extends Mod {
   public KillAuraModule ka;
   public int groundTicks;
   public int velocityTicks;
   public int ticks;
   public int transTicks;
   public boolean forward;
   public boolean backward;
   public boolean left;
   public boolean right;
   public boolean shouldStrafe;
   public boolean started;
   public boolean done;
   public Timer timer = new Timer();
   public LinkedList<Packet> outPackets = new LinkedList<>();
   public LinkedList<Packet> inPackets = new LinkedList<>();
   public ModeSettings mode = new ModeSettings(
      "Mode", new String[]{"Vanilla", "Vulcan", "Vulcan Low", "Verus", "NCP", "Hypixel", "Intave", "Matrix", "Legit", "BlocksMC"}
   );
   public NumberSettings speed = new NumberSettings("Speed", 0.5, 0.1, 3.0, 0.01);
   public BooleanSettings intaveJump = new BooleanSettings("Jump", false);
   public NumberSettings intaveStrafeTicks = new NumberSettings(
      "Intave Strafe Delay", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 3.0, 1.0, 15.0, 1.0
   );
   public NumberSettings intaveOffset = new NumberSettings("Intave Offset", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 0.02, 0.0, 0.25, 0.01);
   public NumberSettings intaveMinSpeed = new NumberSettings("Intave Min Speed", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 0.02, 0.0, 0.25, 0.01);
   public NumberSettings timerTimer = new NumberSettings("Timer", 1.0, 1.0, 2.0, 0.001);
   public BooleanSettings intaveSmart = new BooleanSettings("Intave Smart", () -> this.mode.getMode().equalsIgnoreCase("Intave"), true);
   public BooleanSettings intaveSmartSafe = new BooleanSettings("Intave Smart Safe", () -> this.mode.getMode().equalsIgnoreCase("Intave"), true);
   public NumberSettings intaveSmartDelay = new NumberSettings("Intave Smart Delay", () -> this.mode.getMode().equalsIgnoreCase("Intave"), 2.0, 0.0, 15.0, 1.0);
   public BooleanSettings vulcanFast = new BooleanSettings("Vulcan Fast", () -> this.mode.getMode().equalsIgnoreCase("Vulcan"), true);

   public SpeedModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.mode,
            this.speed,
            this.intaveJump,
            this.intaveStrafeTicks,
            this.intaveOffset,
            this.intaveMinSpeed,
            this.timerTimer,
            this.intaveSmart,
            this.intaveSmartSafe,
            this.intaveSmartDelay,
            this.vulcanFast
         }
      );
   }

   @Override
   public void onDisable() {
      this.mc.thePlayer.speedInAir = 0.02F;
      this.mc.timer.timerSpeed = 1.0F;

      for (Packet p : this.outPackets) {
         if (p != null) {
            this.mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
         }
      }

      this.outPackets.clear();

      for (Packet px : this.inPackets) {
         if (px != null) {
            px.processPacket(this.mc.getNetHandler().getNetworkManager().getNetHandler());
         }
      }

      this.inPackets.clear();
      RotationUtils.customRots = false;
   }

   @Override
   public void onEnable() {
      String var1;
      switch ((var1 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var1.equals("intave")) {
               this.started = false;
            }
         default:
            this.timer.reset();
            this.ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMotion(EventMotion e) {
      this.setInfo(this.mode.getMode());
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave") && e.getType() == EventType.POST) {
               if (!this.intaveJump.isEnabled() && !this.mc.thePlayer.onGround) {
                  return;
               }

               if (MovementUtils.isMoving()) {
                  float[] inputs = MovementUtils.silentStrafe(
                     this.mc.thePlayer.movementInput.moveStrafe, this.mc.thePlayer.movementInput.moveForward, RotationUtils.serverYaw, true
                  );
                  if (!RotationUtils.customRots) {
                     inputs[1] = this.mc.thePlayer.movementInput.moveForward;
                     inputs[0] = this.mc.thePlayer.movementInput.moveStrafe;
                  }

                  if (!this.ka.MoveFix.getMode().equalsIgnoreCase("Silent")) {
                     inputs[1] = this.mc.thePlayer.movementInput.moveForward;
                     inputs[0] = this.mc.thePlayer.movementInput.moveStrafe;
                  }

                  float intaveSpeed = (float)Math.max(this.intaveMinSpeed.getValue(), MovementUtils.getSpeed());
                  this.mc.thePlayer.isSprinting();
                  this.ticks++;
                  if ((this.velocityTicks >= 10 || this.mc.thePlayer.hurtTime == 0) && !this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     if ((double)this.ticks > this.intaveStrafeTicks.getValue()
                        && MovementUtils.getSpeed() <= MovementUtils.getBaseMoveSpeed() / (1.0 + this.intaveOffset.getValue())) {
                        MovementUtils.strafe(
                           intaveSpeed, RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw, inputs[1], inputs[0]
                        );
                        this.ticks = 0;
                        this.timer.reset();
                        this.shouldStrafe = false;
                     }

                     if (this.shouldStrafe
                        && (double)this.ticks > this.intaveSmartDelay.getValue()
                        && (
                           MovementUtils.getSpeed() <= MovementUtils.getBaseMoveSpeed() / (1.0 + this.intaveOffset.getValue())
                              || !this.intaveSmartSafe.isEnabled()
                        )) {
                        MovementUtils.strafe(
                           intaveSpeed, RotationUtils.customRots ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw, inputs[1], inputs[0]
                        );
                        this.timer.reset();
                        this.ticks = 0;
                        this.shouldStrafe = false;
                     }
                  }
               } else {
                  this.mc.timer.timerSpeed = 1.0F;
               }
            }
            break;
         case -1081239615:
            if (var2.equals("matrix") && e.getType() == EventType.PRE) {
               float speed = (float)Math.max(MovementUtils.getBaseMoveSpeed() / 1.25, MovementUtils.getSpeed());
               if (this.mc.thePlayer.isSprinting()) {
                  speed = (float)MovementUtils.getSpeed();
               }

               if (MovementUtils.isMoving()) {
                  if (this.mc.thePlayer.onGround) {
                     MovementUtils.strafe(speed);
                     this.mc.thePlayer.jump();
                     this.shouldStrafe = true;
                  }

                  if (this.mc.thePlayer.fallDistance > 0.0F && this.shouldStrafe) {
                     this.shouldStrafe = false;
                     MovementUtils.strafe(speed);
                  }
               }
            }
            break;
         case -805359837:
            if (var2.equals("vulcan") && e.getType() == EventType.PRE) {
               if (this.mc.thePlayer.onGround) {
                  this.groundTicks = 0;
               } else {
                  this.groundTicks++;
               }

               float yaw = RotationUtils.customRots
                  ? (this.ka.MoveFix.getMode().equalsIgnoreCase("Legit") ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw)
                  : this.mc.thePlayer.rotationYaw;
               float speedModifier = this.vulcanFast.isEnabled() ? 0.0F : 0.05F;
               if (MovementUtils.isMoving()) {
                  switch (this.groundTicks) {
                     case 0:
                        if (!this.mc.gameSettings.keyBindJump.pressed) {
                           this.mc.thePlayer.jump();
                        }

                        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                           MovementUtils.strafe(0.6F - speedModifier, (float)MovementUtils.getDirection(yaw));
                        } else {
                           MovementUtils.strafe(0.485F - speedModifier, (float)MovementUtils.getDirection(yaw));
                        }
                        break;
                     case 1:
                     case 2:
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                     case 3:
                     case 4:
                     case 6:
                     case 7:
                     case 8:
                     default:
                        break;
                     case 5:
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                        break;
                     case 9:
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                  }
               }
            }
            break;
         case -664563300:
            if (var2.equals("blocksmc") && e.getType() == EventType.PRE) {
               if (!MovementUtils.isMoving()) {
                  this.groundTicks = 0;
                  return;
               }

               if (this.mc.thePlayer.onGround) {
                  this.groundTicks = 0;
               } else {
                  this.groundTicks++;
               }

               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
               }

               MovementUtils.strafe(
                  (float)(
                     this.mc.gameSettings.keyBindForward.isPressed()
                        ? (double)((float)MovementUtils.getBaseMoveSpeed()) * 1.2
                        : MovementUtils.getBaseMoveSpeed() * 0.9
                  )
               );
            }
            break;
         case 108891:
            if (var2.equals("ncp") && e.getType() == EventType.PRE) {
               if (MovementUtils.isMoving()) {
                  if (this.mc.thePlayer.onGround) {
                     this.mc.thePlayer.jump();
                  }

                  MovementUtils.strafe((float)Math.max(MovementUtils.getBaseMoveSpeed() / 1.1, MovementUtils.getSpeed()));
               } else {
                  this.mc.thePlayer.motionX = 0.0;
                  this.mc.thePlayer.motionZ = 0.0;
               }
            }
            break;
         case 102851513:
            if (var2.equals("legit") && e.getType() == EventType.PRE && this.mc.thePlayer.onGround && MovementUtils.isMoving()) {
               this.mc.thePlayer.jump();
            }
            break;
         case 112097665:
            if (var2.equals("verus")) {
               if (MovementUtils.isMoving()) {
                  if (this.mc.thePlayer.onGround) {
                     this.mc.thePlayer.jump();
                  }

                  MovementUtils.strafe(
                     (float)(this.mc.gameSettings.keyBindForward.pressed ? MovementUtils.getBaseMoveSpeed() * 1.3 : MovementUtils.getBaseMoveSpeed())
                  );
               } else {
                  this.mc.thePlayer.motionX = 0.0;
                  this.mc.thePlayer.motionZ = 0.0;
               }
            }
            break;
         case 233102203:
            if (var2.equals("vanilla") && e.getType() == EventType.PRE) {
               this.mc.thePlayer.motionX = this.mc.thePlayer.motionZ = 0.0;
               if (MovementUtils.isMoving()) {
                  MovementUtils.strafe((float)this.speed.getValue());
               }

               if (this.mc.thePlayer.onGround && MovementUtils.isMoving()) {
                  this.mc.thePlayer.jump();
               }
            }
            break;
         case 1355617495:
            if (var2.equals("vulcan low") && e.getType() == EventType.PRE) {
               if (this.mc.thePlayer.onGround) {
                  this.groundTicks = 0;
               } else {
                  this.groundTicks++;
               }

               float yaw = RotationUtils.customRots
                  ? (this.ka.MoveFix.getMode().equalsIgnoreCase("Legit") ? RotationUtils.serverYaw : this.mc.thePlayer.rotationYaw)
                  : this.mc.thePlayer.rotationYaw;
               float speedModifier = this.vulcanFast.isEnabled() ? 0.0F : 0.05F;
               if (MovementUtils.isMoving()) {
                  switch (this.groundTicks) {
                     case 0:
                        if (!this.mc.gameSettings.keyBindJump.pressed) {
                           this.mc.thePlayer.jump();
                        }

                        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                           MovementUtils.strafe(0.6F - speedModifier, (float)MovementUtils.getDirection(yaw));
                        } else {
                           MovementUtils.strafe(0.485F - speedModifier, (float)MovementUtils.getDirection(yaw));
                        }
                        break;
                     case 1:
                     case 2:
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                     case 3:
                     case 4:
                     case 6:
                     case 7:
                     case 8:
                     default:
                        break;
                     case 5:
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                        this.mc.thePlayer.motionY = MovementUtils.getPredictedMotionY(this.mc.thePlayer.motionY);
                        break;
                     case 9:
                        MovementUtils.strafe((float)MovementUtils.getSpeed(), (float)MovementUtils.getDirection(yaw));
                  }
               }
            }
            break;
         case 1381910549:
            if (var2.equals("hypixel") && e.getType() == EventType.PRE && MovementUtils.isMoving()) {
               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
                  MovementUtils.strafe(
                     (float)Math.max(
                        this.mc.thePlayer.isSprinting() ? MovementUtils.getBaseMoveSpeed() * 1.65 : MovementUtils.getBaseMoveSpeed() * 1.8,
                        MovementUtils.getSpeed()
                     )
                  );
               } else if (this.mc.thePlayer.hurtTime == 9) {
                  MovementUtils.strafe(
                     (float)Math.max(
                        this.mc.thePlayer.isSprinting() ? MovementUtils.getBaseMoveSpeed() * 1.0 : MovementUtils.getBaseMoveSpeed() * 1.0,
                        MovementUtils.getSpeed()
                     )
                  );
               }
            }
      }
   }

   @EventListener
   public void onJump(EventJump e) {
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onMoveButton(EventMoveButton e) {
      String var2;
      switch ((var2 = this.mode.getMode().toLowerCase()).hashCode()) {
         case -1183766399:
            if (var2.equals("intave") && MovementUtils.isMoving()) {
               if (this.intaveJump.isEnabled()) {
                  e.jump = true;
               }

               if (this.intaveSmart.isEnabled()) {
                  if (this.forward && !e.forward || !this.forward && e.forward) {
                     this.shouldStrafe = true;
                  }

                  if (this.backward && !e.backward || !this.forward && e.backward) {
                     this.shouldStrafe = true;
                  }

                  if (this.left && !e.left || !this.forward && e.left) {
                     this.shouldStrafe = true;
                  }

                  if (this.right && !e.right || !this.forward && e.right) {
                     this.shouldStrafe = true;
                  }
               }

               this.forward = e.forward;
               this.backward = e.backward;
               this.left = e.left;
               this.right = e.right;
            }
            break;
         case -1081239615:
            if (!var2.equals("matrix")) {
            }
            break;
         case -805359837:
            if (!var2.equals("vulcan")) {
            }
            break;
         case 108891:
            if (!var2.equals("ncp")) {
            }
            break;
         case 112097665:
            if (!var2.equals("verus")) {
            }
            break;
         case 233102203:
            if (!var2.equals("vanilla")) {
            }
      }
   }
}
