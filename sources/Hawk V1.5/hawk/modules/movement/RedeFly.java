package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.util.Timer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class RedeFly extends Module {
   Timer timer = new Timer();
   long oldPosZ;
   public BooleanSetting SpeedModifierA = new BooleanSetting("SpeedModifier", false, this);
   public ModeSetting mode = new ModeSetting("Mode", this, "Redesky", new String[]{"Redesky", "Redesky 2"});
   public NumberSetting TimerSpeed = new NumberSetting("Timer", 0.2D, 0.05D, 4.0D, 0.05D, this);
   long oldPosX;
   public BooleanSetting DisableOnDeath = new BooleanSetting("Disable on death", true, this);
   public NumberSetting Speed = new NumberSetting("Speed", 0.3D, 0.05D, 4.0D, 0.05D, this);
   long oldPosY;
   float multiplier = 1.0F;
   int a;

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion) {
         this.mc.thePlayer.cameraYaw = 0.1F;
         this.setDisplayname(String.valueOf((new StringBuilder("Redefly §7")).append(this.mode.getValueName())));
         if (this.DisableOnDeath.isEnabled() && (this.mc.thePlayer.getHealth() <= 0.0F || this.mc.thePlayer.isDead)) {
            this.toggled = false;
         }

         if (this.mode.is("Redesky")) {
            this.mc.thePlayer.cameraYaw = -0.2F;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive(50000));
            this.mc.thePlayer.cameraYaw = -0.2F;
            if (this.timer.hasTimeElapsed(200L, false)) {
               this.mc.timer.timerSpeed = 0.05F;
               this.mc.thePlayer.motionY = 0.0D;
               this.mc.thePlayer.capabilities.isFlying = true;
               this.mc.thePlayer.jumpMovementFactor = 0.06F;
               this.mc.thePlayer.speedInAir = 0.08F;
               if (this.timer.hasTimeElapsed(800L, true)) {
                  if (this.multiplier < 2.0F) {
                     this.mc.timer.timerSpeed = 2.0F;
                  } else {
                     this.mc.timer.timerSpeed = 1.0F;
                  }

                  ++this.multiplier;
               }
            }
         } else if (this.mode.is("Redesky 2")) {
            this.mc.thePlayer.capabilities.isFlying = true;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive(10000));
            ((EventMotion)var1).setYaw(0.0F);
            ((EventMotion)var1).setPitch(0.0F);
            if (this.multiplier < 16.0F) {
               this.mc.thePlayer.speedInAir = 0.15F;
               this.mc.timer.timerSpeed = 0.6F;
               this.mc.thePlayer.motionY = 0.11999999731779099D;
            }

            if (this.multiplier >= 16.0F) {
               this.mc.timer.timerSpeed = 0.25F;
               EntityPlayerSP var10000 = this.mc.thePlayer;
               var10000.motionY -= 0.014999999664723873D;
               this.mc.thePlayer.speedInAir = 0.04F;
            }

            if (this.timer.hasTimeElapsed(50L, true)) {
               ++this.multiplier;
               this.oldPosX = (long)this.mc.thePlayer.posX;
               this.oldPosY = (long)this.mc.thePlayer.posY;
               this.oldPosZ = (long)this.mc.thePlayer.posZ;
            }
         }
      }

   }

   public void onDisable() {
      this.a = 0;
      EntityPlayerSP var10000 = this.mc.thePlayer;
      var10000.motionX *= (double)this.mc.timer.timerSpeed;
      var10000 = this.mc.thePlayer;
      var10000.motionZ *= (double)this.mc.timer.timerSpeed;
      this.mc.thePlayer.capabilities.isFlying = false;
      this.mc.timer.timerSpeed = 1.0F;
      this.mc.thePlayer.capabilities.setFlySpeed(0.045F);
      this.mc.thePlayer.speedInAir = 0.02F;
      this.mc.thePlayer.capabilities.isFlying = false;
      this.mc.timer.timerSpeed = 1.0F;
      this.multiplier = 1.0F;
      this.mc.thePlayer.capabilities.allowFlying = false;
   }

   public void onEnable() {
      this.a = 0;
      this.multiplier = 0.0F;
      this.mode.is("OldRedesky");
      if (!this.mode.is("RedeSkyFast")) {
         this.mode.is("RedeSky");
      }

   }

   public RedeFly() {
      super("Redefly", 25, Module.Category.MOVEMENT);
      this.addSettings(new Setting[]{this.mode, this.DisableOnDeath, this.SpeedModifierA, this.Speed, this.TimerSpeed});
   }
}
