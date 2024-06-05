package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.Render2DEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.combat.Velocity;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.move.LongJump;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.visual.ClickGUI;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.ColorUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import cc.novoline.utils.notifications.NotificationType;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import net.atS;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.StringUtils;
import org.jetbrains.annotations.NotNull;

public class Flight extends AbstractModule {
   @Property("mode")
   private StringProperty P = PropertyFactory.createString("Hypixel").acceptableValues(new String[]{"Hypixel", "Motion"});
   @Property("hypixel-mode")
   private StringProperty E = PropertyFactory.createString("Boost").acceptableValues(new String[]{"Dash", "Boost"});
   @Property("interact-exploit")
   private BooleanProperty O = PropertyFactory.createBoolean(Boolean.valueOf(false));
   @Property("auto-motion")
   private BooleanProperty G = PropertyFactory.createBoolean(Boolean.valueOf(false));
   @Property("dash-distance")
   private DoubleProperty C = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(2.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(4.0D));
   @Property("dash-delay")
   private IntProperty T = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(300)).minimum(Integer.valueOf(100))).maximum(Integer.valueOf(500));
   @Property("dash-check-flag")
   private BooleanProperty Q = PropertyFactory.createBoolean(Boolean.valueOf(false));
   @Property("boost-speed")
   private DoubleProperty V = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(2.0D)).minimum(Double.valueOf(1.5D))).maximum(Double.valueOf(2.0D));
   @Property("motion-speed")
   private DoubleProperty z = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(5.0D)).minimum(Double.valueOf(0.5D))).maximum(Double.valueOf(9.0D));
   @Property("damage-mode")
   private StringProperty x = PropertyFactory.createString("Packet").acceptableValues(new String[]{"Packet", "Edit"});
   @Property("timer-boost")
   private FloatProperty F = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.2F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(1.5F));
   @Property("viewbobbing")
   private FloatProperty view_bobbing = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(60.0F)).minimum(Float.valueOf(0.0F))).maximum(Float.valueOf(100.0F));
   private List M = new CopyOnWriteArrayList();
   private double I;
   private double B;
   private double D;
   private double N;
   private boolean L;
   private boolean R;
   private boolean H;
   private boolean K;
   private Timer U = new Timer();
   private boolean y;
   private atS A;
   private int tick;

   public Flight(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MOVEMENT, "Flight", "Flight");
      Manager.put(new Setting("FLIGHT_MODE", "Mode", SettingType.COMBOBOX, this, this.P));
      Manager.put(new Setting("FLIGHT_HYPIXEL_MODE", "Hypixel Mode", SettingType.COMBOBOX, this, this.E, this::lambda$new$0));
      Manager.put(new Setting("FLIGHT_DASH_DISTANCE", "Dash Distance", SettingType.SLIDER, this, this.C, 0.1D, this::lambda$new$1));
      Manager.put(new Setting("FLIGHT_DASH_DELAY", "Dash Delay", SettingType.SLIDER, this, this.T, 50.0D, this::lambda$new$2));
      Manager.put(new Setting("FLIGHT_DASH_CHECK_FLAG", "Check Flag", SettingType.CHECKBOX, this, this.Q, this::lambda$new$3));
      Manager.put(new Setting("FLIGHT_BOOST_DAMAGE", "Damage", SettingType.COMBOBOX, this, this.x, this::lambda$new$4));
      Manager.put(new Setting("FLIGHT_BOOST_SPEED", "Boost Speed", SettingType.SLIDER, this, this.V, 0.1D, this::lambda$new$5));
      Manager.put(new Setting("FLIGHT_BOOST_TIMER", "Timer Boost", SettingType.SLIDER, this, this.F, 0.10000000149011612D, this::lambda$new$6));
      Manager.put(new Setting("FLIGHT_MOTION_SPEED", "Motion Speed", SettingType.SLIDER, this, this.z, 0.5D, this::lambda$new$7));
      Manager.put(new Setting("FLIGHT_VIEWBOBBING", "Viewbobbing", SettingType.SLIDER, this, this.view_bobbing, 5.0D, this::lambda$new$8));
      Manager.put(new Setting("FLIGHT_INTERACT_EXPLOIT", "Interact Exploit", SettingType.CHECKBOX, this, this.O));
      Manager.put(new Setting("FLIGHT_AUTO_MOTION", "Auto Motion", SettingType.CHECKBOX, this, this.G));
   }

   private int a(double var1) {
      Scaffold.r();
      boolean var4 = ServerUtils.serverIs(Servers.UHC);
      boolean var5 = ServerUtils.serverIs(Servers.SG);
      int var6 = this.mc.player.isPotionActive(Potion.jump)?this.mc.player.getActivePotionEffect(Potion.jump).getAmplifier() + 1:0;
      return (int)(((double)(3 + var6 + (!var4 && !var5?0:1)) + (this.mc.player.isPotionActive(Potion.jump)?0.1D:0.0D)) / var1);
   }

   private void b() {
      String var1 = Scaffold.r();
      if(!ServerUtils.serverIs(Servers.PRE) && !ServerUtils.serverIs(Servers.LOBBY)) {
         double var2 = this.mc.player.posX;
         double var4 = this.mc.player.posY;
         double var6 = this.mc.player.posZ;
         double var8 = 0.0625D;
         int var10 = 0;
         if(var10 <= this.a(var8)) {
            this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var2, var4 + var8, var6, false));
            this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var2, var4, var6, false));
            ++var10;
         }
      }

   }

   private void d(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.y) {
         this.mc.timer.timerSpeed = 2.0F;
         double var3 = 0.125D;
         int var5 = this.a(var3) * 2 + 1;
         if(this.tick <= var5) {
            var1.setOnGround(false);
            var1.setY((this.tick % 2 == 0?var1.getY() + var3:var1.getY()) + ThreadLocalRandom.current().nextDouble(2.0E-4D));
            if(this.tick == var5) {
               this.mc.timer.timerSpeed = 1.0F;
               this.y = false;
            }
         }
      }

   }

   private void c(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(!this.mc.player.onGround) {
         this.R = true;
      }

      if(this.R) {
         this.checkModule(this.getClass());
         this.R = false;
      }

      if(!this.mc.player.onGround && var1.getY() < this.N) {
         this.checkModule(this.getClass());
      }

   }

   private void f(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.movementInput().jump()) {
         this.mc.player.motionY = 1.8D;
      }

      if(this.mc.player.movementInput().sneak()) {
         this.mc.player.motionY = -1.8D;
      }

      if(!this.mc.player.onGround) {
         this.mc.player.motionY = 0.0D;
      }

   }

   private void d() {
      this.mc.player.motionY = 0.0D;
      this.mc.player.motionX = 0.0D;
      this.mc.player.motionZ = 0.0D;
   }

   private void a(MotionUpdateEvent var1) {
      Scaffold.r();
      Entity var10001 = this.mc.getRenderViewEntity();
      double var10003 = this.mc.player.posY - this.N;
      var10001.posY = this.mc.getRenderViewEntity().posY - var10003;
      if(this.mc.player.isMoving()) {
         this.mc.player.motionY += 0.024D;
      }

   }

   private void b(MotionUpdateEvent var1) {
      Scaffold.r();
      this.mc.player.motionY = 0.0D;
      if(this.H) {
         double var3 = ((Double)this.C.get()).doubleValue();
         double var5 = -Math.sin(Math.toRadians((double)this.mc.player.rotationYaw)) * var3;
         double var7 = Math.cos(Math.toRadians((double)this.mc.player.rotationYaw)) * var3;
         if(this.U.delay((double)((Integer)this.T.get()).intValue())) {
            this.mc.player.setPosition(this.mc.player.posX + var5, this.mc.player.posY - 2.0D, this.mc.player.posZ + var7);
            this.U.reset();
            if(((Boolean)this.Q.get()).booleanValue()) {
               this.H = false;
            }
         }
      }

   }

   private void c(PacketEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getPacket() instanceof S08PacketPlayerPosLook) {
         this.checkModule(this.getClass());
      }

      if(var1.getDirection().equals(PacketDirection.INCOMING) && !this.mc.player.isPotionActive(Potion.jump)) {
         ((Velocity)this.getModule(Velocity.class)).a(var1, 140, 110);
      }

   }

   private void a(Render2DEvent var1) {
      int var2 = this.a(0.125D) * 2;
      ScaledResolution var3 = var1.getResolution();
      int var4 = var3.getScaledWidth();
      int var5 = var3.getScaledHeight();
      int var6 = ColorUtils.getColor(0, 0, 0, 0);
      int var7 = ((HUD)this.getModule(HUD.class)).getHUDColor();
      int var8 = ColorUtils.getColor(0, 0, 0, 255);
      int var9 = ColorUtils.getColor(0, 0, 0, 40);
      float var10 = 100.0F / (float)var2 * (float)this.tick;
      byte var11 = 50;
      RenderUtils.drawBorderedRect((float)(var4 / 2 - var11), (float)(var5 / 2 + 15), (float)(var4 / 2 - var11 + 100), (float)(var5 / 2 + 20), 2.0F, var8, var9);
      Gui.drawRect((double)(var4 / 2 - var11), (double)(var5 / 2 + 15), (double)((float)(var4 / 2 - var11) + var10), (double)(var5 / 2 + 20), var7);
   }

   public void onEnable() {
      Scaffold.r();
      this.checkModule(new Class[]{Speed.class, LongJump.class, Scaffold.class});
      if(this.P.equals("Motion")) {
         this.A = atS.MOTION;
      }

      if(this.E.equals("Dash")) {
         this.A = atS.DASH;
         this.H = false;
      }

      this.A = atS.BOOST;
      if(this.mc.player.isMoving() && this.mc.player.onGround) {
         this.mc.player.setSprinting(true);
         this.N = this.mc.player.posY;
         if(this.x.equals("Packet")) {
            this.b();
         }

         this.y = true;
      }

      if(((Boolean)this.G.get()).booleanValue() && (!this.mc.player.isMoving() || !this.mc.player.onGround)) {
         this.A = atS.MOTION;
      }

      if(((Boolean)this.O.get()).booleanValue() && ServerUtils.isHypixel() && ServerUtils.serverIs(Servers.SW)) {
         Iterator var2 = this.mc.world.getLoadedEntityList().iterator();
         if(var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if((var3 instanceof EntityMob || var3 instanceof EntityAnimal) && !((ClickGUI)this.getModule(ClickGUI.class)).k().contains(Integer.valueOf(var3.getEntityID())) && (double)this.mc.player.getDistanceToEntity(var3) < 3.4D) {
               this.sendPacketNoEvent(new C02PacketUseEntity(var3, C02PacketUseEntity$Action.INTERACT));
               this.A = atS.MOTION;
               this.K = true;
               this.y = true;
            }
         }
      }

      this.setSuffix(StringUtils.capitalize(this.A.name().toLowerCase()));
   }

   public void onDisable() {
      Scaffold.r();
      this.mc.timer.timerSpeed = 1.0F;
      this.R = false;
      this.y = false;
      this.H = false;
      this.tick = 0;
      if(this.A.equals(atS.MOTION)) {
         this.d();
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(StringUtils.capitalize(this.A.name().toLowerCase()));
      ++this.tick;
   }

   @EventTarget
   public void a(PlayerUpdateEvent var1) {
      this.B = this.mc.player.a(true, 0.2D);
      this.D = this.mc.player.getLastTickDistance();
   }

   @EventTarget
   public void b(PacketEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(this.A.equals(atS.MOTION)) {
            if(!(var1.getPacket() instanceof S08PacketPlayerPosLook) || !this.K) {
               return;
            }

            this.novoline.getNotificationManager().pop(this.getDisplayName(), "You can now fly!", 3000, NotificationType.SUCCESS);
            this.y = false;
            this.K = false;
         }

         if(this.A.equals(atS.BOOST)) {
            this.c(var1);
         }

         if(this.A.equals(atS.DASH) && var1.getPacket() instanceof S08PacketPlayerPosLook) {
            this.H = true;
         }
      }

   }

   @EventTarget
   public void e(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getState().equals(EventState.PRE)) {
         if(!this.A.equals(atS.MOTION) && !this.A.equals(atS.DASH)) {
            this.c(var1);
         }

         this.mc.player.cameraYaw = ((Float)this.view_bobbing.get()).floatValue() / 1000.0F;
         this.mc.player.cameraPitch = 0.0F;
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

   @EventTarget
   public void b(Render2DEvent var1) {
      String var2 = Scaffold.r();
      if(this.A.equals(atS.BOOST) && this.y) {
         this.a(var1);
      }

   }

   @EventTarget
   public void a(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.y || !this.mc.player.isMoving()) {
         var1.setMoveSpeed(0.0D);
      }

      if(this.A.equals(atS.BOOST)) {
         this.b(var1);
      }

      if(this.A.equals(atS.MOTION)) {
         var1.setMoveSpeed(((Double)this.z.get()).doubleValue());
      }

   }

   private void b(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.onGround) {
         if(this.L) {
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY());
            this.N += this.mc.player.getBaseMotionY();
            this.I *= 2.139999980926514D;
         }

         this.I = this.mc.player.getBySprinting() * 2.0D;
      }

      if(this.L) {
         if(!this.mc.player.isPotionActive(Potion.jump)) {
            this.mc.timer.timerSpeed = 1.5F;
         }

         this.I = this.D - 0.81999D * (this.D - this.B);
      }

      this.I -= this.D / (this.mc.player.motionY > 0.0D?189.0D - 12.5D * (double)this.mc.player.c(Potion.jump):39.0D);
      if(this.mc.player.fallDistance > 0.0F) {
         this.mc.timer.timerSpeed = 1.0F;
      }

      this.I = Math.max(this.I, this.B);
      var1.setMoveSpeed(this.I);
      this.L = this.mc.player.onGround;
   }

   public DoubleProperty e() {
      return this.z;
   }

   public StringProperty a() {
      return this.P;
   }

   private Boolean lambda$new$8() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Motion") || !this.E.equals("Dash"));
   }

   private Boolean lambda$new$7() {
      return Boolean.valueOf(this.P.equals("Motion"));
   }

   private Boolean lambda$new$6() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Hypixel") && this.E.equals("Boost"));
   }

   private Boolean lambda$new$5() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Hypixel") && this.E.equals("Boost"));
   }

   private Boolean lambda$new$4() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Hypixel") && this.E.equals("Boost"));
   }

   private Boolean lambda$new$3() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Hypixel") && this.E.equals("Dash"));
   }

   private Boolean lambda$new$2() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Hypixel") && this.E.equals("Dash"));
   }

   private Boolean lambda$new$1() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.P.equals("Hypixel") && this.E.equals("Dash"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.P.equals("Hypixel"));
   }
}
