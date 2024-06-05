package net;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import java.util.Objects;
import net.Ql;
import net.a6_;
import net.af4;
import net.nZ;

public abstract class nl extends nZ implements af4 {
   protected ProtocolPipeline j;
   protected int i;

   public nl(ProtocolPipeline var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var3, var4, var5, var6, var7);
      this.c(var1);
      this.i = var2;
   }

   public void b(int var1, int var2) {
      nZ.a();
      a6_.a((float)this.c, (float)this.d, (float)this.a, (float)this.f, (float)this.i, this.h);
      if(this.j != null) {
         this.j.a(var1, var2);
      }

   }

   public void a() throws NullPointerException {
      Ql.a((Object)this.j, "title");
      int var1 = (int)((float)this.c + (float)(this.a - this.j.h()) / 2.0F);
      int var2 = this.d + 4;
      this.j.c(var1, var2);
   }

   public ProtocolPipeline c() {
      return this.j;
   }

   public void c(ProtocolPipeline var1) {
      int[] var2 = nZ.a();
      this.j = var1;
      this.a();
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
      } else if(!(var1 instanceof nl)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         nl var3 = (nl)var1;
         return this.i == var3.i && Objects.equals(this.j, var3.j);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.j, Integer.valueOf(this.i)});
   }

   public String toString() {
      int[] var1 = nZ.a();
      return "AbstractRoundedGroupWithTitle{title=" + this.j + ", radius=" + this.i + ", color=" + this.h + ", width=" + this.a + ", height=" + this.f + ", visible=" + this.e + ", x=" + this.c + ", y=" + this.d + '}';
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
