package rina.turok.bope.bopemod.guiscreen.hud;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.pinnables.BopePinnable;

public class BopeServerInfo extends BopePinnable {
   public BopeServerInfo() {
      super("Server Info", "ServerInfo", 1.0F, 0, 0);
   }

   public void render() {
      if (this.is_on_gui()) {
         this.background();
      }

      String ms = Integer.toString(this.get_ping());
      String tps = String.format("%.2f", Bope.get_event_handler().get_tick_rate());
      String info = "MS " + ms + " TPS " + tps;
      this.create_line(info, 1, 1);
      this.set_width(this.get(info, "width") + 1);
      this.set_height(this.get(info, "height") + 2);
   }

   public int get_ping() {
      if (this.mc.player != null && this.mc.getConnection() != null && this.mc.getConnection().getPlayerInfo(this.mc.player.getName()) != null) {
         return this.mc.getConnection().getPlayerInfo(this.mc.player.getName()).getResponseTime();
      } else {
         return -1;
      }
   }
}
