package hawk.modules.render;

import hawk.modules.Module;
import hawk.settings.ModeSetting;
import hawk.settings.Setting;
import hawk.util.Timer;

public class BlockAnimation extends Module {
   public static int BlockAnimationInt = 0;
   Timer timer = new Timer();
   public ModeSetting animation = new ModeSetting("Animation", this, "EZE", new String[]{"EZE", "Vanilla", "Monsoon", "Fan"});
   boolean PlayerEat = false;

   public void onEnable() {
      this.toggle();
   }

   public BlockAnimation() {
      super("SwordAnimations", 0, Module.Category.RENDER);
      this.addSettings(new Setting[]{this.animation});
   }
}
