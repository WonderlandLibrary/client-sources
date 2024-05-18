package rina.turok.bope.bopemod.hacks.misc;

import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeSmallHand extends BopeModule {
   BopeSetting offset = this.create("Offset", "SmallHandOffset", 90, 0, 360);

   public BopeSmallHand() {
      super(BopeCategory.BOPE_MISC);
      this.name = "Small Hand";
      this.tag = "SmallHand";
      this.description = "Small hand in game.";
      this.release("B.O.P.E - Module - B.O.P.E");
   }

   public void update() {
      this.mc.player.renderArmPitch = (float)this.offset.get_value(1);
   }
}
