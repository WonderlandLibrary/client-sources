package rina.turok.bope.bopemod.guiscreen.hud;

import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeUser extends BopePinnable {
   public BopeUser() {
      super("User", "User", 1.0F, 0, 0);
   }

   public void render() {
      if (this.is_on_gui()) {
         this.background();
      }

      String line = "Welcome " + this.mc.player.getName() + " :^)";
      this.create_line(line, 1, 2);
      this.set_width(this.get(line, "width") + 2);
      this.set_height(this.get(line, "height") + 2 + 2);
   }
}
