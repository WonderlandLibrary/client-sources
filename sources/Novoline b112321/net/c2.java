package net;

import com.viaversion.viaversion.api.minecraft.Position;
import net.bgR;
import net.cA;
import net.cq;

public class c2 extends cA {
   private long d = 0L;
   private Position c = null;

   public c2(bgR var1) {
      super(var1);
   }

   public boolean a(int var1) {
      String var2 = cq.c();
      return System.currentTimeMillis() > this.d + (long)var1;
   }

   public void a() {
      this.d = System.currentTimeMillis();
   }

   public long c() {
      return this.d;
   }

   public Position b() {
      return this.c;
   }

   public void a(Position var1) {
      this.c = var1;
   }
}
