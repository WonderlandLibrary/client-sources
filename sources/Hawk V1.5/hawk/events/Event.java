package hawk.events;

public class Event<T> {
   public boolean cancelled;
   public EventDirection direction;
   public EventType type;

   public boolean isPre() {
      if (this.type == null) {
         return false;
      } else {
         return this.type == EventType.PRE;
      }
   }

   public EventType getType() {
      return this.type;
   }

   public void setCancelled(boolean var1) {
      this.cancelled = var1;
   }

   public boolean isPost() {
      if (this.type == null) {
         return false;
      } else {
         return this.type == EventType.POST;
      }
   }

   public boolean isOutgoing() {
      if (this.direction == null) {
         return false;
      } else {
         return this.direction == EventDirection.OUTGOING;
      }
   }

   public void setType(EventType var1) {
      this.type = var1;
   }

   public EventDirection getDirection() {
      return this.direction;
   }

   public void setDirection(EventDirection var1) {
      this.direction = var1;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public boolean isIncoming() {
      if (this.direction == null) {
         return false;
      } else {
         return this.direction == EventDirection.INCOMING;
      }
   }
}
