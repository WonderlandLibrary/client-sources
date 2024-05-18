package baritone.api.utils;

public final class RayTraceUtils
{
    private RayTraceUtils() {
        super();
    }
    
    public static bhc rayTraceTowards(final vg vg, final Rotation rotation, final double n) {
        final bhe f = vg.f(1.0f);
        final bhe calcVec3dFromRotation = RotationUtils.calcVec3dFromRotation(rotation);
        return vg.l.a(f, f.b(calcVec3dFromRotation.b * n, calcVec3dFromRotation.c * n, calcVec3dFromRotation.d * n), false, false, true);
    }
}
