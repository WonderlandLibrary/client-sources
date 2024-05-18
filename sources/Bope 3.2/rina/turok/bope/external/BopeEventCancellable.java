package rina.turok.bope.external;

import me.zero.alpine.type.Cancellable;
import net.minecraft.client.Minecraft;

public class BopeEventCancellable extends Cancellable {
   private BopeEventCancellable.Era era_switch;
   private final float partial_ticks;

   public BopeEventCancellable() {
      this.era_switch = BopeEventCancellable.Era.EVENT_PRE;
      this.partial_ticks = Minecraft.getMinecraft().getRenderPartialTicks();
   }

   public BopeEventCancellable.Era get_era() {
      return this.era_switch;
   }

   public float get_partial_ticks() {
      return this.partial_ticks;
   }

   public static enum Era {
      EVENT_PRE,
      EVENT_PERI,
      EVENT_POST;
   }
}
