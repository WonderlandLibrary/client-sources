package cc.novoline.events.events;

import cc.novoline.events.events.Event;

public class DisplayChestGuiEvent implements Event {
   String string;

   public DisplayChestGuiEvent(String var1) {
      this.string = var1;
   }

   public String getString() {
      return this.string;
   }
}
