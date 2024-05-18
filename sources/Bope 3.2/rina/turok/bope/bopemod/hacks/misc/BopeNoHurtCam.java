package rina.turok.bope.bopemod.hacks.misc;

import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeNoHurtCam extends BopeModule {
   public BopeNoHurtCam() {
      super(BopeCategory.BOPE_MISC, false);
      this.name = "No Hurt Cam";
      this.tag = "NoHurtCam";
      this.description = "Disable effect hurt of player.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }
}
