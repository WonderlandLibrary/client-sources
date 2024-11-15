package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.module.data.Options;
import exhibition.module.data.Setting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DropdownBox {
   public Options option;
   public Setting setting;
   public float x;
   public float y;
   public List buttons = new ArrayList();
   public CategoryPanel panel;
   public boolean active;

   public DropdownBox(Setting setting, float x, float y, CategoryPanel panel) {
      this.setting = setting;
      this.option = (Options)setting.getValue();
      this.panel = panel;
      this.x = x;
      this.y = y;
      panel.categoryButton.panel.theme.dropDownContructor(this, x, y, this.panel);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         if (this.panel.visible) {
            theme.dropDownDraw(this, x, y, this.panel);
         }
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.dropDownMouseClicked(this, x, y, button, this.panel);
      }

   }
}
