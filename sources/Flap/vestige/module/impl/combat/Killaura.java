package vestige.module.impl.combat;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.ItemRenderEvent;
import vestige.event.impl.JumpEvent;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.PostMotionEvent;
import vestige.event.impl.Render3DEvent;
import vestige.event.impl.RenderEvent;
import vestige.event.impl.SlowdownEvent;
import vestige.event.impl.StrafeEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.misc.Targets;
import vestige.module.impl.movement.Speed;
import vestige.module.impl.player.Antivoid;
import vestige.module.impl.visual.ClientTheme;
import vestige.module.impl.world.AutoBridge;
import vestige.module.impl.world.Breaker;
import vestige.module.impl.world.ScaffoldV2;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.misc.LogUtil;
import vestige.util.misc.TimerUtil;
import vestige.util.network.PacketUtil;
import vestige.util.player.AttackUtil;
import vestige.util.player.FixedRotations;
import vestige.util.player.MovementUtil;
import vestige.util.player.RotationsUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.Utils;

public class Killaura extends Module {
   public EntityLivingBase target;
   public final ModeSetting mode = new ModeSetting("Mode", "Single", new String[]{"Single", "Switch"});
   public final ModeSetting autoblock = new ModeSetting("Autoblock", "None", new String[]{"Vanilla", "Hypixel", "Hypixel2", "Cris", "NCP", "AAC5", "Spoof", "Spoof2", "Fake", "None"});
   private final ModeSetting filter = new ModeSetting("Filter", "Range", new String[]{"Range", "Health", "Fov"});
   private final ModeSetting rotations = new ModeSetting("Rotations", "Normal", new String[]{"Normal", "Smooth", "None"});
   private final ModeSetting raycast = new ModeSetting("Raycast", "Disabled", new String[]{"Disabled", "Normal", "Legit"});
   private final ModeSetting moveFix = new ModeSetting("Move fix", "Silent", new String[]{"Disabled", "Normal", "Silent"});
   private final BooleanSetting keepSprint = new BooleanSetting("Keep Sprint", true);
   public final DoubleSetting startingRange = new DoubleSetting("Starting Attack", 3.0D, 3.0D, 6.0D, 0.05D);
   public final DoubleSetting range = new DoubleSetting("Reach", 3.0D, 3.0D, 6.0D, 0.05D);
   public final DoubleSetting rotationRange = new DoubleSetting("Rotation range", 4.0D, 3.0D, 6.0D, 0.05D);
   private final ModeSetting attackDelayMode = new ModeSetting("Attack delay mode", "CPS", new String[]{"CPS"});
   private final IntegerSetting APS = new IntegerSetting("CPS Value", () -> {
      return this.attackDelayMode.is("CPS");
   }, 10, 1, 20, 1);
   private final IntegerSetting failRate = new IntegerSetting("Fail rate", 0, 0, 30, 1);
   private final IntegerSetting hurtTime = new IntegerSetting("Hurt time", 10, 0, 10, 1);
   private final ModeSetting blockTiming = new ModeSetting("Block timing", () -> {
      return this.autoblock.is("Spoof") || this.autoblock.is("Spoof2");
   }, "Post", new String[]{"Pre", "Post"});
   private final IntegerSetting blockHurtTime = new IntegerSetting("Block hurt time", () -> {
      return this.autoblock.is("Spoof") || this.autoblock.is("Spoof2");
   }, 5, 0, 10, 1);
   public final BooleanSetting noHitOnFirstTick = new BooleanSetting("No Hit First Tick", true);
   private final BooleanSetting delayTransactions = new BooleanSetting("Delay transactions", false);
   private final BooleanSetting whileInventoryOpened = new BooleanSetting("Inventory opened", false);
   private final BooleanSetting whileScaffoldEnabled = new BooleanSetting("Scaffold enabled", false);
   private final BooleanSetting whileUsingBreaker = new BooleanSetting("Using breaker", false);
   private final BooleanSetting thougthWalls = new BooleanSetting("Trought Walls", false);
   public final ModeSetting viwermode = new ModeSetting("Visual", "Box", new String[]{"Box", "Jello", "Vape", "Circle", "Circle Lines", "None"});
   private boolean hadTarget;
   private FixedRotations fixedRotations;
   private double random;
   private boolean attackNextTick;
   private double rotSpeed;
   private boolean done;
   private boolean blocking;
   private int autoblockTicks;
   private int attackCounter;
   private Antibot antibotModule;
   private Teams teamsModule;
   private Speed speedModule;
   private AutoBridge autoBridgeModule;
   private Breaker breakerModule;
   private Antivoid antivoidModule;
   private Velocity velocityModule;
   public boolean isBlocking;
   private int currentSlot = 0;
   private boolean couldBlock;
   private boolean blinking;
   private int lastSlot;
   private final TimerUtil attackTimer = new TimerUtil();
   private boolean attack;
   public AtomicBoolean blinkinge = new AtomicBoolean();
   public boolean lag;
   private boolean swapped;
   public boolean rmbDown;
   private float[] prevRotations;
   private boolean startSmoothing;
   private boolean aiming;
   private ConcurrentLinkedQueue<Packet> blinkedPackets = new ConcurrentLinkedQueue();
   private float[] currentRotations;
   private int blinkAutoBlockTicks;
   private String[] swapBlacklist = new String[]{"compass", "snowball", "spawn", "skull"};
   public AtomicBoolean block = new AtomicBoolean();
   private long lastSwitched = System.currentTimeMillis();
   private boolean switchTargets;
   private byte entityIndex;
   public boolean swing;
   private long i;
   private long j;
   private long k;
   private long l;
   private double m;
   private boolean n;
   private Random rand;
   private ClientTheme theme;
   public long leftClickDelay = 0L;

   public Killaura() {
      super("Killaura", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.mode, this.autoblock, this.filter, this.rotations, this.raycast, this.moveFix, this.keepSprint, this.startingRange, this.range, this.rotationRange, this.attackDelayMode, this.APS, this.failRate, this.hurtTime, this.blockTiming, this.blockHurtTime, this.noHitOnFirstTick, this.delayTransactions, this.whileInventoryOpened, this.whileScaffoldEnabled, this.whileUsingBreaker, this.thougthWalls, this.viwermode});
   }

   public void onEnable() {
      this.fixedRotations = new FixedRotations(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
      this.rotSpeed = 15.0D;
      this.done = false;
      this.random = 0.5D;
      this.attackNextTick = false;
      this.couldBlock = false;
   }

   public String getInfo() {
      return this.autoblock.getMode();
   }

   public boolean onDisable() {
      if (mc.thePlayer != null) {
         if (this.hadTarget && this.rotations.is("Smooth")) {
            mc.thePlayer.rotationYaw = this.fixedRotations.getYaw();
         }

         this.stopTargeting();
      }

      Flap.instance.getSlotSpoofHandler().stopSpoofing();
      return false;
   }

   private void stopTargeting() {
      this.target = null;
      this.releaseBlocking();
      this.hadTarget = false;
      this.attackNextTick = false;
      if (this.delayTransactions.isEnabled()) {
         Flap.instance.getPacketDelayHandler().stopAll();
      }

   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      String var2 = this.viwermode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 66987:
         if (var2.equals("Box")) {
            var3 = 0;
         }
         break;
      case 2658816:
         if (var2.equals("Vape")) {
            var3 = 2;
         }
         break;
      case 71456692:
         if (var2.equals("Jello")) {
            var3 = 1;
         }
         break;
      case 558278575:
         if (var2.equals("Circle Lines")) {
            var3 = 4;
         }
         break;
      case 2018617584:
         if (var2.equals("Circle")) {
            var3 = 3;
         }
      }

      switch(var3) {
      case 0:
         if (this.target != null && this.hadTarget) {
            this.renderBox(this.target);
         }
         break;
      case 1:
         if (this.target != null && this.hadTarget) {
            this.render(this.target);
         }
         break;
      case 2:
         if (this.target != null && this.hadTarget) {
            this.vaperender(this.target);
         }
         break;
      case 3:
         if (this.target != null && this.hadTarget) {
            RenderUtils2.renderEntity(this.target, 6, 0.0D, 0.0D, this.theme.getColor(100), this.target.hurtTime != 0);
         }
         break;
      case 4:
         if (this.target != null && this.hadTarget) {
            RenderUtils2.renderEntity(this.target, 7, 0.0D, 0.0D, this.theme.getColor(100), this.target.hurtTime != 0);
         }
      }

   }

   public void renderBox(@NotNull EntityLivingBase target) {
      if (target == null) {
         $$$reportNull$$$0(0);
      }

      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      RenderUtils2.renderEntity(target, 1, 0.0D, 0.0D, this.theme.getColor(100), target.hurtTime != 0);
   }

   public void vaperender(@NotNull EntityLivingBase target) {
      if (target == null) {
         $$$reportNull$$$0(1);
      }

      mc.entityRenderer.disableLightmap();
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glEnable(3042);
      GL11.glDisable(2929);
      GL11.glDisable(2884);
      GL11.glShadeModel(7425);
      mc.entityRenderer.disableLightmap();
      double radius = (double)target.width;
      double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
      double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY;
      double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
      double eased = (double)target.height - 0.2D;

      for(int segments = 0; segments < 360; segments += 5) {
         double x1 = x - Math.sin((double)segments * 3.141592653589793D / 180.0D) * radius;
         double z1 = z + Math.cos((double)segments * 3.141592653589793D / 180.0D) * radius;
         double x2 = x - Math.sin((double)(segments - 5) * 3.141592653589793D / 180.0D) * radius;
         double z2 = z + Math.cos((double)(segments - 5) * 3.141592653589793D / 180.0D) * radius;
         GL11.glBegin(7);
         Targets var10000;
         Targets var10001;
         Targets var10002;
         Targets var10003;
         float var21;
         float var22;
         float var23;
         if (target.hurtTime > 0) {
            var10000 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var21 = (float)(Targets.red.getValue() / 255.0D);
            var10001 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var22 = (float)(Targets.green.getValue() / 255.0D);
            var10002 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var23 = (float)(Targets.blue.getValue() / 255.0D);
            var10003 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            GL11.glColor4f(var21, var22, var23, (float)(Targets.alpha.getValue() / 255.0D));
         } else {
            var10000 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var21 = (float)(Targets.red.getValue() / 255.0D);
            var10001 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var22 = (float)(Targets.green.getValue() / 255.0D);
            var10002 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var23 = (float)(Targets.blue.getValue() / 255.0D);
            var10003 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            GL11.glColor4f(var21, var22, var23, (float)(Targets.alpha.getValue() / 255.0D));
         }

         GL11.glVertex3d(x1, y, z1);
         GL11.glVertex3d(x2, y, z2);
         if (target.hurtTime > 0) {
            var10000 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var21 = (float)(Targets.red.getValue() / 255.0D);
            var10001 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var22 = (float)(Targets.green.getValue() / 255.0D);
            var10002 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            GL11.glColor4f(var21, var22, (float)(Targets.blue.getValue() / 255.0D), 0.0F);
         } else {
            var10000 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var21 = (float)(Targets.red.getValue() / 255.0D);
            var10001 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            var22 = (float)(Targets.green.getValue() / 255.0D);
            var10002 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
            GL11.glColor4f(var21, var22, (float)(Targets.blue.getValue() / 255.0D), 0.0F);
         }

         GL11.glVertex3d(x2, y + eased, z2);
         GL11.glVertex3d(x1, y + eased, z1);
         GL11.glEnd();
         GL11.glBegin(2);
         GL11.glVertex3d(x2, y + eased, z2);
         GL11.glVertex3d(x1, y + eased, z1);
         GL11.glEnd();
      }

      GL11.glEnable(2884);
      GL11.glShadeModel(7424);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public void render(@NotNull EntityLivingBase target) {
      if (target == null) {
         $$$reportNull$$$0(2);
      }

      int drawTime = (int)(System.currentTimeMillis() % 2000L);
      boolean drawMode = drawTime > 1000;
      float drawPercent = (float)drawTime / 1000.0F;
      if (!drawMode) {
         drawPercent = 1.0F - drawPercent;
      } else {
         --drawPercent;
      }

      drawPercent *= 2.0F;
      if (drawPercent < 1.0F) {
         drawPercent = 0.5F * drawPercent * drawPercent * drawPercent;
      } else {
         float f = drawPercent - 2.0F;
         drawPercent = 0.5F * (f * f * f + 2.0F);
      }

      Minecraft mc = Minecraft.getMinecraft();
      mc.entityRenderer.disableLightmap();
      GL11.glPushMatrix();
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glEnable(3042);
      GL11.glDisable(2929);
      GL11.glDisable(2884);
      GL11.glShadeModel(7425);
      mc.entityRenderer.disableLightmap();
      double radius = (double)target.width;
      double height = (double)target.height;
      double x = target.lastTickPosX + (target.posX - target.lastTickPosX) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosX;
      double y = target.lastTickPosY + (target.posY - target.lastTickPosY) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosY + height * (double)drawPercent;
      double z = target.lastTickPosZ + (target.posZ - target.lastTickPosZ) * (double)getTimer().renderPartialTicks - mc.getRenderManager().viewerPosZ;
      double eased = height / 3.0D * (double)((double)drawPercent > 0.5D ? 1.0F - drawPercent : drawPercent) * (double)(drawMode ? -1 : 1);

      for(int segments = 0; segments < 360; segments += 5) {
         double x1 = x - Math.sin((double)segments * 3.141592653589793D / 180.0D) * radius;
         double z1 = z + Math.cos((double)segments * 3.141592653589793D / 180.0D) * radius;
         double x2 = x - Math.sin((double)(segments - 5) * 3.141592653589793D / 180.0D) * radius;
         double z2 = z + Math.cos((double)(segments - 5) * 3.141592653589793D / 180.0D) * radius;
         GL11.glBegin(7);
         Targets var10000 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         float var28 = (float)(Targets.red.getValue() / 255.0D);
         Targets var10001 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         float var29 = (float)(Targets.green.getValue() / 255.0D);
         Targets var10002 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         GL11.glColor4f(var28, var29, (float)(Targets.blue.getValue() / 255.0D), 0.0F);
         GL11.glVertex3d(x1, y + eased, z1);
         GL11.glVertex3d(x2, y + eased, z2);
         var10000 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         var28 = (float)(Targets.red.getValue() / 255.0D);
         var10001 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         var29 = (float)(Targets.green.getValue() / 255.0D);
         var10002 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         float var30 = (float)(Targets.blue.getValue() / 255.0D);
         Targets var10003 = (Targets)Flap.instance.getModuleManager().getModule(Targets.class);
         GL11.glColor4f(var28, var29, var30, (float)Targets.alpha.getValue() / 255.0F);
         GL11.glVertex3d(x2, y, z2);
         GL11.glVertex3d(x1, y, z1);
         GL11.glEnd();
         GL11.glBegin(2);
         GL11.glVertex3d(x2, y, z2);
         GL11.glVertex3d(x1, y, z1);
         GL11.glEnd();
      }

      GL11.glEnable(2884);
      GL11.glShadeModel(7424);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glPopMatrix();
   }

   public static Timer getTimer() {
      try {
         Field timerField = Minecraft.class.getDeclaredField("timer");
         timerField.setAccessible(true);
         return (Timer)timerField.get(Minecraft.getMinecraft());
      } catch (IllegalAccessException | NoSuchFieldException var1) {
         var1.printStackTrace();
         return null;
      }
   }

   public static void glColor(Color color, int i) {
      GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
   }

   @Listener
   public void onRender(RenderEvent event) {
      if (mc.thePlayer != null && mc.thePlayer.ticksExisted >= 10) {
         if (this.target != null && this.attackDelayMode.is("CPS")) {
            this.leftClickDelay = this.updateDelay(this.APS.getValue(), 3.0F);
            if (this.attackTimer.getTimeElapsed() >= this.leftClickDelay) {
               this.attackNextTick = true;
               this.attackTimer.reset();
            }
         }

      } else {
         this.setEnabled(false);
      }
   }

   private long updateDelay(int cps, Float rand) {
      return (long)(1000.0D / AttackUtil.getPattern2Randomization((double)cps, (double)rand));
   }

   public void onClientStarted() {
      this.antibotModule = (Antibot)Flap.instance.getModuleManager().getModule(Antibot.class);
      this.speedModule = (Speed)Flap.instance.getModuleManager().getModule(Speed.class);
      this.teamsModule = (Teams)Flap.instance.getModuleManager().getModule(Teams.class);
      this.autoBridgeModule = (AutoBridge)Flap.instance.getModuleManager().getModule(AutoBridge.class);
      this.breakerModule = (Breaker)Flap.instance.getModuleManager().getModule(Breaker.class);
      this.antivoidModule = (Antivoid)Flap.instance.getModuleManager().getModule(Antivoid.class);
      this.velocityModule = (Velocity)Flap.instance.getModuleManager().getModule(Velocity.class);
   }

   @Listener
   public void onTick(TickEvent event) {
      if (mc.thePlayer.ticksExisted < 10) {
         this.setEnabled(false);
      } else {
         this.random = Math.random();
         String var2 = this.mode.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1818398616:
            if (var2.equals("Single")) {
               var3 = 0;
            }
            break;
         case -1805606060:
            if (var2.equals("Switch")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            if (this.target == null || !this.canAttack(this.target)) {
               this.target = this.findTarget(true);
            }
            break;
         case 1:
            this.target = this.findTarget(true);
         }

         this.getRotations();
         boolean inventoryOpened = mc.currentScreen instanceof GuiContainer && !this.whileInventoryOpened.isEnabled();
         boolean scaffoldEnabled = (((ScaffoldV2)Flap.instance.getModuleManager().getModule(ScaffoldV2.class)).isEnabled() || this.autoBridgeModule.isEnabled()) && !this.whileScaffoldEnabled.isEnabled();
         boolean usingBreaker = this.breakerModule.isEnabled() && this.breakerModule.isBreakingBed() && !this.whileUsingBreaker.isEnabled();
         if (this.target != null && !inventoryOpened && !scaffoldEnabled && !usingBreaker) {
            boolean attackTick = false;
            if (this.getDistanceToEntity(this.target) <= (this.hadTarget ? this.range.getValue() : this.startingRange.getValue())) {
               if (this.target.hurtTime <= this.hurtTime.getValue()) {
                  String var6 = this.attackDelayMode.getMode();
                  byte var7 = -1;
                  switch(var6.hashCode()) {
                  case 66950:
                     if (var6.equals("CPS")) {
                        var7 = 0;
                     }
                  default:
                     switch(var7) {
                     case 0:
                        if (!this.hadTarget) {
                           attackTick = true;
                           this.attackTimer.reset();
                        } else if (this.attackNextTick) {
                           attackTick = true;
                           this.attackNextTick = false;
                        }
                     }
                  }
               }

               if (this.delayTransactions.isEnabled()) {
                  Flap.instance.getPacketDelayHandler().startDelayingPing(2000L);
               }

               this.hadTarget = true;
            } else {
               this.hadTarget = false;
            }

            boolean shouldBlock = this.canBlock();
            this.couldBlock = shouldBlock;
            if (shouldBlock) {
               if (!this.autoblockAllowAttack()) {
                  attackTick = false;
               }

               this.beforeAttackAutoblock(attackTick);
            } else {
               if (this.blocking) {
                  attackTick = false;
               }

               this.releaseBlocking();
            }

            if (attackTick) {
               boolean canAttack = true;
               if (!this.raycast.is("Disabled")) {
                  canAttack = this.raycast.is("Legit") ? RotationsUtil.raycastEntity(this.target, this.fixedRotations.getYaw(), this.fixedRotations.getPitch(), this.fixedRotations.getLastYaw(), this.fixedRotations.getLastPitch(), this.range.getValue() + 0.3D) : RotationsUtil.raycastEntity(this.target, this.fixedRotations.getYaw(), this.fixedRotations.getPitch(), this.fixedRotations.getYaw(), this.fixedRotations.getPitch(), this.range.getValue() + 0.3D);
               }

               double aaa = (double)this.failRate.getValue() / 100.0D;
               if (Math.random() > 1.0D - aaa) {
                  canAttack = false;
               }

               mc.thePlayer.swingItem();
               if (canAttack) {
                  if (this.keepSprint.isEnabled()) {
                     mc.playerController.attackEntityNoSlowdown(mc.thePlayer, this.target);
                  } else {
                     mc.playerController.attackEntity(mc.thePlayer, this.target);
                  }

                  if (this.thougthWalls.isEnabled()) {
                     double range = this.range.getValue();
                     Vec3 rotationVector = mc.thePlayer.getVectorForRotation((float)RotationsUtil.getEyePos().yCoord, (float)RotationsUtil.getEyePos().xCoord);
                     Vec3 eyes = mc.thePlayer.getPositionEyes(1.0F);
                     Vec3 endVec = eyes.addVector(rotationVector.xCoord * range, rotationVector.yCoord * range, rotationVector.zCoord * range);
                     MovingObjectPosition movingObjectPosition = this.target.getEntityBoundingBox().expand(0.1D, 0.1D, 0.1D).calculateIntercept(eyes, endVec);
                     if (movingObjectPosition != null) {
                        movingObjectPosition.typeOfHit = MovingObjectPosition.MovingObjectType.ENTITY;
                        movingObjectPosition.entityHit = this.target;
                     }
                  }
               }

               this.attackCounter = 0;
            }

            if (shouldBlock) {
               this.afterAttackAutoblock(attackTick);
            }

            if (!this.rotations.is("None") && this.isRotating() && this.moveFix.is("Silent")) {
               float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(this.fixedRotations.getYaw()) - MathHelper.wrapAngleTo180_float(MovementUtil.getPlayerDirection())) + 22.5F;
               if (diff < 0.0F) {
                  diff += 360.0F;
               }

               int a = (int)((double)diff / 45.0D);
               float value = mc.thePlayer.moveForward != 0.0F ? Math.abs(mc.thePlayer.moveForward) : Math.abs(mc.thePlayer.moveStrafing);
               float forward = value;
               float strafe = 0.0F;

               for(int i = 0; i < 8 - a; ++i) {
                  float[] dirs = MovementUtil.incrementMoveDirection(forward, strafe);
                  forward = dirs[0];
                  strafe = dirs[1];
               }

               if (forward < 0.8F) {
                  mc.gameSettings.keyBindSprint.pressed = false;
                  mc.thePlayer.setSprinting(false);
               }
            }

         } else {
            this.stopTargeting();
            this.couldBlock = false;
         }
      }
   }

   @Listener
   public void onSlowdown(SlowdownEvent event) {
   }

   @Listener
   public void onJump(JumpEvent event) {
      if (this.target != null && !this.rotations.is("None") && !this.moveFix.is("Disabled")) {
         event.setYaw(this.fixedRotations.getYaw());
      }

   }

   @Listener
   public void onStrafe(StrafeEvent event) {
      if (!this.rotations.is("None") && this.isRotating()) {
         String var2 = this.moveFix.getMode();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1955878649:
            if (var2.equals("Normal")) {
               var3 = 0;
            }
            break;
         case -1818460043:
            if (var2.equals("Silent")) {
               var3 = 1;
            }
         }

         switch(var3) {
         case 0:
            event.setYaw(this.fixedRotations.getYaw());
            break;
         case 1:
            event.setYaw(this.fixedRotations.getYaw());
            float diff = MathHelper.wrapAngleTo180_float(MathHelper.wrapAngleTo180_float(this.fixedRotations.getYaw()) - MathHelper.wrapAngleTo180_float(MovementUtil.getPlayerDirection())) + 22.5F;
            if (diff < 0.0F) {
               diff += 360.0F;
            }

            int a = (int)((double)diff / 45.0D);
            float value = event.getForward() != 0.0F ? Math.abs(event.getForward()) : Math.abs(event.getStrafe());
            float forward = value;
            float strafe = 0.0F;

            for(int i = 0; i < 8 - a; ++i) {
               float[] dirs = MovementUtil.incrementMoveDirection(forward, strafe);
               forward = dirs[0];
               strafe = dirs[1];
            }

            event.setForward(forward);
            event.setStrafe(strafe);
         }
      }

   }

   private boolean canRenderBlocking() {
      return this.canBlock() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword || this.autoblock.is("Fake") && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
   }

   private boolean canBlock() {
      ItemStack stack = mc.thePlayer.getHeldItem();
      if (this.autoblock.is("Spoof") || this.autoblock.is("Spoof2")) {
         if (mc.thePlayer.hurtTime > this.blockHurtTime.getValue()) {
            return false;
         }

         if (this.autoblock.is("Spoof2") && this.target != null) {
            return true;
         }
      }

      return this.target != null && this.target.hurtTime != 0 && stack != null && stack.getItem() instanceof ItemSword;
   }

   private void beforeAttackAutoblock(boolean attackTick) {
      int slot = mc.thePlayer.inventory.currentItem;
      String var3 = this.autoblock.getMode();
      byte var4 = -1;
      switch(var3.hashCode()) {
      case -1811896727:
         if (var3.equals("Spoof2")) {
            var4 = 6;
         }
         break;
      case -1248403467:
         if (var3.equals("Hypixel")) {
            var4 = 3;
         }
         break;
      case -45801763:
         if (var3.equals("Hypixel2")) {
            var4 = 4;
         }
         break;
      case 77115:
         if (var3.equals("NCP")) {
            var4 = 1;
         }
         break;
      case 2108921:
         if (var3.equals("Cris")) {
            var4 = 2;
         }
         break;
      case 80099049:
         if (var3.equals("Spoof")) {
            var4 = 5;
         }
         break;
      case 1897755483:
         if (var3.equals("Vanilla")) {
            var4 = 0;
         }
      }

      switch(var4) {
      case 0:
         mc.gameSettings.keyBindUseItem.pressed = true;
         ++this.autoblockTicks;
         break;
      case 1:
         if (this.blocking) {
            PacketUtil.releaseUseItem(true);
            this.blocking = false;
         }
         break;
      case 2:
         if (this.target != null) {
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.target, C02PacketUseEntity.Action.INTERACT));
         }
         break;
      case 3:
         switch(mc.thePlayer.ticksExisted % 2) {
         case 0:
            if (this.currentSlot != mc.thePlayer.inventory.currentItem % 8 + 1) {
               PacketUtil.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem % 8 + 1));
               this.currentSlot = mc.thePlayer.inventory.currentItem % 8 + 1;
            }
         case 1:
            if (this.currentSlot != mc.thePlayer.inventory.currentItem) {
               PacketUtil.sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
               this.currentSlot = mc.thePlayer.inventory.currentItem;
               this.isBlocking = false;
            }
         default:
            ++this.autoblockTicks;
            return;
         }
      case 4:
         if (this.lag) {
            this.blinkinge.set(true);
            int bestSwapSlot = this.getBestSwapSlot();
            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestSwapSlot));
            mc.thePlayer.inventory.currentItem = bestSwapSlot;
            this.swapped = true;
            this.lag = false;
         } else {
            if (this.blinkAutoBlockTicks <= 0) {
               return;
            }

            mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.currentItem;
            this.swapped = false;
            this.attackAndInteract(this.target, false, true);
            this.sendBlock();
            this.releasePackets();
            this.lag = true;
         }

         ++this.autoblockTicks;
         break;
      case 5:
         PacketUtil.sendPacket(new C09PacketHeldItemChange(slot < 8 ? slot + 1 : 0));
         PacketUtil.sendPacket(new C09PacketHeldItemChange(slot));
         if (this.blockTiming.is("Pre")) {
            PacketUtil.sendBlocking(true, false);
            this.blocking = true;
         }
         break;
      case 6:
         if (this.autoblockTicks >= 2) {
            mc.thePlayer.inventory.currentItem = this.lastSlot;
            mc.playerController.syncCurrentPlayItem();
            Flap.instance.getSlotSpoofHandler().stopSpoofing();
            if (this.blinking) {
               Flap.instance.getPacketBlinkHandler().releaseAll();
            }

            this.autoblockTicks = 0;
         }

         if (this.autoblockTicks == 0) {
            if (this.blockTiming.is("Pre")) {
               PacketUtil.sendBlocking(true, false);
               this.blocking = true;
            }
         } else if (this.autoblockTicks == 1) {
            if (!this.velocityModule.isEnabled() || !this.velocityModule.mode.is("Hypixel") || !this.speedModule.isEnabled()) {
               Flap.instance.getPacketBlinkHandler().startBlinkingAll();
               this.blinking = true;
            }

            this.lastSlot = slot;
            Flap.instance.getSlotSpoofHandler().startSpoofing(slot);
            mc.thePlayer.inventory.currentItem = slot < 8 ? slot + 1 : 0;
         }
      }

   }

   private void attackAndInteract(EntityLivingBase target, boolean swingWhileBlocking, boolean sendInteractAt) {
      if (target != null && this.attack && this.aiming) {
         this.attack = false;
         this.aiming = false;
         this.switchTargets = true;
         if(swingWhileBlocking){
            mc.thePlayer.swingItem();
         }else{
            mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
         }

         mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));

         if (sendInteractAt && this.currentRotations != null) {
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.INTERACT_AT));
         }

         mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.INTERACT));
      }

   }

   private void afterAttackAutoblock(boolean attackTick) {
      String var2 = this.autoblock.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 2001010:
         if (var2.equals("AAC5")) {
            var3 = 0;
         }
      default:
         switch(var3) {
         case 0:
            PacketUtil.sendBlocking(true, false);
            this.blocking = true;
         default:
         }
      }
   }

   private void unBlock() {
      if (mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
         mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }
   }

   private void reset() {
      this.target = null;
      this.block.set(false);
      this.startSmoothing = false;
      this.swing = false;
      this.blinkAutoBlockTicks = 0;
      this.rmbDown = false;
      this.currentRotations = null;
      this.attack = false;
      this.aiming = false;
      this.i = 0L;
      this.j = 0L;
      this.resetBlinkState(true);
      this.swapped = false;
   }

   private int getBestSwapSlot() {
      int currentSlot = mc.thePlayer.inventory.currentItem;
      int bestSlot = -1;
      double bestDamage = -1.0D;

      int i;
      ItemStack stack;
      for(i = 0; i < 9; ++i) {
         if (i != currentSlot) {
            stack = mc.thePlayer.inventory.getStackInSlot(i);
            double damage = Utils.getDamage(stack);
            if (damage != 0.0D && damage > bestDamage) {
               bestDamage = damage;
               bestSlot = i;
            }
         }
      }

      if (bestSlot == -1) {
         i = 0;

         while(true) {
            if (i >= 9) {
               return bestSlot;
            }

            if (i != currentSlot) {
               stack = mc.thePlayer.inventory.getStackInSlot(i);
               if (stack == null) {
                  break;
               }

               String var10001 = stack.getUnlocalizedName().toLowerCase();

               for(String s : this.swapBlacklist){
                  if(var10001.contains(s)){
                     break;
                  }
               }

            }

            ++i;
         }

         bestSlot = i;
      }

      return bestSlot;
   }

   public void resetBlinkState(boolean unblock) {
      this.releasePackets();
      this.blocking = false;
      if (mc.thePlayer.inventory.currentItem != mc.thePlayer.inventory.currentItem && this.swapped) {
         mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
         mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.currentItem;
         this.swapped = false;
      }

      if (this.lag && unblock) {
         this.unBlock();
      }

      this.lag = false;
   }

   private void releasePackets() {
      try {
         synchronized(this.blinkedPackets) {
            Iterator var2 = this.blinkedPackets.iterator();

            while(var2.hasNext()) {
               Packet packet = (Packet)var2.next();
               PacketUtil.sendPacketNoEvent(packet);
            }
         }
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      this.blinkedPackets.clear();
      this.blinkinge.set(false);
   }

   private void sendBlock() {
      mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
   }

   private void postAutoblock() {
      String var1 = this.autoblock.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1811896727:
         if (var1.equals("Spoof2")) {
            var2 = 2;
         }
         break;
      case 77115:
         if (var1.equals("NCP")) {
            var2 = 0;
         }
         break;
      case 80099049:
         if (var1.equals("Spoof")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         if (!this.blocking) {
            PacketUtil.sendBlocking(true, false);
            this.blocking = true;
         }
         break;
      case 1:
         if (this.blockTiming.is("Post")) {
            PacketUtil.sendBlocking(true, false);
            this.blocking = true;
         }
         break;
      case 2:
         if (this.blockTiming.is("Post")) {
            PacketUtil.sendBlocking(true, false);
            this.blocking = true;
         }

         ++this.autoblockTicks;
      }

   }

   private boolean autoblockAllowAttack() {
      String var1 = this.autoblock.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1811896727:
         if (var1.equals("Spoof2")) {
            var2 = 1;
         }
         break;
      case 1897755483:
         if (var1.equals("Vanilla")) {
            var2 = 0;
         }
      }

      switch(var2) {
      case 0:
         return this.noHitOnFirstTick.isEnabled() ? this.autoblockTicks > 1 : true;
      case 1:
         return this.autoblockTicks == 2;
      default:
         return true;
      }
   }

   private void releaseBlocking() {
      ItemStack stack = mc.thePlayer.getHeldItem();
      if (this.hadTarget && this.autoblock.is("Blink") && !this.blocking && this.target == null) {
         LogUtil.addChatMessage("Autoblock test : " + Flap.instance.getPacketBlinkHandler().isBlinking());
      }

      int slot = mc.thePlayer.inventory.currentItem;
      if (this.blocking) {
         String var3 = this.autoblock.getMode();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -1811896727:
            if (var3.equals("Spoof2")) {
               var4 = 4;
            }
            break;
         case 77115:
            if (var3.equals("NCP")) {
               var4 = 1;
            }
            break;
         case 2001010:
            if (var3.equals("AAC5")) {
               var4 = 2;
            }
            break;
         case 80099049:
            if (var3.equals("Spoof")) {
               var4 = 3;
            }
            break;
         case 1897755483:
            if (var3.equals("Vanilla")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
         case 1:
         case 2:
            if (stack != null && stack.getItem() instanceof ItemSword) {
               PacketUtil.releaseUseItem(true);
            }
            break;
         case 3:
            PacketUtil.sendPacket(new C09PacketHeldItemChange(slot < 8 ? slot + 1 : 0));
            PacketUtil.sendPacket(new C09PacketHeldItemChange(slot));
            break;
         case 4:
            if (this.autoblockTicks == 1) {
               mc.thePlayer.inventory.currentItem = this.lastSlot < 8 ? this.lastSlot + 1 : 0;
               (new Thread(() -> {
                  try {
                     Thread.sleep(40L);
                  } catch (InterruptedException var2) {
                     var2.printStackTrace();
                  }

                  mc.thePlayer.inventory.currentItem = this.lastSlot;
                  mc.playerController.syncCurrentPlayItem();
               })).start();
            } else {
               mc.thePlayer.inventory.currentItem = this.lastSlot;
            }

            mc.playerController.syncCurrentPlayItem();
            Flap.instance.getSlotSpoofHandler().stopSpoofing();
            if (this.blinking) {
               Flap.instance.getPacketBlinkHandler().stopAll();
               this.blinking = false;
            }
         }

         this.blocking = false;
      }

      if (this.autoblock.is("Blink") && (this.blinking || this.blocking)) {
         Flap.instance.getPacketBlinkHandler().stopAll();
         this.blinking = false;
         this.blocking = false;
         mc.gameSettings.keyBindUseItem.pressed = false;
      }

      this.autoblockTicks = 0;
   }

   @Listener
   public void onItemRender(ItemRenderEvent event) {
      if (this.target != null && !this.autoblock.is("None") && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
         event.setRenderBlocking(true);
      }

   }

   private void getRotations() {
      float yaw = this.fixedRotations.getYaw();
      float pitch = this.fixedRotations.getPitch();
      if (this.target != null) {
         float[] rots = RotationsUtil.getRotationsToEntity(this.target, false);
         if (this.speedModule.isEnabled() && this.speedModule.mode.is("Pathfind")) {
            rots = RotationsUtil.getRotationsToEntity(this.speedModule.getActualX(), this.speedModule.getActualY(), this.speedModule.getActualZ(), this.target, false);
         }

         String var6 = this.rotations.getMode();
         byte var7 = -1;
         switch(var6.hashCode()) {
         case -1955878649:
            if (var6.equals("Normal")) {
               var7 = 0;
            }
            break;
         case -1814666802:
            if (var6.equals("Smooth")) {
               var7 = 1;
            }
         }

         switch(var7) {
         case 0:
            yaw = rots[0];
            pitch = rots[1];
            break;
         case 1:
            float yaw1 = rots[0];
            float currentYaw = MathHelper.wrapAngleTo180_float(yaw);
            float diff = Math.abs(currentYaw - yaw1);
            if (diff >= 8.0F) {
               if (diff > 35.0F) {
                  this.rotSpeed += 4.0D - Math.random();
                  this.rotSpeed = Math.max(this.rotSpeed, (double)((float)(31.0D - Math.random())));
               } else {
                  this.rotSpeed -= 6.5D - Math.random();
                  this.rotSpeed = Math.max(this.rotSpeed, (double)((float)(14.0D - Math.random())));
               }

               if (diff <= 180.0F) {
                  if (currentYaw > yaw1) {
                     yaw = (float)((double)yaw - this.rotSpeed);
                  } else {
                     yaw = (float)((double)yaw + this.rotSpeed);
                  }
               } else if (currentYaw > yaw1) {
                  yaw = (float)((double)yaw + this.rotSpeed);
               } else {
                  yaw = (float)((double)yaw - this.rotSpeed);
               }
            } else if (currentYaw > yaw1) {
               yaw = (float)((double)yaw - (double)diff * 0.8D);
            } else {
               yaw = (float)((double)yaw + (double)diff * 0.8D);
            }

            yaw = (float)((double)yaw + (Math.random() * 0.7D - 0.35D));
            pitch = (float)((double)mc.thePlayer.rotationPitch + (double)(rots[1] - mc.thePlayer.rotationPitch) * 0.6D);
            pitch = (float)((double)pitch + (Math.random() * 0.5D - 0.25D));
            this.done = false;
         }
      } else {
         String var9 = this.rotations.getMode();
         byte var10 = -1;
         switch(var9.hashCode()) {
         case -1955878649:
            if (var9.equals("Normal")) {
               var10 = 1;
            }
            break;
         case -1814666802:
            if (var9.equals("Smooth")) {
               var10 = 0;
            }
         }

         switch(var10) {
         case 0:
            this.rotSpeed = 15.0D;
            if (!this.hadTarget) {
               this.done = true;
            }
            break;
         case 1:
            if (!this.hadTarget) {
               this.done = true;
            }
         }
      }

      this.fixedRotations.updateRotations(yaw, pitch);
   }

   private boolean isRotating() {
      String var1 = this.rotations.getMode();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1955878649:
         if (var1.equals("Normal")) {
            var2 = 0;
         }
         break;
      case -1814666802:
         if (var1.equals("Smooth")) {
            var2 = 1;
         }
         break;
      case 2433880:
         if (var1.equals("None")) {
            var2 = 2;
         }
      }

      switch(var2) {
      case 0:
      case 1:
         return this.target != null || !this.done;
      case 2:
         return false;
      default:
         return false;
      }
   }

   @Listener
   public void onMotion(MotionEvent event) {
      if (this.isRotating()) {
         event.setYaw(this.fixedRotations.getYaw());
         event.setPitch(this.fixedRotations.getPitch());
      }

   }

   @Listener
   public void onPostMotion(PostMotionEvent event) {
      if (this.couldBlock) {
         this.postAutoblock();
      }

   }

   public EntityLivingBase findTarget(boolean allowSame) {
      return this.findTarget(allowSame, this.rotationRange.getValue());
   }

   public EntityLivingBase findTarget(boolean allowSame, double range) {
      ArrayList<EntityLivingBase> entities = new ArrayList();
      Iterator var5 = mc.theWorld.loadedEntityList.iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         if (entity instanceof EntityLivingBase && entity != mc.thePlayer && this.canAttack((EntityLivingBase)entity, range)) {
            entities.add((EntityLivingBase)entity);
         }
      }

      if (entities != null && entities.size() > 0) {
         String var7 = this.filter.getMode();
         byte var8 = -1;
         switch(var7.hashCode()) {
         case -2137395588:
            if (var7.equals("Health")) {
               var8 = 1;
            }
            break;
         case 70829:
            if (var7.equals("Fov")) {
               var8 = 2;
            }
            break;
         case 78727453:
            if (var7.equals("Range")) {
               var8 = 0;
            }
         }

         switch(var8) {
         case 0:
            entities.sort(Comparator.comparingDouble((entityx) -> {
               return (double)entityx.getDistanceToEntity(mc.thePlayer);
            }));
            break;
         case 1:
            entities.sort(Comparator.comparingDouble((entityx) -> {
               return (double)entityx.getHealth();
            }));
            break;
         case 2:
            entities.sort(Comparator.comparingDouble((entity2) -> {
               return RotationsUtil.distanceFromYaw(entity2, false);
            }));
         }

         if (!allowSame && entities.size() > 1 && entities.get(0) == this.target) {
            return (EntityLivingBase)entities.get(1);
         } else {
            return (EntityLivingBase)entities.get(0);
         }
      } else {
         return null;
      }
   }

   public boolean canAttack(EntityLivingBase entity) {
      return this.canAttack(entity, this.rotationRange.getValue());
   }

   public boolean canAttack(EntityLivingBase entity, double range) {
      if (this.getDistanceToEntity(entity) > range) {
         return false;
      } else if ((entity.isInvisible() || entity.isInvisibleToPlayer(mc.thePlayer)) && !((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).invisiblesTarget.isEnabled()) {
         return false;
      } else if (entity instanceof EntityPlayer && (!((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).playerTarget.isEnabled() || !this.teamsModule.canAttack((EntityPlayer)entity))) {
         return false;
      } else if (entity instanceof EntityAnimal && !((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).animalTarget.isEnabled()) {
         return false;
      } else if (entity instanceof EntityMob && !((Targets)Flap.instance.getModuleManager().getModule(Targets.class)).mobsTarget.isEnabled()) {
         return false;
      } else if (!(entity instanceof EntityPlayer) && !(entity instanceof EntityAnimal) && !(entity instanceof EntityMob)) {
         return false;
      } else if (entity.isDead) {
         return false;
      } else {
         return this.antibotModule.canAttack(entity, this);
      }
   }

   public double getDistanceToEntity(EntityLivingBase entity) {
      Vec3 playerVec = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      if (this.speedModule.isEnabled() && this.speedModule.mode.is("Pathfind")) {
         playerVec = new Vec3(this.speedModule.getActualX(), this.speedModule.getActualY() + (double)mc.thePlayer.getEyeHeight(), this.speedModule.getActualZ());
      }

      double yDiff = mc.thePlayer.posY - entity.posY;
      double targetY = yDiff > 0.0D ? entity.posY + (double)entity.getEyeHeight() : (-yDiff < (double)mc.thePlayer.getEyeHeight() ? mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() : entity.posY);
      Vec3 targetVec = new Vec3(entity.posX, targetY, entity.posZ);
      return playerVec.distanceTo(targetVec) - 0.30000001192092896D;
   }

   public double getDistanceCustomPosition(double x, double y, double z, double eyeHeight) {
      Vec3 playerVec = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      double yDiff = mc.thePlayer.posY - y;
      double targetY = yDiff > 0.0D ? y + eyeHeight : (-yDiff < (double)mc.thePlayer.getEyeHeight() ? mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() : y);
      Vec3 targetVec = new Vec3(x, targetY, z);
      return playerVec.distanceTo(targetVec) - 0.30000001192092896D;
   }

   public String getSuffix() {
      return this.mode.getMode();
   }

   public EntityLivingBase getTarget() {
      return this.target;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[]{"target", "vestige/module/impl/combat/Killaura", null};
      switch(var0) {
      case 0:
      default:
         var10001[2] = "renderBox";
         break;
      case 1:
         var10001[2] = "vaperender";
         break;
      case 2:
         var10001[2] = "render";
      }

      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
