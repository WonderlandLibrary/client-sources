package vestige.module.impl.misc;

import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;

public class Targets extends Module {
   public final BooleanSetting playerTarget = new BooleanSetting("Player", true);
   public final BooleanSetting friends = new BooleanSetting("Friends", false);
   public final BooleanSetting animalTarget = new BooleanSetting("Animals", false);
   public final BooleanSetting mobsTarget = new BooleanSetting("Mobs", false);
   public final BooleanSetting teams = new BooleanSetting("Teams", false);
   public final BooleanSetting invisiblesTarget = new BooleanSetting("Invisibles", false);
   public static final DoubleSetting red = new DoubleSetting("Red", 255.0D, 0.0D, 255.0D, 1.0D);
   public static final DoubleSetting green = new DoubleSetting("Green", 255.0D, 0.0D, 255.0D, 1.0D);
   public static final DoubleSetting blue = new DoubleSetting("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
   public static final DoubleSetting alpha = new DoubleSetting("Alpha", 150.0D, 0.0D, 255.0D, 1.0D);

   public Targets() {
      super("Targets", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.playerTarget, this.animalTarget, this.mobsTarget, this.invisiblesTarget, red, green, blue, alpha});
   }
}
