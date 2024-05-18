package rina.turok.bope.bopemod.hacks.render;

import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeFreeCameraOrient extends BopeModule {
   public BopeFreeCameraOrient() {
      super(BopeCategory.BOPE_RENDER, false);
      this.name = "Free Camera Orient";
      this.tag = "FreeCameraOrient";
      this.description = "Bypass camera.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }
}
