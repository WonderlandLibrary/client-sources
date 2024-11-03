package xyz.cucumber.base.events.ext;

import xyz.cucumber.base.events.Event;

public class EventSkyColor extends Event {
   private float red;
   private float green;
   private float blue;

   public EventSkyColor(float red, float green, float blue) {
      this.red = red;
      this.green = green;
      this.blue = blue;
   }

   public float getRed() {
      return this.red;
   }

   public void setRed(float red) {
      this.red = red;
   }

   public float getGreen() {
      return this.green;
   }

   public void setGreen(float green) {
      this.green = green;
   }

   public float getBlue() {
      return this.blue;
   }

   public void setBlue(float blue) {
      this.blue = blue;
   }
}
