package net;

import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aEl;
import net.adZ;
import net.ae9;
import net.as0;
import net.axu;

public final class asd extends as0 {
   @VN("range")
   private final aEl x = (aEl)((aEl)axu.a(Float.valueOf(5.0F)).d(Float.valueOf(3.0F))).c(Float.valueOf(5.0F));
   private boolean y;

   public asd(UW var1) {
      super(var1, "Reach", a2V.COMBAT, "Expands reach");
      ae9.a(new adZ("Reach_Distance", "Range", a6d.SLIDER, this, this.x, 0.1D));
   }

   public float a() {
      return ((Float)this.x.a()).floatValue();
   }
}
