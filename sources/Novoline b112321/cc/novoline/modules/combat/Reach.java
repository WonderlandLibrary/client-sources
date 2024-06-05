package cc.novoline.modules.combat;

import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;

public final class Reach extends AbstractModule {
   @Property("range")
   private final FloatProperty range = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(5.0F)).minimum(Float.valueOf(3.0F))).maximum(Float.valueOf(5.0F));
   private boolean hittingBlock;

   public Reach(ModuleManager var1) {
      super(var1, "Reach", EnumModuleType.COMBAT, "Expands reach");
      Manager.put(new Setting("Reach_Distance", "Range", SettingType.SLIDER, this, this.range, 0.1D));
   }

   public float getRange() {
      return ((Float)this.range.get()).floatValue();
   }
}
