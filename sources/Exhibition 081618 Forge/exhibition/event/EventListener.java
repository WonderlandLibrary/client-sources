package exhibition.event;

public interface EventListener {
   default EventListener.Priority getPriority() {
      return EventListener.Priority.NORMAL;
   }

   void onEvent(Event var1);

   public static enum Priority {
      LOWEST,
      LOW,
      NORMAL,
      MEDIUM,
      HIGH,
      HIGHEST;
   }
}
