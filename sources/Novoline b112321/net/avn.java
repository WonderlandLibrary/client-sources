package net;

import net.UW;
import net.VN;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEu;
import net.adZ;
import net.ae9;
import net.as0;
import net.axu;

public final class avn extends as0 {
   @VN("place-delay")
   public final aE8 x = (aE8)((aE8)axu.b(Integer.valueOf(3)).d(Integer.valueOf(1))).c(Integer.valueOf(4));
   @VN("blocks-only")
   public final aEu y = axu.b();

   public avn(UW var1) {
      super(var1, "FastPlace", "Fast Place", a2V.MISC, "place blocks faster");
      ae9.a(new adZ("PLACE_DELAY", "Place Delay", a6d.SLIDER, this, this.x, 1.0D));
      ae9.a(new adZ("BLOCKS_ONLY", "Blocks Only", a6d.CHECKBOX, this, this.y));
   }
}
