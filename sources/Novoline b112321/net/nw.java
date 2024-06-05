package net;

import java.util.Collections;
import java.util.Iterator;
import net.aZA;
import net.minecraft.util.Cartesian;
import net.minecraft.util.Cartesian$Product$ProductIterator;

class nw implements Iterable {
   private final Class b;
   private final Iterable[] a;

   private nw(Class var1, Iterable[] var2) {
      this.b = var1;
      this.a = var2;
   }

   public Iterator iterator() {
      return (Iterator)(this.a.length <= 0?Collections.singletonList(Cartesian.access$200(this.b, 0)).iterator():new Cartesian$Product$ProductIterator(this.b, this.a));
   }

   nw(Class var1, Iterable[] var2, aZA var3) {
      this(var1, var2);
   }
}
