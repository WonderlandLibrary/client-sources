package hawk.modules.movement;

import hawk.events.Event;
import hawk.events.listeners.EventUpdate;
import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class Longjump extends Module {
   public ModeSetting mode = new ModeSetting("Mode", this, "Redesky fast", new String[]{"Redesky slow", "Redesky fast", "Redesky vroom"});
   public NumberSetting timeroflj = new NumberSetting("Timer", 1.0D, 0.1D, 4.0D, 0.1D, this);

   public Longjump() {
      super("Longjump", 33, Module.Category.MOVEMENT);
      this.addSettings(new Setting[]{this.mode, this.timeroflj});
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
      this.mc.thePlayer.speedInAir = 0.02F;
      this.mc.thePlayer.capabilities.isFlying = false;
   }

   public void onEnable() {
      this.mode.is("Redesky vroom");
   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventUpdate) {
         this.setDisplayname(String.valueOf((new StringBuilder("LongJump §7")).append(this.mode.getValueName())));
         EntityPlayerSP var10000;
         if (this.mode.is("Redesky vroom")) {
            this.mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive(50000));
            this.mc.thePlayer.jumpMovementFactor = 0.1F;
            if (this.mc.thePlayer.onGround) {
               this.mc.thePlayer.jump();
            }

            var10000 = this.mc.thePlayer;
            var10000.motionX *= 0.949999988079071D;
            var10000 = this.mc.thePlayer;
            var10000.motionY += 0.009999999776482582D;
            var10000 = this.mc.thePlayer;
            var10000.motionZ *= 0.949999988079071D;
            this.mc.thePlayer.speedInAir = 0.1F;
         }

         if (this.mode.is("Redesky fast")) {
            this.mc.timer.timerSpeed = (float)this.timeroflj.getValue();
            if (this.mc.thePlayer.fallDistance != 0.0F) {
               this.mc.thePlayer.motionY += 0.039D;
            }

            if (this.mc.thePlayer.onGround) {
               this.mc.thePlayer.jump();
            }

            if (!this.mc.thePlayer.onGround) {
               this.mc.thePlayer.motionY += 0.275D;
               var10000 = this.mc.thePlayer;
               var10000.motionX *= 1.065000057220459D;
               var10000 = this.mc.thePlayer;
               var10000.motionZ *= 1.065000057220459D;
            }
         } else {
            this.mc.timer.timerSpeed = (float)this.timeroflj.getValue();
            if (this.mc.thePlayer.fallDistance != 0.0F) {
               this.mc.thePlayer.motionY += 0.025D;
            }

            if (this.mc.thePlayer.onGround) {
               this.mc.thePlayer.jump();
            }

            if (!this.mc.thePlayer.onGround) {
               this.mc.thePlayer.motionY += 0.055D;
               var10000 = this.mc.thePlayer;
               var10000.motionX *= 1.045D;
               var10000 = this.mc.thePlayer;
               var10000.motionZ *= 1.045D;
            }
         }
      }

   }
}
