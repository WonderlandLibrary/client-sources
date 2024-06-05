package zamorozka.ui;

import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import optifine.Reflector;
import optifine.ReflectorMethod;
import zamorozka.main.Zamorozka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RayCastUtil {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static RayTraceResult getMouseOver(float yaw, float pitch, float range) {
        float partialTicks = 1.0f;
        Entity var2 = mc.getRenderViewEntity();
        if (var2 != null && RayCastUtil.mc.world != null) {
            double var3 = RayCastUtil.mc.playerController.getBlockReachDistance();
            RayTraceResult objectMouseOver = var2.rayTrace(var3, partialTicks);
            double var5 = var3;
            Vec3d var7 = var2.getPositionEyes(partialTicks);
            var3 = range;
            var5 = range;
            if (objectMouseOver != null) {
                var5 = objectMouseOver.hitVec.distanceTo(var7);
            }
            Vec3d var8 = var2.getLook(partialTicks);
            Vec3d var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
            Entity pointedEntity = null;
            Vec3d var10 = null;
            float var11 = 1.0f;
            List var12 = RayCastUtil.mc.world.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand((double)var11, (double)var11, (double)var11));
            double var13 = var5;
            for (int var15 = 0; var15 < var12.size(); ++var15) {
                double var20;
                Entity var16 = (Entity)var12.get(var15);
                if (!var16.canBeCollidedWith()) continue;
                float var17 = var16.getCollisionBorderSize();
                AxisAlignedBB var18 = var16.getEntityBoundingBox().expand((double)var17, (double)var17, (double)var17);
                RayTraceResult var19 = var18.calculateIntercept(var7, var9);
                if (var18.isVecInside(var7)) {
                    if (!(0.0 < var13) && var13 != 0.0) continue;
                    pointedEntity = var16;
                    var10 = var19 == null ? var7 : var19.hitVec;
                    var13 = 0.0;
                    continue;
                }
                if (var19 == null || !((var20 = var7.distanceTo(var19.hitVec)) < var13) && var13 != 0.0) continue;
                boolean canRiderInteract = false;
                if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                    canRiderInteract = Reflector.callBoolean((Object)var16, (ReflectorMethod)Reflector.ForgeEntity_canRiderInteract, (Object[])new Object[0]);
                }
                if (var16 == var2.ridingEntity && !canRiderInteract) {
                    if (var13 != 0.0) continue;
                    pointedEntity = var16;
                    var10 = var19.hitVec;
                    continue;
                }
                pointedEntity = var16;
                var10 = var19.hitVec;
                var13 = var20;
            }
            if (pointedEntity != null && (var13 < var5 || RayCastUtil.mc.objectMouseOver == null)) {
                objectMouseOver = new RayTraceResult(pointedEntity, var10);
                if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                    return objectMouseOver;
                }
            }
            return objectMouseOver;
        }
        return null;
    }
    
	public static Entity raycast(Entity entiy) {
		Entity var2 = mc.player;
		Vec3d var9 = entiy.getPositionVector().add(new Vec3d(0, entiy.getEyeHeight(), 0));
		Vec3d var7 = mc.player.getPositionVector().add(new Vec3d(0, mc.player.getEyeHeight(), 0));
		Vec3d var10 = null;
		float var11 = 1.0F;
		AxisAlignedBB a = mc.player.getEntityBoundingBox()
				.addCoord(var9.xCoord - var7.xCoord, var9.yCoord - var7.yCoord, var9.zCoord - var7.zCoord)
				.expand(var11, var11, var11);
		List var12 = mc.world.getEntitiesWithinAABBExcludingEntity(var2, a);
		double var13 = Zamorozka.settingsManager.getSettingByName("AttackRange").getValDouble() + 0.45f;
		Entity b = null;
		for (int var15 = 0; var15 < var12.size(); ++var15) {
			Entity var16 = (Entity) var12.get(var15);

			if (var16.canBeCollidedWith()) {
				float var17 = var16.getCollisionBorderSize();
				AxisAlignedBB var18 = var16.getEntityBoundingBox().expand((double) var17, (double) var17,
						(double) var17);
				RayTraceResult var19 = var18.calculateIntercept(var7, var9);

				if (var18.isVecInside(var7)) {
					if (0.0D < var13 || var13 == 0.0D) {
						b = var16;
						var10 = var19 == null ? var7 : var19.hitVec;
						var13 = 0.0D;
					}
				} else if (var19 != null) {
					double var20 = var7.distanceTo(var19.hitVec);

					if (var20 < var13 || var13 == 0.0D) {
						b = var16;
						var10 = var19.hitVec;
						var13 = var20;
					}
				}
			}
		}
		return b;
	}
    
    public static Collection<Entity> raycastEntities(final double range) {
        final Collection<Entity> entities = new ArrayList<Entity>();
        final Entity renderViewEntity = mc.getRenderViewEntity();
        if (renderViewEntity != null && mc.world != null) {
            final Vec3d eyePosition = renderViewEntity.getPositionEyes(1.0f);
            final float yaw = RotationUtility.lastLook[0];
            final float pitch = RotationUtility.lastLook[1];
            final float yawCos = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
            final float yawSin = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
            final float pitchCos = -MathHelper.cos(-pitch * 0.017453292f);
            final float pitchSin = MathHelper.sin(-pitch * 0.017453292f);
            final Vec3d entityLook = new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
            final Vec3d vector = eyePosition.addVector(entityLook.xCoord * range, entityLook.yCoord * range, entityLook.zCoord * range);
            final List<Entity> entityList = (List<Entity>)mc.world.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * range, entityLook.yCoord * range, entityLook.zCoord * range).expand(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
            for (final Entity entity : entityList) {
                final float collisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand((double)collisionBorderSize, (double)collisionBorderSize, (double)collisionBorderSize);
                final RayTraceResult movingObjectPosition = axisalignedbb.calculateIntercept(eyePosition, vector);
                if (axisalignedbb.isVecInside(eyePosition)) {
                    if (range < 0.0) {
                        continue;
                    }
                    entities.add(entity);
                }
                else {
                    if (movingObjectPosition == null) {
                        continue;
                    }
                    final double d3 = eyePosition.distanceTo(movingObjectPosition.hitVec);
                    if (d3 >= range && range != 0.0) {
                        continue;
                    }
                    if (entity == renderViewEntity.ridingEntity) {
                        if (range != 0.0) {
                            continue;
                        }
                        entities.add(entity);
                    }
                    else {
                        entities.add(entity);
                    }
                }
            }
        }
        return entities;
    }
    
    public static Entity rayCast(double range, float yaw, float pitch) {
        double d0 = range;
        double d1 = d0;
        Vec3d vec3 = mc.player.getPositionEyes(1.0f);
        boolean flag = false;
        boolean flag1 = true;

        if (d0 > 3.0D)
        {
            flag = true;
        }

        /*if (this.mc.objectMouseOver != null)
        {
            d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
        }*/

        Vec3d vec31 = getVectorForRotation(pitch, yaw);
        Vec3d vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);

        Entity pointedEntity = null;

        Vec3d vec33 = null;
        float f = 1.0F;
        List list = mc.world.getEntitiesInAABBexcluding(mc.getRenderViewEntity(), mc.getRenderViewEntity().getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

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

                    if (entity1 == mc.getRenderViewEntity().ridingEntity && !flag2)
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

        return pointedEntity;
    }

    private static Entity raycastEntity(final double range, final float yaw, final float pitch, final IEntityFilter entityFilter) {
        final Entity renderViewEntity = mc.getRenderViewEntity();

        if(renderViewEntity != null && mc.world != null) {
            double blockReachDistance = range;
            final Vec3d eyePosition = renderViewEntity.getPositionEyes(1F);

            final float yawCos = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
            final float yawSin = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
            final float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
            final float pitchSin = MathHelper.sin(-pitch * 0.017453292F);

            final Vec3d entityLook = new Vec3d(yawSin * pitchCos, pitchSin, yawCos * pitchCos);
            final Vec3d vector = eyePosition.addVector(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance);
            final List<Entity> entityList = mc.world.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance).expand(1D, 1D, 1D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity :: canBeCollidedWith));

            Entity pointedEntity = null;

            for(final Entity entity : entityList) {
                if(!entityFilter.canRaycast(entity))
                    continue;

                final float collisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
                final RayTraceResult movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);

                if(axisAlignedBB.isVecInside(eyePosition)) {
                    if(blockReachDistance >= 0.0D) {
                        pointedEntity = entity;
                        blockReachDistance = 0.0D;
                    }
                }else if(movingObjectPosition != null) {
                    final double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);

                    if(eyeDistance < blockReachDistance || blockReachDistance == 0.0D) {
                        if(entity == renderViewEntity.ridingEntity) {//&& !renderViewEntity.canRiderInteract()
                            if(blockReachDistance == 0.0D)
                                pointedEntity = entity;
                        }else{
                            pointedEntity = entity;
                            blockReachDistance = eyeDistance;
                        }
                    }
                }
            }

            return pointedEntity;
        }

        return null;
    }

    public interface IEntityFilter {
        boolean canRaycast(final Entity entity);
    }

    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }

    @SuppressWarnings("null")
    public static Entity raycastEntity(final double range, final IEntityFilter entityFilter) {
        CPacketPlayer serverRotation = null;
        return raycastEntity(range, serverRotation.getYaw(0), serverRotation.getPitch(0), entityFilter);
    }
    
}