package cc.slack.events.impl.input;

import cc.slack.events.Event;

public class KeyEvent extends Event {
   private final int key;

   public int getKey() {
      return this.key;
   }

   public KeyEvent(int key) {
      this.key = key;
   }
}
