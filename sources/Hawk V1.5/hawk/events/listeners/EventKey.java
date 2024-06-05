package hawk.events.listeners;

import hawk.events.Event;

public class EventKey extends Event<EventKey> {
   public int code;

   public int getCode() {
      return this.code;
   }

   public EventKey(int var1) {
      this.code = var1;
   }

   public void setCode(int var1) {
      this.code = var1;
   }
}
