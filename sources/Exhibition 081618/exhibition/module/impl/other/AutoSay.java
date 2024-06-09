package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventTick;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.misc.ChatUtil;

public class AutoSay extends Module {
   exhibition.util.Timer timer = new exhibition.util.Timer();
   public static final String WORDS = "SAY";
   public final String DELAY = "DELAY";

   public AutoSay(ModuleData data) {
      super(data);
      this.settings.put("SAY", new Setting("SAY", "/warp", "Message to send."));
      this.settings.put("DELAY", new Setting("DELAY", Integer.valueOf(500), "MS delay between messages.", 100.0D, 100.0D, 10000.0D));
   }

   @RegisterEvent(
      events = {EventTick.class}
   )
   public void onEvent(Event event) {
      String message = (String)((Setting)this.settings.get("SAY")).getValue();
      if (this.timer.delay((float)((Number)((Setting)this.settings.get("DELAY")).getValue()).longValue())) {
         ChatUtil.sendChat(message + " " + (int)(Math.random() * 9.99999999E8D + Math.random() * 9.99999999E8D));
         this.timer.reset();
      }

   }
}
