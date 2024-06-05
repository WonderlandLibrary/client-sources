package hawk.ui.clickgui;

import com.lukflug.panelstudio.settings.Toggleable;
import hawk.modules.render.ModToggleGUI;

public class ColorToggleUtil implements Toggleable {
   public static Toggleable colorModel;

   public boolean isOn() {
      return ModToggleGUI.INSTANCE.colorModel.is("RGB");
   }

   public void toggle() {
   }
}
