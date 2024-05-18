package rina.turok.bope.bopemod.hacks.misc;

import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeNoEntityTrace extends BopeModule {
   public BopeNoEntityTrace() {
      super(BopeCategory.BOPE_MISC, false);
      this.name = "No Entity Trace";
      this.tag = "NoEntityTrace";
      this.description = "Is able to active mining while in entity trace mouse.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public boolean value_boolean_0() {
      return true;
   }
}
