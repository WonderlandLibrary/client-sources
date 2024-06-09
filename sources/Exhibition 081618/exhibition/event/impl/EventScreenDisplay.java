package exhibition.event.impl;

import exhibition.event.Event;
import net.minecraft.client.gui.GuiScreen;

public class EventScreenDisplay extends Event {
   private GuiScreen screen;

   public void fire(GuiScreen screen) {
      this.screen = screen;
      super.fire();
   }

   public GuiScreen getGuiScreen() {
      return this.screen;
   }
}
