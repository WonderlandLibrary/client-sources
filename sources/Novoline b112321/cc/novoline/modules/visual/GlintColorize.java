package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.visual.HUD;
import java.awt.Color;
import java.util.EnumSet;

public class GlintColorize extends AbstractModule {
   private int offset = 0;
   @Property("glint-color")
   private final ColorProperty color = PropertyFactory.createColor(Integer.valueOf(-7697665));
   @Property("rainbow")
   private final BooleanProperty rainbow = PropertyFactory.booleanFalse();

   public GlintColorize(ModuleManager var1) {
      super(var1, "GlintColorize", "Glint Colorize", 0, EnumModuleType.VISUALS);
      Manager.put(new Setting("GLINT_COLOR", "Glint color", SettingType.COLOR_PICKER, this, this.color, (EnumSet)null));
      Manager.put(new Setting("GLINT_RAINBOW", "Rainbow", SettingType.CHECKBOX, this, this.rainbow));
   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      int var2 = HUD.e();
      if(((Boolean)this.rainbow.get()).booleanValue()) {
         ++this.offset;
         if(this.offset <= 255) {
            return;
         }

         this.offset = 0;
      }

      if(this.offset != 0) {
         this.offset = 0;
      }

   }

   public int glintColor() {
      HUD.h();
      float[] var2 = this.color.getHSB();
      float var3 = ((Boolean)this.rainbow.get()).booleanValue()?(float)this.offset / 255.0F:var2[0];
      Color var4 = Color.getHSBColor(var3, var2[1], var2[2]);
      return var4.getRGB();
   }

   public ColorProperty getColor() {
      return this.color;
   }

   public BooleanProperty getRainbow() {
      return this.rainbow;
   }

   public int getOffset() {
      return this.offset;
   }
}
