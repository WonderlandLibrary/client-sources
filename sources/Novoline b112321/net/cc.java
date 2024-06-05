package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.bgR;
import net.cA;
import net.cQ;

public abstract class cc extends cA {
   private double c;
   private double d;
   private double e;

   protected cc(bgR var1) {
      super(var1);
   }

   public double c() {
      return this.c;
   }

   public double a() {
      return this.d;
   }

   public double b() {
      return this.e;
   }

   public void b(double var1) {
      this.c = var1;
   }

   public void a(double var1) {
      this.d = var1;
   }

   public void c(double var1) {
      this.e = var1;
   }

   public void a(PacketWrapperImpl var1, boolean var2) throws Exception {
      this.a(((Double)var1.b(Type.m, 0)).doubleValue(), ((Double)var1.b(Type.m, 1)).doubleValue(), ((Double)var1.b(Type.m, 2)).doubleValue(), var2);
   }

   public void a(double var1, double var3, double var5, boolean var7) {
      boolean var8 = cQ.a();
      this.c += var1;
      this.d += var3;
      this.e += var5;
      this.c = var1;
      this.d = var3;
      this.e = var5;
   }
}
