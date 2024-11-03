package xyz.cucumber.base.utils.button;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class TextBox extends GuiTextField {
   private final String title;
   private final boolean hide;

   public TextBox(int componentId, FontRenderer fontrendererObj, String title, int x, int y, int width, int height) {
      super(componentId, fontrendererObj, x, y, width, height);
      this.title = title;
      this.hide = false;
   }

   public TextBox(int componentId, FontRenderer fontrendererObj, String title, int x, int y, int width, int height, boolean hide) {
      super(componentId, fontrendererObj, x, y, width, height);
      this.title = title;
      this.hide = hide;
   }
}
