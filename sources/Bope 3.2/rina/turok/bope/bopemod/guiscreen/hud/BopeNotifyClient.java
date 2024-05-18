package rina.turok.bope.bopemod.guiscreen.hud;

import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeNotifyClient extends BopePinnable {
   public String actual_notify = "";
   public boolean value_on = false;

   public BopeNotifyClient() {
      super("Notify Client", "NotifyClient", 1.0F, 0, 0);
   }

   public void render() {
      if (this.is_on_gui()) {
         this.background();
      }

      this.create_line(this.actual_notify, 1, 1);
      if (this.value_on) {
         this.set_width(this.get(this.actual_notify, "width") + 1);
      } else {
         this.set_width(10);
      }

      this.set_height(this.get(this.actual_notify, "height") + 2);
   }

   public void notify_hud(String value) {
      if (value.equalsIgnoreCase("null")) {
         this.actual_notify = "";
         this.value_on = false;
      } else {
         this.actual_notify = value;
         this.value_on = true;
      }

   }
}
