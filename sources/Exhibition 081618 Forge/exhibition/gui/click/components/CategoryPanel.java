package exhibition.gui.click.components;

import exhibition.Client;
import exhibition.gui.click.ui.UI;
import java.util.ArrayList;
import java.util.Iterator;

public class CategoryPanel {
   public float x;
   public float y;
   public boolean visible;
   public CategoryButton categoryButton;
   public String headerString;
   public ArrayList<Button> buttons;
   public ArrayList<Slider> sliders;
   public ArrayList<DropdownBox> dropdownBoxes;
   public ArrayList<MultiDropdownBox> multiDropdownBoxes;
   public ArrayList<Label> labels;
   public ArrayList<Checkbox> checkboxes;
   public ArrayList<ColorPreview> colorPreviews;
   public ArrayList<GroupBox> groupBoxes;
   public ArrayList<TextBox> textBoxes;

   public CategoryPanel(String name, CategoryButton categoryButton, float x, float y) {
      this.headerString = name;
      this.x = x;
      this.y = y;
      this.categoryButton = categoryButton;
      this.colorPreviews = new ArrayList();
      this.buttons = new ArrayList();
      this.sliders = new ArrayList();
      this.dropdownBoxes = new ArrayList();
      this.multiDropdownBoxes = new ArrayList();
      this.labels = new ArrayList();
      this.checkboxes = new ArrayList();
      this.groupBoxes = new ArrayList();
      this.textBoxes = new ArrayList();
      this.visible = false;
      categoryButton.panel.theme.categoryPanelConstructor(this, categoryButton, x, y);
   }

   public void draw(float x, float y) {
      Iterator var3 = Client.getClickGui().getThemes().iterator();

      while(var3.hasNext()) {
         UI theme = (UI)var3.next();
         theme.categoryPanelDraw(this, x, y);
      }

   }

   public void mouseClicked(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.categoryPanelMouseClicked(this, x, y, button);
      }

   }

   public void mouseReleased(int x, int y, int button) {
      Iterator var4 = Client.getClickGui().getThemes().iterator();

      while(var4.hasNext()) {
         UI theme = (UI)var4.next();
         theme.categoryPanelMouseMovedOrUp(this, x, y, button);
      }

   }
}
