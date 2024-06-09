package exhibition.gui.console;

import exhibition.gui.console.components.SourceComponent;
import exhibition.gui.console.components.SourceConsolePanel;
import exhibition.util.Timer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class SourceConsoleGUI extends GuiScreen {
   private List components = new ArrayList();
   public SourceConsole sourceConsole = new SourceConsole();
   public Timer timer = new Timer();

   public SourceConsoleGUI() {
      this.components.add(new SourceConsolePanel());
   }

   public void initGui() {
      this.timer.reset();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         SourceComponent component = (SourceComponent)var4.next();
         component.drawScreen((float)mouseX, (float)mouseY);
      }

   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         SourceComponent component = (SourceComponent)var4.next();
         component.mousePressed((float)mouseX, (float)mouseY, mouseButton);
      }

   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      Iterator var4 = this.components.iterator();

      while(var4.hasNext()) {
         SourceComponent component = (SourceComponent)var4.next();
         component.mouseReleased((float)mouseX, (float)mouseY, state);
      }

   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
      Iterator var3 = this.components.iterator();

      while(var3.hasNext()) {
         SourceComponent component = (SourceComponent)var3.next();
         component.keyboardTyped(keyCode);
      }

   }
}
