package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.modules.player.AutoSetting;
import hawk.settings.BooleanSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.util.PlayerUtil;
import hawk.util.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;

public class Speed extends Module {
   public ModeSetting mode = new ModeSetting("Mode", this, "Vanilla", new String[]{"Vanilla", "Mineplex", "Hypixel", "NCP", "Redesky", "Legit bhop", "OldVerus"});
   public NumberSetting SpeedVanilla = new NumberSetting("Speed for Vanilla", 0.5D, 0.1D, 5.0D, 0.1D, this);
   public NumberSetting hypixelpotionmultiplier = new NumberSetting("WatchdogPotionMultiplier", 0.0D, 0.0D, 0.1005D, 0.005D, this);
   public NumberSetting hypixelspeed = new NumberSetting("WatchdogSpeed", 1.0D, 1.0D, 1.11D, 0.01D, this);
   boolean isWalking = false;
   Timer timer = new Timer();
   public BooleanSetting AutoJump = new BooleanSetting("Auto jump", false, this);

   public Speed() {
      super("Speed", 19, Module.Category.MOVEMENT);
      this.addSettings(new Setting[]{this.mode, this.AutoJump, this.SpeedVanilla, this.hypixelspeed, this.hypixelpotionmultiplier});
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
      this.mc.thePlayer.speedInAir = 0.02F;
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         this.setDisplayname(String.valueOf((new StringBuilder("Speed §7")).append(this.mode.getValueName())));
         if (AutoSetting.enabled) {
            if (AutoSetting.isHypixel && !this.mode.is("Hypixel")) {
               this.mode.cycle();
            }

            if (AutoSetting.isMineplex && !this.mode.is("Mineplex")) {
               this.mode.cycle();
            }

            if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
               this.mode.cycle();
            }

            if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
               this.mode.cycle();
            }
         }

         if (var1.isPre()) {
            if (this.AutoJump.isEnabled() && this.isWalking && this.mc.thePlayer.onGround && this.timer.hasTimeElapsed(100L, true)) {
               this.mc.thePlayer.jump();
            }

            if (!(this.mc.thePlayer.motionX > 0.1D) && !(this.mc.thePlayer.motionX < -0.1D) && !(this.mc.thePlayer.motionZ > 0.1D) && !(this.mc.thePlayer.motionZ < -0.1D)) {
               this.isWalking = false;
            } else {
               this.isWalking = true;
            }

            EntityPlayerSP var10000;
            if (this.mode.is("Redesky")) {
               this.mc.timer.timerSpeed = 1.0F;
               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
                  var10000 = this.mc.thePlayer;
                  var10000.motionX *= 1.270799994468689D;
                  var10000 = this.mc.thePlayer;
                  var10000.motionZ *= 1.270799994468689D;
               } else {
                  var10000 = this.mc.thePlayer;
                  var10000.motionX *= 0.9900000095367432D;
                  var10000 = this.mc.thePlayer;
                  var10000.motionZ *= 0.9900000095367432D;
                  if (this.mc.thePlayer.moveForward == 0.0F) {
                     this.mc.thePlayer.speedInAir = 0.03F;
                  } else {
                     this.mc.thePlayer.speedInAir = 0.0225F;
                  }
               }
            }

            if (this.mode.is("Vanilla")) {
               this.mc.timer.timerSpeed = 1.0F;
               SpeedModifier.setSpeed((float)this.SpeedVanilla.getValue());
            }

            if (this.mode.is("Mineplex")) {
               if (this.mc.thePlayer.onGround) {
                  SpeedModifier.setSpeed(0.18F);
               } else {
                  SpeedModifier.setSpeed(0.32F);
               }
            }

            if (this.mode.is("Legit bhop")) {
               this.mc.timer.timerSpeed = 1.0F;
               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
               }

               if (this.mc.thePlayer.isDead || this.mc.thePlayer.getHealth() <= 0.0F) {
                  this.toggled = false;
                  this.mc.timer.timerSpeed = 1.0F;
               }
            }

            if (this.mode.is("Hypixel")) {
               this.mc.timer.timerSpeed = 1.0F;
               if (this.mc.thePlayer.onGround) {
                  this.mc.thePlayer.jump();
                  this.mc.timer.timerSpeed = 1.05F;
                  var10000 = this.mc.thePlayer;
                  var10000.motionX *= 1.0707999467849731D;
                  var10000 = this.mc.thePlayer;
                  var10000.motionZ *= 1.0707999467849731D;
               } else {
                  this.mc.thePlayer.jumpMovementFactor = 0.0265F;
                  this.mc.timer.timerSpeed = 1.05F;
                  double var2 = this.hypixelspeed.getValue() + (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? this.hypixelpotionmultiplier.getValue() : 0.0D);
                  PlayerUtil.setSpeed(PlayerUtil.getCurrentMotion() * var2);
               }
            }

            if (this.mode.is("NCP")) {
               if (this.mc.thePlayer.onGround) {
                  if (this.AutoJump.isEnabled()) {
                     var10000 = this.mc.thePlayer;
                     var10000.motionX *= 0.699999988079071D;
                     var10000 = this.mc.thePlayer;
                     var10000.motionZ *= 0.699999988079071D;
                  }

                  this.mc.thePlayer.isSprinting();
               } else if (this.mc.thePlayer.motionY > 0.0D) {
                  SpeedModifier.setSpeed(0.27F);
                  var10000 = this.mc.thePlayer;
                  var10000.motionX *= 1.0299999713897705D;
                  var10000 = this.mc.thePlayer;
                  var10000.motionZ *= 1.0299999713897705D;
               } else {
                  SpeedModifier.setSpeed(0.27F);
               }
            }

            if (this.mode.is("OldVerus")) {
               this.mc.timer.timerSpeed = 1.0F;
               if (this.isWalking && this.mc.thePlayer.onGround && this.timer.hasTimeElapsed(100L, true)) {
                  this.mc.thePlayer.jump();
               }

               if (this.mc.thePlayer.isSprinting()) {
                  if (this.mc.thePlayer.onGround) {
                     if (this.mc.thePlayer.moveForward > 0.0F) {
                        SpeedModifier.setSpeed(0.19F);
                     } else {
                        SpeedModifier.setSpeed(0.14F);
                     }
                  } else if (this.mc.thePlayer.moveForward > 0.0F) {
                     SpeedModifier.setSpeed(0.295F);
                  } else {
                     SpeedModifier.setSpeed(0.29F);
                  }
               } else if (this.mc.thePlayer.onGround) {
                  if (this.mc.thePlayer.moveForward > 0.0F) {
                     SpeedModifier.setSpeed(0.16F);
                  } else {
                     SpeedModifier.setSpeed(0.14F);
                  }
               } else if (this.mc.thePlayer.moveForward > 0.0F) {
                  SpeedModifier.setSpeed(0.25F);
               } else {
                  SpeedModifier.setSpeed(0.2F);
               }
            }
         }
      }

   }

   public void onEnable() {
      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel) {
            this.mode.is("Hypixel");
         }

         if (AutoSetting.isMineplex) {
            this.mode.is("Mineplex");
         }

         if (AutoSetting.isOldVerus) {
            this.mode.is("OldVerus");
         }

         if (AutoSetting.isRedesky) {
            this.mode.is("Redesky");
         }
      }

   }
}
