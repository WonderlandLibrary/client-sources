package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.management.animate.Opacity;
import exhibition.module.data.Setting;
import java.util.Iterator;

public class TextBox {
   public String textString;
   public float x;
   public float y;
   public Setting setting;
   public CategoryPanel panel;
   public boolean isFocused;
   public boolean isTyping;
   public Opacity opacity = new Opacity(255);
   public boolean backwards;
   public int selectedChar;
   public float offset;

   public TextBox(Setting setting, float x, float y, CategoryPanel panel) {
      this.x = x;
      this.y = y;
      this.panel = panel;
      this.setting = setting;
      this.textString = setting.getValue().toString();
      this.selectedChar = this.textString.length();
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         if (this.panel.visible) {
            theme.textBoxDraw(this, x, y);
         }
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         if (this.panel.visible) {
            theme.textBoxMouseClicked(this, x, y, button);
         }
      }

   }

   public void keyPressed(int key) {
      Iterator var2 = Client.getClickGui().getThemes().iterator();

      while(var2.hasNext()) {
         UI theme = (UI)var2.next();
         if (this.panel.visible) {
            theme.textBoxKeyPressed(this, key);
         }
      }

   }
}
