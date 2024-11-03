package vestige.util.player;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.optifine.reflect.Reflector;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import vestige.util.IMinecraft;
import vestige.util.util.Utils;
import vestige.util.world.BlockUtils;
import vestige.util.world.WorldUtil;

public class RotationsUtil implements IMinecraft {
   public static float renderPitch;
   public static float prevRenderPitch;
   public static float renderYaw;
   public static float prevRenderYaw;
   private static final Set<EnumFacing> FACINGS;

   public static float[] getRotationsToPosition(double x, double y, double z) {
      double deltaX = x - mc.thePlayer.posX;
      double playerY = mc.thePlayer.posY;
      double chestY = playerY + 2.0D;
      double deltaY = y - chestY;
      double deltaZ = z - mc.thePlayer.posZ;
      double horizontalDistance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
      float yaw = (float)Math.toDegrees(-Math.atan2(deltaX, deltaZ));
      float pitch = (float)Math.toDegrees(-Math.atan2(deltaY, horizontalDistance));
      return new float[]{yaw, pitch};
   }

   public static double distanceFromYaw(Entity entity, boolean usePlayerRotationYaw) {
      double deltaX = entity.posX - mc.thePlayer.posX;
      double deltaZ = entity.posZ - mc.thePlayer.posZ;
      double entityYaw = Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0D;
      double playerYaw = usePlayerRotationYaw ? (double)mc.thePlayer.rotationYaw : 0.0D;
      double yawDifference = MathHelper.wrapAngleTo180_double(entityYaw - playerYaw);
      return Math.abs(yawDifference);
   }

   public static float i(double n, double n2) {
      return (float)(Math.atan2(n - mc.thePlayer.posX, n2 - mc.thePlayer.posZ) * 57.295780181884766D * -1.0D);
   }

   public static boolean inRange(BlockPos blockPos, double n) {
      float[] array = getRotations(blockPos);
      Vec3 getPositionEyes = mc.thePlayer.getPositionEyes(1.0F);
      float n2 = -array[0] * 0.017453292F;
      float n3 = -array[1] * 0.017453292F;
      float cos = MathHelper.cos(n2 - 3.1415927F);
      float sin = MathHelper.sin(n2 - 3.1415927F);
      float n4 = -MathHelper.cos(n3);
      Vec3 vec3 = new Vec3((double)(sin * n4), (double)MathHelper.sin(n3), (double)(cos * n4));
      Block block = BlockUtils.getBlock(blockPos);
      IBlockState blockState = BlockUtils.getBlockState(blockPos);
      if (block != null && blockState != null) {
         AxisAlignedBB boundingBox = block.getCollisionBoundingBox(mc.theWorld, blockPos, blockState);
         if (boundingBox != null) {
            Vec3 targetVec = getPositionEyes.addVector(vec3.xCoord * n, vec3.yCoord * n, vec3.zCoord * n);
            MovingObjectPosition intercept = boundingBox.calculateIntercept(getPositionEyes, targetVec);
            if (intercept != null) {
               return true;
            }
         }
      }

      return false;
   }

   public static void setRenderYaw(float yaw) {
      mc.thePlayer.rotationYawHead = yaw;
   }

   public static Vec3 getEyePos(@NotNull Entity entity, @NotNull Vec3 position) {


      return position.add(new Vec3(0.0D, (double)entity.getEyeHeight(), 0.0D));
   }

   public static Vec3 getEyePos(Entity entity) {
      return getEyePos(entity, new Vec3(entity.getPosition()));
   }

   public static Vec3 getEyePos() {
      return getEyePos(mc.thePlayer);
   }

   public static float getYaw(@NotNull BlockPos pos) {

      return getYaw(new Vec3((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D));
   }

   public static float getYaw(@NotNull AbstractClientPlayer from, @NotNull Vec3 pos) {


      return from.rotationYaw + MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(pos.zCoord - from.posZ, pos.xCoord - from.posX)) - 90.0F - from.rotationYaw);
   }

   @Contract(
      pure = true
   )
   @NotNull
   public static Optional<Triple<BlockPos, EnumFacing, Vec3>> getPlaceSide(@NotNull BlockPos blockPos) {


      return getPlaceSide(blockPos, FACINGS);
   }

   @Contract(
      pure = true
   )
   @NotNull
   public static Optional<Triple<BlockPos, EnumFacing, Vec3>> getPlaceSide(@NotNull BlockPos blockPos, Set<EnumFacing> limitFacing) {


      List<BlockPos> possible = Arrays.asList(blockPos.down(), blockPos.east(), blockPos.west(), blockPos.north(), blockPos.south(), blockPos.up());
      Iterator var3 = possible.iterator();

      Optional var10000;
      while(var3.hasNext()) {
         BlockPos pos = (BlockPos)var3.next();
         if (!BlockUtils.replaceable(pos)) {
            EnumFacing facing;
            Vec3 hitPos;
            if (pos.getY() < blockPos.getY()) {
               facing = EnumFacing.UP;
               hitPos = new Vec3((double)pos.getX() + 0.5D, (double)(pos.getY() + 1), (double)pos.getZ() + 0.5D);
            } else if (pos.getX() > blockPos.getX()) {
               facing = EnumFacing.WEST;
               hitPos = new Vec3((double)pos.getX(), (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
            } else if (pos.getX() < blockPos.getX()) {
               facing = EnumFacing.EAST;
               hitPos = new Vec3((double)(pos.getX() + 1), (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
            } else if (pos.getZ() < blockPos.getZ()) {
               facing = EnumFacing.SOUTH;
               hitPos = new Vec3((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)(pos.getZ() + 1));
            } else if (pos.getZ() > blockPos.getZ()) {
               facing = EnumFacing.NORTH;
               hitPos = new Vec3((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ());
            } else {
               facing = EnumFacing.DOWN;
               hitPos = new Vec3((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D);
            }

            if (limitFacing.contains(facing)) {
               var10000 = Optional.of(Triple.of(pos, facing, hitPos));

               return var10000;
            }
         }
      }

      var10000 = Optional.empty();
      return var10000;
   }

   public static float getYaw(@NotNull Vec3 pos) {


      return getYaw(mc.thePlayer, pos);
   }

   public static float getPitch(@NotNull BlockPos pos) {


      return getPitch(new Vec3((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D));
   }

   public static float getPitch(@NotNull AbstractClientPlayer from, @NotNull Vec3 pos) {

      double diffX = pos.xCoord - from.posX;
      double diffY = pos.yCoord - (from.posY + (double)from.getEyeHeight());
      double diffZ = pos.zCoord - from.posZ;
      double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
      return from.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - from.rotationPitch);
   }

   public static float getPitch(@NotNull Vec3 pos) {

      return getPitch(mc.thePlayer, pos);
   }

   public static float normalize(float yaw) {
      yaw %= 360.0F;
      if (yaw >= 180.0F) {
         yaw -= 360.0F;
      }

      if (yaw < -180.0F) {
         yaw += 360.0F;
      }

      return yaw;
   }

   @NotNull
   public static Vec3 getNearestPoint(@NotNull AxisAlignedBB from, @NotNull Vec3 to) {
      double pointX;
      if (to.xCoord >= from.maxX) {
         pointX = from.maxX;
      } else {
         pointX = Math.max(to.xCoord, from.minX);
      }

      double pointY;
      if (to.yCoord >= from.maxY) {
         pointY = from.maxY;
      } else {
         pointY = Math.max(to.yCoord, from.minY);
      }

      double pointZ;
      if (to.zCoord >= from.maxZ) {
         pointZ = from.maxZ;
      } else {
         pointZ = Math.max(to.zCoord, from.minZ);
      }

      return new Vec3(pointX, pointY, pointZ);
   }

   public static float angle(double n, double n2) {
      return (float)(Math.atan2(n - mc.thePlayer.posX, n2 - mc.thePlayer.posZ) * 57.295780181884766D * -1.0D);
   }

   public static MovingObjectPosition rayCast(Vec3 from, double distance, float yaw, float pitch) {
      float n4 = -yaw * 0.017453292F;
      float n5 = -pitch * 0.017453292F;
      float cos = MathHelper.cos(n4 - 3.1415927F);
      float sin = MathHelper.sin(n4 - 3.1415927F);
      float n6 = -MathHelper.cos(n5);
      Vec3 vec3 = new Vec3((double)(sin * n6), (double)MathHelper.sin(n5), (double)(cos * n6));
      return mc.theWorld.rayTraceBlocks(from, from.addVector(vec3.xCoord * distance, vec3.yCoord * distance, vec3.zCoord * distance), false, false, false);
   }

   public static MovingObjectPosition rayCast(double distance, float yaw, float pitch) {
      Vec3 getPositionEyes = mc.thePlayer.getPositionEyes(1.0F);
      return rayCast(getPositionEyes, distance, yaw, pitch);
   }

   public static float[] getRotations(Vec3 start, Vec3 dst) {
      double xDif = dst.xCoord - start.xCoord;
      double yDif = dst.yCoord - start.yCoord;
      double zDif = dst.zCoord - start.zCoord;
      double distXZ = Math.sqrt(xDif * xDif + zDif * zDif);
      return new float[]{(float)(Math.atan2(zDif, xDif) * 180.0D / 3.141592653589793D) - 90.0F, (float)(-(Math.atan2(yDif, distXZ) * 180.0D / 3.141592653589793D))};
   }

   public static float[] getRotations(Entity entity) {
      return getRotations(entity.posX, entity.posY, entity.posZ);
   }

   public static float[] getRotations(Vec3 vec) {
      return getRotations(vec.xCoord, vec.yCoord, vec.zCoord);
   }

   public static float[] getRotations(double x, double y, double z) {
      Vec3 lookVec = mc.thePlayer.getPositionEyes(1.0F);
      double dx = lookVec.xCoord - x;
      double dy = lookVec.yCoord - y;
      double dz = lookVec.zCoord - z;
      double dist = Math.hypot(dx, dz);
      double yaw = Math.toDegrees(Math.atan2(dz, dx));
      double pitch = Math.toDegrees(Math.atan2(dy, dist));
      return new float[]{(float)yaw + 90.0F, (float)pitch};
   }

   public static float[] getRotations(BlockPos blockPos, float n, float n2) {
      float[] array = getRotations(blockPos);
      return Utils.fixRotation(array[0], array[1], n, n2);
   }

   public static float[] getRotations(BlockPos blockPos) {
      double n = (double)blockPos.getX() + 0.45D - mc.thePlayer.posX;
      double n2 = (double)blockPos.getY() + 0.45D - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
      double n3 = (double)blockPos.getZ() + 0.45D - mc.thePlayer.posZ;
      return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(Math.atan2(n3, n) * 57.295780181884766D) - 90.0F - mc.thePlayer.rotationYaw), clampTo90(mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(n2, (double)MathHelper.sqrt_double(n * n + n3 * n3)) * 57.295780181884766D)) - mc.thePlayer.rotationPitch))};
   }

   public static float clampTo90(float n) {
      return MathHelper.clamp_float(n, -90.0F, 90.0F);
   }

   public static float getDirection() {
      return PlayerUtil.getBindsDirection(mc.thePlayer.rotationYaw);
   }

   public static float getDirection(float rotationYaw, float moveForward, float moveStrafing) {
      if (moveForward == 0.0F && moveStrafing == 0.0F) {
         return rotationYaw;
      } else {
         boolean reversed = moveForward < 0.0F;
         double strafingYaw = 90.0D * (moveForward > 0.0F ? 0.5D : (reversed ? -0.5D : 1.0D));
         if (reversed) {
            rotationYaw += 180.0F;
         }

         if (moveStrafing > 0.0F) {
            rotationYaw = (float)((double)rotationYaw - strafingYaw);
         } else if (moveStrafing < 0.0F) {
            rotationYaw = (float)((double)rotationYaw + strafingYaw);
         }

         return rotationYaw;
      }
   }

   public static double getRotationDifference(Entity e) {
      float[] entityRotation = getRotations(e.posX, e.posY, e.posZ);
      return getRotationDifference(entityRotation);
   }

   public static double getRotationDifference(Vec3 e) {
      float[] entityRotation = getRotations(e.xCoord, e.yCoord, e.zCoord);
      return getRotationDifference(entityRotation);
   }

   public static double getRotationDifference(double x, double y, double z) {
      float[] entityRotation = getRotations(x, y, z);
      return getRotationDifference(entityRotation);
   }

   public static double getRotationDifference(float[] e) {
      double yawDif = MathHelper.wrapAngleTo180_double((double)(mc.thePlayer.rotationYaw - e[0]));
      double pitchDif = MathHelper.wrapAngleTo180_double((double)(mc.thePlayer.rotationPitch - e[1]));
      return Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
   }

   public static float[] getRotationsToPosition(double x, double y, double z, double targetX, double targetY, double targetZ) {
      double dx = targetX - x;
      double dy = targetY - y;
      double dz = targetZ - z;
      double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
      float yaw = (float)Math.toDegrees(-Math.atan2(dx, dz));
      float pitch = (float)Math.toDegrees(-Math.atan2(dy, horizontalDistance));
      return new float[]{yaw, pitch};
   }

   public static float[] getRotationsToEntity(EntityLivingBase entity, boolean usePartialTicks) {
      float partialTicks = mc.timer.renderPartialTicks;
      double entityX = usePartialTicks ? entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks : entity.posX;
      double entityY = usePartialTicks ? entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks : entity.posY;
      double entityZ = usePartialTicks ? entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks : entity.posZ;
      double yDiff = mc.thePlayer.posY - entityY;
      double finalEntityY = yDiff >= 0.0D ? entityY + (double)entity.getEyeHeight() : (-yDiff < (double)mc.thePlayer.getEyeHeight() ? mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() : entityY);
      return getRotationsToPosition(entityX, finalEntityY, entityZ);
   }

   public static float[] getRotationsToEntity(double x, double y, double z, EntityLivingBase entity, boolean usePartialTicks) {
      float partialTicks = mc.timer.renderPartialTicks;
      double entityX = usePartialTicks ? entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks : entity.posX;
      double entityY = usePartialTicks ? entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks : entity.posY;
      double entityZ = usePartialTicks ? entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks : entity.posZ;
      double yDiff = mc.thePlayer.posY - entityY;
      double finalEntityY = yDiff >= 0.0D ? entityY + (double)entity.getEyeHeight() : (-yDiff < (double)mc.thePlayer.getEyeHeight() ? y + (double)mc.thePlayer.getEyeHeight() : entityY);
      return getRotationsToPosition(x, y + (double)mc.thePlayer.getEyeHeight(), z, entityX, finalEntityY, entityZ);
   }

   public static float[] getRotationsToEntityRandomised(EntityLivingBase entity, boolean usePartialTicks, double randomAmount) {
      float partialTicks = mc.timer.renderPartialTicks;
      double entityX = usePartialTicks ? entity.lastTickPosX + (entity.posX + (Math.random() * randomAmount - randomAmount * 0.5D) - entity.lastTickPosX) * (double)partialTicks : entity.posX + (Math.random() * randomAmount - randomAmount * 0.5D);
      double entityY = usePartialTicks ? entity.lastTickPosY + (entity.posY + (Math.random() * randomAmount - randomAmount * 0.5D) - entity.lastTickPosY) * (double)partialTicks : entity.posY + (Math.random() * randomAmount - randomAmount * 0.9D);
      double entityZ = usePartialTicks ? entity.lastTickPosZ + (entity.posZ + (Math.random() * randomAmount - randomAmount * 0.5D) - entity.lastTickPosZ) * (double)partialTicks : entity.posZ + (Math.random() * randomAmount - randomAmount * 0.5D);
      double yDiff = mc.thePlayer.posY - entityY;
      double finalEntityY = yDiff >= 0.0D ? entityY + (double)entity.getEyeHeight() : (-yDiff < (double)mc.thePlayer.getEyeHeight() ? mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() : entityY);
      return getRotationsToPosition(entityX, finalEntityY, entityZ);
   }

   public static boolean raycastEntity(EntityLivingBase target, float yaw, float pitch, float lastYaw, float lastPitch, double reach) {
      Entity entity = mc.getRenderViewEntity();
      Entity pointedEntity = null;
      if (entity != null && mc.theWorld != null) {
         float partialTicks = mc.timer.renderPartialTicks;
         double d0 = (double)mc.playerController.getBlockReachDistance();
         mc.objectMouseOver = WorldUtil.raytraceLegit(yaw, pitch, lastYaw, lastPitch);
         double d1 = d0;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         boolean var10000 = true;
         if (mc.playerController.extendedReach()) {
            d0 = 6.0D;
            d1 = 6.0D;
         } else if (d0 > reach) {
            flag = true;
         }

         if (mc.objectMouseOver != null) {
            d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
         }

         float aaaa = lastPitch + (pitch - lastPitch) * partialTicks;
         float bbbb = lastYaw + (yaw - lastYaw) * partialTicks;
         Vec3 vec31 = mc.thePlayer.getVectorForRotation(aaaa, bbbb);
         Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
         pointedEntity = null;
         Vec3 vec33 = null;
         float f = 1.0F;
         List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
            public boolean apply(Entity p_apply_1_) {
               return p_apply_1_.canBeCollidedWith();
            }
         }));
         double d2 = d1;

         for(int j = 0; j < list.size(); ++j) {
            Entity entity1 = (Entity)list.get(j);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
            if (axisalignedbb.isVecInside(vec3)) {
               if (d2 >= 0.0D) {
                  pointedEntity = entity1;
                  vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                  d2 = 0.0D;
               }
            } else if (movingobjectposition != null) {
               double d3 = vec3.distanceTo(movingobjectposition.hitVec);
               if (d3 < d2 || d2 == 0.0D) {
                  boolean flag1 = false;
                  if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                     flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                  }

                  if (!flag1 && entity1 == entity.ridingEntity) {
                     if (d2 == 0.0D) {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition.hitVec;
                     }
                  } else {
                     pointedEntity = entity1;
                     vec33 = movingobjectposition.hitVec;
                     d2 = d3;
                  }
               }
            }
         }

         if (pointedEntity != null && flag && vec3.distanceTo(vec33) > reach) {
            pointedEntity = null;
            mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, (EnumFacing)null, new BlockPos(vec33));
         }

         if (pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null)) {
            mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
         }
      }

      return pointedEntity != null && pointedEntity == target;
   }

   public static float getGCD() {
      return (float)(Math.pow((double)mc.gameSettings.mouseSensitivity * 0.6D + 0.2D, 3.0D) * 1.2D);
   }

   static {
      FACINGS = new HashSet(Arrays.asList(EnumFacing.VALUES));
   }
}
