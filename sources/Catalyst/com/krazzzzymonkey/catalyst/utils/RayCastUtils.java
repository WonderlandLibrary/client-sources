// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.math.Vec3d;
import com.google.common.base.Predicates;
import net.minecraft.util.EntitySelectors;
import com.krazzzzymonkey.catalyst.utils.system.Wrapper;
import net.minecraft.entity.Entity;

public class RayCastUtils
{
    private static final /* synthetic */ int[] lIlIll;
    
    private static boolean lIlIlIll(final Object lllIIlllIIIlIII) {
        return lllIIlllIIIlIII == null;
    }
    
    private static int lIlIIIll(final double n, final double n2) {
        return dcmpl(n, n2);
    }
    
    private static void lIlIIIIl() {
        (lIlIll = new int[2])[0] = ((0x84 ^ 0xB9 ^ (0xF1 ^ 0xAF)) & (0xBD ^ 0xB9 ^ (0x59 ^ 0x3E) ^ -" ".length()));
        RayCastUtils.lIlIll[1] = " ".length();
    }
    
    private static boolean lIlIlllI(final int lllIIlllIIIIlII) {
        return lllIIlllIIIIlII == 0;
    }
    
    private static boolean lIlIllll(final Object lllIIlllIIIlIll, final Object lllIIlllIIIlIlI) {
        return lllIIlllIIIlIll == lllIIlllIIIlIlI;
    }
    
    static {
        lIlIIIIl();
    }
    
    public static Entity rayCast(final double lllIIlllIlllIll, final float lllIIlllIlllIlI, final float lllIIlllIlllIIl) {
        final double lllIIllllIIIllI;
        final double lllIIllllIIIlll = lllIIllllIIIllI = lllIIlllIlllIll;
        final Vec3d lllIIllllIIIlIl = Wrapper.INSTANCE.player().getPositionEyes(1.0f);
        boolean lllIIllllIIIlII = RayCastUtils.lIlIll[0] != 0;
        final boolean lllIIllllIIIIll = RayCastUtils.lIlIll[1] != 0;
        if (lIlIIllI(lIlIIIll(lllIIllllIIIlll, 3.0))) {
            lllIIllllIIIlII = (RayCastUtils.lIlIll[1] != 0);
        }
        final Vec3d lllIIllllIIIIlI = getVectorForRotation(lllIIlllIlllIIl, lllIIlllIlllIlI);
        final Vec3d lllIIllllIIIIIl = lllIIllllIIIlIl.add(lllIIllllIIIIlI.x * lllIIllllIIIlll, lllIIllllIIIIlI.y * lllIIllllIIIlll, lllIIllllIIIIlI.z * lllIIllllIIIlll);
        Entity lllIIllllIIIIII = null;
        Vec3d lllIIlllIllllll = null;
        final float lllIIlllIlllllI = 1.0f;
        final List lllIIlllIllllIl = Wrapper.INSTANCE.world().getEntitiesInAABBexcluding(Wrapper.INSTANCE.mc().getRenderViewEntity(), Wrapper.INSTANCE.mc().getRenderViewEntity().getEntityBoundingBox().offset(lllIIllllIIIIlI.x * lllIIllllIIIlll, lllIIllllIIIIlI.y * lllIIllllIIIlll, lllIIllllIIIIlI.z * lllIIllllIIIlll).expand((double)lllIIlllIlllllI, (double)lllIIlllIlllllI, (double)lllIIlllIlllllI), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::func_70067_L));
        double lllIIlllIllllII = lllIIllllIIIllI;
        int lllIIllllIIlIll = RayCastUtils.lIlIll[0];
        while (lIlIlIII(lllIIllllIIlIll, lllIIlllIllllIl.size())) {
            final Entity lllIIllllIIllll = lllIIlllIllllIl.get(lllIIllllIIlIll);
            final float lllIIllllIIlllI = lllIIllllIIllll.getCollisionBorderSize();
            final AxisAlignedBB lllIIllllIIllIl = lllIIllllIIllll.getEntityBoundingBox().expand((double)lllIIllllIIlllI, (double)lllIIllllIIlllI, (double)lllIIllllIIlllI);
            final RayTraceResult lllIIllllIIllII = lllIIllllIIllIl.calculateIntercept(lllIIllllIIIlIl, lllIIllllIIIIIl);
            if (lIlIlIIl(lllIIllllIIllIl.contains(lllIIllllIIIlIl) ? 1 : 0)) {
                if (lIlIlIlI(lIlIIIll(lllIIlllIllllII, 0.0))) {
                    lllIIllllIIIIII = lllIIllllIIllll;
                    Vec3d hitVec;
                    if (lIlIlIll(lllIIllllIIllII)) {
                        hitVec = lllIIllllIIIlIl;
                        "".length();
                        if ("   ".length() != "   ".length()) {
                            return null;
                        }
                    }
                    else {
                        hitVec = lllIIllllIIllII.hitVec;
                    }
                    lllIIlllIllllll = hitVec;
                    lllIIlllIllllII = 0.0;
                    "".length();
                    if (" ".length() >= "   ".length()) {
                        return null;
                    }
                }
            }
            else if (lIlIllIl(lllIIllllIIllII)) {
                final double lllIIllllIlIIII = lllIIllllIIIlIl.distanceTo(lllIIllllIIllII.hitVec);
                if (!lIlIlIlI(lIlIIlII(lllIIllllIlIIII, lllIIlllIllllII)) || lIlIlllI(lIlIIIll(lllIIlllIllllII, 0.0))) {
                    final boolean lllIIllllIlIIIl = RayCastUtils.lIlIll[0] != 0;
                    if (lIlIllll(lllIIllllIIllll, Wrapper.INSTANCE.mc().getRenderViewEntity().getRidingEntity()) && lIlIlllI(lllIIllllIlIIIl ? 1 : 0)) {
                        if (lIlIlllI(lIlIIIll(lllIIlllIllllII, 0.0))) {
                            lllIIllllIIIIII = lllIIllllIIllll;
                            lllIIlllIllllll = lllIIllllIIllII.hitVec;
                            "".length();
                            if (((0x90 ^ 0x80) & ~(0x9D ^ 0x8D)) != ((0xC ^ 0x3C) & ~(0x53 ^ 0x63))) {
                                return null;
                            }
                        }
                    }
                    else {
                        lllIIllllIIIIII = lllIIllllIIllll;
                        lllIIlllIllllll = lllIIllllIIllII.hitVec;
                        lllIIlllIllllII = lllIIllllIlIIII;
                    }
                }
            }
            ++lllIIllllIIlIll;
            "".length();
            if (null != null) {
                return null;
            }
        }
        return lllIIllllIIIIII;
    }
    
    private static int lIlIIlII(final double n, final double n2) {
        return dcmpg(n, n2);
    }
    
    private static boolean lIlIIllI(final int lllIIlllIIIIIII) {
        return lllIIlllIIIIIII > 0;
    }
    
    private static boolean lIlIlIIl(final int lllIIlllIIIIllI) {
        return lllIIlllIIIIllI != 0;
    }
    
    private static boolean lIlIllIl(final Object lllIIlllIIIlllI) {
        return lllIIlllIIIlllI != null;
    }
    
    public static Vec3d getVectorForRotation(final float lllIIlllIIllIIl, final float lllIIlllIIllllI) {
        final float lllIIlllIIlllIl = MathHelper.cos(-lllIIlllIIllllI * 0.017453292f - 3.1415927f);
        final float lllIIlllIIlllII = MathHelper.sin(-lllIIlllIIllllI * 0.017453292f - 3.1415927f);
        final float lllIIlllIIllIll = -MathHelper.cos(-lllIIlllIIllIIl * 0.017453292f);
        final float lllIIlllIIllIlI = MathHelper.sin(-lllIIlllIIllIIl * 0.017453292f);
        return new Vec3d((double)(lllIIlllIIlllII * lllIIlllIIllIll), (double)lllIIlllIIllIlI, (double)(lllIIlllIIlllIl * lllIIlllIIllIll));
    }
    
    private static boolean lIlIlIII(final int lllIIlllIIlIIIl, final int lllIIlllIIlIIII) {
        return lllIIlllIIlIIIl < lllIIlllIIlIIII;
    }
    
    private static boolean lIlIlIlI(final int lllIIlllIIIIIlI) {
        return lllIIlllIIIIIlI >= 0;
    }
}
