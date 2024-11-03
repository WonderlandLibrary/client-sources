package vestige.module.impl.visual;

import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;

public class Nametags extends Module {
   public final BooleanSetting background = new BooleanSetting("Background", true);
   public final BooleanSetting themecolor = new BooleanSetting("Theme Color", false);
   public final BooleanSetting clientfont = new BooleanSetting("Client Font", false);

   public Nametags() {
      super("Nametags", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.background, this.themecolor, this.clientfont});
   }
}
