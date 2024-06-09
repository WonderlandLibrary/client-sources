package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.module.data.Setting;
import java.util.Iterator;

public class Checkbox {
   public CategoryPanel panel;
   public boolean enabled;
   public float x;
   public float y;
   public String name;
   public Setting setting;

   public Checkbox(CategoryPanel panel, String name, float x, float y, Setting setting) {
      this.panel = panel;
      this.name = name;
      this.x = x;
      this.y = y;
      this.setting = setting;
      this.enabled = ((Boolean)setting.getValue()).booleanValue();
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         if (this.panel.visible) {
            theme.checkBoxDraw(this, x, y, this.panel);
         }
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.checkBoxMouseClicked(this, x, y, button, this.panel);
      }

   }
}
