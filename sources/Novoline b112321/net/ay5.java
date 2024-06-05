package net;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import net.aCM;
import net.aCV;
import net.azW;
import net.bgR;
import net.cA;
import net.cD;
import net.r;

public class ay5 extends BackwardsProtocol {
   public ay5() {
      super(azW.class, azW.class, r.class, r.class);
   }

   protected void registerPackets() {
      this.a(azW.KEEP_ALIVE, new aCV(this));
      this.a(r.KEEP_ALIVE, new aCM(this));
   }

   public void a(bgR var1) {
      var1.a((cA)(new cD(var1)));
   }
}
