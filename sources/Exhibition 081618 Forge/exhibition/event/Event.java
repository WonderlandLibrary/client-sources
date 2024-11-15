package exhibition.event;

public abstract class Event {
   private boolean cancelled;

   public void fire() {
      this.cancelled = false;
      EventSystem.fire(this);
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public Event cast() {
      return this;
   }
}
