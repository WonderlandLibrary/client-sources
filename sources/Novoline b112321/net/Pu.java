package net;

import com.google.common.collect.Iterators;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import net.C1;
import net.Jv;
import net.aFm;
import net.aIp;
import net.akH;

final class Pu implements Iterator {
   private final Deque a = new ArrayDeque();

   Pu(akH var1) {
      this.a.push(Iterators.singletonIterator(new Jv(var1.g(), var1)));
   }

   public boolean hasNext() {
      String[] var1 = aIp.c();
      return !this.a.isEmpty();
   }

   public C1 a() {
      Iterator var2 = (Iterator)this.a.getLast();
      aIp.c();
      Jv var3 = (Jv)var2.next();
      if(!var2.hasNext()) {
         this.a.removeLast();
      }

      Iterator var4 = aFm.b(var3);
      if(var4.hasNext()) {
         this.a.addLast(var4);
      }

      return var3;
   }
}
