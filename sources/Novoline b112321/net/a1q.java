package net;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.Fc;
import net.a14;
import net.a2V;
import net.abf;
import net.ava;
import net.azi;
import net.gZ;
import net.l6;
import net.lX;
import net.le;
import net.minecraft.client.gui.Gui;

public class a1q extends a14 {
   private final List i = new CopyOnWriteArrayList();
   private lX h;
   private l6 j;

   public a1q(float var1, float var2) {
      super((a2V)null, var1, var2);
      this.a();
   }

   public void a(int var1, int var2) {
      l6.d();
      ava var4 = (ava)gZ.g().d().b(ava.class);
      int var5 = 0;
      Iterator var6 = this.i.iterator();
      if(var6.hasNext()) {
         l6 var7 = (l6)var6.next();
         var5 += var7.a();
      }

      ++var5;
      if(!this.e) {
         var5 = 0;
      }

      Gui.a((double)(this.d() - 1.0F), (double)this.c(), (double)(this.d() + 101.0F), (double)(this.c() + 15.0F + (float)var5), (new Color(29, 29, 29, 255)).getRGB());
      abf.a.a("Configs", (double)(this.d() + 4.0F), (double)(this.c() + 4.0F), -1, true);
      if(this.e) {
         this.i.forEach(a1q::lambda$drawScreen$0);
      }

   }

   public void a() {
      this.i.clear();
      this.a((l6)null);
      gZ.g().d().b().e().forEach(this::lambda$refreshConfigs$1);
      this.h = new lX("Config name", this);
      this.i.add(this.h);
      this.i.add(new le("Load", this, a1q::lambda$refreshConfigs$2));
      this.i.add(new le("Save", this, this::lambda$refreshConfigs$3));
      this.i.add(new le("Delete", this, this::lambda$refreshConfigs$4));
   }

   public void a(int var1, int var2, int var3) {
      String var4 = l6.d();
      if(this.b(var1, var2) && var3 == 1) {
         this.e = !this.e;
      }

      if(this.e) {
         this.i.forEach(a1q::lambda$mouseClicked$5);
      }

   }

   public void a(char var1, int var2) {
      this.i.forEach(a1q::lambda$keyTyped$6);
   }

   public List c() {
      return this.i;
   }

   public l6 d() {
      return this.j;
   }

   public void a(l6 var1) {
      this.j = var1;
   }

   private static void lambda$keyTyped$6(char var0, int var1, l6 var2) {
      var2.a(var0, var1);
   }

   private static void lambda$mouseClicked$5(int var0, int var1, int var2, l6 var3) {
      var3.a(var0, var1, var2);
   }

   private void lambda$refreshConfigs$4(String var1) {
      String var2 = l6.d();
      if(var1.isEmpty()) {
         gZ.g().t().a("Select a config!", azi.ERROR);
      }

      Fc.b(gZ.g().d().b(), var1);
      this.a();
   }

   private void lambda$refreshConfigs$3(String var1) {
      String var2 = l6.d();
      Fc.c(gZ.g().d().b(), this.d() != null?this.d().b():this.h.a());
      this.a();
   }

   private static void lambda$refreshConfigs$2(String var0) {
      String var1 = l6.d();
      if(var0.isEmpty()) {
         gZ.g().t().a("Select a config!", azi.ERROR);
      }

      Fc.a(gZ.g().d().b(), var0);
   }

   private void lambda$refreshConfigs$1(String var1) {
      this.i.add(new l6(var1, this));
   }

   private static void lambda$drawScreen$0(int var0, int var1, l6 var2) {
      var2.b(var0, var1);
   }
}
