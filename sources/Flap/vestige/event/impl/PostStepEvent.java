package vestige.event.impl;

import vestige.event.Event;

public class PostStepEvent extends Event {
   private float height;

   public float getHeight() {
      return this.height;
   }

   public void setHeight(float height) {
      this.height = height;
   }

   public PostStepEvent(float height) {
      this.height = height;
   }
}
