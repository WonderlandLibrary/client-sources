package rina.turok.bope.bopemod.system.event;

import rina.turok.bope.bopemod.BopeModule;
import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.hacks.BopeCategory;

public class BopeEventRender0 extends BopeModule {
   float event_partial = 0.0F;

   public BopeEventRender0() {
      super(BopeCategory.BOPE_SYS, false);
      this.name = "System Event Render 0";
      this.tag = "SystemEventRender0";
      this.description = "Events render to HUD and stuff.";
      this.set_active(true);
   }

   public void render(BopeEventRender event) {
      this.event_partial = event.get_partial_ticks();
   }

   public double value_double_0() {
      return (double)this.event_partial;
   }
}
