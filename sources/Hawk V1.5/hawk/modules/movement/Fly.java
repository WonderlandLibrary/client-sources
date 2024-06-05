package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.events.listeners.EventReceivePacket;
import hawk.events.listeners.EventSendPacket;
import hawk.modules.Module;
import hawk.modules.player.AutoSetting;
import hawk.settings.BooleanSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.util.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S00PacketKeepAlive;

public class Fly extends Module {
   public NumberSetting VanillaSpeed = new NumberSetting("Speed (Vanilla)", 1.0D, 0.1D, 10.0D, 0.1D, this);
   Timer timer = new Timer();
   public NumberSetting GlideMotionY = new NumberSetting("Motion Y (Glide)", 0.05D, 0.01D, 1.0D, 0.01D, this);
   public BooleanSetting DisableOnDeath = new BooleanSetting("Disable on death", true, this);
   public ModeSetting mode = new ModeSetting("Mode", this, "Vanilla", new String[]{"Creative", "Redesky", "OldVerus", "RedeSkyFast", "Vanilla", "Airwalk", "Glide", "Bhopland", "Minebox"});

   public Fly() {
      super("Fly", 25, Module.Category.MOVEMENT);
      this.addSettings(new Setting[]{this.mode, this.VanillaSpeed, this.DisableOnDeath});
   }

   public void AutoSetting() {
      if (AutoSetting.isHypixel && !this.mode.is("Vanilla fast")) {
         this.mode.cycle();
      }

      if (AutoSetting.isMineplex && !this.mode.is("Vanilla fast")) {
         this.mode.cycle();
      }

      if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
         this.mode.cycle();
      }

      if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
         this.mode.cycle();
      }

   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventSendPacket) {
         EventSendPacket var2 = (EventSendPacket)var1;
         if (var2.getPacket() instanceof C03PacketPlayer && this.mode.is("Minebox")) {
            C03PacketPlayer var3 = (C03PacketPlayer)var2.getPacket();
            var3.onground = true;
         }
      }

      if (var1 instanceof EventReceivePacket && this.mode.is("Minebox")) {
         EventReceivePacket var4 = (EventReceivePacket)var1;
         if (((EventReceivePacket)var1).getPacket() instanceof S00PacketKeepAlive) {
            var1.setCancelled(true);
         }

         if (((EventReceivePacket)var1).getPacket() instanceof C0FPacketConfirmTransaction) {
            var1.setCancelled(true);
         }
      }

      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel && !this.mode.is("Vanilla fast")) {
            this.mode.cycle();
         }

         if (AutoSetting.isMineplex && !this.mode.is("Vanilla fast")) {
            this.mode.cycle();
         }

         if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
            this.mode.cycle();
         }

         if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
            this.mode.cycle();
         }
      }

      if (var1 instanceof EventMotion) {
         this.mc.thePlayer.cameraYaw = 0.1F;
         this.setDisplayname(String.valueOf((new StringBuilder("Fly §7")).append(this.mode.getMode())));
         if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.getHealth() <= 0.0F || this.mc.thePlayer.isDead)) {
            this.toggled = false;
         }

         if (this.mode.is("Creative")) {
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.timer.timerSpeed = 1.0F;
            this.mc.thePlayer.capabilities.setFlySpeed(0.07F);
         }

         if (this.mode.is("Redesky")) {
            if (this.mc.thePlayer.onGround) {
               this.mc.thePlayer.setSprinting(true);
               this.mc.thePlayer.jump();
            }

            if (this.mc.thePlayer.motionY < 0.0D) {
               this.mc.timer.timerSpeed = 0.3F;
               this.mc.thePlayer.speedInAir = 0.1F;
               this.mc.thePlayer.motionY = 0.0D;
               if (this.mc.thePlayer.ticksExisted % 8 == 0) {
                  this.mc.timer.timerSpeed = 25.0F;
               }
            }
         } else if (this.mode.is("RedeSkyFast")) {
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.setSprinting(false);
            this.mc.timer.timerSpeed = 2.0F;
         }

         EntityPlayerSP var10000;
         if (this.mode.is("OldVerus")) {
            if (this.timer.hasTimeElapsed(500L, true)) {
               var10000 = this.mc.thePlayer;
               var10000.posY -= 0.20000000298023224D;
               this.mc.thePlayer.onGround = false;
               var10000 = this.mc.thePlayer;
               var10000.motionX *= 0.10000000149011612D;
               var10000 = this.mc.thePlayer;
               var10000.motionZ *= 0.10000000149011612D;
               ((EventMotion)var1).setOnGround(true);
            } else {
               this.mc.thePlayer.motionY = 0.0D;
               this.mc.thePlayer.onGround = true;
               ((EventMotion)var1).setOnGround(true);
            }
         }

         if (this.mode.is("Vanilla")) {
            SpeedModifier.setSpeed((float)this.VanillaSpeed.getValue());
            if (!this.mc.gameSettings.keyBindJump.isPressed() && !this.mc.gameSettings.keyBindSneak.isPressed()) {
               this.mc.thePlayer.motionY = 0.0D;
               this.mc.thePlayer.onGround = true;
            }

            if (this.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
               this.mc.thePlayer.motionY = this.VanillaSpeed.getValue();
               this.mc.thePlayer.setJumping(false);
            }

            if (this.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
               this.mc.thePlayer.motionY = this.VanillaSpeed.getValue() * -1.0D;
            }
         }

         if (this.mode.is("Glide")) {
            this.mc.thePlayer.onGround = true;
            this.mc.thePlayer.motionY = this.GlideMotionY.getValue() * -1.0D;
         }

         if (this.mode.is("Airwalk")) {
            this.mc.thePlayer.motionY = 0.0D;
            this.mc.thePlayer.onGround = true;
            if (this.timer.hasTimeElapsed(100L, true)) {
               var10000 = this.mc.thePlayer;
               var10000.motionX *= 100.0D;
               var10000 = this.mc.thePlayer;
               var10000.motionZ *= 100.0D;
            }

            var10000 = this.mc.thePlayer;
            var10000.motionX *= 0.009999999776482582D;
            var10000 = this.mc.thePlayer;
            var10000.motionZ *= 0.009999999776482582D;
         }

         if (this.mode.is("Bhopland")) {
            this.mc.thePlayer.motionY = 0.0D;
            this.mc.thePlayer.onGround = true;
            if ((this.mc.timer.timerSpeed == 1.0F || this.mc.timer.timerSpeed > 1.0F) && this.timer.hasTimeElapsed(50L, true)) {
               net.minecraft.util.Timer var5 = this.mc.timer;
               var5.timerSpeed -= 0.01F;
               var10000 = this.mc.thePlayer;
               var10000.motionX *= 1.0499999523162842D;
               var10000 = this.mc.thePlayer;
               var10000.motionZ *= 1.0499999523162842D;
            }
         }

         if (this.mode.is("Minebox")) {
            this.mc.thePlayer.speedInAir = 0.1F;
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.motionY = -0.07999999821186066D;
            this.mc.timer.timerSpeed = 0.5F;
         }
      }

   }

   public void onEnable() {
      if (this.mode.is("RedeSkyFast") || this.mode.is("Bhopland")) {
         this.mc.thePlayer.jump();
      }

      if (this.mode.is("Bhopland")) {
         this.mc.timer.timerSpeed = 1.25F;
      }

      if (!this.mode.is("RedeSkyFast")) {
         this.mode.is("RedeSky");
      }

      if (AutoSetting.enabled) {
         if (AutoSetting.isHypixel && !this.mode.is("Vanilla fast")) {
            this.mode.cycle();
         }

         if (AutoSetting.isMineplex && !this.mode.is("Vanilla fast")) {
            this.mode.cycle();
         }

         if (AutoSetting.isOldVerus && !this.mode.is("OldVerus")) {
            this.mode.cycle();
         }

         if (AutoSetting.isRedesky && !this.mode.is("Redesky")) {
            this.mode.cycle();
         }
      }

   }

   public void onDisable() {
      this.mc.thePlayer.capabilities.isFlying = false;
      this.mc.timer.timerSpeed = 1.0F;
      this.mc.thePlayer.capabilities.setFlySpeed(0.045F);
      this.mc.thePlayer.speedInAir = 0.02F;
      if (this.mode.is("OldVerus")) {
         EntityPlayerSP var10000 = this.mc.thePlayer;
         var10000.motionX *= 0.009999999776482582D;
         var10000 = this.mc.thePlayer;
         var10000.motionZ *= 0.009999999776482582D;
      }

      this.mc.thePlayer.capabilities.isFlying = false;
      this.mc.timer.timerSpeed = 1.0F;
   }
}
