package cc.slack.ui.NewCGUI;

import cc.slack.features.modules.api.Category;
import cc.slack.ui.NewCGUI.components.Components;
import cc.slack.ui.NewCGUI.components.impl.CategoryComp;
import cc.slack.utils.client.mc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class TransparentClickGUI extends GuiScreen {
   protected static final List<CategoryComp> frames = new ArrayList();

   public TransparentClickGUI() {
      int posX = 114;
      int gap = 1;
      Category[] var3 = Category.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Category category = var3[var5];
         CategoryComp catComp = new CategoryComp(category, posX, gap, 110, 15);
         catComp.init();
         frames.add(catComp);
         posX += catComp.getWidth() + gap;
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawScreen(mouseX, mouseY, partialTicks);
      frames.forEach((categoryComp) -> {
         categoryComp.draw(mc.getFontRenderer(), mouseX, mouseY, partialTicks);
      });
   }

   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
      super.mouseClicked(mouseX, mouseY, mouseButton);
      frames.forEach((categoryComp) -> {
         categoryComp.mouseClicked(mouseX, mouseY, mouseButton);
      });
   }

   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      super.keyTyped(typedChar, keyCode);
      frames.forEach((categoryComp) -> {
         categoryComp.keyClicked(typedChar, keyCode);
      });
   }

   protected void mouseReleased(int mouseX, int mouseY, int state) {
      super.mouseReleased(mouseX, mouseY, state);
      frames.forEach((categoryComp) -> {
         categoryComp.mouseReleased(mouseX, mouseY, state);
      });
   }

   public void onGuiClosed() {
      super.onGuiClosed();
      frames.forEach(Components::close);
   }
}
