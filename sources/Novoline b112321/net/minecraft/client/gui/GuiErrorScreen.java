package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiErrorScreen extends GuiScreen {
   private String field_146313_a;
   private String field_146312_f;

   public GuiErrorScreen(String var1, String var2) {
      this.field_146313_a = var1;
      this.field_146312_f = var2;
   }

   public void initGui() {
      super.initGui();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, 140, I18n.format("gui.cancel", new Object[0])));
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawGradientRect(0.0F, 0.0F, (float)this.width, (float)this.height, -12574688, -11530224);
      this.drawCenteredString(this.fontRendererObj, this.field_146313_a, this.width / 2, 90, 16777215);
      this.drawCenteredString(this.fontRendererObj, this.field_146312_f, this.width / 2, 110, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      this.mc.displayGuiScreen((GuiScreen)null);
   }
}
