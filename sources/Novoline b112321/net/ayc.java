package net;

import java.util.concurrent.ThreadLocalRandom;
import net.azi;
import net.d3;
import net.minecraft.util.MathHelper;

public final class ayc {
   private final azi i;
   private final String g;
   final d3 f = new d3();
   boolean b;
   public final int c;
   private String d;
   public double h;
   public double a;
   public int e;

   ayc(String var1, int var2, azi var3) {
      this.g = var1;
      this.i = var3;
      this.h = 0.0D;
      this.a = 50.0D;
      this.c = var2;
      this.b = false;
      this.e = ThreadLocalRandom.current().nextInt(0, 360);
   }

   ayc(String var1, String var2, int var3, azi var4) {
      this.g = var2;
      this.i = var4;
      this.h = 0.0D;
      azi.b();
      this.a = 50.0D;
      this.c = var3;
      this.b = false;
      this.d = var1;
      this.e = ThreadLocalRandom.current().nextInt(0, 360);
   }

   public azi h() {
      return this.i;
   }

   public String j() {
      return this.g;
   }

   public d3 d() {
      return this.f;
   }

   public int k() {
      return this.c;
   }

   public double a() {
      return this.h;
   }

   public double e() {
      return this.a;
   }

   public azi c() {
      return this.i;
   }

   public boolean g() {
      return this.b;
   }

   public void a(boolean var1) {
      this.b = var1;
   }

   public void a(double var1) {
      this.h = var1;
   }

   public void b(double var1) {
      this.a = var1;
   }

   public double f() {
      return (double)MathHelper.a((float)(this.d().d() - this.d().c()), 0.0F, (float)this.k());
   }

   public String b() {
      return this.d;
   }

   public int i() {
      return this.e;
   }
}
