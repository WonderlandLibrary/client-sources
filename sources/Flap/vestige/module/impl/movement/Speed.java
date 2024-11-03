package vestige.module.impl.movement;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockSlime;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.EntityActionEvent;
import vestige.event.impl.JumpEvent;
import vestige.event.impl.MotionEvent;
import vestige.event.impl.MoveEvent;
import vestige.event.impl.PacketReceiveEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.client.NotificationManager;
import vestige.module.impl.combat.Killaura;
import vestige.module.impl.world.ScaffoldV2;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.DoubleSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.util.network.PacketUtil;
import vestige.util.player.MovementUtil;
import vestige.util.player.PlayerUtil;
import vestige.util.player.RotationsUtil;
import vestige.util.util.StrafeUtil;
import vestige.util.world.BlockInfo;
import vestige.util.world.BlockUtils;
import vestige.util.world.WorldUtil;

public class Speed extends Module {
   public final ModeSetting mode = new ModeSetting("Mode", "Vanilla", new String[]{"Vanilla", "Vulcan", "Ground", "NCP", "CrisAC A", "CrisAC B", "Blocksmc", "MMC", "Strafe", "Hypixel", "Grim"});
   public final ModeSetting hypixelsubmode = new ModeSetting("Submode", () -> {
      return this.mode.is("Hypixel");
   }, "Strafe", new String[]{"Strafe", "Ground"});
   public final ModeSetting fallmode = new ModeSetting("Fall Mode", () -> {
      return this.mode.is("Hypixel") && this.hypixelsubmode.is("Strafe");
   }, "Agreesive", new String[]{"Agressive", "Semi", "Safe"});
   private final DoubleSetting vanillaSpeed = new DoubleSetting("Vanilla speed", () -> {
      return this.mode.is("Vanilla");
   }, 1.0D, 0.2D, 9.0D, 0.1D);
   private final BooleanSetting autoJump = new BooleanSetting("Autojump", () -> {
      return this.mode.is("Vanilla") || this.mode.is("Strafe");
   }, true);
   private final ModeSetting ncpMode = new ModeSetting("NCP Mode", () -> {
      return this.mode.is("NCP");
   }, "Hop", new String[]{"Hop", "Updated Hop"});
   private final BooleanSetting damageBoost = new BooleanSetting("Damage Boost", () -> {
      return this.mode.is("NCP") && this.ncpMode.is("Updated Hop");
   }, true);
   private final DoubleSetting fastspeedmush = new DoubleSetting("Fast Speed Ticks", () -> {
      return this.mode.is("CrisAC B");
   }, 3.0D, 0.0D, 10.0D, 1.0D);
   private final BooleanSetting fullScaffold = new BooleanSetting("Full scaffold", () -> {
      return this.mode.is("MMC");
   }, false);
   private final BooleanSetting allDirSprint = new BooleanSetting("All directions sprint", () -> {
      return this.mode.is("Strafe");
   }, true);
   private final IntegerSetting minHurtTime = new IntegerSetting("Min hurttime", () -> {
      return this.mode.is("Strafe");
   }, 10, 0, 10, 1);
   private final BooleanSetting flag = new BooleanSetting("Disable When Flag", true);
   private final ModeSetting timerMode = new ModeSetting("Timer mode", () -> {
      return this.mode.is("NCP");
   }, "None", new String[]{"None", "Bypass", "Custom"});
   private final DoubleSetting customTimer = new DoubleSetting("Custom timer", () -> {
      return this.mode.is("NCP") && this.timerMode.is("Custom") || this.mode.is("Watchdog");
   }, 1.0D, 0.1D, 3.0D, 0.05D);
   private double speed;
   private boolean prevOnGround;
   public int counter;
   public int ticks;
   public int offGroundTicks;
   public int ticksSinceVelocity;
   private boolean takingVelocity;
   private boolean wasTakingVelocity;
   private double velocityX;
   private double velocityY;
   private double velocityZ;
   private double velocityDist;
   private float lastDirection;
   private float lastYaw;
   private double motionX;
   private double motionY;
   private double motionZ;
   private double actualX;
   private double actualY;
   private double actualZ;
   private double lastActualX;
   private double lastActualY;
   private double lastActualZ;
   private boolean actualGround;
   private boolean started;
   private boolean firstJumpDone;
   private boolean wasCollided;
   private int oldSlot;
   private final ArrayList<BlockPos> barriers = new ArrayList();
   private float lastForward;
   private float lastStrafe;
   public static final double BASE_JUMP_HEIGHT = 0.41999998688698D;
   int jumpTick = 0;
   boolean wasSlow = false;
   float yaw = 0.0F;
   private double lastAngle = 999.0D;

   public Speed() {
      super("Speed", Category.MOVEMENT);
      this.addSettings(new AbstractSetting[]{this.mode, this.hypixelsubmode, this.fallmode, this.vanillaSpeed, this.autoJump, this.ncpMode, this.damageBoost, this.fullScaffold, this.allDirSprint, this.minHurtTime, this.timerMode, this.customTimer, this.fastspeedmush, this.flag});
   }

   @Listener
   public void onReceivePacket(@NotNull PacketReceiveEvent event) {
      if (event == null) {
         $$$reportNull$$$0(0);
      }

      if (event.getPacket() instanceof S08PacketPlayerPosLook && mc.thePlayer.ticksExisted > 40) {
         NotificationManager.showNotification("Speed", "Speed disabled due Flags", NotificationManager.NotificationType.WARNING, 2L);
         this.toggle();
      }

   }

   public void onEnable() {
      this.prevOnGround = false;
      this.speed = 0.28D;
      this.ticks = this.offGroundTicks = this.counter = 0;
      this.ticksSinceVelocity = Integer.MAX_VALUE;
      this.started = this.firstJumpDone = false;
      this.takingVelocity = this.wasTakingVelocity = false;
      this.motionX = mc.thePlayer.motionX;
      this.motionY = mc.thePlayer.motionY;
      this.motionZ = mc.thePlayer.motionZ;
      this.actualX = mc.thePlayer.posX;
      this.actualY = mc.thePlayer.posY;
      this.actualZ = mc.thePlayer.posZ;
      this.actualGround = mc.thePlayer.onGround;
      this.lastDirection = MovementUtil.getPlayerDirection();
      this.lastYaw = mc.thePlayer.rotationYaw;
      this.lastForward = mc.thePlayer.moveForward;
      this.lastStrafe = mc.thePlayer.moveStrafing;
      this.oldSlot = mc.thePlayer.inventory.currentItem;
      this.wasCollided = false;
   }

   public String getInfo() {
      return this.mode.getMode();
   }

   public boolean onDisable() {
      Timer var10000 = mc.timer;
      var10000.timerSpeed = 1.0F;
      if (this.mode.getMode().equals("Hypixel") && this.fallmode.is("Agressive")) {
         MovementUtil.adjustMotionYForFall();
      }

      if (!this.barriers.isEmpty()) {
         Iterator var1 = this.barriers.iterator();

         while(var1.hasNext()) {
            BlockPos pos = (BlockPos)var1.next();
            mc.theWorld.setBlockToAir(pos);
         }

         this.barriers.clear();
      }

      return false;
   }

   @Listener
   public void onJump(JumpEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1808126673:
         if (var2.equals("Strafe")) {
            var3 = 2;
         }
         break;
      case -1248403467:
         if (var2.equals("Hypixel")) {
            var3 = 1;
         }
         break;
      case 2014647612:
         if (var2.equals("CrisAC A")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         event.setCancelled(true);
         break;
      case 1:
         event.setCancelled(true);
         break;
      case 2:
         if (this.allDirSprint.isEnabled()) {
            event.setBoosting(MovementUtil.isMoving());
            event.setYaw(MovementUtil.getPlayerDirection());
         }
      }

   }

   @Listener
   public void onUpdate(TickEvent event) {
      if (mc.thePlayer.onGround) {
         this.offGroundTicks = 0;
      } else {
         ++this.offGroundTicks;
      }

      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1248403467:
         if (var2.equals("Hypixel")) {
            var3 = 2;
         }
         break;
      case 76451:
         if (var2.equals("MMC")) {
            var3 = 0;
         }
         break;
      case 2228079:
         if (var2.equals("Grim")) {
            var3 = 3;
         }
         break;
      case 2141373863:
         if (var2.equals("Ground")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
         if (!this.started || this.fullScaffold.isEnabled()) {
            break;
         }
      case 1:
         if (!mc.gameSettings.keyBindJump.isPressed() && MovementUtil.isMoving() && mc.currentScreen == null && mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            mc.thePlayer.setSprinting(true);
            double horizontalSpeed = PlayerUtil.getHorizontalSpeed();
            double additionalSpeed = 0.43623D;
            if (horizontalSpeed < additionalSpeed) {
               horizontalSpeed = additionalSpeed;
            }

            setSpeed(horizontalSpeed);
         }
         break;
      case 2:
         String var4 = this.hypixelsubmode.getMode();
         byte var5 = -1;
         switch(var4.hashCode()) {
         case -1808126673:
            if (var4.equals("Strafe")) {
               var5 = 1;
            }
            break;
         case 2141373863:
            if (var4.equals("Ground")) {
               var5 = 0;
            }
         }

         switch(var5) {
         case 0:
            mc.thePlayer.motionY = 0.0D;
            MovementUtil.strafe(0.255D);
            return;
         case 1:
            String var6 = this.fallmode.getMode();
            byte var7 = -1;
            switch(var6.hashCode()) {
            case -1302100705:
               if (var6.equals("Agressive")) {
                  var7 = 0;
               }
               break;
            case 2569133:
               if (var6.equals("Safe")) {
                  var7 = 1;
               }
               break;
            case 2573198:
               if (var6.equals("Semi")) {
                  var7 = 2;
               }
            }

            switch(var7) {
            case 0:
               double[] motions = new double[]{0.399999006D, 0.3536000119D, 0.2681280169D, 0.1843654552D, -0.0807218421D, -0.3175074179D, -0.3145572677D, -0.3866661346D};
               if (this.offGroundTicks >= 8) {
                  break;
               }

               AxisAlignedBB headBoundingBox = new AxisAlignedBB(mc.thePlayer.posX - 0.3D, mc.thePlayer.posY + 1.6D, mc.thePlayer.posZ - 0.3D, mc.thePlayer.posX + 0.3D, mc.thePlayer.posY + 1.8D, mc.thePlayer.posZ + 0.3D);
               boolean isHeadTouchingBlock = !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, headBoundingBox).isEmpty();
               if (!mc.thePlayer.isCollidedHorizontally && !BlockUtils.isFullBlock(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)) && !isHeadTouchingBlock) {
                  if (MovementUtil.isMoving()) {
                     mc.thePlayer.motionY = motions[this.offGroundTicks];
                  } else {
                     EntityPlayerSP var10000 = mc.thePlayer;
                     var10000.motionY -= 0.09166661346D;
                  }
               } else if (this.offGroundTicks == 5 || this.offGroundTicks == 6) {
                  mc.thePlayer.motionY = MovementUtil.predictedMotion(mc.thePlayer.motionY, 1);
               }
               break;
            case 1:
               if (this.offGroundTicks > 4 && this.offGroundTicks < 8) {
                  mc.thePlayer.motionY = MovementUtil.predictedMotion(mc.thePlayer.motionY / 2.0D, 0);
               }
            case 2:
            }

            if (mc.thePlayer.onGround) {
               if (MovementUtil.isMoving()) {
                  this.wasSlow = false;
                  if (this.jumpTick > 6) {
                     this.jumpTick = 5;
                  }

                  mc.thePlayer.jump();
                  MovementUtil.strafe((double)(0.475F + (float)this.jumpTick * 0.007F));
                  if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     float amplifier = (float)mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                     MovementUtil.strafe((double)(0.46F + (float)this.jumpTick * 0.008F + 0.023F * (amplifier + 1.0F)));
                     return;
                  }
               } else {
                  this.jumpTick = 0;
               }

               return;
            } else {
               if (mc.thePlayer.ticksExisted == 1) {
                  MovementUtil.strafe(0.34125F, this.yaw);
                  if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                     MovementUtil.strafe(0.37F, this.yaw);
                  }

                  return;
               }

               Timer var14;
               if (mc.thePlayer.ticksExisted < 13) {
                  if (mc.thePlayer.motionY < 0.0D) {
                     var14 = mc.timer;
                     var14.timerSpeed = 1.0F;
                  } else {
                     var14 = mc.timer;
                     var14.timerSpeed = 0.95F;
                  }
               } else {
                  var14 = mc.timer;
                  var14.timerSpeed = 1.0F;
               }

               if (!PlayerUtil.isOverAir(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.motionY + 1.0D, mc.thePlayer.posZ) && mc.thePlayer.ticksExisted > 2) {
                  MovementUtil.strafe();
               }

               if (this.wasSlow) {
                  if (!StrafeUtil.isConsecutiveStrafing()) {
                     MovementUtil.strafe(0.14000000059604645D);
                  }

                  this.wasSlow = false;
               }

               if (Math.abs(MathHelper.wrapAngleTo180_float(PlayerUtil.getBindsDirection(mc.thePlayer.rotationYaw) - RotationsUtil.getRotations(new Vec3(0.0D, 0.0D, 0.0D), new Vec3(mc.thePlayer.motionX, 0.0D, mc.thePlayer.motionZ))[0])) > 110.0F) {
                  MovementUtil.reduceSpeed(0.8F, true);
                  MovementUtil.strafe();
                  this.wasSlow = true;
               }

               int[] var15 = new int[]{10, 11, 13, 14, 16, 17, 19, 20, 22, 23, 25, 26, 28, 29};
               return;
            }
         default:
            return;
         }
      case 3:
         if (mc.thePlayer.onGround) {
            mc.thePlayer.motionY = 0.15D;
         }
      }

   }

   public static void setSpeed(double n) {
      if (n == 0.0D) {
         mc.thePlayer.motionZ = 0.0D;
         mc.thePlayer.motionX = 0.0D;
      } else {
         float n3 = n();
         mc.thePlayer.motionX = -Math.sin((double)n3) * n;
         mc.thePlayer.motionZ = Math.cos((double)n3) * n;
      }
   }

   public static float n() {
      return ae(mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe);
   }

   public boolean noAction() {
      return !PlayerUtil.nullCheck() || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || ((ScaffoldV2)Flap.instance.getModuleManager().getModule(ScaffoldV2.class)).isEnabled();
   }

   public static float ae(float n, float n2, float n3) {
      float n4 = 1.0F;
      if (n2 < 0.0F) {
         n += 180.0F;
         n4 = -0.5F;
      } else if (n2 > 0.0F) {
         n4 = 0.5F;
      }

      if (n3 > 0.0F) {
         n -= 90.0F * n4;
      } else if (n3 < 0.0F) {
         n += 90.0F * n4;
      }

      return n * 0.017453292F;
   }

   public static float getYaw(Entity ent) {
      double x = ent.posX - mc.thePlayer.posX;
      double z = ent.posZ - mc.thePlayer.posZ;
      double yaw = Math.atan2(x, z) * 57.29577951308232D;
      return (float)(yaw * -1.0D);
   }

   @Listener
   public void onMove(MoveEvent event) {
      if (!this.takingVelocity && mc.thePlayer.onGround) {
         this.wasTakingVelocity = false;
      }

      double velocityExtra = 0.28D + (double)MovementUtil.getSpeedAmplifier() * 0.07D;
      MathHelper.wrapAngleTo180_float(MovementUtil.getPlayerDirection());
      float var10000 = mc.thePlayer.moveForward;
      var10000 = mc.thePlayer.moveStrafing;
      String var7 = this.mode.getMode();
      byte var8 = -1;
      switch(var7.hashCode()) {
      case -1808126673:
         if (var7.equals("Strafe")) {
            var8 = 4;
         }
         break;
      case -1721492669:
         if (var7.equals("Vulcan")) {
            var8 = 1;
         }
         break;
      case -599919172:
         if (var7.equals("Blocksmc")) {
            var8 = 3;
         }
         break;
      case 76451:
         if (var7.equals("MMC")) {
            var8 = 5;
         }
         break;
      case 77115:
         if (var7.equals("NCP")) {
            var8 = 2;
         }
         break;
      case 1897755483:
         if (var7.equals("Vanilla")) {
            var8 = 0;
         }
         break;
      case 2014647612:
         if (var7.equals("CrisAC A")) {
            var8 = 6;
         }
         break;
      case 2014647613:
         if (var7.equals("CrisAC B")) {
            var8 = 7;
         }
      }

      float amplifier;
      Timer var20;
      switch(var8) {
      case 0:
         if (mc.thePlayer.onGround && MovementUtil.isMoving() && this.autoJump.isEnabled()) {
            event.setY(mc.thePlayer.motionY = (double)mc.thePlayer.getJumpUpwardsMotion());
         }

         MovementUtil.strafe(event, this.vanillaSpeed.getValue());
         break;
      case 1:
         if ((!mc.thePlayer.isCollidedHorizontally || !mc.thePlayer.isCollidedVertically) && this.offGroundTicks == 6) {
            mc.thePlayer.motionY = MovementUtil.predictedMotion(mc.thePlayer.motionY, 1);
         }

         if (mc.thePlayer.onGround) {
            if (MovementUtil.isMoving()) {
               this.wasSlow = false;
               if (this.jumpTick > 6) {
                  this.jumpTick = 5;
               }

               MovementUtil.jump(event);
               MovementUtil.strafe((double)(0.475F + (float)this.jumpTick * 0.007F));
               if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                  amplifier = (float)mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
                  MovementUtil.strafe((double)(0.46F + (float)this.jumpTick * 0.008F + 0.023F * (amplifier + 1.0F)));
               }

               this.yaw = RotationsUtil.getDirection();
               mc.thePlayer.motionY = MovementUtil.getJumpHeight();
            } else {
               this.jumpTick = 0;
            }
         } else {
            if (mc.thePlayer.ticksExisted == 1) {
               MovementUtil.strafe(0.34125F, this.yaw);
               if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                  MovementUtil.strafe(0.37F, this.yaw);
               }

               return;
            }

            EntityPlayerSP var21 = mc.thePlayer;
            var21.motionX *= 1.0005D;
            var21 = mc.thePlayer;
            var21.motionZ *= 1.0005D;
            if (mc.thePlayer.ticksExisted < 13) {
               if (mc.thePlayer.motionY < 0.0D) {
                  var20 = mc.timer;
                  var20.timerSpeed = 1.0F;
               } else {
                  var20 = mc.timer;
                  var20.timerSpeed = 0.95F;
               }
            } else {
               var20 = mc.timer;
               var20.timerSpeed = 1.0F;
            }

            if (!PlayerUtil.isOverAir(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.motionY + 1.0D, mc.thePlayer.posZ) && mc.thePlayer.ticksExisted > 2) {
               MovementUtil.strafe();
            }

            if (mc.thePlayer.ticksExisted == 6 && ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget() == null && Math.abs(MathHelper.wrapAngleTo180_float(PlayerUtil.getBindsDirection(mc.thePlayer.rotationYaw) - RotationsUtil.getRotations(new Vec3(0.0D, 0.0D, 0.0D), new Vec3(mc.thePlayer.motionX, 0.0D, mc.thePlayer.motionZ))[0])) > 30.0F) {
               MovementUtil.strafe((double)(PlayerUtil.getSpeed() * 0.9F));
            }

            if (this.wasSlow) {
               MovementUtil.strafe(0.14000000059604645D);
               this.wasSlow = false;
            }

            if (Math.abs(MathHelper.wrapAngleTo180_float(PlayerUtil.getBindsDirection(mc.thePlayer.rotationYaw) - RotationsUtil.getRotations(new Vec3(0.0D, 0.0D, 0.0D), new Vec3(mc.thePlayer.motionX, 0.0D, mc.thePlayer.motionZ))[0])) > 135.0F) {
               PlayerUtil.resetMotion(false);
               this.wasSlow = true;
            }
         }
         break;
      case 2:
         String var17 = this.ncpMode.getMode();
         byte var19 = -1;
         switch(var17.hashCode()) {
         case 72745:
            if (var17.equals("Hop")) {
               var19 = 0;
            }
            break;
         case 1096506148:
            if (var17.equals("Updated Hop")) {
               var19 = 1;
            }
         }

         switch(var19) {
         case 0:
            if (mc.thePlayer.onGround) {
               this.prevOnGround = true;
               if (MovementUtil.isMoving()) {
                  event.setY(mc.thePlayer.motionY = (double)mc.thePlayer.getJumpUpwardsMotion());
                  this.speed *= 0.91D;
                  this.speed += (this.ticks >= 8 ? 0.2D : 0.15D) + (double)mc.thePlayer.getAIMoveSpeed();
                  this.ticks = 0;
               }
            } else if (this.prevOnGround) {
               this.speed *= 0.58D;
               this.speed += 0.026D;
               this.prevOnGround = false;
            } else {
               this.speed *= 0.91D;
               this.speed += 0.026D;
               ++this.ticks;
            }

            if (this.speed > 0.2D) {
               this.speed -= 1.0E-6D;
            }
            break;
         case 1:
            if (mc.thePlayer.onGround) {
               this.prevOnGround = true;
               if (MovementUtil.isMoving()) {
                  MovementUtil.jump(event);
                  this.speed *= 0.91D;
                  if (this.takingVelocity && this.damageBoost.isEnabled()) {
                     this.speed = this.velocityDist + velocityExtra;
                  }

                  this.speed += 0.2D + (double)mc.thePlayer.getAIMoveSpeed();
               }
            } else if (this.prevOnGround) {
               this.speed *= 0.53D;
               if (this.takingVelocity && this.damageBoost.isEnabled()) {
                  this.speed = this.velocityDist + velocityExtra;
               }

               this.speed += 0.026000000536441803D;
               this.prevOnGround = false;
            } else {
               this.speed *= 0.91D;
               if (this.takingVelocity && this.damageBoost.isEnabled()) {
                  this.speed = this.velocityDist + velocityExtra;
               }

               this.speed += 0.026000000536441803D;
            }
         }

         var17 = this.timerMode.getMode();
         var19 = -1;
         switch(var17.hashCode()) {
         case 2433880:
            if (var17.equals("None")) {
               var19 = 0;
            }
            break;
         case 2004703496:
            if (var17.equals("Bypass")) {
               var19 = 1;
            }
            break;
         case 2029746065:
            if (var17.equals("Custom")) {
               var19 = 2;
            }
         }

         switch(var19) {
         case 0:
            var20 = mc.timer;
            var20.timerSpeed = 1.0F;
            break;
         case 1:
            var20 = mc.timer;
            var20.timerSpeed = 1.08F;
            break;
         case 2:
            var20 = mc.timer;
            var20.timerSpeed = (float)this.customTimer.getValue();
         }

         MovementUtil.strafe(event, this.speed);
         break;
      case 3:
         if (mc.thePlayer.onGround) {
            this.prevOnGround = true;
            if (MovementUtil.isMoving()) {
               MovementUtil.jump(event);
               this.speed = 0.57D + (double)MovementUtil.getSpeedAmplifier() * 0.065D;
               if (this.takingVelocity && this.damageBoost.isEnabled()) {
                  this.speed = this.velocityDist + velocityExtra;
               }

               this.ticks = 1;
            }
         } else if (this.prevOnGround) {
            this.speed *= 0.53D;
            if (this.takingVelocity && this.damageBoost.isEnabled()) {
               this.speed = this.velocityDist + velocityExtra;
            }

            this.speed += 0.026000000536441803D;
            this.prevOnGround = false;
         } else {
            this.speed *= 0.91D;
            if (this.takingVelocity && this.damageBoost.isEnabled()) {
               this.speed = this.velocityDist + velocityExtra;
            }

            this.speed += 0.026000000536441803D;
         }

         if (this.takingVelocity) {
            this.ticks = -7;
         }

         if (++this.ticks == 0 && !mc.thePlayer.onGround) {
            this.speed = 0.28D + (double)MovementUtil.getSpeedAmplifier() * 0.065D;
         }

         MovementUtil.strafe(event, this.speed);
         break;
      case 4:
         if (mc.thePlayer.hurtTime <= this.minHurtTime.getValue()) {
            MovementUtil.strafe(event);
         }
         break;
      case 5:
         if (this.started) {
            BlockInfo blockOver = WorldUtil.getBlockInfo(mc.thePlayer.posX, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ, 2);
            BlockInfo blockUnder = WorldUtil.getBlockUnder(mc.thePlayer.posY, 2);
            ++this.counter;
            if (this.fullScaffold.isEnabled()) {
               if (this.counter % 14 >= 12) {
                  MovementUtil.strafe(event, 0.04D);
               } else {
                  MovementUtil.strafe(event, 0.495D);
               }

               event.setY(mc.thePlayer.motionY = 0.0D);
               if (this.counter % 2 == 0) {
                  if (blockOver != null && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockOver.getPos(), blockOver.getFacing(), WorldUtil.getVec3(blockOver.getPos(), blockOver.getFacing(), false))) {
                     PacketUtil.sendPacket(new C0APacketAnimation());
                  }
               } else if (blockUnder != null && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockUnder.getPos(), blockUnder.getFacing(), WorldUtil.getVec3(blockUnder.getPos(), blockUnder.getFacing(), false))) {
                  PacketUtil.sendPacket(new C0APacketAnimation());
               }
            } else if (blockOver != null && mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockOver.getPos(), blockOver.getFacing(), WorldUtil.getVec3(blockOver.getPos(), blockOver.getFacing(), false))) {
               PacketUtil.sendPacket(new C0APacketAnimation());
            }
         } else {
            amplifier = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
            double x = 0.0D;
            double z = 0.0D;
            EnumFacing facing = EnumFacing.UP;
            if (!(amplifier > 135.0F) && !(amplifier < -135.0F)) {
               if (amplifier > -135.0F && amplifier < -45.0F) {
                  x = -1.0D;
                  facing = EnumFacing.EAST;
               } else if (amplifier > -45.0F && amplifier < 45.0F) {
                  z = -1.0D;
                  facing = EnumFacing.SOUTH;
               } else if (amplifier > 45.0F && amplifier < 135.0F) {
                  x = 1.0D;
                  facing = EnumFacing.WEST;
               }
            } else {
               z = 1.0D;
               facing = EnumFacing.NORTH;
            }

            BlockPos pos;
            switch(++this.counter) {
            case 1:
               pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ + z);
               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, EnumFacing.UP, WorldUtil.getVec3(pos, EnumFacing.DOWN, true));
               break;
            case 2:
               pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z);
               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, EnumFacing.UP, WorldUtil.getVec3(pos, EnumFacing.DOWN, true));
               break;
            case 3:
               pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + 1.0D, mc.thePlayer.posZ + z);
               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, EnumFacing.UP, WorldUtil.getVec3(pos, EnumFacing.DOWN, true));
            case 4:
            default:
               break;
            case 5:
               pos = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + 2.0D, mc.thePlayer.posZ + z);
               mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), pos, facing, WorldUtil.getVec3(pos, facing, true));
               this.started = true;
               this.counter = 0;
            }

            PacketUtil.sendPacket(new C0APacketAnimation());
            MovementUtil.strafe(event, 0.04D);
         }
         break;
      case 6:
         if (mc.thePlayer.onGround) {
            MovementUtil.strafe(event, 0.69D);
         } else {
            MovementUtil.strafe(event, 0.44D);
         }
         break;
      case 7:
         if (!MovementUtil.isMoving()) {
            return;
         }

         if (this.offGroundTicks == 5) {
            mc.thePlayer.motionY = MovementUtil.predictedMotion(mc.thePlayer.motionY, (int)this.fastspeedmush.getValue());
         }

         if (mc.thePlayer.onGround) {
            MovementUtil.jump(event);
         }

         MovementUtil.strafe(event, 0.45D);
      }

   }

   @Listener
   public void onEntityAction(EntityActionEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 2603186:
         if (var2.equals("Test")) {
            var3 = 0;
         }
         break;
      case 80698816:
         if (var2.equals("Test2")) {
            var3 = 1;
         }
      }

      switch(var3) {
      case 0:
      case 1:
         event.setSprinting(MovementUtil.isMoving());
      default:
      }
   }

   @Listener
   public void onMotion(MotionEvent event) {
      String var2 = this.mode.getMode();
      byte var3 = -1;
      switch(var2.hashCode()) {
      case 76451:
         if (var2.equals("MMC")) {
            var3 = 0;
         }
      }

      switch(var3) {
      case 0:
         event.setYaw(event.getYaw() - 180.0F);
         if (this.started) {
            event.setPitch(this.counter % 2 != 0 && this.fullScaffold.isEnabled() ? 82.0F : -82.0F);
         }
      default:
         this.takingVelocity = false;
         ++this.ticksSinceVelocity;
      }
   }

   @Listener
   public void onReceive(PacketReceiveEvent event) {
      if (event.getPacket() instanceof S12PacketEntityVelocity) {
         S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
         if (mc.thePlayer.getEntityId() == packet.getEntityID()) {
            this.takingVelocity = this.wasTakingVelocity = true;
            this.velocityX = (double)packet.getMotionX() / 8000.0D;
            this.velocityY = (double)packet.getMotionY() / 8000.0D;
            this.velocityZ = (double)packet.getMotionZ() / 8000.0D;
            this.velocityDist = Math.hypot(this.velocityX, this.velocityZ);
            this.ticksSinceVelocity = 0;
         }
      } else if (event.getPacket() instanceof S08PacketPlayerPosLook) {
      }

   }

   private float getFriction(double x, double y, double z) {
      Block block = mc.theWorld.getBlockState(new BlockPos(x, Math.floor(y) - 1.0D, z)).getBlock();
      if (block != null) {
         if (block instanceof BlockIce || block instanceof BlockPackedIce) {
            return 0.98F;
         }

         if (block instanceof BlockSlime) {
            return 0.8F;
         }
      }

      return 0.6F;
   }

   public double getActualX() {
      return this.actualX;
   }

   public double getActualY() {
      return this.actualY;
   }

   public double getActualZ() {
      return this.actualZ;
   }

   public double getLastActualX() {
      return this.lastActualX;
   }

   public double getLastActualY() {
      return this.lastActualY;
   }

   public double getLastActualZ() {
      return this.lastActualZ;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "event", "vestige/module/impl/movement/Speed", "onReceivePacket"));
   }
}
