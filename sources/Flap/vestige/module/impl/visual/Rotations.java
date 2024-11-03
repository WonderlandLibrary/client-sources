package vestige.module.impl.visual;

import org.lwjgl.input.Keyboard;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.RotationsRenderEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.world.ScaffoldV2;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;

public class Rotations extends Module {
   private float yaw;
   private float pitch;
   private float lastYaw;
   private float lastPitch;
   private boolean customRender;
   private BooleanSetting smooth = new BooleanSetting("Smooth", true);

   public Rotations() {
      super("Rotations", Category.VISUAL);
      this.setEnabledSilently(true);
      this.addSettings(new AbstractSetting[]{this.smooth});
   }

   @Listener(4)
   public void onMotion(MotionEvent event) {
      this.customRender = mc.thePlayer.rotationYaw != event.getYaw() || mc.thePlayer.rotationPitch != event.getPitch();
      this.lastYaw = this.yaw;
      this.lastPitch = this.pitch;
      this.yaw = event.getYaw();
      this.pitch = event.getPitch();
   }

   @Listener(4)
   public void onRotsRender(RotationsRenderEvent event) {
      if (this.customRender) {
         float partialTicks = event.getPartialTicks();
         event.setYaw(this.smooth.isEnabled() ? this.interpolateRotation(this.lastYaw, this.yaw, partialTicks) : this.yaw);
         if (((ScaffoldV2)Flap.instance.getModuleManager().getModule(ScaffoldV2.class)).isEnabled()) {
            if (Keyboard.isKeyDown(29)) {
               event.setBodyYaw(this.interpolateRotation(this.lastYaw - 30.0F, this.yaw - 30.0F, partialTicks));
            } else {
               event.setBodyYaw(this.interpolateRotation(this.lastYaw - 30.0F, this.yaw - 30.0F, partialTicks));
            }

            event.setPitch(85.0F);
         }

         event.setPitch(this.smooth.isEnabled() ? this.lastPitch + (this.pitch - this.lastPitch) * partialTicks : this.pitch);
      }

   }

   protected float interpolateRotation(float par1, float par2, float par3) {
      float f;
      for(f = par2 - par1; f < -180.0F; f += 360.0F) {
      }

      while(f >= 180.0F) {
         f -= 360.0F;
      }

      return par1 + par3 * f;
   }
}
