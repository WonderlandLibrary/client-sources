package vestige.module.impl.combat;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.RenderEvent;
import vestige.event.impl.UpdateEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.player.AimSimulator;
import vestige.util.player.PlayerUtil;
import vestige.util.player.RotationsUtil;
public class AimAssistV2 extends Module {
   private final BooleanSetting clickAim = new BooleanSetting("Click aim", true);
   private final BooleanSetting aimWhileOnTarget = new BooleanSetting("Aim while on target", true);
   private final BooleanSetting strafeIncrease = new BooleanSetting("Strafe increase", false);
   private final BooleanSetting checkBlockBreak = new BooleanSetting("Check block break", true);
   private final BooleanSetting aimVertically = new BooleanSetting("Aim vertically", false);
   private final IntegerSetting verticalSpeed = new IntegerSetting("Vertical speed", 5, 1, 20, 1);
   private final IntegerSetting horizontalSpeed = new IntegerSetting("Horizontal speed", 5, 1, 20, 1);
   private final IntegerSetting maxAngle = new IntegerSetting("Max angle", 180, 1, 360, 5);
   private final IntegerSetting distance = new IntegerSetting("Distance", 5, 1, 8, 1);
   private final BooleanSetting weaponOnly = new BooleanSetting("Weapon only", false);
   private final BooleanSetting ignoreTeammates = new BooleanSetting("Ignore teammates", false);
   private final BooleanSetting throughBlock = new BooleanSetting("Through block", true);
   private Double yawNoise = null;
   private Double pitchNoise = null;
   private long nextNoiseRefreshTime = -1L;
   private long nextNoiseEmptyTime = 200L;

   public AimAssistV2() {
      super("AimAssist (D USE, BETA)", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.clickAim, this.aimWhileOnTarget, this.strafeIncrease, this.checkBlockBreak, this.aimVertically, this.verticalSpeed, this.horizontalSpeed, this.maxAngle, this.distance, this.weaponOnly, this.ignoreTeammates, this.throughBlock});
   }

   public boolean onDisable() {
      this.yawNoise = this.pitchNoise = null;
      this.nextNoiseRefreshTime = -1L;
      return false;
   }

   @Listener
   public void onUpdate(UpdateEvent event) {
      if (!this.noAction()) {
         EntityPlayer target = this.getEnemy();
         if (target != null) {
            boolean onTarget = mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit == target;
            double deltaYaw = this.yawNoise != null ? this.yawNoise : 0.0D;
            double deltaPitch = this.pitchNoise != null ? this.pitchNoise : 0.0D;
            double hSpeed = (double)this.horizontalSpeed.getValue();
            double vSpeed = (double)this.verticalSpeed.getValue();
            if (onTarget) {
               if (this.aimWhileOnTarget.isEnabled()) {
                  hSpeed *= 0.85D;
                  vSpeed *= 0.85D;
               } else {
                  hSpeed = 0.0D;
                  vSpeed = 0.0D;
               }
            }

            if (this.strafeIncrease.isEnabled()) {
               int mouseX = Math.abs(mc.mouseHelper.deltaX);
               int mouseY = Math.abs(mc.mouseHelper.deltaY);
               if (mouseX > 100) {
                  hSpeed = 0.0D;
               } else {
                  hSpeed = Math.min(hSpeed, (double)(100 - mouseX) / 35.0D);
               }

               if (mouseY > 100) {
                  vSpeed = 0.0D;
               } else {
                  vSpeed = Math.min(vSpeed, (double)(100 - mouseY) / 35.0D);
               }
            }

            Pair<Pair<Float, Float>, Pair<Float, Float>> rotation = this.getRotation(target.getEntityBoundingBox());
            Pair<Float, Float> yaw = (Pair)rotation.getA();
            Pair<Float, Float> pitch = (Pair)rotation.getB();
            boolean move = false;
            float curYaw = mc.thePlayer.rotationYaw;
            float curPitch = mc.thePlayer.rotationPitch;
            float after;
            if ((Float)yaw.getA() > curYaw) {
               move = true;
               after = AimSimulator.rotMove((double)(Float)yaw.getA(), (double)curYaw, (double)((float)hSpeed));
               deltaYaw += (double)(after - curYaw);
            } else if ((Float)yaw.getB() < curYaw) {
               move = true;
               after = AimSimulator.rotMove((double)(Float)yaw.getB(), (double)curYaw, (double)((float)hSpeed));
               deltaYaw += (double)(after - curYaw);
            }

            if (this.aimVertically.isEnabled()) {
               if ((Float)pitch.getA() > curPitch) {
                  move = true;
                  after = AimSimulator.rotMove((double)(Float)pitch.getA(), (double)curPitch, (double)((float)vSpeed));
                  deltaPitch += (double)(after - curPitch);
               } else if ((Float)pitch.getB() < curPitch) {
                  move = true;
                  after = AimSimulator.rotMove((double)(Float)pitch.getB(), (double)curPitch, (double)((float)vSpeed));
                  deltaPitch += (double)(after - curPitch);
               }
            }

            if (move) {
               deltaYaw += (Math.random() - 0.5D) * Math.min(0.8D, deltaPitch / 10.0D);
               deltaPitch += (Math.random() - 0.5D) * Math.min(0.8D, deltaYaw / 10.0D);
            }

            EntityPlayerSP var10000 = mc.thePlayer;
            var10000.rotationYaw += (float)deltaYaw;
            var10000 = mc.thePlayer;
            var10000.rotationPitch += (float)deltaPitch;
         }
      }
   }

   @NotNull
   private Pair<Pair<Float, Float>, Pair<Float, Float>> getRotation(@NotNull AxisAlignedBB boundingBox) {
      if (boundingBox == null) {
         $$$reportNull$$$0(0);
      }

      AxisAlignedBB expandedBox = boundingBox.expand(-0.05D, -0.9D, -0.1D).offset(0.0D, 0.405D, 0.0D);
      Vec3 minVec = new Vec3(expandedBox.minX, expandedBox.minY, expandedBox.minZ);
      Vec3 maxVec = new Vec3(expandedBox.maxX, expandedBox.maxY, expandedBox.maxZ);
      float yaw1 = RotationsUtil.getYaw(minVec);
      float yaw2 = RotationsUtil.getYaw(maxVec);
      Vec3 centerVec = new Vec3((expandedBox.minX + expandedBox.maxX) / 2.0D, (expandedBox.minY + expandedBox.maxY) / 2.0D, (expandedBox.minZ + expandedBox.maxZ) / 2.0D);
      float pitch1 = RotationsUtil.getPitch(centerVec);
      float pitch2 = RotationsUtil.getPitch(centerVec);
      return new Pair(this.sortYaw(yaw1, yaw2), new Pair(Math.min(pitch1, pitch2), Math.max(pitch1, pitch2)));
   }

   @NotNull
   private Pair<Float, Float> sortYaw(float yaw1, float yaw2) {
      float fixedYaw1 = fixYaw(yaw1);
      float fixedYaw2 = fixYaw(yaw2);
      return fixedYaw1 < fixedYaw2 ? new Pair(yaw1, yaw2) : new Pair(yaw2, yaw1);
   }

   private static float fixYaw(float yaw) {
      while(yaw < 0.0F) {
         yaw += 360.0F;
      }

      while(yaw > 360.0F) {
         yaw -= 360.0F;
      }

      return yaw;
   }

   @Listener
   public void onRender(RenderEvent event) {
      long time = System.currentTimeMillis();
      if (this.nextNoiseRefreshTime != -1L && time < this.nextNoiseRefreshTime + this.nextNoiseEmptyTime) {
         if (time >= this.nextNoiseRefreshTime) {
            this.yawNoise = 0.0D;
            this.pitchNoise = 0.0D;
         }
      } else {
         this.nextNoiseRefreshTime = (long)((double)time + Math.random() * 60.0D + 80.0D);
         this.nextNoiseEmptyTime = (long)(Math.random() * 100.0D + 180.0D);
         this.yawNoise = (Math.random() - 0.5D) * 2.0D * ((Math.random() - 0.5D) * 0.3D + 0.8D);
         this.pitchNoise = (Math.random() - 0.5D) * 2.0D * ((Math.random() - 0.5D) * 0.35D + 0.6D);
      }

   }

   private boolean noAction() {
      if (mc.currentScreen == null && mc.inGameHasFocus) {
         if (this.weaponOnly.isEnabled() && !PlayerUtil.holdingWeapon()) {
            return true;
         } else if (this.yawNoise != null && this.pitchNoise != null) {
            return this.clickAim.isEnabled() && !PlayerUtil.isLeftClicking() ? true : mc.playerController.isHittingBlock;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   @Nullable
   private EntityPlayer getEnemy() {
      int fov = this.maxAngle.getValue();
      List<EntityPlayer> players = mc.theWorld.playerEntities;
      Vec3 playerPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
      EntityPlayer target = null;
      double targetFov = Double.MAX_VALUE;
      Iterator var7 = players.iterator();

      while(true) {
         EntityPlayer entityPlayer;
         double dist;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           do {
                              if (!var7.hasNext()) {
                                 return target;
                              }

                              entityPlayer = (EntityPlayer)var7.next();
                           } while(entityPlayer == mc.thePlayer);
                        } while(entityPlayer.deathTime != 0);

                        dist = playerPos.distanceTo(new Vec3(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ));
                     } while(((Antibot)Flap.instance.getModuleManager().getModule(Antibot.class)).isBot(entityPlayer));
                  } while(this.ignoreTeammates.isEnabled() && ((Teams)Flap.instance.getModuleManager().getModule(Teams.class)).canAttack(entityPlayer));
               } while(dist > (double)this.distance.getValue());
            } while(fov != 360 && !PlayerUtil.inFov((float)fov, (Entity)entityPlayer));
         } while(!this.throughBlock.isEnabled() && RotationsUtil.rayCast(dist, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch) != null);

         double curFov = Math.abs(PlayerUtil.getFov(entityPlayer.posX, entityPlayer.posZ));
         if (curFov < targetFov) {
            target = entityPlayer;
            targetFov = curFov;
         }
      }
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "boundingBox", "vestige/module/impl/combat/AimAssistV2", "getRotation"));
   }
}
