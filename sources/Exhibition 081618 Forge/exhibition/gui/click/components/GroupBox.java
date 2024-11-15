package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.module.Module;
import java.util.Iterator;

public class GroupBox {
   public float x;
   public float y;
   public float ySize;
   public CategoryPanel categoryPanel;
   public Module module;

   public GroupBox(Module module, CategoryPanel categoryPanel, float x, float y, float ySize) {
      this.x = x;
      this.y = y;
      this.categoryPanel = categoryPanel;
      this.module = module;
      this.ySize = ySize;
      categoryPanel.categoryButton.panel.theme.groupBoxConstructor(this, x, y);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.groupBoxDraw(this, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.groupBoxMouseClicked(this, x, y, button);
      }

   }

   public void mouseReleased(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.groupBoxMouseMovedOrUp(this, x, y, button);
      }

   }
}
