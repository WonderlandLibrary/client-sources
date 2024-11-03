package vestige.module.impl.visual;

import net.minecraft.potion.Potion;
import vestige.event.Listener;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.DoubleSetting;

public class FovChanger extends Module {
   private float oldFov = 0.0F;
   private final DoubleSetting sprintFov = new DoubleSetting("Sprint FOV", 80.0D, 30.0D, 110.0D, 1.0D);
   private final DoubleSetting speedFov = new DoubleSetting("Speed FOV", 90.0D, 30.0D, 110.0D, 1.0D);

   public FovChanger() {
      super("Fov Changer", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.sprintFov, this.speedFov});
   }

   public void onEnable() {
      this.oldFov = mc.gameSettings.fovSetting;
   }

   public boolean onDisable() {
      mc.gameSettings.fovSetting = this.oldFov;
      return false;
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (mc.thePlayer.isSprinting()) {
         mc.gameSettings.fovSetting = (float)this.sprintFov.getValue();
      } else if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
         mc.gameSettings.fovSetting = (float)this.speedFov.getValue();
      }

   }
}
