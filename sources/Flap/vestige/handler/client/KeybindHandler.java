package vestige.handler.client;

import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.KeyPressEvent;

public class KeybindHandler {
   public KeybindHandler() {
      Flap.instance.getEventManager().register(this);
   }

   @Listener
   public void onKeyPress(KeyPressEvent event) {
      Flap.instance.getModuleManager().modules.stream().filter((m) -> {
         return m.getKey() == event.getKey();
      }).forEach((m) -> {
         m.toggle();
      });
   }
}
