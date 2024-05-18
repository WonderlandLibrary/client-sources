package rina.turok.bope.bopemod.hacks.gui;

import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeContainerUtil extends BopeModule {
   BopeSetting transparent = this.create("Transparent", "ContainerUtilTransparent", 255, 0, 255);

   public BopeContainerUtil() {
      super(BopeCategory.BOPE_GUI);
      this.name = "Container Util";
      this.tag = "ContainerUtil";
      this.description = "Modify container inventory, anvil...";
      this.release("B.O.P.E - Module - B.O.P.E");
   }
}
