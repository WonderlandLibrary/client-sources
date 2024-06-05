package digital.rbq.gui.clickgui.classic.component;

import digital.rbq.gui.clickgui.classic.ClickGUI;
import digital.rbq.gui.clickgui.classic.window.Window;

public class Placeholder extends Component {
   public Placeholder(Window window, int offX, int offY, Component target) {
      super(window, offX, offY, target.title);
      this.width = Math.max(ClickGUI.defaultWidth, window.width);
      this.height = 0;
      this.type = "Placeholder";
   }

   public void render(int mouseX, int mouseY) {
   }

   public void mouseUpdates(int mouseX, int mouseY, boolean isPressed) {
   }
}
