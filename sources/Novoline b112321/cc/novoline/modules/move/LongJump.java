package cc.novoline.modules.move;

import cc.novoline.Initializer;
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
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.configurations.property.object.StringProperty;
import cc.novoline.modules.move.Flight;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.ColorUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.ServerUtils;
import cc.novoline.utils.Servers;
import cc.novoline.utils.Timer;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import net.Bw;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer$C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.util.StringUtils;
import org.jetbrains.annotations.NotNull;

public class LongJump extends AbstractModule {
   @Property("mode")
   private StringProperty I = PropertyFactory.createString("Hypixel").acceptableValues(new String[]{"Hypixel", "NCP", "Verus"});
   @Property("hypixel-mode")
   private StringProperty H = PropertyFactory.createString("Boost").acceptableValues(new String[]{"HighJump", "Boost", "Normal"});
   @Property("boost-speed")
   private DoubleProperty N = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(3.0D)).minimum(Double.valueOf(1.5D))).maximum(Double.valueOf(9.0D));
   @Property("damage-mode")
   private StringProperty M = PropertyFactory.createString("Packet").acceptableValues(new String[]{"Packet", "Edit"});
   @Property("blocksmc-speed")
   private DoubleProperty x = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(4.0D)).minimum(Double.valueOf(2.0D))).maximum(Double.valueOf(4.5D));
   @Property("explosion-exploit")
   private BooleanProperty E = PropertyFactory.createBoolean(Boolean.valueOf(true));
   private double moveSpeed;
   private double z;
   private double O;
   private double K;
   private double D;
   private boolean J;
   private boolean F;
   private boolean B;
   private Timer y = new Timer();
   private boolean A;
   private Bw C;
   private int tick;

   public LongJump(@NotNull ModuleManager var1) {
      super(var1, EnumModuleType.MOVEMENT, "LongJump", "Long Jump");
      Manager.put(new Setting("LJ_MODE", "Mode", SettingType.COMBOBOX, this, this.I));
      Manager.put(new Setting("LJ_HYPIXEL_MODE", "Hypixel Mode", SettingType.COMBOBOX, this, this.H, this::lambda$new$0));
      Manager.put(new Setting("LJ_BOOST_DAMAGE", "Damage", SettingType.COMBOBOX, this, this.M, this::lambda$new$1));
      Manager.put(new Setting("LJ_NCP_SPEED", "Speed", SettingType.SLIDER, this, this.N, 0.1D, this::lambda$new$2));
      Manager.put(new Setting("LJ_BLOCKSMC_SPEED", "Speed", SettingType.SLIDER, this, this.x, 0.1D, this::lambda$new$3));
      Manager.put(new Setting("LJ_EXPLOSION_EXPLOIT", "Explosion Exploit", SettingType.CHECKBOX, this, this.E));
   }

   private int a(double var1) {
      Scaffold.r();
      boolean var4 = ServerUtils.serverIs(Servers.UHC);
      boolean var5 = ServerUtils.serverIs(Servers.SG);
      int var6 = this.mc.player.isPotionActive(Potion.jump)?this.mc.player.getActivePotionEffect(Potion.jump).getAmplifier() + 1:0;
      return (int)((double)(3 + var6 + (!var4 && !var5?0:1)) / var1);
   }

   private int bowSlot() {
      return this.mc.player.getSlotByItem(Items.fire_charge);
   }

   private void f() {
      this.sendPacketNoEvent(new C09PacketHeldItemChange(this.bowSlot()));
      this.sendPacketNoEvent(new C03PacketPlayer$C06PacketPlayerPosLook(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, this.mc.player.rotationYaw, 90.0F, this.mc.player.onGround));
      this.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.player.inventory.getStackInSlot(this.bowSlot())));
      this.sendPacketNoEvent(new C09PacketHeldItemChange(this.mc.player.inventory.currentItem));
   }

   private void a(C03PacketPlayer var1, double var2, double var4) {
      var1.setX(var1.getX() + (Minecraft.getInstance().player.ticksExisted % 2 == 0?ThreadLocalRandom.current().nextDouble(var2, var4):-ThreadLocalRandom.current().nextDouble(var2, var4)));
      var1.setZ(var1.getZ() + (Minecraft.getInstance().player.ticksExisted % 2 != 0?ThreadLocalRandom.current().nextDouble(var2, var4):-ThreadLocalRandom.current().nextDouble(var2, var4)));
   }

   private void d() {
      String var1 = Scaffold.r();
      if(!ServerUtils.serverIs(Servers.PRE) && !ServerUtils.serverIs(Servers.LOBBY)) {
         double var2 = this.mc.player.posX;
         double var4 = this.mc.player.posY;
         double var6 = this.mc.player.posZ;
         double var8 = ThreadLocalRandom.current().nextDouble(0.001D);
         int var10 = 0;
         if(var10 <= this.a(this.D)) {
            if(var10 % 2 == 0) {
               this.sendPacket(new C03PacketPlayer$C06PacketPlayerPosLook(var2 + var8, var4 + this.D + var8, var6 + var8, RotationUtil.b(this.mc.player.rotationYaw), RotationUtil.a(this.mc.player.rotationPitch), false));
               this.sendPacket(new C03PacketPlayer$C06PacketPlayerPosLook(var2 + var8, var4 + var8, var6 + var8, RotationUtil.b(this.mc.player.rotationYaw), RotationUtil.a(this.mc.player.rotationPitch), false));
            }

            this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var2 + var8, var4 + this.D + var8, var6 + var8, false));
            this.sendPacket(new C03PacketPlayer$C04PacketPlayerPosition(var2 + var8, var4 + var8, var6 + var8, false));
            ++var10;
         }
      }

   }

   private void g(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.A) {
         double var3 = this.D;
         int var5 = this.a(var3) * 2 + 1;
         if(this.tick <= var5) {
            var1.setOnGround(false);
            var1.setY((this.tick % 2 == 0?var1.getY() + var3:var1.getY()) + ThreadLocalRandom.current().nextDouble(2.0E-4D));
            Initializer.getInstance().a(var1);
            if(this.tick == var5) {
               this.A = false;
            }
         }
      }

   }

   private void a(Render2DEvent var1) {
      int var2 = this.a(this.D) * 2;
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

   private void a(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(!this.mc.player.onGround) {
         this.F = true;
      }

      if(this.F) {
         this.checkModule(this.getClass());
         this.F = false;
      }

   }

   private void b(MotionUpdateEvent var1) {
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

   private void d(MotionUpdateEvent var1) {
      Scaffold.r();
      this.mc.timer.timerSpeed = 0.4F;
      if(this.mc.player.posY < this.K - 1.0D || this.mc.player.onGround) {
         this.mc.player.motionY = 0.519999986886978D;
         this.moveSpeed = this.z;
      }

      this.moveSpeed = ((Double)this.x.get()).doubleValue();
   }

   private void e(MotionUpdateEvent var1) {
      Scaffold.r();
      this.mc.timer.timerSpeed = 0.88F;
      if(!this.mc.player.isPotionActive(Potion.jump)) {
         if(this.mc.player.motionY > 0.0D) {
            this.mc.player.motionY += 0.0036D;
         }

         this.mc.player.motionY += 0.0108D;
      }

   }

   private void c(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.isMoving() && !this.mc.player.isPotionActive(Potion.jump)) {
         this.mc.player.motionY += 0.024D;
      }

   }

   private void b(PacketEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getDirection().equals(PacketDirection.INCOMING) && !this.mc.player.isPotionActive(Potion.jump)) {
         ((Velocity)this.getModule(Velocity.class)).a(var1, 140, 110);
      }

   }

   public void onEnable() {
      Scaffold.r();
      this.checkModule(new Class[]{Speed.class, Flight.class, Scaffold.class});
      this.moveSpeed = this.z = this.mc.player.a(true, 0.2D);
      if(this.I.equals("NCP")) {
         this.C = Bw.NCP;
      }

      if(this.I.equals("Verus")) {
         this.C = Bw.BLOCKSMC;
      }

      if(this.H.equals("Normal")) {
         this.C = Bw.NORMAL;
      }

      if(this.H.equals("Boost")) {
         this.C = Bw.BOOST;
      }

      if(this.H.equals("HighJump")) {
         this.C = Bw.HIGHJUMP;
      }

      if(((Boolean)this.E.get()).booleanValue() && ServerUtils.isHypixel()) {
         if(ServerUtils.serverIs(Servers.BW) && this.bowSlot() != -1) {
            this.f();
            this.C = Bw.EXPLOSION;
            this.A = true;
         }

         Iterator var2 = this.mc.world.getLoadedEntityList().iterator();
         if(var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if(var3 instanceof EntityTNTPrimed && this.mc.player.getDistanceToEntity(var3) < 7.0F) {
               this.C = Bw.EXPLOSION;
               this.A = true;
            }
         }
      }

      if(this.mc.player.isMoving() && this.mc.player.onGround) {
         this.mc.player.setSprinting(true);
         if(this.C.equals(Bw.BOOST) || this.C.equals(Bw.HIGHJUMP)) {
            if(this.M.equals("Packet")) {
               this.D = 0.0625D;
               this.d();
            }

            this.D = 0.125D;
            this.A = true;
         }

         if(this.C.equals(Bw.BLOCKSMC)) {
            this.K = this.mc.player.posY;
            this.D = 3.0005123413124D;
            this.d();
         }

         if(this.C.equals(Bw.NCP)) {
            this.D = 0.0625D;
            this.d();
         }
      }

      this.setSuffix(this.a());
   }

   private String a() {
      String var1 = Scaffold.r();
      return this.C.equals(Bw.NCP)?"NCP":(this.C.equals(Bw.BLOCKSMC)?"Verus":(this.C.equals(Bw.HIGHJUMP)?"HighJump":StringUtils.capitalize(this.C.name().toLowerCase())));
   }

   public void onDisable() {
      this.mc.timer.timerSpeed = 1.0F;
      this.F = false;
      this.A = false;
      this.B = false;
      this.tick = 0;
   }

   @EventTarget
   public void a(TickUpdateEvent var1) {
      this.setSuffix(this.a());
      ++this.tick;
   }

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      this.z = this.mc.player.a(true, 0.2D);
      this.O = this.mc.player.getLastTickDistance();
   }

   @EventTarget
   public void c(PacketEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getDirection().equals(PacketDirection.INCOMING)) {
         if(var1.getPacket() instanceof S08PacketPlayerPosLook && !this.C.equals(Bw.BLOCKSMC)) {
            this.checkModule(this.getClass());
         }

         if(this.C.equals(Bw.BOOST)) {
            this.b(var1);
         }

         if(this.C.equals(Bw.EXPLOSION) && var1.getPacket() instanceof S27PacketExplosion) {
            this.A = false;
         }
      }

   }

   @EventTarget
   public void f(MotionUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(var1.getState().equals(EventState.PRE)) {
         if(!this.C.equals(Bw.BLOCKSMC)) {
            this.a(var1);
         }

         if((this.C.equals(Bw.BOOST) || this.C.equals(Bw.NCP) || this.C.equals(Bw.HIGHJUMP) || this.C.equals(Bw.BLOCKSMC)) && this.A) {
            this.g(var1);
         }

         if(this.C.equals(Bw.BOOST)) {
            this.c(var1);
         }

         if(this.C.equals(Bw.BLOCKSMC)) {
            this.d(var1);
         }

         if(this.C.equals(Bw.HIGHJUMP)) {
            this.e(var1);
         }

         if(this.C.equals(Bw.NORMAL)) {
            this.mc.timer.timerSpeed = 0.9F;
         }
      }

   }

   @EventTarget
   public void b(Render2DEvent var1) {
      String var2 = Scaffold.r();
      if((this.C.equals(Bw.BOOST) || this.C.equals(Bw.NCP) || this.C.equals(Bw.HIGHJUMP) || this.C.equals(Bw.BLOCKSMC)) && this.A) {
         this.a(var1);
      }

   }

   @EventTarget
   public void c(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.A || !this.mc.player.isMoving()) {
         var1.setMoveSpeed(0.0D);
      }

      if(this.C.equals(Bw.BOOST)) {
         this.d(var1);
      }

      if(this.C.equals(Bw.HIGHJUMP)) {
         this.b(var1);
      }

      if(this.C.equals(Bw.NORMAL)) {
         this.e(var1);
      }

      if(this.C.equals(Bw.NCP)) {
         this.g(var1);
      }

      if(this.C.equals(Bw.BLOCKSMC)) {
         this.a(var1);
      }

      if(this.C.equals(Bw.EXPLOSION)) {
         this.f(var1);
      }

   }

   private void d(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.onGround) {
         if(this.J) {
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY());
            this.moveSpeed *= 2.139999980926514D;
         }

         this.moveSpeed = this.mc.player.getBySprinting() * 2.0D;
      }

      if(this.J) {
         if(!this.mc.player.isPotionActive(Potion.jump)) {
            this.mc.timer.timerSpeed = 1.5F;
         }

         this.moveSpeed = this.O - 0.81999D * (this.O - this.z);
      }

      this.moveSpeed -= this.O / (this.mc.player.motionY > 0.0D?189.0D - 12.5D * (double)this.mc.player.c(Potion.jump):39.0D);
      if(this.mc.player.fallDistance > 0.0F) {
         this.mc.timer.timerSpeed = 1.0F;
      }

      this.moveSpeed = Math.max(this.moveSpeed, this.z);
      var1.setMoveSpeed(this.moveSpeed);
      this.J = this.mc.player.onGround;
   }

   private void b(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.onGround) {
         if(this.J) {
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY(0.41999998688698D) + 0.4D + 0.1D * (double)this.mc.player.c(Potion.jump));
            this.moveSpeed *= 2.139999980926514D;
         }

         this.moveSpeed = this.mc.player.getBySprinting() * 2.0D;
      }

      if(this.J) {
         this.moveSpeed = this.O - 0.81999D * (this.O - this.z);
      }

      this.moveSpeed -= this.O / (69.0D - 20.0D * (double)this.mc.player.c(Potion.jump) + 16.0D * (double)this.mc.player.c(Potion.moveSpeed));
      this.moveSpeed = Math.max(this.moveSpeed, this.z);
      var1.setMoveSpeed(this.moveSpeed);
      this.J = this.mc.player.onGround;
   }

   private void e(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.onGround) {
         if(this.J) {
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY());
            this.moveSpeed *= 2.139999980926514D;
         }

         this.moveSpeed = this.mc.player.getBySprinting() * 2.0D;
      }

      if(this.J) {
         this.moveSpeed = this.O - 0.66D * (this.O - this.z);
      }

      this.moveSpeed -= this.O / 24.0D;
      if(!this.mc.player.isPotionActive(Potion.jump) && this.mc.player.motionY < 0.0D) {
         this.moveSpeed = this.z;
         if(this.mc.player.ticksExisted % 2 == 0 && (double)this.mc.player.fallDistance < 0.45D) {
            this.moveSpeed = this.z * 1.2D;
            this.mc.player.motionY = 0.0D;
         }
      }

      this.moveSpeed = Math.max(this.moveSpeed, this.z);
      var1.setMoveSpeed(this.moveSpeed);
      this.J = this.mc.player.onGround;
   }

   private void g(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.onGround) {
         if(this.J) {
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY());
            this.moveSpeed *= 2.1449999809265137D;
         }

         this.moveSpeed = this.mc.player.getBySprinting() * ((Double)this.N.get()).doubleValue();
      }

      if(this.J) {
         this.moveSpeed = this.O - 0.66D * (this.O - this.z);
      }

      this.moveSpeed -= this.O / 159.0D;
      this.moveSpeed = Math.max(this.moveSpeed, this.z);
      var1.setMoveSpeed(this.moveSpeed);
      this.J = this.mc.player.onGround;
   }

   private void a(MoveEvent var1) {
      this.moveSpeed -= 0.1D;
      var1.setMoveSpeed(this.moveSpeed);
   }

   private void f(MoveEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.onGround) {
         if(this.J) {
            var1.setY(this.mc.player.motionY = this.mc.player.getBaseMotionY() * 9.0D);
            this.moveSpeed *= 2.1449999809265137D;
         }

         this.moveSpeed = this.mc.player.getBySprinting() * 6.0D;
      }

      if(this.J) {
         this.moveSpeed = this.O - 0.66D * (this.O - this.z);
      }

      this.moveSpeed = this.z;
      this.moveSpeed = Math.max(this.moveSpeed, this.z);
      var1.setMoveSpeed(this.moveSpeed);
      this.J = this.mc.player.onGround;
   }

   public StringProperty e() {
      return this.I;
   }

   public Bw b() {
      return this.C;
   }

   private Boolean lambda$new$3() {
      return Boolean.valueOf(this.I.equals("Verus"));
   }

   private Boolean lambda$new$2() {
      return Boolean.valueOf(this.I.equals("NCP"));
   }

   private Boolean lambda$new$1() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(this.I.equals("Hypixel") && !this.H.equals("Normal"));
   }

   private Boolean lambda$new$0() {
      return Boolean.valueOf(this.I.equals("Hypixel"));
   }
}
