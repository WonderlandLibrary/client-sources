package ClassSub;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;

public class Class0
{
    private static Minecraft mc;
    
    
    public static Entity getEntity(final double n) {
        return (getEntity(n, 0.0, 0.0f) == null) ? null : ((Entity)getEntity(n, 0.0, 0.0f)[0]);
    }
    
    public static Object[] getEntity(final double n, final double n2, final float n3) {
        final Entity getRenderViewEntity = Class0.mc.getRenderViewEntity();
        Object o = null;
        if (getRenderViewEntity == null || Class0.mc.theWorld == null) {
            return null;
        }
        Class0.mc.mcProfiler.startSection("pick");
        final Vec3 getPositionEyes = getRenderViewEntity.getPositionEyes(0.0f);
        final Vec3 getLook = getRenderViewEntity.getLook(0.0f);
        final Vec3 addVector = getPositionEyes.addVector(getLook.xCoord * n, getLook.yCoord * n, getLook.zCoord * n);
        Object o2 = null;
        final float n4 = 1.0f;
        final List getEntitiesWithinAABBExcludingEntity = Class0.mc.theWorld.getEntitiesWithinAABBExcludingEntity(getRenderViewEntity, getRenderViewEntity.getEntityBoundingBox().addCoord(getLook.xCoord * n, getLook.yCoord * n, getLook.zCoord * n).expand((double)n4, (double)n4, (double)n4));
        double n5 = n;
        for (int i = 0; i < getEntitiesWithinAABBExcludingEntity.size(); ++i) {
            final Entity entity = getEntitiesWithinAABBExcludingEntity.get(i);
            if (entity.canBeCollidedWith()) {
                final float getCollisionBorderSize = entity.getCollisionBorderSize();
                final AxisAlignedBB expand = entity.getEntityBoundingBox().expand((double)getCollisionBorderSize, (double)getCollisionBorderSize, (double)getCollisionBorderSize).expand(n2, n2, n2);
                final MovingObjectPosition calculateIntercept = expand.calculateIntercept(getPositionEyes, addVector);
                if (expand.isVecInside(getPositionEyes)) {
                    if (0.0 < n5 || n5 == 0.0) {
                        o = entity;
                        o2 = ((calculateIntercept == null) ? getPositionEyes : calculateIntercept.hitVec);
                        n5 = 0.0;
                    }
                }
                else if (calculateIntercept != null) {
                    final double distanceTo = getPositionEyes.distanceTo(calculateIntercept.hitVec);
                    if (distanceTo < n5 || n5 == 0.0) {
                        final boolean b = false;
                        if (entity == getRenderViewEntity.ridingEntity && !b) {
                            if (n5 == 0.0) {
                                o = entity;
                                o2 = calculateIntercept.hitVec;
                            }
                        }
                        else {
                            o = entity;
                            o2 = calculateIntercept.hitVec;
                            n5 = distanceTo;
                        }
                    }
                }
            }
        }
        if (n5 < n && !(o instanceof EntityLivingBase) && !(o instanceof EntityItemFrame)) {
            o = null;
        }
        Class0.mc.mcProfiler.endSection();
        if (o != null && o2 != null) {
            return new Object[] { o, o2 };
        }
        return null;
    }
    
    static {
        Class0.mc = Minecraft.getMinecraft();
    }
}
