package cc.novoline.modules.move;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MoveEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.TickUpdateEvent;
import cc.novoline.gui.screen.setting.Manager;
import cc.novoline.gui.screen.setting.Setting;
import cc.novoline.gui.screen.setting.SettingType;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.combat.KillAura;
import cc.novoline.modules.configurations.annotation.Property;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.ColorProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.move.Scaffold;
import cc.novoline.modules.move.Speed;
import cc.novoline.modules.move.TargetStrafe$Point;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.RotationUtil;
import cc.novoline.utils.Timer;
import java.awt.Color;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.acE;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public final class TargetStrafe extends AbstractModule {
   private static final double DOUBLED_PI = 6.283185307179586D;
   private static final double POINT_MULTIPLIER = 3.5D;
   private int position;
   private int direction = 1;
   @Property("range")
   private final DoubleProperty radius = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(2.0D)).minimum(Double.valueOf(0.5D))).maximum(Double.valueOf(4.5D));
   @Property("safe-radius")
   private final DoubleProperty safeRadius = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(2.0D)).minimum(Double.valueOf(0.5D))).maximum(Double.valueOf(4.5D));
   @Property("attack-radius")
   private final DoubleProperty attackRadius = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(2.0D)).minimum(Double.valueOf(0.5D))).maximum(Double.valueOf(4.5D));
   @Property("points-multiplier")
   private final DoubleProperty pointsMultiplier = (DoubleProperty)((DoubleProperty)PropertyFactory.createDouble(Double.valueOf(2.0D)).minimum(Double.valueOf(1.0D))).maximum(Double.valueOf(3.5D));
   @Property("switch-hurt-tick")
   private final IntProperty switchHurtTick = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(8)).minimum(Integer.valueOf(5))).maximum(Integer.valueOf(15));
   @Property("dynamic-range")
   private final BooleanProperty dynamicRange = PropertyFactory.booleanFalse();
   @Property("only-target")
   private final BooleanProperty target = PropertyFactory.booleanFalse();
   @Property("points")
   private final BooleanProperty points = PropertyFactory.booleanFalse();
   @Property("space")
   private final BooleanProperty space = PropertyFactory.booleanFalse();
   @Property("controllable")
   private final BooleanProperty controllable = PropertyFactory.booleanFalse();
   @Property("behind")
   private final BooleanProperty behind = PropertyFactory.booleanFalse();
   @Property("autothirdperson")
   private final BooleanProperty autoThirdPerson = PropertyFactory.booleanFalse();
   @Property("color")
   private final ColorProperty color = PropertyFactory.createColor(Integer.valueOf(-7697665));
   @Property("avoid-edges")
   private final BooleanProperty avoidEdges = PropertyFactory.booleanFalse();
   @Property("height")
   private final IntProperty height = (IntProperty)((IntProperty)PropertyFactory.createInt(Integer.valueOf(5)).minimum(Integer.valueOf(4))).maximum(Integer.valueOf(10));
   private boolean inverted;
   private boolean thirded;
   private final Timer timer;
   private float dist;

   public TargetStrafe(ModuleManager var1) {
      super(var1, EnumModuleType.COMBAT, "TargetStrafe", "Target Strafe");
      Scaffold.r();
      this.timer = new Timer();
      this.dist = 0.0F;
      Manager.put(new Setting("TS_RADIUS", "Radius", SettingType.SLIDER, this, this.radius, 0.1D, this::lambda$new$0));
      Manager.put(new Setting("TS_PMULTIPLIER", "Points Multiplier", SettingType.SLIDER, this, this.pointsMultiplier, 0.1D));
      Manager.put(new Setting("TS_DYNAMIC", "Dynamic Radius", SettingType.CHECKBOX, this, this.dynamicRange, this::lambda$new$1));
      SettingType var10005 = SettingType.SLIDER;
      DoubleProperty var10007 = this.safeRadius;
      BooleanProperty var10009 = this.dynamicRange;
      this.dynamicRange.getClass();
      Manager.put(new Setting("TS_SAFE_RADIUS", "Safe Radius", var10005, this, var10007, 0.1D, var10009::get));
      var10005 = SettingType.SLIDER;
      var10007 = this.attackRadius;
      var10009 = this.dynamicRange;
      this.dynamicRange.getClass();
      Manager.put(new Setting("TS_ATTACK_RADIUS", "Attack Radius", var10005, this, var10007, 0.1D, var10009::get));
      var10005 = SettingType.SLIDER;
      IntProperty var8 = this.switchHurtTick;
      var10009 = this.dynamicRange;
      this.dynamicRange.getClass();
      Manager.put(new Setting("TS_DYNAMIC_SWITCH_TICK", "Switch Hurt Tick", var10005, this, var8, 1.0D, var10009::get));
      Manager.put(new Setting("TS_TAR", "Only target", SettingType.CHECKBOX, this, this.target));
      Manager.put(new Setting("TS_SPACE", "On jump key", SettingType.CHECKBOX, this, this.space));
      Manager.put(new Setting("TS_CONTROL", "Controllable", SettingType.CHECKBOX, this, this.controllable));
      Manager.put(new Setting("TS_BEHIND", "Behind", SettingType.CHECKBOX, this, this.behind, this::lambda$new$5));
      Manager.put(new Setting("POINTS", "Draw circle", SettingType.CHECKBOX, this, this.points));
      Manager.put(new Setting("ATP", "Auto Third Person", SettingType.CHECKBOX, this, this.autoThirdPerson));
      SettingType var10004 = SettingType.COLOR_PICKER;
      ColorProperty var10006 = this.color;
      BooleanProperty var10008 = this.points;
      this.points.getClass();
      Manager.put(new Setting("TS_CIRCLE_COLOR", "Circle color", var10004, this, var10006, (EnumSet)null, var10008::get));
      Manager.put(new Setting("TS_EDGES", "Stop on Edges", SettingType.CHECKBOX, this, this.avoidEdges));
      var10004 = SettingType.SLIDER;
      IntProperty var6 = this.height;
      var10008 = this.avoidEdges;
      this.avoidEdges.getClass();
      Manager.put(new Setting("TS_EDGES_MIN_AMOUNT", "Maximum Height", var10004, this, var6, 1.0D, var10008::get));
      if(acE.b() == null) {
         Scaffold.e("DbPcFb");
      }

   }

   @EventTarget
   public void onTick(TickUpdateEvent var1) {
      String var2 = Scaffold.r();
      if(this.mc.player.movementInput().getMoveStrafe() < 0.0F) {
         this.direction = -1;
      }

      if(this.mc.player.movementInput().getMoveStrafe() > 0.0F) {
         this.direction = 1;
      }

      if(this.isCollided()) {
         if(this.timer.delay(200.0D)) {
            this.inverted = !this.inverted;
            this.position = this.inverted?this.position - 1:this.position + 1;
         }

         this.timer.reset();
      }

      KillAura var3 = (KillAura)this.getModule(KillAura.class);
      double var4 = this.getRadius(var3.getTarget());
      int var6 = (int)(3.141592653589793D * var4);
      double var7 = 6.283185307179586D / (double)((float)var6);
      Entity var9 = var3.getTarget();
      double var10 = (double)MathHelper.sin(var7 * (double)(this.position + 1) * var4 * (double)(((Boolean)this.controllable.get()).booleanValue()?this.direction:1));
      double var12 = (double)MathHelper.cos(var7 * (double)(this.position + 1)) * var4;
      if(!this.isVoidBelow(var9.posX + var10, var9.posY, var9.posZ + var12)) {
         this.inverted = !this.inverted;
      }

      this.dist = 0.7F;
      if(((Boolean)this.autoThirdPerson.get()).booleanValue() && (!var3.isEnabled() || var3.getTarget() == null || !var3.shouldAttack() || !this.shouldTarget())) {
         this.setThirded(false);
         this.mc.gameSettings.thirdPersonView = 0;
      }

   }

   public void circleStrafe(MoveEvent var1, double var2, Entity var4) {
      Scaffold.r();
      double var6 = this.getRadius(var4);
      int var8 = (int)((double)((int)(3.141592653589793D * var6)) * ((Double)this.pointsMultiplier.get()).doubleValue());
      double var9 = 6.283185307179586D / (double)((float)var8);
      double var11 = (double)MathHelper.sin(var9 * (double)this.position) * var6 * (double)(((Boolean)this.controllable.get()).booleanValue()?this.direction:1);
      double var13 = (double)MathHelper.cos(var9 * (double)this.position) * var6;
      Vec3 var15 = new Vec3(this.mc.player.posX, this.mc.player.posY + (double)this.mc.player.getEyeHeight(), this.mc.player.posZ);
      Vec3 var16 = this.getVectorForRotation(90.0F, 0.0F);
      Vec3 var17 = var15.addVector(var16.xCoord * (double)((Integer)this.height.get()).intValue(), var16.yCoord * (double)((Integer)this.height.get()).intValue(), var16.zCoord * (double)((Integer)this.height.get()).intValue());
      MovingObjectPosition var18 = this.mc.world.rayTraceBlocks(var15, var17, false, false, false);
      if(!((Boolean)this.avoidEdges.get()).booleanValue() || var18 != null) {
         if(((Boolean)this.autoThirdPerson.get()).booleanValue()) {
            this.setThirded(true);
            this.mc.gameSettings.thirdPersonView = 1;
         }

         if(((Boolean)this.behind.get()).booleanValue()) {
            double var19 = var4.posX + -Math.sin(Math.toRadians((double)var4.rotationYaw)) * -2.0D;
            double var21 = var4.posZ + Math.cos(Math.toRadians((double)var4.rotationYaw)) * -2.0D;
            var1.setX(var2 * (double)(-MathHelper.sin(Math.toRadians((double)RotationUtil.getRotations(var19, var4.posY, var21)[0]))));
            var1.setZ(var2 * (double)MathHelper.cos(Math.toRadians((double)RotationUtil.getRotations(var19, var4.posY, var21)[0])));
         }

         var1.setX(var2 * (double)(-MathHelper.sin(Math.toRadians((double)RotationUtil.getRotations(var4.posX + var11, var4.posY, var4.posZ + var13)[0]))));
         var1.setZ(var2 * (double)MathHelper.cos(Math.toRadians((double)RotationUtil.getRotations(var4.posX + var11, var4.posY, var4.posZ + var13)[0])));
      }

      var1.setX(0.0D);
      var1.setZ(0.0D);
      double var25 = Math.abs(var4.posX + var11 - this.mc.player.posX);
      double var26 = Math.abs(var4.posZ + var13 - this.mc.player.posZ);
      double var23 = Math.sqrt(var25 * var25 + var26 * var26);
      if(var23 <= (double)this.dist) {
         this.position += (this.inverted?-1:1) % var8;
      }

      if(var23 > 3.0D) {
         this.position = this.getClosestPoint(var4);
      }

   }

   private double getRadius(Entity var1) {
      String var2 = Scaffold.r();
      return ((Boolean)this.dynamicRange.get()).booleanValue()?(var1.hurtResistantTime <= ((Integer)this.switchHurtTick.get()).intValue()?((Double)this.attackRadius.get()).doubleValue():((Double)this.safeRadius.get()).doubleValue()):((Double)this.radius.get()).doubleValue();
   }

   protected final Vec3 getVectorForRotation(float var1, float var2) {
      float var3 = MathHelper.cos(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var4 = MathHelper.sin(Math.toRadians((double)(-var2)) - 3.1415927410125732D);
      float var5 = -MathHelper.cos(Math.toRadians((double)(-var1)));
      float var6 = MathHelper.sin(Math.toRadians((double)(-var1)));
      return new Vec3((double)(var4 * var5), (double)var6, (double)(var3 * var5));
   }

   private boolean isVoidBelow(double var1, double var3, double var5) {
      Scaffold.r();
      int var8 = (int)var3;
      if(this.mc.world.getBlockState(new BlockPos(var1, (double)var8, var5)).getBlock() != Blocks.air) {
         return true;
      } else {
         --var8;
         return false;
      }
   }

   private int getClosestPoint(Entity var1) {
      return ((TargetStrafe$Point)((List)this.getPoints(var1).stream().sorted(Comparator.comparingDouble(TargetStrafe$Point::getDistanceToPlayer)).collect(Collectors.toList())).get(0)).poscount;
   }

   private boolean isCollided() {
      String var1 = Scaffold.r();
      return !this.mc.world.getCollidingBoundingBoxes(this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0D, 0.0D, -0.5D)).isEmpty() || !this.mc.world.getCollidingBoundingBoxes(this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.5D, 0.0D, 0.0D)).isEmpty() || !this.mc.world.getCollidingBoundingBoxes(this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0D, 0.0D, 0.5D)).isEmpty() || !this.mc.world.getCollidingBoundingBoxes(this.mc.player, this.mc.player.getEntityBoundingBox().offset(-0.5D, 0.0D, 0.0D)).isEmpty();
   }

   private boolean space() {
      String var1 = Scaffold.r();
      return !((Boolean)this.space.get()).booleanValue() || this.mc.gameSettings.keyBindJump.isKeyDown();
   }

   public boolean shouldTarget() {
      String var1 = Scaffold.r();
      return this.isEnabled(KillAura.class) && ((KillAura)this.getModule(KillAura.class)).getTarget() != null && this.mc.player.canEntityBeSeen(((KillAura)this.getModule(KillAura.class)).getTarget()) && (!((Boolean)this.target.get()).booleanValue() || this.novoline.getPlayerManager().hasType(((KillAura)this.getModule(KillAura.class)).getTarget().getName(), PlayerManager$EnumPlayerType.TARGET)) && this.mc.player.getDistance2D(((KillAura)this.getModule(KillAura.class)).getTarget()) < ((Double)((KillAura)this.getModule(KillAura.class)).getRange().get()).doubleValue() + 2.0D && this.mc.player.posY >= ((KillAura)this.getModule(KillAura.class)).getTarget().posY - 3.4D && ((KillAura)this.getModule(KillAura.class)).getTarget().isEntityAlive() && this.mc.player.isMoving() && this.mc.player.posY <= ((KillAura)this.getModule(KillAura.class)).getTarget().posY + 3.4D && this.space() && this.isEnabled(Speed.class);
   }

   public void setInverted(boolean var1) {
      this.inverted = var1;
   }

   public boolean isInverted() {
      return this.inverted;
   }

   private List getPoints(Entity var1) {
      CopyOnWriteArrayList var3 = new CopyOnWriteArrayList();
      Scaffold.r();
      double var4 = this.getRadius(var1);
      int var6 = (int)((double)((int)(3.141592653589793D * var4)) * ((Double)this.pointsMultiplier.get()).doubleValue());
      int var7 = 0;
      if(var7 <= var6) {
         double var8 = 6.283185307179586D / (double)((float)var6);
         double var10 = (double)MathHelper.sin(var8 * (double)var7) * var4;
         double var12 = (double)MathHelper.cos(var8 * (double)var7) * var4;
         var3.add(new TargetStrafe$Point(var1.posX + var10, var1.posZ + var12, var7));
         ++var7;
      }

      return var3;
   }

   @EventTarget
   public void onRender3D(Render3DEvent var1) {
      KillAura var3 = (KillAura)this.getModule(KillAura.class);
      Scaffold.r();
      RenderUtils.pre3D();
      if(var3.getTarget() != null && ((Boolean)this.points.get()).booleanValue() && (double)this.mc.player.getDistanceToEntity(var3.getTarget()) < ((Double)var3.getRange().get()).doubleValue() && var3.getTarget().isEntityAlive() && this.shouldTarget()) {
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
         this.renderCicle(5L, var3, var1);
         GL11.glColor4f((float)this.color.getAwtColor().getRed() / 255.0F, (float)this.color.getAwtColor().getGreen() / 255.0F, (float)this.color.getAwtColor().getBlue() / 255.0F, 1.0F);
         this.renderCicle(2L, var3, var1);
      }

      GlStateManager.disableBlend();
      RenderUtils.post3D();
   }

   private void renderCicle(long var1, KillAura var3, Render3DEvent var4) {
      Scaffold.r();
      GL11.glLineWidth((float)var1);
      GL11.glBegin(3);
      double var6 = this.getRadius(var3.getTarget());
      double var8 = 6.283185307179586D / (3.141592653589793D * var6 * ((Double)this.pointsMultiplier.get()).doubleValue());
      double var10 = 0.0D;
      if(var10 < 6.283185307179586D) {
         double var12 = var3.getTarget().lastTickPosX + (var3.getTarget().posX - var3.getTarget().lastTickPosX) * (double)var4.getPartialTicks() + Math.sin(var10) * var6 - this.mc.getRenderManager().renderPosX;
         double var14 = var3.getTarget().lastTickPosY + (var3.getTarget().posY - var3.getTarget().lastTickPosY) * (double)var4.getPartialTicks() - this.mc.getRenderManager().renderPosY;
         double var16 = var3.getTarget().lastTickPosZ + (var3.getTarget().posZ - var3.getTarget().lastTickPosZ) * (double)var4.getPartialTicks() + Math.cos(var10) * var6 - this.mc.getRenderManager().renderPosZ;
         GL11.glVertex3d(var12, var14, var16);
         double var10000 = var10 + var8;
      }

      var10 = var3.getTarget().lastTickPosX + (var3.getTarget().posX - var3.getTarget().lastTickPosX) * (double)var4.getPartialTicks() + Math.sin(0.0D) * var6 - this.mc.getRenderManager().renderPosX;
      double var19 = var3.getTarget().lastTickPosY + (var3.getTarget().posY - var3.getTarget().lastTickPosY) * (double)var4.getPartialTicks() - this.mc.getRenderManager().renderPosY;
      double var20 = var3.getTarget().lastTickPosZ + (var3.getTarget().posZ - var3.getTarget().lastTickPosZ) * (double)var4.getPartialTicks() + Math.cos(0.0D) * var6 - this.mc.getRenderManager().renderPosZ;
      GL11.glVertex3d(var10, var19, var20);
      GL11.glEnd();
   }

   private int getArrayRainbowHue(int var1) {
      boolean var3 = true;
      Scaffold.r();
      boolean var4 = true;
      float var5 = ((float)var1 * 0.00999999F + (float)(System.currentTimeMillis() % 500L) / 500.0F) % 1.0F;
      float[] var6 = this.color.getHSB();
      return Color.getHSBColor(var6[0], var6[1], var5 < 0.4F?0.9F - var5:var5).getRGB();
   }

   public BooleanProperty getTarget() {
      return this.target;
   }

   public BooleanProperty isOnSpace() {
      return this.space;
   }

   public boolean isThirded() {
      return this.thirded;
   }

   public void setThirded(boolean var1) {
      this.thirded = var1;
   }

   private Boolean lambda$new$5() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(!((Boolean)this.dynamicRange.get()).booleanValue());
   }

   private Boolean lambda$new$1() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(!((Boolean)this.behind.get()).booleanValue());
   }

   private Boolean lambda$new$0() {
      String var1 = Scaffold.r();
      return Boolean.valueOf(!((Boolean)this.dynamicRange.get()).booleanValue());
   }
}
