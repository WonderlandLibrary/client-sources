package cc.novoline.modules.visual;

import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import java.awt.Color;
import java.util.EnumSet;
import org.jetbrains.annotations.NotNull;

public class CustomHit extends AbstractModule {
   @Property("color")
   private ColorProperty x = PropertyFactory.createColor(Integer.valueOf((new Color(255, 110, 110)).getRGB()));

   public CustomHit(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.VISUALS, "CustomHit", "Custom Hit");
      Manager.put(new Setting("ch_color", "Hit Color", SettingType.COLOR_PICKER, this, this.x, (EnumSet)null));
   }

   public ColorProperty a() {
      return this.x;
   }
}
