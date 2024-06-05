package net;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Objects;
import net.Ql;
import net.a6_;
import net.ak9;
import net.nZ;

public abstract class nH extends nZ implements ak9 {
   protected ProtocolPipeline i;

   public nH(ProtocolPipeline var1, int var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, var5, var6);
      this.c(var1);
   }

   public void b(int var1, int var2) {
      nZ.a();
      a6_.b((float)this.c, (float)this.d, (float)this.a, (float)this.f, this.h);
      if(this.i != null) {
         this.i.a(var1, var2);
      }

   }

   public void a() throws NullPointerException {
      Ql.a((Object)this.i, "title");
      int var1 = (int)((float)this.c + (float)(this.a - this.i.h()) / 2.0F);
      int var2 = this.d + 4;
      this.i.c(var1, var2);
   }

   public ProtocolPipeline c() {
      return this.i;
   }

   public void c(ProtocolPipeline var1) {
      int[] var2 = nZ.a();
      this.i = var1;
      this.a();
   }

   public boolean equals(Object var1) {
      int[] var2 = nZ.a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof nH)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         nH var3 = (nH)var1;
         return Objects.equals(this.i, var3.i);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.i});
   }

   public String toString() {
      int[] var1 = nZ.a();
      String var10000 = "AbstractGroupWithTitle{title=" + this.i + ", color=" + this.h + ", width=" + this.a + ", height=" + this.f + ", visible=" + this.e + ", x=" + this.c + ", y=" + this.d + '}';
      if(PacketRemapper.b() == null) {
         nZ.a(new int[4]);
      }

      return var10000;
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
