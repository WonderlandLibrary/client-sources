package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import java.util.function.Supplier;
import net.BT;
import net.UW;
import net.VN;
import net.WB;
import net.a2V;
import net.a6d;
import net.aE8;
import net.aEE;
import net.aEl;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aL_;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.apn;
import net.as0;
import net.as5;
import net.av8;
import net.avB;
import net.avI;
import net.avl;
import net.avu;
import net.axu;
import net.lS;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;

public final class avS extends as0 {
   private double I;
   private double A;
   private double F;
   private double D;
   private double z;
   private boolean K;
   private boolean G;
   private int H;
   @VN("mode")
   private final aEs J = axu.a("NCP").a(new String[]{"NCP", "Vanilla"});
   @VN("jump-mode")
   private final aEs M = axu.a("Reduced").a(new String[]{"Vanilla", "Reduced"});
   @VN("down-motion")
   private aEu x = axu.a(Boolean.valueOf(false));
   @VN("lag-back")
   private final aEu L = axu.g();
   @VN("dmg-boost")
   private final aEu E = axu.g();
   @VN("timer-boost-value")
   private final aEl y = (aEl)((aEl)axu.a(Float.valueOf(1.0F)).d(Float.valueOf(1.0F))).c(Float.valueOf(2.0F));
   @VN("friction")
   private final aE8 B = (aE8)((aE8)axu.b(Integer.valueOf(150)).d(Integer.valueOf(60))).c(Integer.valueOf(180));
   @VN("speed")
   private final aEE C = (aEE)((aEE)axu.a(Double.valueOf(1.5D)).d(Double.valueOf(1.0D))).c(Double.valueOf(2.0D));

   public avS(UW var1) {
      super(var1, "Speed", "Speed", 0, a2V.MOVEMENT, "Increases your in-game speed");
      ae9.a(new adZ("SPEED_MODE", "Mode", a6d.COMBOBOX, this, this.J));
      ae9.a(new adZ("SPEED_HEIGHT_MODE", "Jump Mode", a6d.COMBOBOX, this, this.M));
      ae9.a(new adZ("SPEED_DOWN_MOTION", "Down Motion", a6d.CHECKBOX, this, this.x));
      ae9.a(new adZ("SPEED_LAG_CHECK", "Lagback check", a6d.CHECKBOX, this, this.L));
      ae9.a(new adZ("SPEED_BOOST", "Damage Boost", a6d.CHECKBOX, this, this.E));
      ae9.a(new adZ("SPEED_TIMER_BOOST", "Timer Boost", a6d.SLIDER, this, this.y, 0.05D));
      ae9.a(new adZ("SPEED_VANILLA_SPEED", "Vanilla Speed", a6d.SLIDER, this, this.C, 0.1D, this::lambda$new$0));
      ae9.a(new adZ("SPEED_FRICTION", "Friction", a6d.SLIDER, this, this.B, 2.0D));
   }

   @agu
   public void a(apn var1) {
      String var2 = avu.r();
      if(this.w.thePlayer.p() && !this.w.thePlayer.q()) {
         var1.setCancelled(true);
      }

   }

   @agu
   public void a(WB var1) {
      this.c(lS.c()?"Hypixel":(String)this.J.a());
   }

   @agu
   public void a(BT var1) {
      avu.r();
      this.A = this.w.thePlayer.ay();
      this.F = this.w.thePlayer.a(true, 0.2D) * (this.w.thePlayer.q()?0.5D:(this.w.thePlayer.ap().b() && !this.a((Class)avl.class)?0.8D:1.0D));
   }

   @agu
   public void a(aG1 var1) {
      String var2 = avu.r();
      if(var1.h() == EventState.PRE) {
         if(this.c() && (this.w.thePlayer.ticksExisted % 2 == 0 || this.H == 0)) {
            var1.c(true);
         }

         if(this.w.thePlayer.p() && ((Float)this.y.a()).floatValue() > 1.0F && !this.a((Class)avI.class)) {
            this.w.timer.i = ((Float)this.y.a()).floatValue();
         }
      }

   }

   public boolean c() {
      String var1 = avu.r();
      return this.y() && lS.c() && this.w.thePlayer.ay() > 0.0D && this.w.thePlayer.fallDistance < (float)(1 + this.w.thePlayer.c(Potion.jump));
   }

   @agu
   public void a(aL_ var1) {
      String var2 = avu.r();
      if(this.w.thePlayer.p()) {
         if(this.w.thePlayer.onGround && !this.w.thePlayer.N()) {
            this.H = 0;
            var1.a(this.w.thePlayer.motionY = this.w.thePlayer.f(this.M.a("Reduced")?0.399999986886978D:0.419999986886978D));
            this.I = this.F * 2.139999980926514D;
         }

         if(this.K) {
            this.I = this.A - (this.J.a("NCP")?0.81999D:0.66D) * (this.A - this.F);
            this.I *= this.J.a("Vanilla")?((Double)this.C.a()).doubleValue():1.0D;
         }

         ++this.H;
         this.I -= this.A / (double)(((Integer)this.B.a()).intValue() - 1);
         if(((Boolean)this.x.a()).booleanValue() && this.H == 3 && !this.w.thePlayer.isPotionActive(Potion.jump)) {
            this.w.thePlayer.motionY = -0.099999986886978D;
         }

         if(((Boolean)this.E.a()).booleanValue() && this.G && !this.w.thePlayer.isPotionActive(Potion.poison) && !this.w.thePlayer.isBurning()) {
            this.I += this.D;
            this.G = false;
         }

         var1.c(Math.max(this.I, this.F));
         this.K = this.w.thePlayer.onGround;
      }

      var1.c(0.0D);
   }

   @agu
   public void b(ap9 var1) {
      String var2 = avu.r();
      if(var1.a().equals(PacketDirection.INCOMING)) {
         if(((Boolean)this.L.a()).booleanValue() && var1.d() instanceof S08PacketPlayerPosLook) {
            this.c(this.getClass());
         }

         if(!(var1.d() instanceof S27PacketExplosion)) {
            return;
         }

         S27PacketExplosion var3 = (S27PacketExplosion)var1.d();
         if(var3.getAffectedBlockPositions().isEmpty()) {
            this.D = Math.hypot(0.15D + (double)(var3.func_149149_c() / 8500.0F), 0.15D + (double)(var3.func_149147_e() / 8500.0F));
            this.z = this.w.thePlayer.motionY + (double)(var3.func_149144_d() / 8500.0F);
            this.G = true;
         }
      }

      if(this.a((Class)as5.class) && ((as5)this.b(as5.class)).b().a("Verus") && this.J.a("Vanilla") && ((Double)this.C.a()).doubleValue() > 1.2D && var1.d() instanceof C03PacketPlayer && this.w.thePlayer.ticksExisted % 40 == 0) {
         ;
      }

   }

   public void n() {
      String var1 = avu.r();
      this.c(lS.c()?"Hypixel":(String)this.J.a());
      this.a((Class[])(new Class[]{av8.class, avu.class, avB.class}));
      this.I = this.w.thePlayer.as();
      if(this.w.thePlayer.onGround) {
         this.w.thePlayer.E();
      }

   }

   public void c() {
      this.w.timer.i = 1.0F;
   }

   public aEs a() {
      return this.J;
   }

   public aEs d() {
      return this.M;
   }

   public aEl b() {
      return this.y;
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.J.a("Vanilla"));
   }
}
