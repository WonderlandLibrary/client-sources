package cc.novoline.modules.visual;

import cc.novoline.Novoline;
import cc.novoline.events.EventTarget;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.visual.HUD;

public final class Brightness extends AbstractModule {
   @Property("mode")
   private final StringProperty mode = PropertyFactory.createString("Gamma").acceptableValues(new String[]{"Gamma", "Effect"});

   public Brightness(ModuleManager var1) {
      super(var1, "Brightness", EnumModuleType.VISUALS, "It\'s too dark in here");
      Manager.put(new Setting("BN_MODE", "Mode", SettingType.COMBOBOX, this, this.mode));
   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      this.setSuffix((String)this.mode.get());
   }

   public StringProperty getMode() {
      return this.mode;
   }

   public static boolean a() {
      int var0 = HUD.e();
      if(Novoline.getInstance().isAnythingNull()) {
         return false;
      } else {
         Brightness var1 = (Brightness)Novoline.getInstance().getModuleManager().getModule(Brightness.class);
         return var1.isEnabled() && var1.getMode().equals("Gamma");
      }
   }

   public static boolean c() {
      int var0 = HUD.e();
      if(Novoline.getInstance().isAnythingNull()) {
         return false;
      } else {
         Brightness var1 = (Brightness)Novoline.getInstance().getModuleManager().getModule(Brightness.class);
         return var1.isEnabled() && var1.getMode().equals("Effect");
      }
   }
}
