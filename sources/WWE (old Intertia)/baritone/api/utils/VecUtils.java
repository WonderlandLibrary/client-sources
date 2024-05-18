package baritone.api.utils;

public final class VecUtils
{
    private VecUtils() {
        super();
    }
    
    public static bhe calculateBlockCenter(final amu amu, final et et) {
        final awt o;
        final bhb e;
        final double n = ((e = (o = amu.o(et)).e((amy)amu, et)).a + e.d) / 2.0;
        double n2 = (e.b + e.e) / 2.0;
        final double n3 = (e.c + e.f) / 2.0;
        if (o.u() instanceof aqq) {
            n2 = 0.0;
        }
        return new bhe(et.p() + n, et.q() + n2, et.r() + n3);
    }
    
    public static bhe getBlockPosCenter(final et et) {
        return new bhe(et.p() + 0.5, et.q() + 0.5, et.r() + 0.5);
    }
    
    public static double distanceToCenter(final et et, final double n, final double n2, final double n3) {
        final double n4 = et.p() + 0.5 - n;
        final double n5 = et.q() + 0.5 - n2;
        final double n6 = et.r() + 0.5 - n3;
        final double n7 = n4;
        final double n8 = n7 * n7;
        final double n9 = n5;
        final double n10 = n8 + n9 * n9;
        final double n11 = n6;
        return Math.sqrt(n10 + n11 * n11);
    }
    
    public static double entityDistanceToCenter(final vg vg, final et et) {
        return distanceToCenter(et, vg.p, vg.q, vg.r);
    }
    
    public static double entityFlatDistanceToCenter(final vg vg, final et et) {
        return distanceToCenter(et, vg.p, et.q() + 0.5, vg.r);
    }
}
