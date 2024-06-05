package koks.utilities;

import com.google.common.base.Predicates;
import koks.Koks;
import koks.event.impl.MouseOverEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import optifine.Reflector;

import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 15:23
 */
public class RayCastUtil {

    private final Minecraft mc = Minecraft.getMinecraft();

    public boolean isRayCastBlock(BlockPos blockPos, float yaw, float pitch) {
        float range = mc.playerController.getBlockReachDistance();

        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        MovingObjectPosition ray = mc.theWorld.rayTraceBlocks(vec3,vec32,false);
            return ray.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && blockPos.equals(ray.getBlockPos());
    }

    public MovingObjectPosition getRayCastBlock(float yaw, float pitch) {
        float range = mc.playerController.getBlockReachDistance();

        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        MovingObjectPosition ray = mc.theWorld.rayTraceBlocks(vec3,vec32,false);
        if(ray.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            return ray;
        }
        return null;

    }

    Entity pointedEntity;

    public Entity getMouseOver(double range, float yaw, float pitch)
    {
        Entity entity = mc.getRenderViewEntity();

        if (entity != null && mc.theWorld != null)
        {
            mc.pointedEntity = null;

            Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);

            if (mc.objectMouseOver != null)
            {
                range = mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
            float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
            float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
            float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

            Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
            pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = range;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = list.get(i);
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
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
                            flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
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

            if (pointedEntity != null && vec3.distanceTo(vec33) > range)
            {
                pointedEntity = null;
                mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, null, new BlockPos(vec33));
            }

            if (pointedEntity != null && (d2 < range || mc.objectMouseOver == null))
            {
                mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                {
                    mc.pointedEntity = pointedEntity;
                }
            }

            mc.mcProfiler.endSection();
        }

        return pointedEntity;
    }

/*    public Entity getMouseOver(double range, float yaw, float pitch)
    {
        Entity entity = mc.getRenderViewEntity();

        if (entity != null && mc.theWorld != null)
        {
            mc.pointedEntity = null;

            double d0 = mc.playerController.getBlockReachDistance();
            double d1 = d0;
            Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
            boolean flag = false;
            boolean flag1 = true;

            if (mc.playerController.extendedReach())
            {
                d0 = 6.0D;
                d1 = 6.0D;
            }
            else
            {
                if (d0 > 3.0D)
                {
                    flag = true;
                }

                d0 = d0;
            }

            if (mc.objectMouseOver != null)
            {
                d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
            }

            float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
            float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
            float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
            float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

            Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
            Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
            pointedEntity = null;
            Vec3 vec33 = null;
            float f = 1.0F;
            List list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            double d2 = d1;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);
                float f1 = entity1.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
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
                mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, vec33, (EnumFacing)null, new BlockPos(vec33));
            }

            if (pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null))
            {
                mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                {
                    mc.pointedEntity = pointedEntity;
                }
            }

            mc.mcProfiler.endSection();
        }

        return pointedEntity;
    }*/

    public Entity getRayCastedEntity(double range, float yaw, float pitch) {
        float cosYaw = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float sinYaw = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float cosPitch = -MathHelper.cos(-pitch * 0.017453292F);
        float sinPitch = MathHelper.sin(-pitch * 0.017453292F);

        Vec3 vec3 = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 vec31 = new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);

        Entity pointedEntity = null;

        float f = 1.0F;
        List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d2 = range;

        for (int i = 0; i < list.size(); ++i) {
            Entity entity1 = list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

            if (axisalignedbb.isVecInside(vec3)) {
                if (d2 >= 0.0D) {
                    pointedEntity = entity1;
                    d2 = 0.0D;
                }
            } else if (movingobjectposition != null) {
                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                if (d3 < d2 || d2 == 0.0D) {
                    boolean flag2 = false;

                    if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                        flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract);
                    }

                    if (entity1 == mc.getRenderViewEntity().ridingEntity && !flag2) {
                        if (d2 == 0.0D) {
                            pointedEntity = entity1;
                        }
                    } else {
                        pointedEntity = entity1;
                        d2 = d3;
                    }
                }
            }
        }
        return pointedEntity;
    }
}
