package xyz.cucumber.base.module.feat.combat;

import god.buddy.aot.BCompiler;
import java.io.IOException;
import java.util.LinkedList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.util.MathHelper;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.events.ext.EventTimeDelay;
import xyz.cucumber.base.events.ext.EventWorldChange;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.feat.player.ScaffoldModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.math.RotationUtils;

@ModuleInfo(
   category = Category.COMBAT,
   description = "Allows you to teleport to player",
   name = "Timer Range",
   key = 0,
   priority = ArrayPriority.HIGH
)
public class TimerRangeModule extends Mod {
   private LinkedList<Packet> outPackets = new LinkedList<>();
   private KillAuraModule killAura;
   private double balance;
   private double lastBalance;
   private double smartMaxBalance;
   private boolean fast;
   private EntityLivingBase target;
   private Timer delayTimer = new Timer();
   private Timer attackTimer = new Timer();
   public NumberSettings minRange = new NumberSettings("Min Range", 6.0, 3.0, 6.0, 0.01);
   public NumberSettings maxRange = new NumberSettings("Max Range", 6.0, 3.0, 6.0, 0.01);
   public NumberSettings maxTimer = new NumberSettings("Timer", 10.0, 1.0, 10.0, 0.1);
   public NumberSettings slowTimer = new NumberSettings("Slow Timer", 0.0, 0.0, 1.0, 0.01);
   public NumberSettings chargeMultiplier = new NumberSettings("Charge Multiplier", 1.0, 0.0, 1.0, 0.01);
   public NumberSettings delay = new NumberSettings("Delay", 200.0, 0.0, 3000.0, 50.0);
   public BooleanSettings instantTimer = new BooleanSettings("Instant Teleport", true);
   public BooleanSettings notInCombo = new BooleanSettings("Not In Combo", true);
   public BooleanSettings onlyForward = new BooleanSettings("Only Forward", true);
   public BooleanSettings preLoad = new BooleanSettings("Pre Load", false);
   public BooleanSettings legitPreload = new BooleanSettings("Legit preload", () -> this.preLoad.isEnabled(), false);
   public BooleanSettings onlyOnGround = new BooleanSettings("Only On Ground", false);
   public BooleanSettings noFluid = new BooleanSettings("No Fluid", true);

   public TimerRangeModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.minRange,
            this.maxRange,
            this.maxTimer,
            this.slowTimer,
            this.chargeMultiplier,
            this.delay,
            this.instantTimer,
            this.notInCombo,
            this.onlyForward,
            this.preLoad,
            this.legitPreload,
            this.onlyOnGround,
            this.noFluid
         }
      );
   }

   @Override
   public void onEnable() {
      this.killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
   }

   @Override
   public void onDisable() {
      this.outPackets.clear();
      this.mc.timer.timerSpeed = 1.0F;
   }

   @EventListener
   public void onWorldChange(EventWorldChange e) {
      this.balance = 0.0;
      this.lastBalance = 0.0;
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onTick(EventTick e) {
      this.setInfo(String.valueOf(this.maxRange.getValue()));
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         if (this.preLoad.isEnabled()) {
            if (this.mc.timer.timerSpeed != 1.0F) {
               this.balance = this.balance + this.chargeMultiplier.getValue();
            } else {
               this.balance++;
            }
         } else if (this.fast) {
            this.balance = this.balance + this.chargeMultiplier.getValue();
         } else {
            this.balance++;
         }
      }
   }

   @EventListener
   public void onTickDelay(EventTimeDelay e) {
      if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
         this.balance--;
      }
   }

   @EventListener
   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void onGameLoop(EventGameLoop e) {
      this.target = EntityUtils.getTargetBox(
         this.maxRange.getValue() + 10.0,
         this.killAura.Targets.getMode(),
         "Off",
         (int)this.killAura.switchDelay.getValue(),
         Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
         this.killAura.TroughWalls.isEnabled(),
         this.killAura.attackInvisible.isEnabled(),
         this.killAura.attackDead.isEnabled()
      );
      if (this.mc != null
         && this.mc.thePlayer != null
         && this.mc.theWorld != null
         && this.killAura != null
         && this.killAura.isEnabled()
         && !Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()
         && this.mc.thePlayer.ticksExisted >= 10) {
         if (this.target != null && this.outOfRange()) {
            this.target = null;
         }

         if (this.fast) {
            if (this.preLoad.isEnabled() ? this.balance < this.lastBalance : this.balance < this.smartMaxBalance + this.lastBalance) {
               if (this.target != null) {
                  if (!this.isTargetCloseOrVisible()) {
                     if (this.isHurtTime()) {
                        if (this.instantTimer.isEnabled()) {
                           try {
                              boolean shouldStop = false;

                              while (!shouldStop) {
                                 if (this.preLoad.isEnabled()
                                    ? this.balance >= this.lastBalance || !this.legitPreload.isEnabled() && this.shouldStop()
                                    : this.shouldStop() || this.balance >= this.smartMaxBalance + this.lastBalance) {
                                    shouldStop = true;
                                    this.delayTimer.reset();
                                    this.mc.timer.timerSpeed = 1.0F;
                                    this.fast = false;
                                    if (this.preLoad.isEnabled()) {
                                       this.delayTimer.reset();
                                    }

                                    if (this.attackTimer.hasTimeElapsed(350.0, false)) {
                                       this.killAura.canWork();
                                       this.killAura.calculateRots();
                                       this.killAura.attackTimes++;
                                       this.killAura.attackLoop();
                                       this.killAura.attackTimes--;
                                       this.attackTimer.reset();
                                    }
                                 }

                                 if (!shouldStop) {
                                    this.mc.runTick();
                                    this.balance = this.balance + this.chargeMultiplier.getValue();
                                 }
                              }
                           } catch (IOException var3) {
                           }
                        } else {
                           this.mc.timer.timerSpeed = (float)this.maxTimer.getValue();
                           if (this.shouldStop() && this.fast) {
                              this.mc.timer.timerSpeed = 1.0F;
                              this.fast = false;
                              if (this.preLoad.isEnabled()) {
                                 this.delayTimer.reset();
                              }
                           }
                        }
                     } else if (!this.preLoad.isEnabled()) {
                        this.mc.timer.timerSpeed = 1.0F;
                        this.fast = false;
                        if (this.preLoad.isEnabled()) {
                           this.delayTimer.reset();
                        }
                     }
                  } else {
                     this.mc.timer.timerSpeed = 1.0F;
                     this.fast = false;
                     if (this.preLoad.isEnabled()) {
                        this.delayTimer.reset();
                     }

                     if (this.attackTimer.hasTimeElapsed(350.0, false)) {
                        this.killAura.canWork();
                        this.killAura.calculateRots();
                        this.killAura.attackTimes++;
                        this.killAura.attackLoop();
                        this.killAura.attackTimes--;
                        this.attackTimer.reset();
                     }
                  }
               } else {
                  this.mc.timer.timerSpeed = 1.0F;
                  this.fast = false;
                  if (this.preLoad.isEnabled()) {
                     this.delayTimer.reset();
                  }
               }
            } else {
               this.mc.timer.timerSpeed = 1.0F;
               this.fast = false;
               if (this.preLoad.isEnabled()) {
                  this.delayTimer.reset();
               }
            }
         }

         if (!this.fast) {
            if (this.preLoad.isEnabled()) {
               if (!this.delayTimer.hasTimeElapsed(this.delay.getValue(), false)) {
                  return;
               }

               if (this.target != null) {
                  if (!this.shouldStop() && (this.mc.timer.timerSpeed == 1.0F || this.mc.timer.timerSpeed == (float)this.slowTimer.getValue())) {
                     this.setSmartBalance();
                  }

                  if (this.isTargetCloseOrVisible() || !this.isHurtTime()) {
                     if (this.mc.timer.timerSpeed != (float)this.slowTimer.getValue()) {
                        this.lastBalance = this.balance;
                     }

                     this.mc.timer.timerSpeed = 1.0F;
                  } else if (this.balance > this.lastBalance - this.smartMaxBalance) {
                     if (this.shouldStop()) {
                        if (this.mc.timer.timerSpeed != (float)this.slowTimer.getValue()) {
                           this.lastBalance = this.balance;
                        }

                        this.mc.timer.timerSpeed = 1.0F;
                        return;
                     }

                     this.mc.timer.timerSpeed = (float)this.slowTimer.getValue();
                  } else {
                     this.fast = true;
                     this.mc.timer.timerSpeed = 1.0F;
                  }
               } else {
                  if (this.mc.timer.timerSpeed != (float)this.slowTimer.getValue()) {
                     this.lastBalance = this.balance;
                  }

                  this.mc.timer.timerSpeed = 1.0F;
               }
            } else if (this.balance > this.lastBalance) {
               this.mc.timer.timerSpeed = (float)this.slowTimer.getValue();
            } else {
               if (this.mc.timer.timerSpeed == (float)this.slowTimer.getValue()) {
                  this.mc.timer.timerSpeed = 1.0F;
               }

               if (!this.delayTimer.hasTimeElapsed(this.delay.getValue(), false)) {
                  return;
               }

               if (this.target != null && !this.isTargetCloseOrVisible() && this.isHurtTime()) {
                  this.setSmartBalance();
                  this.fast = true;
                  this.delayTimer.reset();
                  this.lastBalance = this.balance;
               }
            }
         }

         if (this.mc.thePlayer.ticksExisted <= 20) {
            this.mc.timer.timerSpeed = 1.0F;
         }
      } else {
         if (!Client.INSTANCE.getModuleManager().getModule(ScaffoldModule.class).isEnabled()) {
            this.mc.timer.timerSpeed = 1.0F;
         }

         this.target = null;
      }
   }

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public void setSmartBalance() {
      double distance = EntityUtils.getDistanceToEntityBox(this.target) + this.targetMovementAdjust();
      if (this.target == null) {
         this.smartMaxBalance = 0.0;
      } else if (this.shouldStop()) {
         this.smartMaxBalance = 0.0;
      } else {
         double playerBPS = Math.max(MovementUtils.getBaseMoveSpeed() / 1.2, MovementUtils.getSpeed());
         double finalDistance = distance - 3.0;
         if (!this.preLoad.isEnabled()) {
            this.smartMaxBalance = Math.ceil(finalDistance / playerBPS);
         } else {
            double targetMotionX = Math.abs(this.target.lastTickPosX - this.target.posX);
            double targetMotionZ = Math.abs(this.target.lastTickPosZ - this.target.posZ);
            double targetBPS = Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ);
            if (Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ) <= 0.1) {
               targetBPS = Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ);
            } else {
               targetBPS = MovementUtils.getBaseMoveSpeed();
            }

            this.smartMaxBalance = (double)Math.round(finalDistance / (playerBPS + targetBPS));
         }
      }
   }

   @BCompiler(
      aot = BCompiler.AOT.AGGRESSIVE
   )
   public boolean shouldStop() {
      boolean stop = false;
      if (this.target == null) {
         return true;
      } else {
         double predictX = this.mc.thePlayer.posX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * 2.0;
         double predictZ = this.mc.thePlayer.posZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * 2.0;
         float f = (float)(predictX - this.target.posX);
         float f1 = (float)(this.mc.thePlayer.posY - this.target.posY);
         float f2 = (float)(predictZ - this.target.posZ);
         double predictedDistance = (double)MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
         if (this.onlyOnGround.isEnabled() && !this.mc.thePlayer.onGround) {
            stop = true;
         }

         if (EntityUtils.getDistanceToEntityBox(this.target) <= this.minRange.getValue()) {
            if (this.preLoad.isEnabled()) {
               if (!this.fast) {
                  stop = true;
               }
            } else if (!this.fast && this.mc.timer.timerSpeed != (float)this.slowTimer.getValue()) {
               stop = true;
            }
         }

         if (this.isTargetCloseOrVisible()) {
            stop = true;
         }

         if (!this.isHurtTime()) {
            stop = true;
         }

         if (this.outOfRange()) {
            stop = true;
         }

         if ((
               MovementUtils.getSpeed() <= 0.12
                  || !this.mc.gameSettings.keyBindForward.pressed
                  || predictedDistance > (double)this.mc.thePlayer.getDistanceToEntity(this.target) + 0.12
            )
            && this.onlyForward.isEnabled()) {
            stop = true;
         }

         if (this.noFluid.isEnabled() && (this.mc.thePlayer.isInWater() || this.mc.thePlayer.isInLava() || this.mc.thePlayer.isInWeb)) {
            stop = true;
         }

         if (this.mc.thePlayer.getDistance(this.target.lastTickPosX, this.target.lastTickPosY, this.target.lastTickPosZ)
            < this.mc.thePlayer.getDistance(this.target.posX, this.target.posY, this.target.posZ)) {
            stop = this.notInCombo.isEnabled();
            if (this.preLoad.isEnabled() && !this.fast) {
               stop = false;
            }
         }

         if (Client.INSTANCE.getModuleManager().getModule(BackTrackModule.class).isEnabled()
            && !BackTrackModule.incomingPackets.isEmpty()
            && this.mc.thePlayer.getDistance(this.target.posX, this.target.posY, this.target.posZ)
               < this.mc.thePlayer.getDistance(this.target.realPosX, this.target.realPosY, this.target.realPosZ)) {
            stop = this.notInCombo.isEnabled();
            if (this.preLoad.isEnabled() && !this.fast) {
               stop = false;
            }
         }

         return stop;
      }
   }

   public boolean outOfRange() {
      return EntityUtils.getDistanceToEntityBox(this.target) > this.getMaxDistance() + this.targetMovementAdjust();
   }

   public double getMaxDistance() {
      return this.maxRange.getValue();
   }

   public double targetMovementAdjust() {
      return 0.0;
   }

   public boolean isTargetCloseOrVisible() {
      if (this.target == null) {
         return false;
      } else {
         Entity rayTracedEntity = RotationUtils.rayTrace(3.0, new float[]{RotationUtils.serverYaw, RotationUtils.serverPitch});
         return rayTracedEntity == this.target || this.mc.objectMouseOver.entityHit == this.target;
      }
   }

   public boolean isHurtTime() {
      if (this.preLoad.isEnabled()) {
         double distance = EntityUtils.getDistanceToEntityBox(this.target) + this.targetMovementAdjust();
         double playerBPS = Math.max(MovementUtils.getBaseMoveSpeed() / 1.1, MovementUtils.getSpeed());
         double finalDistance = distance - 3.0;
         double targetMotionX = Math.abs(this.target.lastTickPosX - this.target.posX);
         double targetMotionZ = Math.abs(this.target.lastTickPosZ - this.target.posZ);
         double targetBPS = Math.max(MovementUtils.getBaseMoveSpeed() / 1.1, Math.sqrt(targetMotionX * targetMotionX + targetMotionZ * targetMotionZ));
         double hurtTime = finalDistance / (playerBPS + targetBPS * 1.1);
         return (double)this.mc.thePlayer.hurtTime <= hurtTime;
      } else {
         return this.mc.thePlayer.hurtTime <= 1;
      }
   }
}
