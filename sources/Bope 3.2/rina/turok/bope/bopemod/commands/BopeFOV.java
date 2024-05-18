package rina.turok.bope.bopemod.commands;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeCommand;
import rina.turok.bope.bopemod.BopeMessage;

public class BopeFOV extends BopeCommand {
   public BopeFOV() {
      super("fov", "To change fov value");
   }

   public boolean get_message(String[] message) {
      String value = "null";
      if (message.length > 1) {
         value = message[1];
      }

      if (message.length > 2) {
         BopeMessage.send_client_error_message("fov +30 or -180");
         return true;
      } else if (value.equals("null")) {
         BopeMessage.send_client_error_message("fov +30 or -180");
         return true;
      } else {
         try {
            Float.parseFloat(value);
         } catch (Exception var4) {
            BopeMessage.send_client_error_message("Its not a float or a intenger.");
            return true;
         }

         float value_to_float = Float.parseFloat(value);
         this.mc.gameSettings.fovSetting = this.clamp(value_to_float, 30.0F, 179.9F);
         BopeMessage.send_client_message("Setted " + Bope.g + Float.toString(this.clamp(value_to_float, 30.0F, 179.9F)) + Bope.r + " to field of view.");
         return true;
      }
   }

   public float clamp(float value, float min, float max) {
      if (value <= min) {
         return min;
      } else {
         return value >= max ? max : value;
      }
   }
}
