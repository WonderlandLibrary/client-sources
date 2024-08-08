package com.example.editme.events;

import com.example.editme.util.client.Wrapper;
import me.zero.alpine.type.Cancellable;

public class EditmeEvent extends Cancellable {
   private final float partialTicks;
   private EditmeEvent.Era era;

   public EditmeEvent.Era setEra(EditmeEvent.Era var1) {
      this.era = var1;
      return this.era;
   }

   public EditmeEvent() {
      this.era = EditmeEvent.Era.PRE;
      this.partialTicks = Wrapper.getMinecraft().func_184121_ak();
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public EditmeEvent.Era getEra() {
      return this.era;
   }

   public static enum Era {
      PERI;

      private static final EditmeEvent.Era[] $VALUES = new EditmeEvent.Era[]{PRE, PERI, POST};
      PRE,
      POST;
   }
}
