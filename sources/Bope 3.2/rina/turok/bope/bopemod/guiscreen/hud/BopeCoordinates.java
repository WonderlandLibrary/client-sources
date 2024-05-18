package rina.turok.bope.bopemod.guiscreen.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeCoordinates extends BopePinnable {
   ChatFormatting db;
   ChatFormatting dr;
   boolean state;

   public BopeCoordinates() {
      super("Coordinates", "Coordinates", 1.0F, 0, 0);
      this.db = Bope.r;
      this.dr = Bope.r;
      this.state = false;
   }

   public void render() {
      if (this.is_on_gui()) {
         this.background();
      }

      String x = Bope.g + "[" + this.db + String.format("%.2f", this.mc.player.posX) + Bope.g + Bope.r;
      String y = Bope.g + ", " + this.db + String.format("%.2f", this.mc.player.posY) + Bope.g + Bope.r;
      String z = Bope.g + ", " + this.db + String.format("%.2f", this.mc.player.posZ) + Bope.g + "]" + Bope.r;
      String x_nether = Bope.g + "[" + this.dr + String.format("%.2f", this.mc.player.posX * 0.125D) + Bope.g + Bope.r;
      String z_nether = Bope.g + ", " + this.dr + String.format("%.2f", this.mc.player.posZ * 0.125D) + Bope.g + "]" + Bope.r;
      String line = "XYZ " + x + y + z + x_nether + z_nether;
      this.create_line(line, 1, 2);
      boolean in_gui = this.mc.ingameGUI.getChatGUI().getChatOpen();
      if (in_gui && this.get_y() + this.get_height() >= BopeDraw.get_height() - this.get_height() - 1) {
         int comparator = BopeDraw.get_height() - this.get_height() - 17;
         this.set_y(comparator);
         this.state = true;
      }

      if (!in_gui && this.state) {
         this.set_y(BopeDraw.get_height() - this.get_height() - 1);
         this.state = false;
      }

      this.set_width(this.get(line, "width"));
      this.set_height(this.get(line, "height") + 2 + 2);
   }
}
