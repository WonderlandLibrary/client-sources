package vestige.event.impl;

import vestige.event.Event;

public class ItemRenderEvent extends Event {
   private boolean renderBlocking;

   public boolean shouldRenderBlocking() {
      return this.renderBlocking;
   }

   public void setRenderBlocking(boolean renderBlocking) {
      this.renderBlocking = renderBlocking;
   }

   public ItemRenderEvent(boolean renderBlocking) {
      this.renderBlocking = renderBlocking;
   }
}
