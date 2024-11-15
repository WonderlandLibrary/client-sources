package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import java.util.Iterator;

public class CategoryButton {
   public float x;
   public float y;
   public String name;
   public MainPanel panel;
   public boolean enabled;
   public CategoryPanel categoryPanel;

   public CategoryButton(MainPanel panel, String name, float x, float y) {
      this.panel = panel;
      this.name = name;
      this.x = x;
      this.y = y;
      panel.theme.categoryButtonConstructor(this, this.panel);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.categoryButtonDraw(this, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.categoryButtonMouseClicked(this, this.panel, x, y, button);
      }

   }

   public void mouseReleased(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.categoryButtonMouseReleased(this, x, y, button);
      }

   }
}
