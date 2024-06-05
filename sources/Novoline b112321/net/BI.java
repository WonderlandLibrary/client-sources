package net;

import java.util.SortedMap;
import java.util.TreeMap;
import net.CZ;
import net.MU;
import net.al3;
import net.s;
import net.u;

public final class BI {
   private CZ a = CZ.OVERWRITE;
   private final SortedMap b = new TreeMap(new al3());

   public BI a(Object[] var1, MU var2) {
      this.b.put(var1, var2);
      return this;
   }

   public CZ a() {
      return this.a;
   }

   public BI a(CZ var1) {
      this.a = var1;
      return this;
   }

   public s b() {
      return new u(this.b, this.a);
   }
}
