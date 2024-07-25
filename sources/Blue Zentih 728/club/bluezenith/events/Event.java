package club.bluezenith.events;

public class Event {
     public boolean cancelled = false;
     public void cancel() {
         cancelled = true;
     }
}

