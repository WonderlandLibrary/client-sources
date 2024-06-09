package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import java.util.Iterator;

public class SLButton {
   public float x;
   public float y;
   public String name;
   public MainPanel panel;
   public boolean load;

   public SLButton(MainPanel panel, String name, float x, float y, boolean load) {
      this.panel = panel;
      this.name = name;
      this.x = x;
      this.y = y;
      this.load = load;
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.slButtonDraw(this, x, y, this.panel);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.slButtonMouseClicked(this, (float)x, (float)y, button, this.panel);
      }

   }
}
