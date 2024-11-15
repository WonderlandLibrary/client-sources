package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.module.Module;
import exhibition.module.ModuleManager;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;

public class Animations extends Module {

   public Animations(ModuleData data) {
      super(data);
   }

   @Override
   public void toggle() {
	   ModuleManager.saveSettings();
	   ModuleManager.saveStatus();
      ChatUtil.printChat("Save.");
   }

   @Override
   public void onEvent(Event event) {
   }
}
