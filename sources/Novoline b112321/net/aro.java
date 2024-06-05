package net;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.Es;
import net.Nv;
import net.OG;
import net.ST;
import net.UW;
import net.a2V;
import net.a6d;
import net.aSt;
import net.adZ;
import net.agu;
import net.apD;
import net.as0;
import net.ava;
import net.dI;
import net.pd;
import org.lwjgl.opengl.GL11;

public class aro extends as0 {
   public List z = new ObjectArrayList();
   private int B = 0;
   private int x = 0;
   private int A = 0;
   private int y = 0;

   public aro(UW var1) {
      super(var1, "TabGui", "Tab Gui", 0, a2V.VISUALS);
   }

   public void n() {
      ava.h();
      a2V[] var2 = a2V.values();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         a2V var5 = var2[var4];
         this.z.add(new pd(this, var5));
         ++var4;
      }

   }

   public int a() {
      return ((ava)this.b(ava.class)).q();
   }

   public void c() {
      this.z.clear();
   }

   @agu
   public void a(aSt var1) {
      GL11.glPushMatrix();
      dI.a(this.w);
      this.z.forEach(pd::e);
      GL11.glPopMatrix();
   }

   @agu
   public void a(apD var1) {
      int var3 = var1.a();
      pd var4 = this.d();
      boolean var5 = ((pd)Objects.requireNonNull(var4)).c();
      ava.h();
      OG var6 = var4.b();
      boolean var7 = var6.h();
      Nv var8 = var6.e();
      if(var8 != null) {
         adZ var9 = var8.h();
      }

      Object var11 = null;
      switch(var3) {
      case 28:
         if(!var5) {
            break;
         }

         if(var7) {
            if(((adZ)Objects.requireNonNull(var11)).f() == a6d.CHECKBOX) {
               ((adZ)var11).w().a();
            }

            Es var10 = var8.b();
            switch(ST.a[((adZ)var11).f().ordinal()]) {
            case 1:
               ((adZ)var11).b(var10.d());
            case 2:
               if(((adZ)var11).n().contains(var10.d())) {
                  ((adZ)var11).n().remove(var10.d());
               }

               ((adZ)var11).n().add(var10.d());
            }
         }

         var6.c().e();
      case 200:
         if(!var5) {
            if(this.B == 0) {
               this.B = this.z.size() - 1;
            }

            --this.B;
         }

         if(!var7) {
            if(this.x == 0) {
               this.x = var4.a().size() - 1;
            }

            --this.x;
         }

         if(!((Nv)Objects.requireNonNull(var8)).e()) {
            if(this.A == 0) {
               this.A = var6.b().size() - 1;
            }

            --this.A;
         }

         if(((adZ)Objects.requireNonNull(var11)).f() == a6d.SLIDER) {
            ((adZ)var11).a((Number)Double.valueOf(((adZ)var11).h() + ((adZ)var11).m()));
         }

         if(this.y == 0) {
            this.y = var8.d().size() - 1;
         }

         --this.y;
      case 208:
         if(!var5) {
            if(this.B == this.z.size() - 1) {
               this.B = 0;
            }

            ++this.B;
         }

         if(!var7) {
            if(this.x == var4.a().size() - 1) {
               this.x = 0;
            }

            ++this.x;
         }

         if(!((Nv)Objects.requireNonNull(var8)).e()) {
            if(this.A == var6.b().size() - 1) {
               this.A = 0;
            }

            ++this.A;
         }

         if(((adZ)var11).f() == a6d.SLIDER) {
            ((adZ)var11).a((Number)Double.valueOf(((adZ)var11).h() - ((adZ)var11).m()));
         }

         if(this.y == var8.d().size() - 1) {
            this.y = 0;
         }

         ++this.y;
      case 205:
         if(!var5) {
            this.x = 0;
            var4.a(true);
         }

         if(!var7 && !var6.i()) {
            this.A = 0;
            var6.a(true);
         }

         if(((Nv)Objects.requireNonNull(var8)).e() || ((adZ)var11).f() != a6d.SELECTBOX && ((adZ)var11).f() != a6d.COMBOBOX && ((adZ)var11).f() != a6d.SLIDER) {
            break;
         }

         var8.a(true);
      case 203:
         if(var4.c()) {
            if(var6.h()) {
               if(((Nv)Objects.requireNonNull(var8)).e()) {
                  this.y = 0;
                  var8.a(false);
               }

               this.A = 0;
               var6.a(false);
            }

            this.x = 0;
            var4.a(false);
         }
      }

   }

   private pd d() {
      return (pd)this.z.stream().filter(pd::h).findFirst().orElse((Object)null);
   }

   public List b() {
      return this.z;
   }

   public int e() {
      return this.B;
   }

   public int c() {
      return this.x;
   }

   public int g() {
      return this.A;
   }

   public int f() {
      return this.y;
   }
}
