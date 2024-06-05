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

public final class asO extends as0 {
   @VN("size")
   private final aEl x = (aEl)((aEl)axu.a(Float.valueOf(0.3F)).d(Float.valueOf(0.1F))).c(Float.valueOf(1.0F));

   public asO(UW var1) {
      super(var1, a2V.COMBAT, "HitBox", "Hit Box");
      ae9.a(new adZ("ENTITY_BOX", "Box Size", a6d.SLIDER, this, this.x, 0.10000000149011612D));
   }

   public aEl a() {
      return this.x;
   }
}
