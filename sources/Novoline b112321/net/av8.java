package net;

import cc.novoline.events.events.EventState;
import cc.novoline.events.events.PacketDirection;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.BT;
import net.Ju;
import net.UW;
import net.VN;
import net.WB;
import net.WL;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aE8;
import net.aEE;
import net.aEl;
import net.aEs;
import net.aEu;
import net.aG1;
import net.aL_;
import net.aSt;
import net.adZ;
import net.ae9;
import net.agu;
import net.ap9;
import net.as0;
import net.asa;
import net.atS;
import net.av2;
import net.avB;
import net.avS;
import net.ava;
import net.avu;
import net.axu;
import net.azi;
import net.d3;
import net.lS;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.StringUtils;
import org.jetbrains.annotations.NotNull;

public class av8 extends as0 {
   @VN("mode")
   private aEs P = axu.a("Hypixel").a(new String[]{"Hypixel", "Motion"});
   @VN("hypixel-mode")
   private aEs E = axu.a("Boost").a(new String[]{"Dash", "Boost"});
   @VN("interact-exploit")
   private aEu O = axu.a(Boolean.valueOf(false));
   @VN("auto-motion")
   private aEu G = axu.a(Boolean.valueOf(false));
   @VN("dash-distance")
   private aEE C = (aEE)((aEE)axu.a(Double.valueOf(2.0D)).d(Double.valueOf(1.0D))).c(Double.valueOf(4.0D));
   @VN("dash-delay")
   private aE8 T = (aE8)((aE8)axu.b(Integer.valueOf(300)).d(Integer.valueOf(100))).c(Integer.valueOf(500));
   @VN("dash-check-flag")
   private aEu Q = axu.a(Boolean.valueOf(false));
   @VN("boost-speed")
   private aEE V = (aEE)((aEE)axu.a(Double.valueOf(2.0D)).d(Double.valueOf(1.5D))).c(Double.valueOf(2.0D));
   @VN("motion-speed")
   private aEE z = (aEE)((aEE)axu.a(Double.valueOf(5.0D)).d(Double.valueOf(0.5D))).c(Double.valueOf(9.0D));
   @VN("damage-mode")
   private aEs x = axu.a("Packet").a(new String[]{"Packet", "Edit"});
   @VN("timer-boost")
   private aEl F = (aEl)((aEl)axu.a(Float.valueOf(1.2F)).d(Float.valueOf(1.0F))).c(Float.valueOf(1.5F));
   @VN("viewbobbing")
   private aEl J = (aEl)((aEl)axu.a(Float.valueOf(60.0F)).d(Float.valueOf(0.0F))).c(Float.valueOf(100.0F));
   private List M = new CopyOnWriteArrayList();
   private double I;
   private double B;
   private double D;
   private double N;
   private boolean L;
   private boolean R;
   private boolean H;
   private boolean K;
   private d3 U = new d3();
   private boolean y;
   private atS A;
   private int S;

   public av8(@NotNull UW var1) {
      super(var1, a2V.MOVEMENT, "Flight", "Flight");
      ae9.a(new adZ("FLIGHT_MODE", "Mode", a6d.COMBOBOX, this, this.P));
      ae9.a(new adZ("FLIGHT_HYPIXEL_MODE", "Hypixel Mode", a6d.COMBOBOX, this, this.E, this::lambda$new$0));
      ae9.a(new adZ("FLIGHT_DASH_DISTANCE", "Dash Distance", a6d.SLIDER, this, this.C, 0.1D, this::lambda$new$1));
      ae9.a(new adZ("FLIGHT_DASH_DELAY", "Dash Delay", a6d.SLIDER, this, this.T, 50.0D, this::lambda$new$2));
      ae9.a(new adZ("FLIGHT_DASH_CHECK_FLAG", "Check Flag", a6d.CHECKBOX, this, this.Q, this::lambda$new$3));
      ae9.a(new adZ("FLIGHT_BOOST_DAMAGE", "Damage", a6d.COMBOBOX, this, this.x, this::lambda$new$4));
      ae9.a(new adZ("FLIGHT_BOOST_SPEED", "Boost Speed", a6d.SLIDER, this, this.V, 0.1D, this::lambda$new$5));
      ae9.a(new adZ("FLIGHT_BOOST_TIMER", "Timer Boost", a6d.SLIDER, this, this.F, 0.10000000149011612D, this::lambda$new$6));
      ae9.a(new adZ("FLIGHT_MOTION_SPEED", "Motion Speed", a6d.SLIDER, this, this.z, 0.5D, this::lambda$new$7));
      ae9.a(new adZ("FLIGHT_VIEWBOBBING", "Viewbobbing", a6d.SLIDER, this, this.J, 5.0D, this::lambda$new$8));
      ae9.a(new adZ("FLIGHT_INTERACT_EXPLOIT", "Interact Exploit", a6d.CHECKBOX, this, this.O));
      ae9.a(new adZ("FLIGHT_AUTO_MOTION", "Auto Motion", a6d.CHECKBOX, this, this.G));
   }

   private int a(double var1) {
      avu.r();
      boolean var4 = lS.a(WL.UHC);
      boolean var5 = lS.a(WL.SG);
      int var6 = this.w.thePlayer.isPotionActive(Potion.jump)?this.w.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1:0;
      return (int)(((double)(3 + var6 + (!var4 && !var5?0:1)) + (this.w.thePlayer.isPotionActive(Potion.jump)?0.1D:0.0D)) / var1);
   }

   private void b() {
      String var1 = avu.r();
      if(!lS.a(WL.PRE) && !lS.a(WL.LOBBY)) {
         double var2 = this.w.thePlayer.posX;
         double var4 = this.w.thePlayer.posY;
         double var6 = this.w.thePlayer.posZ;
         double var8 = 0.0625D;
         int var10 = 0;
         if(var10 <= this.a(var8)) {
            this.b((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(var2, var4 + var8, var6, false)));
            this.b((Packet)(new C03PacketPlayer$C04PacketPlayerPosition(var2, var4, var6, false)));
            ++var10;
         }
      }

   }

   private void d(aG1 var1) {
      String var2 = avu.r();
      if(this.y) {
         this.w.timer.i = 2.0F;
         double var3 = 0.125D;
         int var5 = this.a(var3) * 2 + 1;
         if(this.S <= var5) {
            var1.c(false);
            var1.c((this.S % 2 == 0?var1.f() + var3:var1.f()) + ThreadLocalRandom.current().nextDouble(2.0E-4D));
            if(this.S == var5) {
               this.w.timer.i = 1.0F;
               this.y = false;
            }
         }
      }

   }

   private void c(aG1 var1) {
      String var2 = avu.r();
      if(!this.w.thePlayer.onGround) {
         this.R = true;
      }

      if(this.R) {
         this.c((Class)this.getClass());
         this.R = false;
      }

      if(!this.w.thePlayer.onGround && var1.f() < this.N) {
         this.c((Class)this.getClass());
      }

   }

   private void f(aG1 var1) {
      String var2 = avu.r();
      if(this.w.thePlayer.ap().d()) {
         this.w.thePlayer.motionY = 1.8D;
      }

      if(this.w.thePlayer.ap().b()) {
         this.w.thePlayer.motionY = -1.8D;
      }

      if(!this.w.thePlayer.onGround) {
         this.w.thePlayer.motionY = 0.0D;
      }

   }

   private void d() {
      this.w.thePlayer.motionY = 0.0D;
      this.w.thePlayer.motionX = 0.0D;
      this.w.thePlayer.motionZ = 0.0D;
   }

   private void a(aG1 var1) {
      avu.r();
      Entity var10001 = this.w.getRenderViewEntity();
      double var10003 = this.w.thePlayer.posY - this.N;
      var10001.posY = this.w.getRenderViewEntity().posY - var10003;
      if(this.w.thePlayer.p()) {
         this.w.thePlayer.motionY += 0.024D;
      }

   }

   private void b(aG1 var1) {
      avu.r();
      this.w.thePlayer.motionY = 0.0D;
      if(this.H) {
         double var3 = ((Double)this.C.a()).doubleValue();
         double var5 = -Math.sin(Math.toRadians((double)this.w.thePlayer.rotationYaw)) * var3;
         double var7 = Math.cos(Math.toRadians((double)this.w.thePlayer.rotationYaw)) * var3;
         if(this.U.a((double)((Integer)this.T.a()).intValue())) {
            this.w.thePlayer.setPosition(this.w.thePlayer.posX + var5, this.w.thePlayer.posY - 2.0D, this.w.thePlayer.posZ + var7);
            this.U.b();
            if(((Boolean)this.Q.a()).booleanValue()) {
               this.H = false;
            }
         }
      }

   }

   private void c(ap9 var1) {
      String var2 = avu.r();
      if(var1.d() instanceof S08PacketPlayerPosLook) {
         this.c((Class)this.getClass());
      }

      if(var1.a().equals(PacketDirection.INCOMING) && !this.w.thePlayer.isPotionActive(Potion.jump)) {
         ((asa)this.b((Class)asa.class)).a(var1, 140, 110);
      }

   }

   private void a(aSt var1) {
      int var2 = this.a(0.125D) * 2;
      ScaledResolution var3 = var1.a();
      int var4 = var3.getScaledWidth();
      int var5 = var3.getScaledHeight();
      int var6 = Ju.a(0, 0, 0, 0);
      int var7 = ((ava)this.b((Class)ava.class)).q();
      int var8 = Ju.a(0, 0, 0, 255);
      int var9 = Ju.a(0, 0, 0, 40);
      float var10 = 100.0F / (float)var2 * (float)this.S;
      byte var11 = 50;
      a6_.a((float)(var4 / 2 - var11), (float)(var5 / 2 + 15), (float)(var4 / 2 - var11 + 100), (float)(var5 / 2 + 20), 2.0F, var8, var9);
      Gui.a((double)(var4 / 2 - var11), (double)(var5 / 2 + 15), (double)((float)(var4 / 2 - var11) + var10), (double)(var5 / 2 + 20), var7);
   }

   public void n() {
      avu.r();
      this.a((Class[])(new Class[]{avS.class, avB.class, avu.class}));
      if(this.P.a("Motion")) {
         this.A = atS.MOTION;
      }

      if(this.E.a("Dash")) {
         this.A = atS.DASH;
         this.H = false;
      }

      this.A = atS.BOOST;
      if(this.w.thePlayer.p() && this.w.thePlayer.onGround) {
         this.w.thePlayer.setSprinting(true);
         this.N = this.w.thePlayer.posY;
         if(this.x.a("Packet")) {
            this.b();
         }

         this.y = true;
      }

      if(((Boolean)this.G.a()).booleanValue() && (!this.w.thePlayer.p() || !this.w.thePlayer.onGround)) {
         this.A = atS.MOTION;
      }

      if(((Boolean)this.O.a()).booleanValue() && lS.c() && lS.a(WL.SW)) {
         Iterator var2 = this.w.theWorld.getLoadedEntityList().iterator();
         if(var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if((var3 instanceof EntityMob || var3 instanceof EntityAnimal) && !((av2)this.b((Class)av2.class)).k().contains(Integer.valueOf(var3.getEntityId())) && (double)this.w.thePlayer.getDistanceToEntity(var3) < 3.4D) {
               this.a((Packet)(new C02PacketUseEntity(var3, C02PacketUseEntity$Action.INTERACT)));
               this.A = atS.MOTION;
               this.K = true;
               this.y = true;
            }
         }
      }

      this.c((String)StringUtils.a(this.A.name().toLowerCase()));
   }

   public void c() {
      avu.r();
      this.w.timer.i = 1.0F;
      this.R = false;
      this.y = false;
      this.H = false;
      this.S = 0;
      if(this.A.equals(atS.MOTION)) {
         this.d();
      }

   }

   @agu
   public void a(WB var1) {
      this.c((String)StringUtils.a(this.A.name().toLowerCase()));
      ++this.S;
   }

   @agu
   public void a(BT var1) {
      this.B = this.w.thePlayer.a(true, 0.2D);
      this.D = this.w.thePlayer.ay();
   }

   @agu
   public void b(ap9 var1) {
      String var2 = avu.r();
      if(var1.a().equals(PacketDirection.INCOMING)) {
         if(this.A.equals(atS.MOTION)) {
            if(!(var1.d() instanceof S08PacketPlayerPosLook) || !this.K) {
               return;
            }

            this.j.t().a(this.f(), "You can now fly!", 3000, azi.SUCCESS);
            this.y = false;
            this.K = false;
         }

         if(this.A.equals(atS.BOOST)) {
            this.c(var1);
         }

         if(this.A.equals(atS.DASH) && var1.d() instanceof S08PacketPlayerPosLook) {
            this.H = true;
         }
      }

   }

   @agu
   public void e(aG1 var1) {
      String var2 = avu.r();
      if(var1.h().equals(EventState.PRE)) {
         if(!this.A.equals(atS.MOTION) && !this.A.equals(atS.DASH)) {
            this.c(var1);
         }

         this.w.thePlayer.cameraYaw = ((Float)this.J.a()).floatValue() / 1000.0F;
         this.w.thePlayer.cameraPitch = 0.0F;
         if(this.A.equals(atS.MOTION)) {
            this.f(var1);
         }

         if(this.A.equals(atS.BOOST)) {
            this.a(var1);
            this.d(var1);
         }

         if(this.A.equals(atS.DASH)) {
            this.b(var1);
         }
      }

   }

   @agu
   public void b(aSt var1) {
      String var2 = avu.r();
      if(this.A.equals(atS.BOOST) && this.y) {
         this.a(var1);
      }

   }

   @agu
   public void a(aL_ var1) {
      String var2 = avu.r();
      if(this.y || !this.w.thePlayer.p()) {
         var1.c(0.0D);
      }

      if(this.A.equals(atS.BOOST)) {
         this.b(var1);
      }

      if(this.A.equals(atS.MOTION)) {
         var1.c(((Double)this.z.a()).doubleValue());
      }

   }

   private void b(aL_ var1) {
      String var2 = avu.r();
      if(this.w.thePlayer.onGround) {
         if(this.L) {
            var1.a(this.w.thePlayer.motionY = this.w.thePlayer.ab());
            this.N += this.w.thePlayer.ab();
            this.I *= 2.139999980926514D;
         }

         this.I = this.w.thePlayer.E() * 2.0D;
      }

      if(this.L) {
         if(!this.w.thePlayer.isPotionActive(Potion.jump)) {
            this.w.timer.i = 1.5F;
         }

         this.I = this.D - 0.81999D * (this.D - this.B);
      }

      this.I -= this.D / (this.w.thePlayer.motionY > 0.0D?189.0D - 12.5D * (double)this.w.thePlayer.c(Potion.jump):39.0D);
      if(this.w.thePlayer.fallDistance > 0.0F) {
         this.w.timer.i = 1.0F;
      }

      this.I = Math.max(this.I, this.B);
      var1.c(this.I);
      this.L = this.w.thePlayer.onGround;
   }

   public aEE e() {
      return this.z;
   }

   public aEs a() {
      return this.P;
   }

   private Boolean lambda$new$8() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Motion") || !this.E.a("Dash"));
   }

   private Boolean lambda$new$7() {
      return Boolean.valueOf(this.P.a("Motion"));
   }

   private Boolean lambda$new$6() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Hypixel") && this.E.a("Boost"));
   }

   private Boolean lambda$new$5() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Hypixel") && this.E.a("Boost"));
   }

   private Boolean lambda$new$4() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Hypixel") && this.E.a("Boost"));
   }

   private Boolean lambda$new$3() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Hypixel") && this.E.a("Dash"));
   }

   private Boolean lambda$new$2() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Hypixel") && this.E.a("Dash"));
   }

   private Boolean lambda$new$1() {
      String var1 = avu.r();
      return Boolean.valueOf(this.P.a("Hypixel") && this.E.a("Dash"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.P.a("Hypixel"));
   }
}
