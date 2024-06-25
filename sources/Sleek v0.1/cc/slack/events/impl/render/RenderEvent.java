package cc.slack.events.impl.render;

import cc.slack.events.Event;

public class RenderEvent extends Event {
   public RenderEvent.State state;
   public float partialTicks;
   public float width;
   public float height;

   public RenderEvent(RenderEvent.State state, float partialTicks) {
      this.state = state;
      this.partialTicks = partialTicks;
   }

   public RenderEvent(RenderEvent.State state, float partialTicks, float width, float height) {
      this.state = state;
      this.partialTicks = partialTicks;
      this.width = width;
      this.height = height;
   }

   public RenderEvent.State getState() {
      return this.state;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public void setState(RenderEvent.State state) {
      this.state = state;
   }

   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public void setWidth(float width) {
      this.width = width;
   }

   public void setHeight(float height) {
      this.height = height;
   }

   public static enum State {
      RENDER_3D,
      RENDER_2D;
   }
}
