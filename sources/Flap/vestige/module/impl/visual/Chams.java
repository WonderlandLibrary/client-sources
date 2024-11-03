package vestige.module.impl.visual;

import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;

public class Chams extends Module {
   public final ModeSetting mode = new ModeSetting("Fill Mode", "Transparent", new String[]{"Transparent", "Fill"});
   public final IntegerSetting Red = new IntegerSetting("Red", 0, 0, 10, 1);
   public final IntegerSetting Green = new IntegerSetting("Green", 0, 0, 10, 1);
   public final IntegerSetting Blue = new IntegerSetting("Blue", 0, 0, 10, 1);

   public Chams() {
      super("Chams", Category.VISUAL);
   }
}
