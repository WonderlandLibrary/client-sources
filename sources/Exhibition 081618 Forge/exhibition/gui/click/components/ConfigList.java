package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConfigList {
   public float x;
   public float y;
   public CategoryPanel categoryPanel;
   public List configButtonList = new ArrayList();

   public ConfigList(float x, float y, CategoryPanel categoryPanel) {
      this.x = x;
      this.y = y;
      this.categoryPanel = categoryPanel;
      this.configButtonList.add(new ConfigButton(this, x, y, ConfigButton.ButtonType.LOAD));
      this.configButtonList.add(new ConfigButton(this, x, y, ConfigButton.ButtonType.SAVE));
      this.configButtonList.add(new ConfigButton(this, x, y, ConfigButton.ButtonType.RESET));
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.configListDraw(this, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.configListMouseClicked(this, (float)x, (float)y, button);
      }

   }
}
