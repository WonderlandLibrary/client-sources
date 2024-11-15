package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.management.ColorObject;
import java.util.Iterator;

public class HSVColorPicker {
   public float x;
   public float y;
   public ColorPreview colorPreview;
   public ColorObject color;
   public boolean selectingOpacity;
   public boolean selectingColor;
   public boolean selectingHue;
   public float hue;
   public float saturation;
   public float brightness;
   public float opacity;

   public HSVColorPicker(float x, float y, ColorPreview colorPreview, ColorObject color) {
      this.x = x;
      this.y = y;
      this.colorPreview = colorPreview;
      this.color = color;
      colorPreview.categoryPanel.panel.theme.colorPickerConstructor(this, x, y);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.colorPickerDraw(this, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.colorPickerClick(this, (float)x, (float)y, button);
      }

   }

   public void mouseReleased(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.colorPickerMovedOrUp(this, (float)x, (float)y, button);
      }

   }
}
