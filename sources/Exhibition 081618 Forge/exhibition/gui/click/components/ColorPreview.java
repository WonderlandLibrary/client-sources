package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.management.ColorObject;
import java.util.ArrayList;
import java.util.Iterator;

public class ColorPreview {
   public String colorName;
   public float x;
   public float y;
   public CategoryButton categoryPanel;
   public ColorObject colorObject;
   public ArrayList sliders = new ArrayList();

   public ColorPreview(ColorObject colorObject, String colorName, float x, float y, CategoryButton categoryPanel) {
      this.colorObject = colorObject;
      this.categoryPanel = categoryPanel;
      this.colorName = colorName;
      this.x = x;
      this.y = y;
      categoryPanel.panel.theme.colorConstructor(this, x, y);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         if (this.categoryPanel.enabled) {
            theme.colorPrewviewDraw(this, x, y);
         }
      }

   }
}
