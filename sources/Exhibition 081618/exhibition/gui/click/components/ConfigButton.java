package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import java.util.Iterator;

public class ConfigButton {
   public float x;
   public float y;
   public ConfigList configList;
   public ConfigButton.ButtonType buttonType;

   public ConfigButton(ConfigList configList, float x, float y, ConfigButton.ButtonType buttonType) {
      this.x = x;
      this.y = y;
      this.buttonType = buttonType;
      this.configList = configList;
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.configButtonDraw(this, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.configButtonMouseClicked(this, (float)x, (float)y, button);
      }

   }

   public static enum ButtonType {
      LOAD,
      SAVE,
      RESET;
   }
}
