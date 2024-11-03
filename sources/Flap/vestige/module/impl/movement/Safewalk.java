package vestige.module.impl.movement;

import vestige.event.Listener;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.util.util.Utils;

public class Safewalk extends Module {
   public final BooleanSetting offGround = new BooleanSetting("Offground", false);
   private final DoubleSetting motionX = new DoubleSetting("Motion X", 0.8D, 0.5D, 1.2D, 0.01D);

   public Safewalk() {
      super("Safewalk", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.offGround, this.motionX});
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      Utils.setSpeed(Utils.getHorizontalSpeed() * this.motionX.getValue());
   }
}
