package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Color;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE8;
import net.aEB;
import net.aEE;
import net.aEu;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.aiG;
import net.as0;
import net.asx;
import net.au7;
import net.avS;
import net.avu;
import net.awR;
import net.axu;
import net.aye;
import net.d3;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class avv extends as0 {
   private static final double L = 6.283185307179586D;
   private static final double T = 3.5D;
   private int O;
   private int R = 1;
   @VN("range")
   private final aEE C = (aEE)((aEE)axu.a(Double.valueOf(2.0D)).d(Double.valueOf(0.5D))).c(Double.valueOf(4.5D));
   @VN("safe-radius")
   private final aEE N = (aEE)((aEE)axu.a(Double.valueOf(2.0D)).d(Double.valueOf(0.5D))).c(Double.valueOf(4.5D));
   @VN("attack-radius")
   private final aEE x = (aEE)((aEE)axu.a(Double.valueOf(2.0D)).d(Double.valueOf(0.5D))).c(Double.valueOf(4.5D));
   @VN("points-multiplier")
   private final aEE z = (aEE)((aEE)axu.a(Double.valueOf(2.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(3.5D));
   @VN("switch-hurt-tick")
   private final aE8 F = (aE8)((aE8)axu.b(Integer.valueOf(8)).d(Integer.valueOf(5))).c(Integer.valueOf(15));
   @VN("dynamic-range")
   private final aEu D = axu.g();
   @VN("only-target")
   private final aEu y = axu.g();
   @VN("points")
   private final aEu G = axu.g();
   @VN("space")
   private final aEu J = axu.g();
   @VN("controllable")
   private final aEu E = axu.g();
   @VN("behind")
   private final aEu A = axu.g();
   @VN("autothirdperson")
   private final aEu K = axu.g();
   @VN("color")
   private final aEB H = axu.a(Integer.valueOf(-7697665));
   @VN("avoid-edges")
   private final aEu S = axu.g();
   @VN("height")
   private final aE8 B = (aE8)((aE8)axu.b(Integer.valueOf(5)).d(Integer.valueOf(4))).c(Integer.valueOf(10));
   private boolean M;
   private boolean I;
   private final d3 P;
   private float Q;

   public avv(UW var1) {
      super(var1, a2V.COMBAT, "TargetStrafe", "Target Strafe");
      avu.r();
      this.P = new d3();
      this.Q = 0.0F;
      ae9.a(new adZ("TS_RADIUS", "Radius", a6d.SLIDER, this, this.C, 0.1D, this::lambda$new$0));
      ae9.a(new adZ("TS_PMULTIPLIER", "Points Multiplier", a6d.SLIDER, this, this.z, 0.1D));
      ae9.a(new adZ("TS_DYNAMIC", "Dynamic Radius", a6d.CHECKBOX, this, this.D, this::lambda$new$1));
      a6d var10005 = a6d.SLIDER;
      aEE var10007 = this.N;
      aEu var10009 = this.D;
      this.D.getClass();
      ae9.a(new adZ("TS_SAFE_RADIUS", "Safe Radius", var10005, this, var10007, 0.1D, var10009::a));
      var10005 = a6d.SLIDER;
      var10007 = this.x;
      var10009 = this.D;
      this.D.getClass();
      ae9.a(new adZ("TS_ATTACK_RADIUS", "Attack Radius", var10005, this, var10007, 0.1D, var10009::a));
      var10005 = a6d.SLIDER;
      aE8 var8 = this.F;
      var10009 = this.D;
      this.D.getClass();
      ae9.a(new adZ("TS_DYNAMIC_SWITCH_TICK", "Switch Hurt Tick", var10005, this, var8, 1.0D, var10009::a));
      ae9.a(new adZ("TS_TAR", "Only target", a6d.CHECKBOX, this, this.y));
      ae9.a(new adZ("TS_SPACE", "On jump key", a6d.CHECKBOX, this, this.J));
      ae9.a(new adZ("TS_CONTROL", "Controllable", a6d.CHECKBOX, this, this.E));
      ae9.a(new adZ("TS_BEHIND", "Behind", a6d.CHECKBOX, this, this.A, this::lambda$new$2));
      ae9.a(new adZ("POINTS", "Draw circle", a6d.CHECKBOX, this, this.G));
      ae9.a(new adZ("ATP", "Auto Third Person", a6d.CHECKBOX, this, this.K));
      a6d var10004 = a6d.COLOR_PICKER;
      aEB var10006 = this.H;
      aEu var10008 = this.G;
      this.G.getClass();
      ae9.a(new adZ("TS_CIRCLE_COLOR", "Circle color", var10004, this, var10006, (EnumSet)null, var10008::a));
      ae9.a(new adZ("TS_EDGES", "Stop on Edges", a6d.CHECKBOX, this, this.S));
      var10004 = a6d.SLIDER;
      aE8 var6 = this.B;
      var10008 = this.S;
      this.S.getClass();
      ae9.a(new adZ("TS_EDGES_MIN_AMOUNT", "Maximum Height", var10004, this, var6, 1.0D, var10008::a));
      if(PacketRemapper.b() == null) {
         avu.e("DbPcFb");
      }

   }

   @agu
   public void a(WB var1) {
      String var2 = avu.r();
      if(this.w.thePlayer.ap().e() < 0.0F) {
         this.R = -1;
      }

      if(this.w.thePlayer.ap().e() > 0.0F) {
         this.R = 1;
      }

      if(this.f()) {
         if(this.P.a(200.0D)) {
            this.M = !this.M;
            this.O = this.M?this.O - 1:this.O + 1;
         }

         this.P.b();
      }

      asx var3 = (asx)this.b(asx.class);
      double var4 = this.b(var3.n());
      int var6 = (int)(3.141592653589793D * var4);
      double var7 = 6.283185307179586D / (double)((float)var6);
      Entity var9 = var3.n();
      double var10 = (double)MathHelper.h(var7 * (double)(this.O + 1) * var4 * (double)(((Boolean)this.E.a()).booleanValue()?this.R:1));
      double var12 = (double)MathHelper.d(var7 * (double)(this.O + 1)) * var4;
      if(!this.a(var9.posX + var10, var9.posY, var9.posZ + var12)) {
         this.M = !this.M;
      }

      this.Q = 0.7F;
      if(((Boolean)this.K.a()).booleanValue() && (!var3.y() || var3.n() == null || !var3.P() || !this.g())) {
         this.d(false);
         this.w.gameSettings.thirdPersonView = 0;
      }

   }

   public void a(aL_ var1, double var2, Entity var4) {
      avu.r();
      double var6 = this.b(var4);
      int var8 = (int)((double)((int)(3.141592653589793D * var6)) * ((Double)this.z.a()).doubleValue());
      double var9 = 6.283185307179586D / (double)((float)var8);
      double var11 = (double)MathHelper.h(var9 * (double)this.O) * var6 * (double)(((Boolean)this.E.a()).booleanValue()?this.R:1);
      double var13 = (double)MathHelper.d(var9 * (double)this.O) * var6;
      Vec3 var15 = new Vec3(this.w.thePlayer.posX, this.w.thePlayer.posY + (double)this.w.thePlayer.getEyeHeight(), this.w.thePlayer.posZ);
      Vec3 var16 = this.a(90.0F, 0.0F);
      Vec3 var17 = var15.addVector(var16.xCoord * (double)((Integer)this.B.a()).intValue(), var16.yCoord * (double)((Integer)this.B.a()).intValue(), var16.zCoord * (double)((Integer)this.B.a()).intValue());
      MovingObjectPosition var18 = this.w.theWorld.rayTraceBlocks(var15, var17, false, false, false);
      if(!((Boolean)this.S.a()).booleanValue() || var18 != null) {
         if(((Boolean)this.K.a()).booleanValue()) {
            this.d(true);
            this.w.gameSettings.thirdPersonView = 1;
         }

         if(((Boolean)this.A.a()).booleanValue()) {
            double var19 = var4.posX + -Math.sin(Math.toRadians((double)var4.rotationYaw)) * -2.0D;
            double var21 = var4.posZ + Math.cos(Math.toRadians((double)var4.rotationYaw)) * -2.0D;
            var1.b(var2 * (double)(-MathHelper.h(Math.toRadians((double)awR.c(var19, var4.posY, var21)[0]))));
            var1.d(var2 * (double)MathHelper.d(Math.toRadians((double)awR.c(var19, var4.posY, var21)[0])));
         }

         var1.b(var2 * (double)(-MathHelper.h(Math.toRadians((double)awR.c(var4.posX + var11, var4.posY, var4.posZ + var13)[0]))));
         var1.d(var2 * (double)MathHelper.d(Math.toRadians((double)awR.c(var4.posX + var11, var4.posY, var4.posZ + var13)[0])));
      }

      var1.b(0.0D);
      var1.d(0.0D);
      double var25 = Math.abs(var4.posX + var11 - this.w.thePlayer.posX);
      double var26 = Math.abs(var4.posZ + var13 - this.w.thePlayer.posZ);
      double var23 = Math.sqrt(var25 * var25 + var26 * var26);
      if(var23 <= (double)this.Q) {
         this.O += (this.M?-1:1) % var8;
      }

      if(var23 > 3.0D) {
         this.O = this.c(var4);
      }

   }

   private double b(Entity var1) {
      String var2 = avu.r();
      return ((Boolean)this.D.a()).booleanValue()?(var1.hurtResistantTime <= ((Integer)this.F.a()).intValue()?((Double)this.x.a()).doubleValue():((Double)this.N.a()).doubleValue()):((Double)this.C.a()).doubleValue();
   }

   protected final Vec3 a(float var1, float var2) {
      float var3 = MathHelper.d(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var4 = MathHelper.h(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var5 = -MathHelper.d(Math.toRadians((double)(-var1)));
      float var6 = MathHelper.h(Math.toRadians((double)(-var1)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }

   private boolean a(double var1, double var3, double var5) {
      avu.r();
      int var8 = (int)var3;
      if(this.w.theWorld.getBlockState(new BlockPos(var1, (double)var8, var5)).getBlock() != Blocks.air) {
         return true;
      } else {
         --var8;
         return false;
      }
   }

   private int c(Entity var1) {
      return ((aiG)((List)this.a(var1).stream().sorted(Comparator.comparingDouble(aiG::a)).collect(Collectors.toList())).get(0)).b;
   }

   private boolean f() {
      String var1 = avu.r();
      return !this.w.theWorld.getCollidingBoundingBoxes(this.w.thePlayer, this.w.thePlayer.getEntityBoundingBox().offset(0.0D, 0.0D, -0.5D)).isEmpty() || !this.w.theWorld.getCollidingBoundingBoxes(this.w.thePlayer, this.w.thePlayer.getEntityBoundingBox().offset(0.5D, 0.0D, 0.0D)).isEmpty() || !this.w.theWorld.getCollidingBoundingBoxes(this.w.thePlayer, this.w.thePlayer.getEntityBoundingBox().offset(0.0D, 0.0D, 0.5D)).isEmpty() || !this.w.theWorld.getCollidingBoundingBoxes(this.w.thePlayer, this.w.thePlayer.getEntityBoundingBox().offset(-0.5D, 0.0D, 0.0D)).isEmpty();
   }

   private boolean a() {
      String var1 = avu.r();
      return !((Boolean)this.J.a()).booleanValue() || this.w.gameSettings.keyBindJump.isKeyDown();
   }

   public boolean g() {
      String var1 = avu.r();
      return this.a((Class)asx.class) && ((asx)this.b(asx.class)).n() != null && this.w.thePlayer.canEntityBeSeen(((asx)this.b(asx.class)).n()) && (!((Boolean)this.y.a()).booleanValue() || this.j.k().b(((asx)this.b(asx.class)).n().getName(), au7.TARGET)) && this.w.thePlayer.b(((asx)this.b(asx.class)).n()) < ((Double)((asx)this.b(asx.class)).v().a()).doubleValue() + 2.0D && this.w.thePlayer.posY >= ((asx)this.b(asx.class)).n().posY - 3.4D && ((asx)this.b(asx.class)).n().isEntityAlive() && this.w.thePlayer.p() && this.w.thePlayer.posY <= ((asx)this.b(asx.class)).n().posY + 3.4D && this.a() && this.a((Class)avS.class);
   }

   public void e(boolean var1) {
      this.M = var1;
   }

   public boolean d() {
      return this.M;
   }

   private List a(Entity var1) {
      CopyOnWriteArrayList var3 = new CopyOnWriteArrayList();
      avu.r();
      double var4 = this.b(var1);
      int var6 = (int)((double)((int)(3.141592653589793D * var4)) * ((Double)this.z.a()).doubleValue());
      int var7 = 0;
      if(var7 <= var6) {
         double var8 = 6.283185307179586D / (double)((float)var6);
         double var10 = (double)MathHelper.h(var8 * (double)var7) * var4;
         double var12 = (double)MathHelper.d(var8 * (double)var7) * var4;
         var3.add(new aiG(var1.posX + var10, var1.posZ + var12, var7));
         ++var7;
      }

      return var3;
   }

   @agu
   public void a(aye var1) {
      asx var3 = (asx)this.b(asx.class);
      avu.r();
      a6_.h();
      if(var3.n() != null && ((Boolean)this.G.a()).booleanValue() && (double)this.w.thePlayer.getDistanceToEntity(var3.n()) < ((Double)var3.v().a()).doubleValue() && var3.n().isEntityAlive() && this.g()) {
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
         this.a(5L, var3, var1);
         GL11.glColor4f((float)this.H.a().getRed() / 255.0F, (float)this.H.a().getGreen() / 255.0F, (float)this.H.a().getBlue() / 255.0F, 1.0F);
         this.a(2L, var3, var1);
      }

      GlStateManager.disableBlend();
      a6_.i();
   }

   private void a(long var1, asx var3, aye var4) {
      avu.r();
      GL11.glLineWidth((float)var1);
      GL11.glBegin(3);
      double var6 = this.b(var3.n());
      double var8 = 6.283185307179586D / (3.141592653589793D * var6 * ((Double)this.z.a()).doubleValue());
      double var10 = 0.0D;
      if(var10 < 6.283185307179586D) {
         double var12 = var3.n().lastTickPosX + (var3.n().posX - var3.n().lastTickPosX) * (double)var4.a() + Math.sin(var10) * var6 - this.w.getRenderManager().h;
         double var14 = var3.n().lastTickPosY + (var3.n().posY - var3.n().lastTickPosY) * (double)var4.a() - this.w.getRenderManager().g;
         double var16 = var3.n().lastTickPosZ + (var3.n().posZ - var3.n().lastTickPosZ) * (double)var4.a() + Math.cos(var10) * var6 - this.w.getRenderManager().m;
         GL11.glVertex3d(var12, var14, var16);
         double var10000 = var10 + var8;
      }

      var10 = var3.n().lastTickPosX + (var3.n().posX - var3.n().lastTickPosX) * (double)var4.a() + Math.sin(0.0D) * var6 - this.w.getRenderManager().h;
      double var19 = var3.n().lastTickPosY + (var3.n().posY - var3.n().lastTickPosY) * (double)var4.a() - this.w.getRenderManager().g;
      double var20 = var3.n().lastTickPosZ + (var3.n().posZ - var3.n().lastTickPosZ) * (double)var4.a() + Math.cos(0.0D) * var6 - this.w.getRenderManager().m;
      GL11.glVertex3d(var10, var19, var20);
      GL11.glEnd();
   }

   private int a(int var1) {
      boolean var3 = true;
      avu.r();
      boolean var4 = true;
      float var5 = ((float)var1 * 0.00999999F + (float)(System.currentTimeMillis() % 500L) / 500.0F) % 1.0F;
      float[] var6 = this.H.i();
      return Color.getHSBColor(var6[0], var6[1], var5 < 0.4F?0.9F - var5:var5).getRGB();
   }

   public aEu e() {
      return this.y;
   }

   public aEu c() {
      return this.J;
   }

   public boolean b() {
      return this.I;
   }

   public void d(boolean var1) {
      this.I = var1;
   }

   private Boolean lambda$new$2() {
      String var1 = avu.r();
      return Boolean.valueOf(!((Boolean)this.D.a()).booleanValue());
   }

   private Boolean lambda$new$1() {
      String var1 = avu.r();
      return Boolean.valueOf(!((Boolean)this.A.a()).booleanValue());
   }

   private Boolean lambda$new$0() {
      String var1 = avu.r();
      return Boolean.valueOf(!((Boolean)this.D.a()).booleanValue());
   }
}
