package cc.novoline.events.events;

import cc.novoline.events.events.callables.CancellableEvent;

public class KeyPressEvent extends CancellableEvent {
   private int key;

   public KeyPressEvent(int var1) {
      this.key = var1;
   }

   public int getKey() {
      return this.key;
   }
}
