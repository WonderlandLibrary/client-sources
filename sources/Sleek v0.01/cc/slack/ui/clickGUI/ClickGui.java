package cc.slack.ui.clickGUI;

import cc.slack.features.modules.api.Category;
import cc.slack.ui.clickGUI.component.Frame;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui extends GuiScreen {
   public static ArrayList<Frame> frames;

   public ClickGui() {
      frames = new ArrayList();
      int frameX = 5;
      Category[] var2 = Category.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Category category = var2[var4];
         Frame frame = new Frame(category);
         frame.setX(frameX);
         frames.add(frame);
         frameX += frame.getWidth() + 1;
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      frames.forEach((frame) -> {
         frame.renderFrame(this.fontRendererObj);
         frame.updatePosition(mouseX, mouseY);
         frame.getComponents().forEach((comp) -> {
            comp.updateComponent(mouseX, mouseY);
         });
      });
   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      frames.forEach((frame) -> {
         if (frame.isWithinHeader(mouseX, mouseY)) {
            switch(mouseButton) {
            case 0:
               frame.setDrag(true);
               frame.dragX = mouseX - frame.getX();
               frame.dragY = mouseY - frame.getY();
               break;
            case 1:
               frame.setOpen(!frame.isOpen());
            }
         }

         if (frame.isOpen() && !frame.getComponents().isEmpty()) {
            frame.getComponents().forEach((component) -> {
               component.mouseClicked(mouseX, mouseY, mouseButton);
            });
         }

      });
   }

   protected void keyTyped(char typedChar, int keyCode) {
      frames.forEach((frame) -> {
         if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
            frame.getComponents().forEach((component) -> {
               component.keyTyped(typedChar, keyCode);
            });
         }

      });
      if (keyCode == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
      }

   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      frames.forEach((frame) -> {
         frame.setDrag(false);
         if (frame.isOpen() && !frame.getComponents().isEmpty()) {
            frame.getComponents().forEach((component) -> {
               component.mouseReleased(mouseX, mouseY, state);
            });
         }

      });
   }
}
