package net.augustus.events;

public class EventChat extends Event {
   private String message;

   public EventChat(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
