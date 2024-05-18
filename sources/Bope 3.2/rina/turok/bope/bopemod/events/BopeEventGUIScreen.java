package rina.turok.bope.bopemod.events;

import net.minecraft.client.gui.GuiScreen;
import rina.turok.bope.external.BopeEventCancellable;

public class BopeEventGUIScreen extends BopeEventCancellable {
   private final GuiScreen guiscreen;

   public BopeEventGUIScreen(GuiScreen screen) {
      this.guiscreen = screen;
   }

   public GuiScreen get_guiscreen() {
      return this.guiscreen;
   }
}
