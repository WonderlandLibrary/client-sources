package net;

import com.viaversion.viaversion.api.protocol.ProtocolPathKey;
import java.util.Objects;
import net.a6_;
import net.nZ;

public abstract class ni extends nZ implements ProtocolPathKey {
   protected int i;

   public ni(int var1, int var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, var5, var6);
      this.i = var1;
   }

   public void b(int var1, int var2) {
      a6_.a((float)this.c, (float)this.d, (float)this.a, (float)this.f, (float)this.i, this.h);
   }

   public int f() {
      return this.i;
   }

   public void g(int var1) {
      this.i = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = nZ.a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ni)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         ni var3 = (ni)var1;
         return this.i == var3.i;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.i)});
   }

   public String toString() {
      return "AbstractRoundedGroup{radius=" + this.i + ", color=" + this.h + ", width=" + this.a + ", height=" + this.f + ", visible=" + this.e + ", x=" + this.c + ", y=" + this.d + '}';
   }
}
