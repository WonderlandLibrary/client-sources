package exhibition.module.impl.other;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.util.misc.ChatUtil;
import org.lwjgl.input.Keyboard;

public class AutoSwap extends Module {
   public static int multiSwap;
   public static boolean isSwapping;
   public static boolean settingKey;
   public static boolean keysSet;
   private static final String MULTI = "MULTI";
   private static final String SINGLE = "SINGLE";
   public int multiKey;
   public int single;
   exhibition.util.Timer timer = new exhibition.util.Timer();

   public AutoSwap(ModuleData data) {
      super(data);
   }

   public void onEnable() {
      isSwapping = false;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      EventMotionUpdate em = (EventMotionUpdate)event;
      if (em.isPre()) {
         if (settingKey && this.timer.delay(1000.0F) && !keysSet) {
            ChatUtil.printChat("§4[§cE§4]§8 press your key you'd like to set for Multi Swap.");
            keysSet = true;
         } else if (!settingKey && !keysSet) {
            ;
         }

         if (keysSet && Keyboard.getEventKey() != 0) {
            this.multiKey = Keyboard.getEventKey();
         }
      }

      if (em.isPost() && !settingKey) {
         ;
      }

   }
}
