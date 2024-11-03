package net.augustus.events;

public class EventKeyboard extends Event {
   private final int key;

   public EventKeyboard(int key) {
      this.key = key;
   }

   public int getKey() {
      return this.key;
   }
}
