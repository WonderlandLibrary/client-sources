package win.sightclient.utils;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import optifine.Reflector;

public class RotationUtils {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public static float[] getRotations(final Entity target) {
	    final double var4 = target.posX - mc.thePlayer.posX;
        final double var5 = target.posZ - mc.thePlayer.posZ;
        double var7;
        if (target instanceof EntityLivingBase) {
            final EntityLivingBase var6 = (EntityLivingBase)target;
            var7 = var6.posY + var6.getEyeHeight() - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        else {
            var7 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0 - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        }
        final double var8 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
        final float var9 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        final float var10 = (float)(-(Math.atan2(var7 - ((target instanceof EntityPlayer) ? 0.25 : 0.0), var8) * 180.0 / 3.141592653589793));
        float pitch = changeRotation(mc.thePlayer.rotationPitch, var10);
        float yaw = changeRotation(mc.thePlayer.rotationYaw, var9);
        return new float[] { yaw, pitch };
	}
	
	public static float changeRotation(final float p_706631, final float p_706632) {
        float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
        if (var4 > 1000F) {
            var4 = 1000F;
        }
        if (var4 < -1000F) {
            var4 = -1000F;
        }
        return p_706631 + var4;
	}
	
	public static float[] faceBlock(final BlockPos target) {
	    EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)mc.theWorld, mc.thePlayer.getGameProfile());
	    entityOtherPlayerMP.setPositionAndRotation(target.getX() + 0.5, target.getY() - 1, target.getZ() + 0.5, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
	    entityOtherPlayerMP.rotationYawHead = mc.thePlayer.rotationYawHead;
	    entityOtherPlayerMP.setSneaking(mc.thePlayer.isSneaking());
	    float rots[] = getRotations(entityOtherPlayerMP);
        return new float[] { rots[0], rots[1] };
	}

	public static float roundTo360(float value) {
		return value % 360.0F;
	}

    public static float getYawChange(float yaw, double posX, double posZ) {
        double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0;
        if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
        	if(deltaX != 0)
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
        	if(deltaX != 0)
            yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
        } else {
        	if(deltaZ != 0)
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }

        return MathHelper.wrapAngleTo180_float(-(yaw- (float) yawToEntity));
    }

	public static float getYawToTarget(Entity target) {
	    final double var4 = target.posX - mc.thePlayer.posX;
        final double var5 = target.posZ - mc.thePlayer.posZ;
        final float var9 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return changeRotation(mc.thePlayer.rotationYaw, var9);
	}
	
    public static Entity getMouseOver(float partialTicks, Entity entity) {
        Entity pointedEntity = null;
        MovingObjectPosition objectMouseOver = null;
        Entity mcpointedentity = null;
        if (entity != null && mc.theWorld != null)
        {
            mcpointedentity = null;
            double d0 = (double)mc.playerController.getBlockReachDistance();
            objectMouseOver = entity.rayTrace(d0, partialTicks);
            double d1 = d0;
            Vec3 vec3 = entity.getPositionEyes(partialTicks);
            boolean flag = false;
            boolean flag1 = true;
            d0 = 6.0D;
            d1 = 6.0D;

            if (objectMouseOver != null)
            {
                d1 = objectMouseOver.hitVec.distanceTo(vec3);
            }

            Vec3 vec31 = entity.getLook(partialTicks);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING));
            double d2 = d1;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                if (axisalignedbb.isVecInside(vec3))
                {
                    if (d2 >= 0.0D)
                    {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                }
                else if (movingobjectposition != null)
                {
                    double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D)
                    {
                        boolean flag2 = false;

                        if (Reflector.ForgeEntity_canRiderInteract.exists())
                        {
                            flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                        }

                        if (entity1 == entity.ridingEntity && !flag2)
                        {
                            if (d2 == 0.0D)
                            {
                                pointedEntity = entity1;
                                vec33 = movingobjectposition.hitVec;
                            }
                        }
                        else
                        {
                            pointedEntity = entity1;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }

            if (pointedEntity != null && flag && vec3.distanceTo(vec33) > 3.0D)
            {
                pointedEntity = null;
                objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, (EnumFacing)null, new BlockPos(vec33));
            }

            if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
            {
                objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                {
                    mcpointedentity = pointedEntity;
                }
            }
        }
        return mcpointedentity;
    }
}
