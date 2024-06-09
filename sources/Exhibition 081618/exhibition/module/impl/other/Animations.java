package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;

public class Animations extends Module {
   private Options swang = new Options("Block Swing", "Swing", new String[]{"Swing", "Swang", "Swong", "Swank"});

   public Animations(ModuleData data) {
      super(data);
      this.settings.put("COLOR", new Setting("COLOR", this.swang, "Choose one and stop bitching."));
   }

   public void toggle() {
      ChatUtil.printChat("This doesn't turn on retard.");
   }

   public void onEvent(Event event) {
   }

   public String getSelected() {
      return this.swang.getSelected();
   }
}
