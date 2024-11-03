package vestige.event.impl;

import vestige.event.type.CancellableEvent;

public class ChatSendEvent extends CancellableEvent {
   private String message;

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public ChatSendEvent(String message) {
      this.message = message;
   }
}
