package vestige.module.impl.combat;

import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;

public class Reach extends Module {
   public final DoubleSetting reach = new DoubleSetting("Reach", 3.0D, 1.0D, 6.0D, 0.05D);
   public final BooleanSetting onlysprint = new BooleanSetting("Only Sprint", false);
   public final BooleanSetting onair = new BooleanSetting("Only Ground", false);
   public final BooleanSetting checkwater = new BooleanSetting("Check Water", true);

   public Reach() {
      super("Reach", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.reach, this.onlysprint, this.onair, this.checkwater});
   }
}
