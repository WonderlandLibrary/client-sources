package rina.turok.bope.bopemod.commands;

import java.awt.GraphicsEnvironment;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;

public class BopeFont extends BopeCommand {
   public BopeFont() {
      super("font", "Change font.");
   }

   public boolean get_message(String[] message) {
      String font = "null";
      if (message.length > 1) {
         font = message[1];
      }

      if (message.length > 4) {
         BopeMessage.send_client_error_message("font font");
         return true;
      } else if (font.equals("null")) {
         BopeMessage.send_client_error_message("font font");
         return true;
      } else if (!this.true_font(font)) {
         BopeMessage.send_client_error_message("Never see about this font.");
         return true;
      } else {
         Bope.font_name = font;
         BopeMessage.send_client_message("The new font is " + Bope.g + font + Bope.r + " restart Minecraft");
         Bope.get_config_manager().save_client();
         return true;
      }
   }

   public boolean true_font(String font_name) {
      GraphicsEnvironment g = null;
      g = GraphicsEnvironment.getLocalGraphicsEnvironment();
      String[] fonts = g.getAvailableFontFamilyNames();

      for(int i = 0; i < fonts.length; ++i) {
         if (fonts[i].equals(font_name)) {
            return true;
         }
      }

      return false;
   }
}
