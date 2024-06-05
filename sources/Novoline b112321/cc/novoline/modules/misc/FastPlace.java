package cc.novoline.modules.misc;

import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;

public final class FastPlace extends AbstractModule {
   @Property("place-delay")
   public final IntProperty placeDelay = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(3)).minimum(Integer.valueOf(1))).maximum(Integer.valueOf(4));
   @Property("blocks-only")
   public final BooleanProperty blocksOnly = PropertyFactory.booleanTrue();

   public FastPlace(ModuleManager var1) {
      super(var1, "FastPlace", "Fast Place", EnumModuleType.MISC, "place blocks faster");
      Manager.put(new Setting("PLACE_DELAY", "Place Delay", SettingType.SLIDER, this, this.placeDelay, 1.0D));
      Manager.put(new Setting("BLOCKS_ONLY", "Blocks Only", SettingType.CHECKBOX, this, this.blocksOnly));
   }
}
