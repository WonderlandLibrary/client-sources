package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.EventState;
import cc.novoline.events.events.JumpEvent;
import cc.novoline.events.events.MotionUpdateEvent;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.PacketDirection;
import cc.novoline.events.events.PacketEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.exploits.Disabler;
import cc.novoline.modules.move.FastSneak;
import cc.novoline.modules.move.Flight;
import cc.novoline.modules.move.LongJump;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.player.GameSpeed;
import cc.novoline.utils.ServerUtils;
import java.util.function.Supplier;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;

public final class Speed extends AbstractModule {
   private double I;
   private double A;
   private double F;
   private double D;
   private double z;
   private boolean K;
   private boolean G;
   private int H;
   @Property("mode")
   private final StringProperty J = PropertyFactory.createString("NCP").acceptableValues(new String[]{"NCP", "Vanilla"});
   @Property("jump-mode")
   private final StringProperty M = PropertyFactory.createString("Reduced").acceptableValues(new String[]{"Vanilla", "Reduced"});
   @Property("down-motion")
   private BooleanProperty x = PropertyFactory.createBoolean(Boolean.valueOf(false));
   @Property("lag-back")
   private final BooleanProperty L = PropertyFactory.booleanFalse();
   @Property("dmg-boost")
   private final BooleanProperty timerboost = PropertyFactory.booleanFalse();
   @Property("timer-boost-value")
   private final FloatProperty y = (FloatProperty)((FloatProperty)PropertyFactory.createFloat(Float.valueOf(1.0F)).minimum(Float.valueOf(1.0F))).maximum(Float.valueOf(2.0F));
   @Property("friction")
   private final IntProperty B = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(150)).minimum(Integer.valueOf(60))).maximum(Integer.valueOf(180));
   @Property("speed")
   private final DoubleProperty C = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(1.5D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(2.0D));

   public Speed(ModuleManager var1) {
      super(var1, "Speed", "Speed", 0, EnumModuleType.MOVEMENT, "Increases your in-game speed");
      Manager.put(new Setting("SPEED_MODE", "Mode", SettingType.COMBOBOX, this, this.J));
      Manager.put(new Setting("SPEED_HEIGHT_MODE", "Jump Mode", SettingType.COMBOBOX, this, this.M));
      Manager.put(new Setting("SPEED_DOWN_MOTION", "Down Motion", SettingType.CHECKBOX, this, this.x));
      Manager.put(new Setting("SPEED_LAG_CHECK", "Lagback check", SettingType.CHECKBOX, this, this.L));
      Manager.put(new Setting("SPEED_BOOST", "Damage Boost", SettingType.CHECKBOX, this, this.timerboost));
      Manager.put(new Setting("SPEED_TIMER_BOOST", "Timer Boost", SettingType.SLIDER, this, this.y, 0.05D));
      Manager.put(new Setting("SPEED_VANILLA_SPEED", "Vanilla Speed", SettingType.SLIDER, this, this.C, 0.1D, this::lambda$new$0));
      Manager.put(new Setting("SPEED_FRICTION", "Friction", SettingType.SLIDER, this, this.B, 2.0D));
   }

   @EventTarget
   public void onJump(JumpEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.isMoving() && !this.mc.player.isInLiquid()) {
         var1.setCancelled(true);
      }

   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(ServerUtils.isHypixel()?"Hypixel":(String)this.J.get());
   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      Scaffold.r();
      this.A = this.mc.player.getLastTickDistance();
      this.F = this.mc.player.a(true, 0.2D) * (this.mc.player.isInLiquid()?0.5D:(this.mc.player.movementInput().sneak() && !this.isEnabled(FastSneak.class)?0.8D:1.0D));
   }

   @EventTarget
   public void a(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getState() == EventState.PRE) {
         if(this.c() && (this.mc.player.ticksExisted % 2 == 0 || this.H == 0)) {
            var1.setOnGround(true);
         }

         if(this.mc.player.isMoving() && ((Float)this.y.get()).floatValue() > 1.0F && !this.isEnabled(GameSpeed.class)) {
            this.mc.timer.timerSpeed = ((Float)this.y.get()).floatValue();
         }
      }

   }

   public boolean c() {
      String var1 = Scaffold.r();
      return this.isEnabled() && ServerUtils.isHypixel() && this.mc.player.getLastTickDistance() > 0.0D && this.mc.player.fallDistance < (float)(1 + this.mc.player.c(Potion.jump));
   }

   @EventTarget
   public void a(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.isMoving()) {
         if(this.mc.player.onGround && !this.mc.player.N()) {
            this.H = 0;
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY(this.M.equals("Reduced")?0.399999986886978D:0.419999986886978D));
            this.I = this.F * 2.139999980926514D;
         }

         if(this.K) {
            this.I = this.A - (this.J.equals("NCP")?0.81999D:0.66D) * (this.A - this.F);
            this.I *= this.J.equals("Vanilla")?((Double)this.C.get()).doubleValue():1.0D;
         }

         ++this.H;
         this.I -= this.A / (double)(((Integer)this.B.get()).intValue() - 1);
         if(((Boolean)this.x.get()).booleanValue() && this.H == 3 && !this.mc.player.isPotionActive(Potion.jump)) {
            this.mc.player.motionY = -0.099999986886978D;
         }

         if(((Boolean)this.timerboost.get()).booleanValue() && this.G && !this.mc.player.isPotionActive(Potion.poison) && !this.mc.player.isBurning()) {
            this.I += this.D;
            this.G = false;
         }

         var1.setMoveSpeed(Math.max(this.I, this.F));
         this.K = this.mc.player.onGround;
      }

      var1.setMoveSpeed(0.0D);
   }

   @EventTarget
   public void b(PacketEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(((Boolean)this.L.get()).booleanValue() && var1.getPacket() instanceof S08PacketPlayerPosLook) {
            this.checkModule(this.getClass());
         }

         if(!(var1.getPacket() instanceof S27PacketExplosion)) {
            return;
         }

         S27PacketExplosion var3 = (S27PacketExplosion)var1.getPacket();
         if(var3.getAffectedBlockPositions().isEmpty()) {
            this.D = Math.hypot(0.15D + (double)(var3.getMotionX() / 8500.0F), 0.15D + (double)(var3.getMotionZ() / 8500.0F));
            this.z = this.mc.player.motionY + (double)(var3.getMotionY() / 8500.0F);
            this.G = true;
         }
      }

      if(this.isEnabled(Disabler.class) && ((Disabler)this.getModule(Disabler.class)).b().equals("Verus") && this.J.equals("Vanilla") && ((Double)this.C.get()).doubleValue() > 1.2D && var1.getPacket() instanceof C03PacketPlayer && this.mc.player.ticksExisted % 40 == 0) {
         ;
      }

   }

   public void onEnable() {
      String var1 = Scaffold.r();
      this.setSuffix(ServerUtils.isHypixel()?"Hypixel":(String)this.J.get());
      this.checkModule(new Class[]{Flight.class, Scaffold.class, LongJump.class});
      this.I = this.mc.player.getBaseMoveSpeed();
      if(this.mc.player.onGround) {
         this.mc.player.resetLastTickDistance();
      }

   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
   }

   public StringProperty a() {
      return this.J;
   }

   public StringProperty d() {
      return this.M;
   }

   public FloatProperty b() {
      return this.y;
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.J.equals("Vanilla"));
   }
}
