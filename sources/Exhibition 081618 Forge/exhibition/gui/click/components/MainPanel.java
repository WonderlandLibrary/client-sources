package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import java.util.ArrayList;
import java.util.Iterator;

public class MainPanel {
   public int opacity = 0;
   public float x;
   public float y;
   public String headerString;
   public float dragX;
   public float dragY;
   public float lastDragX;
   public float lastDragY;
   public boolean dragging;
   UI theme;
   public boolean opened;
   public ArrayList<CategoryButton> typeButton;
   public ArrayList<CategoryPanel> typePanel;
   public ArrayList<SLButton> slButtons;

   public boolean isOpened() {
      return this.opened;
   }

   public void setOpened(boolean opened) {
      this.opened = opened;
   }

   public MainPanel(String header, float x, float y, UI theme) {
      this.headerString = header;
      this.x = x;
      this.y = y;
      this.theme = theme;
      this.typeButton = new ArrayList();
      this.typePanel = new ArrayList();
      this.slButtons = new ArrayList();
      theme.panelConstructor(this, x, y);
   }

   public void mouseClicked(int x, int y, int state) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.panelMouseClicked(this, x, y, state);
      }

   }

   public void mouseMovedOrUp(int x, int y, int state) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.panelMouseMovedOrUp(this, x, y, state);
      }

   }

   public void draw(int mouseX, int mouseY) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.mainPanelDraw(this, mouseX, mouseY);
      }

   }

   public void keyPressed(int key) {
      Iterator var2 = Client.getClickGui().getThemes().iterator();

      while(var2.hasNext()) {
         UI theme = (UI)var2.next();
         theme.mainPanelKeyPress(this, key);
      }

   }
}
