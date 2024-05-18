package baritone.api.utils;

import java.util.*;
import baritone.api.*;

public final class RotationUtils
{
    public static final double DEG_TO_RAD = 0.017453292519943295;
    public static final double RAD_TO_DEG = 57.29577951308232;
    private static final bhe[] BLOCK_SIDE_MULTIPLIERS;
    
    private RotationUtils() {
        super();
    }
    
    public static Rotation calcRotationFromCoords(final et et, final et et2) {
        return calcRotationFromVec3d(new bhe((fq)et), new bhe((fq)et2));
    }
    
    public static Rotation wrapAnglesToRelative(final Rotation rotation, final Rotation rotation2) {
        if (rotation.yawIsReallyClose(rotation2)) {
            return new Rotation(rotation.getYaw(), rotation2.getPitch());
        }
        return rotation2.subtract(rotation).normalize().add(rotation);
    }
    
    public static Rotation calcRotationFromVec3d(final bhe bhe, final bhe bhe2, final Rotation rotation) {
        return wrapAnglesToRelative(rotation, calcRotationFromVec3d(bhe, bhe2));
    }
    
    private static Rotation calcRotationFromVec3d(final bhe bhe, final bhe bhe2) {
        final double[] array;
        return new Rotation((float)(rk.c((array = new double[] { bhe.b - bhe2.b, bhe.c - bhe2.c, bhe.d - bhe2.d })[0], -array[2]) * 57.29577951308232), (float)(rk.c(array[1], Math.sqrt(array[0] * array[0] + array[2] * array[2])) * 57.29577951308232));
    }
    
    public static bhe calcVec3dFromRotation(final Rotation rotation) {
        final float b = rk.b(-rotation.getYaw() * 0.017453292f - 3.1415927f);
        final float a = rk.a(-rotation.getYaw() * 0.017453292f - 3.1415927f);
        final float n = -rk.b(-rotation.getPitch() * 0.017453292f);
        return new bhe((double)(a * n), (double)rk.a(-rotation.getPitch() * 0.017453292f), (double)(b * n));
    }
    
    public static Optional<Rotation> reachable(final IPlayerContext playerContext, final et et) {
        return reachable(playerContext.player(), et, playerContext.playerController().getBlockReachDistance());
    }
    
    public static Optional<Rotation> reachable(final bud bud, final et et, final double n) {
        if (et.equals((Object)BaritoneAPI.getProvider().getBaritoneForPlayer(bud).getPlayerContext().getSelectedBlock().orElse(null))) {
            return Optional.of(new Rotation(bud.v, bud.w + 1.0E-4f));
        }
        final Optional<Rotation> reachableCenter;
        if ((reachableCenter = reachableCenter((vg)bud, et, n)).isPresent()) {
            return reachableCenter;
        }
        final bhb e = bud.l.o(et).e((amy)bud.l, et);
        bhe[] block_SIDE_MULTIPLIERS;
        for (int length = (block_SIDE_MULTIPLIERS = RotationUtils.BLOCK_SIDE_MULTIPLIERS).length, i = 0; i < length; ++i) {
            final bhe bhe = block_SIDE_MULTIPLIERS[i];
            final Optional<Rotation> reachableOffset;
            if ((reachableOffset = reachableOffset((vg)bud, et, new bhe((fq)et).b(e.a * bhe.b + e.d * (1.0 - bhe.b), e.b * bhe.c + e.e * (1.0 - bhe.c), e.c * bhe.d + e.f * (1.0 - bhe.d)), n)).isPresent()) {
                return reachableOffset;
            }
        }
        return Optional.empty();
    }
    
    public static Optional<Rotation> reachableOffset(final vg vg, final et et, final bhe bhe, final double n) {
        final Rotation calcRotationFromVec3d = calcRotationFromVec3d(vg.f(1.0f), bhe, new Rotation(vg.v, vg.w));
        final bhc rayTraceTowards;
        if ((rayTraceTowards = RayTraceUtils.rayTraceTowards(vg, calcRotationFromVec3d, n)) != null && rayTraceTowards.a == bhc$a.b) {
            if (rayTraceTowards.a().equals((Object)et)) {
                return Optional.of(calcRotationFromVec3d);
            }
            if (vg.l.o(et).u() instanceof aqq && rayTraceTowards.a().equals((Object)et.b())) {
                return Optional.of(calcRotationFromVec3d);
            }
        }
        return Optional.empty();
    }
    
    public static Optional<Rotation> reachableCenter(final vg vg, final et et, final double n) {
        return reachableOffset(vg, et, VecUtils.calculateBlockCenter(vg.l, et), n);
    }
    
    static {
        BLOCK_SIDE_MULTIPLIERS = new bhe[] { new bhe(0.5, 0.0, 0.5), new bhe(0.5, 1.0, 0.5), new bhe(0.5, 0.5, 0.0), new bhe(0.5, 0.5, 1.0), new bhe(0.0, 0.5, 0.5), new bhe(1.0, 0.5, 0.5) };
    }
}
