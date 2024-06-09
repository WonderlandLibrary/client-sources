package com.client.glowclient.utils;

import net.minecraft.entity.monster.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.client.entity.*;
import com.client.glowclient.*;

public class EntityUtils
{
    public static boolean b;
    
    public static boolean j(final Entity entity) {
        final Entity ridingEntity = Wrapper.mc.player.getRidingEntity();
        return entity.ticksExisted > 1 && !D(entity) && (ridingEntity == null || !ridingEntity.equals((Object)entity));
    }
    
    public static Vec3d A(final Entity entity, final float n) {
        final Vec3d d = D(entity, n);
        final double n2 = entity.getEyeHeight();
        final double n3 = 0.0;
        return d.add(n3, n2, n3);
    }
    
    public static boolean i(final Entity entity) {
        return entity instanceof EntityLivingBase;
    }
    
    public static boolean l(final Entity entity) {
        try {
            if (entity instanceof EntityPigZombie) {
                if (((EntityPigZombie)entity).isArmsRaised() || ((EntityPigZombie)entity).isAngry()) {
                    if (!((EntityPigZombie)entity).isAngry()) {
                        ((EntityPigZombie)entity).angerLevel = 400;
                    }
                    return true;
                }
            }
            else {
                if (entity instanceof EntityWolf) {
                    return ((EntityWolf)entity).isAngry() && !Wrapper.mc.player.equals((Object)((EntityWolf)entity).getOwner());
                }
                if (entity instanceof EntityEnderman) {
                    return ((EntityEnderman)entity).isScreaming();
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static Vec3d M(final Entity entity, final double n, final double n2, final double n3) {
        return new Vec3d((entity.posX - entity.lastTickPosX) * n, (entity.posY - entity.lastTickPosY) * n2, (entity.posZ - entity.lastTickPosZ) * n3);
    }
    
    public static boolean K(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.CREATURE, false) && !k(entity)) || (entity.isCreatureType(EnumCreatureType.AMBIENT, false) && !EntityUtils.b) || entity instanceof EntityVillager || entity instanceof EntityIronGolem || (k(entity) && !l(entity));
    }
    
    public static boolean M(final Entity entity, final boolean b) {
        if (entity == null) {
            return false;
        }
        final double n = entity.posY - (b ? 0.03 : (e(entity) ? 0.2 : 0.5));
        int floor;
        int i = floor = MathHelper.floor(entity.posX);
        while (i < MathHelper.ceil(entity.posX)) {
            int floor2;
            int j = floor2 = MathHelper.floor(entity.posZ);
            while (j < MathHelper.ceil(entity.posZ)) {
                if (Wrapper.mc.world.getBlockState(new BlockPos(floor, MathHelper.floor(n), floor2)).getBlock() instanceof BlockLiquid) {
                    return true;
                }
                j = ++floor2;
            }
            i = ++floor;
        }
        return false;
    }
    
    public static Vec3d M(final Entity entity, final double n) {
        return M(entity, n, n, n);
    }
    
    public static Vec3d D(final Entity entity) {
        return new Vec3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
    }
    
    public static boolean m(final Entity entity) {
        return Wrapper.mc.player != null && entity != null && entity == Ob.M();
    }
    
    public static RayTraceResult M(final World world, final Vec3d vec3d, final Vec3d vec3d2, final List<Entity> list) {
        RayTraceResult rayTraceResult = null;
        double n = -1.0;
        final Iterator<Entity> iterator = world.loadedEntityList.iterator();
    Label_0019:
        while (true) {
            Iterator<Entity> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final Entity entityHit = iterator.next();
                if (list.contains(entityHit)) {
                    iterator2 = iterator;
                }
                else {
                    final double distanceTo = vec3d.distanceTo(entityHit.getPositionVector());
                    final RayTraceResult calculateIntercept;
                    if ((calculateIntercept = entityHit.getEntityBoundingBox().calculateIntercept(vec3d, vec3d2)) != null && (n == -1.0 || distanceTo < n)) {
                        n = distanceTo;
                        (rayTraceResult = calculateIntercept).entityHit = entityHit;
                        continue Label_0019;
                    }
                    continue Label_0019;
                }
            }
            break;
        }
        return rayTraceResult;
    }
    
    public static Vec3d D(final Entity entity, final float n) {
        return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(M(entity, (double)n));
    }
    
    public static boolean a(final Entity entity) {
        return i(entity) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0f;
    }
    
    static {
        EntityUtils.b = false;
    }
    
    public static boolean d(final Entity entity) {
        return (!(entity instanceof EntityWolf) || !((EntityWolf)entity).isAngry()) && (entity instanceof EntityAnimal || entity instanceof EntityAgeable || entity instanceof EntityTameable || entity instanceof EntityAmbientCreature || entity instanceof EntitySquid || (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).getRevengeTarget() == null));
    }
    
    public static Vec3d M(final Entity entity) {
        final AxisAlignedBB entityBoundingBox = entity.getEntityBoundingBox();
        return new Vec3d((entityBoundingBox.maxX + entityBoundingBox.minX) / 2.0, (entityBoundingBox.maxY + entityBoundingBox.minY) / 2.0, (entityBoundingBox.maxZ + entityBoundingBox.minZ) / 2.0);
    }
    
    public static boolean B(final Entity entity) {
        return M(entity, false);
    }
    
    public static Vec3d M(final Entity entity, final Vec3d vec3d) {
        return M(entity, vec3d.x, vec3d.y, vec3d.z);
    }
    
    public static double[] M(final double n, double asin, double atan2, final EntityPlayer entityPlayer) {
        final double n2 = entityPlayer.posX - n;
        asin = entityPlayer.posY - asin;
        atan2 = entityPlayer.posZ - atan2;
        final double n3 = n2;
        final double n4 = n3 * n3;
        final double n5 = asin;
        final double n6 = n4 + n5 * n5;
        final double n7 = atan2;
        final double sqrt = Math.sqrt(n6 + n7 * n7);
        final double n8 = n2 / sqrt;
        asin /= sqrt;
        atan2 /= sqrt;
        asin = Math.asin(asin);
        atan2 = Math.atan2(atan2, n8);
        asin = asin * 180.0 / 3.141592653589793;
        atan2 = (atan2 = atan2 * 180.0 / 3.141592653589793) + 90.0;
        return new double[] { atan2, asin };
    }
    
    public static Vec3d M(final Entity entity, final float n) {
        try {
            return D(entity, n).subtract(Wrapper.mc.getRenderManager().renderPosX, Wrapper.mc.getRenderManager().renderPosY, Wrapper.mc.getRenderManager().renderPosZ);
        }
        catch (Exception ex) {
            final double n2 = 1.0;
            return new Vec3d(n2, n2, n2);
        }
    }
    
    public static boolean E(final Entity entity) {
        return Objects.equals(Wrapper.mc.player, entity);
    }
    
    public static V M(final Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            return V.G;
        }
        final Iterator<N> iterator = s.M().iterator();
        while (iterator.hasNext()) {
            final N n;
            if ((n = iterator.next()).M(entity)) {
                return n.D(entity);
            }
        }
        if (s.B.M(entity)) {
            return V.B;
        }
        if (s.b.M(entity)) {
            return V.d;
        }
        return V.B;
    }
    
    public static boolean e(final Entity entity) {
        return entity instanceof EntityPlayer;
    }
    
    public static boolean k(final Entity entity) {
        return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
    }
    
    public static boolean A(final Entity entity) {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !k(entity)) || l(entity);
    }
    
    public static boolean D(final Entity entity) {
        return entity != null && entity.getEntityId() == -100;
    }
    
    public EntityUtils() {
        super();
    }
    
    public static boolean M(final Entity entity) {
        if (entity == null) {
            return false;
        }
        final double n = entity.posY + 0.01;
        int floor;
        int i = floor = MathHelper.floor(entity.posX);
        while (i < MathHelper.ceil(entity.posX)) {
            int floor2;
            int j = floor2 = MathHelper.floor(entity.posZ);
            while (j < MathHelper.ceil(entity.posZ)) {
                if (Wrapper.mc.world.getBlockState(new BlockPos(floor, (int)n, floor2)).getBlock() instanceof BlockLiquid) {
                    return true;
                }
                j = ++floor2;
            }
            i = ++floor;
        }
        return false;
    }
}
