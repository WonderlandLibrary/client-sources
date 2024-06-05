package net;

import java.awt.Color;
import java.util.EnumSet;
import java.util.function.Supplier;
import net.UW;
import net.VN;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE3;
import net.aE8;
import net.aEB;
import net.aEs;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.ava;
import net.axu;
import net.yS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public final class avd extends as0 {
   yS D = new yS();
   @VN("mode")
   private aEs y = axu.a("Outline").a(new String[]{"Shader", "Outline"});
   @VN("radius")
   private aE8 C = (aE8)((aE8)axu.b(Integer.valueOf(15)).d(Integer.valueOf(1))).c(Integer.valueOf(50));
   @VN("color")
   private final aEB A = axu.a(Integer.valueOf(-5751202));
   @VN("alpha")
   private final aE8 x = (aE8)((aE8)axu.b(Integer.valueOf(50)).d(Integer.valueOf(50))).c(Integer.valueOf(255));
   @VN("color-outline-mode")
   private final aEs z = axu.a("Static").a(new String[]{"Team", "Static", "Rainbow", "Astolfo"});
   @VN("color-shader-mode")
   private final aEs B = axu.a("Static").a(new String[]{"Static", "Rainbow", "Astolfo"});
   @VN("targets")
   private final aE3 E = axu.a((Object)"Players").a((Object[])(new String[]{"Players", "Animals", "Mobs", "Passives"}));

   public avd(UW var1) {
      super(var1, a2V.VISUALS, "Glow");
      ae9.a(new adZ("GLOW_MODE", "Mode", a6d.COMBOBOX, this, this.y));
      ae9.a(new adZ("GLOW_TARGETS", "Targets", a6d.SELECTBOX, this, this.E));
      ae9.a(new adZ("GLOW_COLOR_MODE", "Outline Mode", a6d.COMBOBOX, this, this.z, this::lambda$new$0));
      ae9.a(new adZ("GLOW_SHADER_MODE", "Shader Mode", a6d.COMBOBOX, this, this.B, this::lambda$new$1));
      ae9.a(new adZ("GLOW_COLOR", "Color", a6d.COLOR_PICKER, this, this.A, (EnumSet)null));
      ae9.a(new adZ("GLOW_SHADER_RADIUS", "Radius", a6d.SLIDER, this, this.C, 1.0D, this::lambda$new$2));
      ae9.a(new adZ("CLOW_ALPHA", "Alpha", a6d.SLIDER, this, this.x, 5.0D));
   }

   private boolean a(Entity var1) {
      int var2 = ava.h();
      return this.E.a((Object)"Players") && var1 instanceof EntityPlayer || this.E.a((Object)"Mobs") && (var1 instanceof EntityMob || var1 instanceof EntitySlime) || this.E.a((Object)"Passives") && (var1 instanceof EntityVillager || var1 instanceof EntityGolem) || this.E.a((Object)"Animals") && var1 instanceof EntityAnimal;
   }

   public boolean b(Entity var1) {
      int var2 = ava.h();
      return var1 != null && var1.isEntityAlive() && !var1.isInvisible() && var1 != this.w.thePlayer && this.a(var1);
   }

   @agu
   public void a(aSt var1) {
      int var2 = ava.h();
      if(this.y.a("Shader") && !this.E.e()) {
         this.D.a((avd)this, (aSt)var1);
      }

   }

   public int[] j() {
      float[] var1 = this.A.i();
      Color var2 = Color.getHSBColor(var1[0], var1[1], var1[2]);
      return new int[]{var2.getRed(), var2.getGreen(), var2.getBlue()};
   }

   public int[] f() {
      return a6_.a(this.A);
   }

   public int[] h() {
      return a6_.b(this.A);
   }

   public aEB e() {
      return this.A;
   }

   public aE8 a() {
      return this.x;
   }

   public aEs k() {
      return this.z;
   }

   public aE3 d() {
      return this.E;
   }

   public aE8 g() {
      return this.C;
   }

   public aEs i() {
      return this.y;
   }

   public boolean b() {
      int var1 = ava.h();
      return this.y() && this.y.a("Shader") && !this.E.e();
   }

   public aEs c() {
      return this.B;
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.y.a("Shader"));
   }

   private Boolean lambda$new$1() {
      return Boolean.valueOf(this.y.a("Shader"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.y.a("Outline"));
   }
}
