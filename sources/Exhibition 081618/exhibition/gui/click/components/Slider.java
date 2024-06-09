package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.module.data.Setting;
import java.util.Iterator;

public class Slider {
   public float x;
   public float y;
   public String name;
   public Setting setting;
   public CategoryPanel panel;
   public boolean dragging;
   public double dragX;
   public double lastDragX;

   public Slider(CategoryPanel panel, float x, float y, Setting setting) {
      this.panel = panel;
      this.x = x;
      this.y = y;
      this.setting = setting;
      panel.categoryButton.panel.theme.SliderContructor(this, panel);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.SliderDraw(this, x, y, this.panel);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.SliderMouseClicked(this, x, y, button, this.panel);
      }

   }

   public void mouseReleased(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.SliderMouseMovedOrUp(this, x, y, button, this.panel);
      }

   }
}
