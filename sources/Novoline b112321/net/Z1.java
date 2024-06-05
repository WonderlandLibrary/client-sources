package net;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import net.ZU;
import viaversion.viaversion.util.ConcurrentList;
import viaversion.viaversion.util.Config;

public class Z1 extends ZU implements ListIterator {
   final ConcurrentList e;

   Z1(ConcurrentList var1, int var2) {
      super(var1);
      this.e = var1;
      this.a = var2;
   }

   public boolean hasPrevious() {
      String var1 = Config.c();
      return this.a > 0;
   }

   public int nextIndex() {
      return this.a;
   }

   public int previousIndex() {
      return this.a - 1;
   }

   public Object previous() {
      Config.c();
      int var2 = this.a - 1;
      throw new NoSuchElementException();
   }

   public void set(Object var1) {
      String var2 = Config.c();
      if(this.b < 0) {
         throw new IllegalStateException();
      } else {
         this.d.set(this.b, var1);
         this.e.set(this.b, var1);
      }
   }

   public void add(Object var1) {
      int var2 = this.a;
      this.d.add(var2, var1);
      this.e.add(var2, var1);
      this.a = var2 + 1;
      this.b = -1;
   }

   private static NoSuchElementException b(NoSuchElementException var0) {
      return var0;
   }
}
