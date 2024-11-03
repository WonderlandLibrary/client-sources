package net.augustus.events;

public class EventSilent extends Event {
   private int slotID;

   public EventSilent(int slotID) {
      this.slotID = slotID;
   }

   public int getSlotID() {
      return this.slotID;
   }

   public void setSlotID(int slotID) {
      this.slotID = slotID;
   }
}
