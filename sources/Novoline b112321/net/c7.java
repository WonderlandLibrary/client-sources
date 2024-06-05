package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.HashMap;
import java.util.Map;
import net.bgR;
import net.c9;
import net.cA;

public class c7 extends cA {
   public int c;
   public String e;
   public boolean d;
   public Map f;

   public c7(bgR var1) {
      super(var1);
      c9.e();
      this.f = new HashMap();
      if(PacketRemapper.b() == null) {
         c9.a(false);
      }

   }
}
