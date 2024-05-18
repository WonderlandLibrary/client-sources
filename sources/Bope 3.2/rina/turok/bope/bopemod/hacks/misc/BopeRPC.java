package rina.turok.bope.bopemod.hacks.misc;

import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeRPC extends BopeModule {
   BopeSetting message = this.create("Custom", "RPCCustom", "");

   public BopeRPC() {
      super(BopeCategory.BOPE_MISC);
      this.name = "RPC";
      this.tag = "RPC";
      this.description = "Enable Discord rich presence library to work with client B.O.P.E.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void enable() {
      Bope.get_rpc().run();
   }

   public void disable() {
      Bope.get_rpc().stop();
   }

   public void update() {
      Bope.get_rpc().state_option_1 = this.message.get_value("");
   }
}
