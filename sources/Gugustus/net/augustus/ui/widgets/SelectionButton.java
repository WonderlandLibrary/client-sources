package net.augustus.ui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class SelectionButton extends GuiButton {
   public SelectionButton(int buttonId, int x, int y, String buttonText) {
      super(buttonId, x, y, buttonText);
   }

   public SelectionButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      super(buttonId, x, y, widthIn, heightIn, buttonText);
   }

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      super.drawButton(mc, mouseX, mouseY);
   }
}
