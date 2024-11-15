package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.module.data.Setting;
import java.util.Iterator;

public class MultiDropdownButton {
   public String name;
   public float x;
   public float y;
   public Setting setting;
   public MultiDropdownBox box;

   public MultiDropdownButton(String name, float x, float y, MultiDropdownBox box, Setting setting) {
      this.setting = setting;
      this.name = name;
      this.x = x;
      this.y = y;
      this.box = box;
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.multiDropDownButtonDraw(this, this.box, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.multiDropDownButtonMouseClicked(this, this.box, x, y, button);
      }

   }
}
