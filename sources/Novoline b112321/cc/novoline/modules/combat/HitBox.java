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

public final class HitBox extends AbstractModule {
   @Property("size")
   private final FloatProperty hitBoxSize = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(0.3F)).minimum(Float.valueOf(0.1F))).maximum(Float.valueOf(1.0F));

   public HitBox(ModuleManager var1) {
      super(var1, EnumModuleType.COMBAT, "HitBox", "Hit Box");
      Manager.put(new Setting("ENTITY_BOX", "Box Size", SettingType.SLIDER, this, this.hitBoxSize, 0.10000000149011612D));
   }

   public FloatProperty getHitBoxSize() {
      return this.hitBoxSize;
   }
}
