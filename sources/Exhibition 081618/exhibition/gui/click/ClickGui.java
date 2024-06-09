package exhibition.gui.click;

import exhibition.gui.click.components.MainPanel;
import exhibition.gui.click.ui.SkeetMenu;
import exhibition.gui.click.ui.UI;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class ClickGui extends GuiScreen {
   public MainPanel mainPanel;
   ArrayList themes;

   public ArrayList getThemes() {
      return this.themes;
   }

   public ClickGui() {
      (this.themes = new ArrayList()).add(new SkeetMenu());
      this.mainPanel = new MainPanel("Exhibition", 50.0F, 50.0F, (UI)this.themes.get(0));
   }

   public void initGui() {
   }

   public void drawMenu(int mouseX, int mouseY, float partialTicks) {
      GlStateManager.pushMatrix();
      this.mainPanel.draw(mouseX, mouseY);
      GlStateManager.popMatrix();
   }

   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
      this.mainPanel.mouseMovedOrUp(mouseX, mouseY, mouseButton);
      super.mouseReleased(mouseX, mouseY, mouseButton);
   }

   public void mouseClicked(int mouseX, int mouseY, int clickedButton) {
      try {
         this.mainPanel.mouseClicked(mouseX, mouseY, clickedButton);
         super.mouseClicked(mouseX, mouseY, clickedButton);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
      this.mainPanel.keyPressed(keyCode);
   }

   public void onGuiClosed() {
      this.mainPanel.opacity = 0;
      Keyboard.enableRepeatEvents(false);
   }
}
