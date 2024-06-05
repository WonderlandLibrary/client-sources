package net;

import cc.novoline.Novoline;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.fonts.impl.Fonts$ICONFONT$ICONFONT_35;
import java.awt.Color;
import net.D2;
import net.aB0;
import net.aHK;
import net.acE;
import net.akP;
import net.za;
import net.minecraft.client.gui.Gui;

public class akb extends akP {
   private double q;
   private double p;
   private aHK m;
   private D2 n;
   private aB0 o;
   private static String r;

   public akb(aHK var1, EnumModuleType var2) {
      super(var1, "OTHER", var2);
      this.m = var1;
      this.i();
      this.a();
   }

   public void a() {
      this.o = new aB0(this);
      this.n = new D2(this);
   }

   public void a(int var1, int var2, float var3) {
      b();
      this.i();
      HUD var5 = (HUD)Novoline.getInstance().getModuleManager().getModule(HUD.class);
      Fonts$ICONFONT$ICONFONT_35.ICONFONT_35.drawString(this.a((EnumModuleType)null), this.q, this.p, this.a(var1, var2)?(new Color(170, 170, 170)).getRGB():-1, true);
      za.b(false);
      Gui.drawRect(this.m.e() + 5.0D, this.m.d() + 40.0D, this.m.e() + this.m.a() - 5.0D, this.m.d() + this.m.b() - 5.0D, -16777216);
      za.a(true);
      if(this.f()) {
         this.n.d(var1, var2);
         this.o.a(var1, var2);
      }

      za.b();
   }

   public boolean a(int var1, int var2) {
      String var3 = b();
      return (double)var1 >= this.q - 10.0D && (double)var1 <= this.q + 20.0D && (double)var2 >= this.p - 5.0D && (double)var2 <= this.p + 20.0D;
   }

   public void i() {
      double var2 = (this.m.a() - 10.0D - 10.0D) / (double)this.m.c().size();
      int var4 = this.m.c().indexOf(this);
      this.q = this.m.e() + 5.0D + 5.0D + (double)var4 * var2 + var2 / 2.0D - 7.0D;
      b();
      this.p = this.m.d() + 15.0D;
      if(acE.b() == null) {
         b("XwYQec");
      }

   }

   public void a(char var1, int var2) {
      String var3 = b();
      if(this.f()) {
         this.n.a(var1, var2);
      }

      super.a(var1, var2);
   }

   public void b(int var1, int var2, int var3) {
      String var4 = b();
      if(this.f()) {
         this.n.a(var1, var2, var3);
         this.o.b(var1, var2, var3);
      }

      super.b(var1, var2, var3);
   }

   public void a(int var1, int var2, int var3) {
      String var4 = b();
      if(this.f()) {
         this.n.b(var1, var2, var3);
         this.o.a(var1, var2, var3);
      }

      super.a(var1, var2, var3);
   }

   public aHK a() {
      return this.m;
   }

   public String a(EnumModuleType var1) {
      return "E";
   }

   public static void b(String var0) {
      r = var0;
   }

   public static String b() {
      return r;
   }

   static {
      if(b() == null) {
         b("HIHSDb");
      }

   }
}
