package vestige.module.impl.visual;

import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.ModeSetting;

public class Animations extends Module {
   public final DoubleSetting x = new DoubleSetting("X", 0.0D, -5.0D, 3.0D, 0.05D);
   public final DoubleSetting y = new DoubleSetting("Y", 0.0D, -5.0D, 3.0D, 0.05D);
   public final DoubleSetting z = new DoubleSetting("Z", 0.0D, -5.0D, 3.0D, 0.05D);
   public final DoubleSetting swingSlowdown = new DoubleSetting("Swing slowdown", 1.0D, 0.1D, 8.0D, 0.1D);
   public final ModeSetting blockanim = new ModeSetting("Block Animation", "1.7", new String[]{"None", "1.7", "Smooth", "Exhibition", "Tap", "Scale", "Monsoon", "Stab", "Spin", "Sigma", "Chill"});
   public final ModeSetting hitanim = new ModeSetting("Hit Animation", "1.7", new String[]{"1.7", "FlapMode"});
   public final BooleanSetting alwaysshow = new BooleanSetting("Offset Animation", false);
   private int swing;

   public Animations() {
      super("Animations", Category.VISUAL);
      this.setEnabledSilently(true);
      this.addSettings(new AbstractSetting[]{this.x, this.y, this.z, this.swingSlowdown, this.blockanim, this.hitanim, this.alwaysshow});
   }
}
