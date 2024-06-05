package net;

import java.awt.Color;
import java.util.Iterator;
import net.K6;
import net.a1I;
import net.aX9;
import net.ava;
import net.aw5;
import net.d3;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;

public class ap6 {
   private K6 e;
   public int a;
   public a1I i;
   private float h = 0.0F;
   private d3 b = new d3();
   public static final Color d = new Color(255, 255, 255, 255);
   public static final Color f = new Color(0, 0, 0, 130);
   public static final Color g = new Color(0, 0, 0, 120);
   private static boolean c;

   public ap6(K6 var1, a1I var2) {
      this.e = var1;
      this.i = var2;
   }

   public void b(int var1, int var2) {
      a();
      this.a = (int)(this.i.c() + 15.0F);
      boolean var4 = this.i.c() == this;
      ava var5 = (ava)gZ.g().d().b(ava.class);
      Iterator var6 = this.i.a().iterator();
      if(var6.hasNext()) {
         ap6 var7 = (ap6)var6.next();
         if(var7 == this) {
            ;
         }

         this.a += var7.c();
      }

      if(var4 && this.h < 1.0F) {
         this.h = (float)((double)this.h + 0.0025D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()));
      }

      if(!var4 && this.h > 0.0F) {
         this.h = (float)((double)this.h - 0.0025D * (double)(2000 / Minecraft.getMinecraft().getDebugFPS()));
      }

      this.h = MathHelper.a(this.h, 0.0F, 1.0F);
      Gui.a((double)this.i.d(), (double)this.a, (double)(this.i.d() + 100.0F), (double)(this.a + this.c()), (new Color(40, 40, 40, 255)).getRGB());
      aw5.a.a(this.e.g(), this.i.d() + 50.0F, (float)(this.a + 4), aX9.a(d, new Color(var5.z().getRed(), var5.z().getGreen(), var5.z().getBlue(), 250), this.h), true);
   }

   public boolean a(int var1, int var2) {
      d();
      this.a = (int)(this.i.c() + 15.0F);
      Iterator var4 = this.i.a().iterator();
      if(var4.hasNext()) {
         ap6 var5 = (ap6)var4.next();
         if(var5 == this) {
            ;
         }

         this.a += var5.c();
      }

      return (float)var1 >= this.i.d() && var2 >= this.a && (float)var1 <= this.i.d() + 101.0F && var2 <= this.a + this.c();
   }

   public void a(int var1, int var2, int var3) {
      boolean var4 = a();
      if(this.a(var1, var2) && var3 == 0) {
         this.i.a(this);
         if(!this.b.a(200.0F)) {
            gZ.g().u().d(this.e.g());
         }

         this.b.b();
      }

   }

   public int c() {
      return 14;
   }

   public String e() {
      return this.e.g();
   }

   public a1I b() {
      return this.i;
   }

   static {
      b(true);
   }

   public static void b(boolean var0) {
      c = var0;
   }

   public static boolean d() {
      return c;
   }

   public static boolean a() {
      boolean var0 = d();
      return true;
   }
}
