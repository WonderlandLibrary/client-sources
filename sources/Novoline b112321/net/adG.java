package net;

import java.awt.Color;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.At;
import net.Jg;
import net.a14;
import net.a6_;
import net.a6d;
import net.adZ;
import net.ae9;
import net.as0;
import net.av2;
import net.ava;
import net.aw5;
import net.d3;
import net.gZ;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class adG {
   private final as0 m;
   public int i;
   public int j;
   public a14 f;
   public boolean b;
   public List d;
   public d3 h;
   private double g;
   private double k;
   private int c;
   float a;
   float l;
   private float e;

   public adG(as0 var1, a14 var2) {
      a14.e();
      this.d = new CopyOnWriteArrayList();
      this.h = new d3();
      this.g = 3.0D;
      this.k = 5.0D;
      this.c = 0;
      this.a = 0.0F;
      this.l = 0.0F;
      this.e = 0.0F;
      this.m = var1;
      this.f = var2;
      Iterator var4 = ae9.a(var1).iterator();
      if(var4.hasNext()) {
         adZ var5 = (adZ)var4.next();
         this.d.add(new Jg(var5, this));
      }

   }

   public void b(int var1, int var2) {
      a14.f();
      Minecraft var4 = Minecraft.getMinecraft();
      int var5 = var4.getDebugFPS();
      if(this.m.y() && this.a < 1.0F) {
         this.a = (float)((double)this.a + 0.0025D * (double)(2000 / var5));
      }

      if(!this.m.y() && this.a > 0.0F) {
         this.a = (float)((double)this.a - 0.0025D * (double)(2000 / var5));
      }

      if(!this.m.y()) {
         if(this.a(var1, var2) && this.l < 1.0F) {
            this.l = (float)((double)this.l + 0.0025D * (double)(2000 / var5));
         }

         if(!this.a(var1, var2) && this.l > 0.0F) {
            this.l = (float)((double)this.l - 0.0025D * (double)(2000 / var5));
         }
      }

      this.a = MathHelper.a(this.a, 0.0F, 1.0F);
      this.l = MathHelper.a(this.l, 0.0F, 1.0F);
      if(this.i < this.a()) {
         this.i = (int)((double)this.i + (double)(this.a() + 9 - this.i) * 0.1D);
      }

      if(this.i > this.a()) {
         this.i = (int)((double)this.i + (double)(this.a() - this.i) * 0.1D);
      }

      this.j = (int)(this.f.c() + 15.0F);
      Iterator var6 = this.f.b().iterator();
      if(var6.hasNext()) {
         adG var7 = (adG)var6.next();
         if(var7 == this) {
            ;
         }

         this.j += var7.i;
      }

      ava var11 = (ava)gZ.g().d().b(ava.class);
      Color var12 = var11.z();
      new Color(16777215);
      if(var12.getRed() > 220 && var12.getBlue() > 220 && var12.getGreen() > 220) {
         Color var8 = var12.darker().darker();
      }

      Gui.a((double)this.f.d(), (double)this.j, (double)(this.f.d() + 100.0F), (double)(this.j + this.i), (new Color(40, 40, 40, 255)).getRGB());
      if(!this.m.y() && this.a == 0.0F) {
         Gui.a((double)this.f.d(), (double)this.j, (double)(this.f.d() + 100.0F), (double)(this.j + 14), this.a(new Color(40, 40, 40, 255), new Color(29, 29, 29, 255), MathHelper.a(this.l, 0.0F, 1.0F)));
      }

      Gui.a((double)this.f.d(), (double)this.j, (double)(this.f.d() + 100.0F), (double)(this.j + 14), this.a(new Color(40, 40, 40, 255), var12, MathHelper.a(this.a, 0.0F, 1.0F)));
      aw5.a.a(this.m.j(), (double)(this.f.d() + 2.0F), (double)((float)(this.j + 4)), -1, true);
      if(!this.d.isEmpty()) {
         double var9 = (double)var5 / 8.3D;
         if(this.b && this.g > -3.0D) {
            this.g -= 3.0D / var9;
         }

         if(!this.b && this.g < 3.0D) {
            this.g += 3.0D / var9;
         }

         if(this.b && this.k < 8.0D) {
            this.k += 3.0D / var9;
         }

         if(!this.b && this.k > 5.0D) {
            this.k -= 3.0D / var9;
         }

         a6_.a((double)(this.f.d() + 92.0F), (double)this.j + this.k, 2, -1, this.g);
      }

      if(this.b || this.i != 14) {
         ScaledResolution var13 = new ScaledResolution(Minecraft.getMinecraft());
         if(this.i != this.a() && var13.getScaleFactor() != 1) {
            GL11.glScissor(0, var13.b(Minecraft.getMinecraft()) * 2 - this.j * 2 - this.i * 2, var13.a(Minecraft.getMinecraft()) * 2, this.i * 2);
            GL11.glEnable(3089);
            this.d.stream().filter(adG::lambda$drawScreen$0).forEach(adG::lambda$drawScreen$1);
            GL11.glDisable(3089);
            this.d.stream().filter(adG::lambda$drawScreen$2).forEach(adG::lambda$drawScreen$3);
         }

         this.d.stream().filter(adG::lambda$drawScreen$4).forEach(adG::lambda$drawScreen$5);
      }

      this.d.forEach(adG::lambda$drawScreen$6);
   }

   private int a(Color var1, Color var2, float var3) {
      int var4 = (int)((float)var1.getRed() + (float)(var2.getRed() - var1.getRed()) * var3);
      int var5 = (int)((float)var1.getGreen() + (float)(var2.getGreen() - var1.getGreen()) * var3);
      int var6 = (int)((float)var1.getBlue() + (float)(var2.getBlue() - var1.getBlue()) * var3);
      int var7 = (int)((float)var1.getAlpha() + (float)(var2.getAlpha() - var1.getAlpha()) * var3);

      try {
         return (new Color(var4, var5, var6, var7)).getRGB();
      } catch (Exception var9) {
         return -1;
      }
   }

   public void a(char var1, int var2) {
      boolean var3 = a14.e();
      if(this.b) {
         this.d.stream().filter(adG::lambda$keyTyped$7).forEach(adG::lambda$keyTyped$8);
      }

   }

   public int a() {
      boolean var1 = a14.e();
      if(this.b) {
         int var2 = 17;
         Iterator var3 = ((List)this.d.stream().filter(adG::lambda$getY$9).collect(Collectors.toList())).iterator();
         if(var3.hasNext()) {
            Jg var4 = (Jg)var3.next();
            switch(At.a[var4.b.f().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               var2 += 15;
            case 8:
               if(var4.c) {
                  var2 += 15 + var4.b.v().f().size() * 17;
               }

               var2 += 15;
            }
         }

         return this.f.b.indexOf(this) == this.f.b.size() - 1?var2:var2;
      } else {
         return 14;
      }
   }

   public void a(int var1, int var2, int var3) throws IOException {
      boolean var4 = a14.f();
      if(this.a(var1, var2)) {
         switch(var3) {
         case 0:
            this.m.e();
         case 1:
            if(!ae9.a(this.m).isEmpty()) {
               if(!this.b && ((Boolean)((av2)gZ.g().d().b(av2.class)).y().a()).booleanValue()) {
                  this.f.b.forEach(adG::lambda$mouseClicked$10);
               }

               this.b = !this.b;
               Iterator var5 = ae9.a(this.m).iterator();
               if(var5.hasNext()) {
                  adZ var6 = (adZ)var5.next();
                  if(var6.f() == a6d.TEXTBOX) {
                     var6.e(false);
                  }
               }
            }
         }
      }

      if(this.b) {
         this.d.stream().filter(adG::lambda$mouseClicked$11).forEach(adG::lambda$mouseClicked$12);
      }

   }

   public void b(int var1, int var2, int var3) {
      boolean var4 = a14.e();
      if(this.b) {
         this.d.stream().filter(adG::lambda$mouseReleased$13).forEach(adG::lambda$mouseReleased$14);
      }

   }

   public boolean a(int var1, int var2) {
      a14.e();
      this.j = (int)(this.f.c() + 15.0F);
      Iterator var4 = this.f.b().iterator();
      if(var4.hasNext()) {
         adG var5 = (adG)var4.next();
         if(var5 == this) {
            ;
         }

         this.j += var5.i;
      }

      return this.b?(float)var1 >= this.f.d() && var2 >= this.j && (float)var1 <= this.f.d() + 101.0F && var2 <= this.j + 14:(float)var1 >= this.f.d() && var2 >= this.j && (float)var1 <= this.f.d() + 101.0F && var2 <= this.j + this.i;
   }

   private void c() {
   }

   public as0 b() {
      return this.m;
   }

   private static void lambda$mouseReleased$14(int var0, int var1, int var2, Jg var3) {
      var3.b(var0, var1, var2);
   }

   private static boolean lambda$mouseReleased$13(Jg var0) {
      boolean var1 = a14.f();
      return var0.b.l() != null?((Boolean)var0.b.l().get()).booleanValue():true;
   }

   private static void lambda$mouseClicked$12(int var0, int var1, int var2, Jg var3) {
      Jg var10000 = var3;
      int var10001 = var0;
      int var10002 = var1;
      int var10003 = var2;

      try {
         var10000.a(var10001, var10002, var10003);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private static boolean lambda$mouseClicked$11(Jg var0) {
      boolean var1 = a14.f();
      return var0.b.l() != null?((Boolean)var0.b.l().get()).booleanValue():true;
   }

   private static void lambda$mouseClicked$10(adG var0) {
      boolean var1 = a14.f();
      if(var0.b) {
         var0.b = false;
      }

   }

   private static boolean lambda$getY$9(Jg var0) {
      boolean var1 = a14.e();
      return var0.b.l() != null?((Boolean)var0.b.l().get()).booleanValue():true;
   }

   private static void lambda$keyTyped$8(char var0, int var1, Jg var2) {
      var2.a(var0, var1);
   }

   private static boolean lambda$keyTyped$7(Jg var0) {
      boolean var1 = a14.e();
      return var0.b.l() != null?((Boolean)var0.b.l().get()).booleanValue():true;
   }

   private static void lambda$drawScreen$6(Jg var0) {
      var0.a(0.0F);
   }

   private static void lambda$drawScreen$5(int var0, int var1, Jg var2) {
      var2.c(var0, var1);
   }

   private static boolean lambda$drawScreen$4(Jg var0) {
      boolean var1 = a14.f();
      return var0.b.l() != null?((Boolean)var0.b.l().get()).booleanValue():true;
   }

   private static void lambda$drawScreen$3(Jg var0) {
      var0.a(0.0F);
   }

   private static boolean lambda$drawScreen$2(Jg var0) {
      boolean var1 = a14.f();
      return var0.b.l() != null && !((Boolean)var0.b.l().get()).booleanValue();
   }

   private static void lambda$drawScreen$1(int var0, int var1, Jg var2) {
      var2.c(var0, var1);
   }

   private static boolean lambda$drawScreen$0(Jg var0) {
      boolean var1 = a14.e();
      return var0.b.l() != null?((Boolean)var0.b.l().get()).booleanValue():true;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
