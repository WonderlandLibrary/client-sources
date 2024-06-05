package net;

import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Objects;
import net.a2t;
import net.nK;

public abstract class nm extends nK implements ProtocolPipeline {
   protected String a;
   protected int f;
   protected a2t g;
   private static PacketRemapper[] h;

   public nm(String var1, int var2, a2t var3, int var4, int var5) {
      super(var4, var5);
      this.a = var1;
      this.f = var2;
      a();
      this.g = var3;
      if(PacketRemapper.b() == null) {
         b(new PacketRemapper[5]);
      }

   }

   public nm(String var1, int var2, a2t var3) {
      this(var1, var2, var3, 0, 0);
   }

   public void b(int var1, int var2) {
      this.g.b(this.a, (float)this.c, (float)this.d, this.f);
   }

   public String a() {
      return this.a;
   }

   public void a(String var1) {
      this.a = var1;
   }

   public int g() {
      return this.f;
   }

   public void h(int var1) {
      this.f = var1;
   }

   public a2t i() {
      return this.g;
   }

   public void a(a2t var1) {
      this.g = var1;
   }

   public boolean equals(Object var1) {
      PacketRemapper[] var2 = a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof nm)) {
         return false;
      } else {
         nm var3 = (nm)var1;
         return Objects.equals(this.a, var3.a) && this.g.equals(var3.g);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.a, this.g});
   }

   public String toString() {
      PacketRemapper[] var1 = a();
      return "AbstractLabel{text=\'" + this.a + '\'' + ", color=" + this.f + ", fontRenderer=" + this.g + '}';
   }

   public static void b(PacketRemapper[] var0) {
      h = var0;
   }

   public static PacketRemapper[] a() {
      return h;
   }

   static {
      b(new PacketRemapper[4]);
   }
}
