package vestige.event.impl;

import vestige.event.type.CancellableEvent;

public class KeyPressEvent extends CancellableEvent {
   private int key;

   public int getKey() {
      return this.key;
   }

   public KeyPressEvent(int key) {
      this.key = key;
   }
}
