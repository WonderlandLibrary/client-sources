package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import exhibition.module.Module;
import java.util.Iterator;

public class Button {
   public float x;
   public float y;
   public String name;
   public CategoryPanel panel;
   public boolean enabled;
   public Module module;
   public boolean isBinding;

   public Button(CategoryPanel panel, String name, float x, float y, Module module) {
      this.panel = panel;
      this.name = name;
      this.x = x;
      this.y = y;
      this.module = module;
      this.enabled = module.isEnabled();
      panel.categoryButton.panel.theme.buttonContructor(this, this.panel);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         if (this.panel.visible) {
            theme.buttonDraw(this, x, y, this.panel);
         }
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.buttonMouseClicked(this, x, y, button, this.panel);
      }

   }

   public void keyPressed(int key) {
      Iterator var2 = Client.getClickGui().getThemes().iterator();

      while(var2.hasNext()) {
         UI theme = (UI)var2.next();
         theme.buttonKeyPressed(this, key);
      }

   }
}
