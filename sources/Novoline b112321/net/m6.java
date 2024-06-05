package net;

import java.util.concurrent.CopyOnWriteArrayList;
import net.aaO;

final class m6 extends CopyOnWriteArrayList {
   private static final long serialVersionUID = 666L;
   final aaO a;

   m6(aaO var1) {
      this.a = var1;
      this.add(this.a);
   }
}
