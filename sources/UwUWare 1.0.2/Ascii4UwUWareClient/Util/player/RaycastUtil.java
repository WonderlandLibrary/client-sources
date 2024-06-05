package Ascii4UwUWareClient.Util.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;

public class RaycastUtil {


    private static MovingObjectPosition tracePath(final World world, final float x, final float y, final float z, final float tx, final float ty, final float tz, final float borderSize, final HashSet<Entity> excluded) {
        Vec3 startVec = new Vec3(x, y, z);
        Vec3 endVec = new Vec3(tx, ty, tz);
        final float minX = (x < tx) ? x : tx;
        final float minY = (y < ty) ? y : ty;
        final float minZ = (z < tz) ? z : tz;
        final float maxX = (x > tx) ? x : tx;
        final float maxY = (y > ty) ? y : ty;
        final float maxZ = (z > tz) ? z : tz;
        final AxisAlignedBB bb = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ).expand(borderSize, borderSize, borderSize);
        final ArrayList<Entity> allEntities = (ArrayList<Entity>) world.getEntitiesWithinAABBExcludingEntity(null, bb);
        MovingObjectPosition blockHit = world.rayTraceBlocks(startVec, endVec);
        startVec = new Vec3(x, y, z);
        endVec = new Vec3(tx, ty, tz);
        Entity closestHitEntity = null;
        float closestHit = Float.POSITIVE_INFINITY;
        float currentHit;
        for (final Entity ent : allEntities) {
            if (ent.canBeCollidedWith() && !excluded.contains(ent)) {
                final float entBorder = ent.getCollisionBorderSize();
                AxisAlignedBB entityBb = ent.getEntityBoundingBox();
                if (entityBb == null) {
                    continue;
                }
                entityBb = entityBb.expand(entBorder, entBorder, entBorder);
                final MovingObjectPosition intercept = entityBb.calculateIntercept(startVec, endVec);
                if (intercept == null) {
                    continue;
                }
                currentHit = (float) intercept.hitVec.distanceTo(startVec);
                if (currentHit >= closestHit && currentHit != 0.0f) {
                    continue;
                }
                closestHit = currentHit;
                closestHitEntity = ent;
            }
        }
        if (closestHitEntity != null) {
            blockHit = new MovingObjectPosition(closestHitEntity);
        }
        return blockHit;
    }

    private static MovingObjectPosition tracePathD(final World w, final double posX, final double posY, final double posZ, final double v, final double v1, final double v2, final float borderSize, final HashSet<Entity> exclude) {
        return tracePath(w, (float) posX, (float) posY, (float) posZ, (float) v, (float) v1, (float) v2, borderSize, exclude);
    }

    public static MovingObjectPosition rayCast(final EntityPlayerSP player, final double x, final double y, final double z) {
        final HashSet<Entity> excluded = new HashSet<>();
        excluded.add(player);
        return tracePathD(player.worldObj, player.posX, player.posY + player.getEyeHeight(), player.posZ, x, y, z, 1.0f, excluded);
    }
}
