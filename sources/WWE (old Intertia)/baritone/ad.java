package baritone;

import baritone.api.*;

public class ad
{
    private static final aip a;
    public final boolean a;
    public final IBaritone a;
    public final amu a;
    public final v a;
    public final cn a;
    public final da a;
    public final boolean b;
    public final boolean c;
    public final boolean d;
    protected final double a;
    public final boolean e;
    public final boolean f;
    public final boolean g;
    public final boolean h;
    public final boolean i;
    public final boolean j;
    public final int a;
    public final int b;
    public final double b;
    public final double c;
    public double d;
    public double e;
    public final double f;
    public final df a;
    
    public ad(final IBaritone baritone) {
        this(baritone, false);
    }
    
    public ad(final IBaritone a, final boolean a2) {
        super();
        this.a = a2;
        this.a = a;
        final bud player = a.getPlayerContext().player();
        this.a = a.getPlayerContext().world();
        this.a = (v)a.getWorldProvider().getCurrentWorld();
        this.a = new cn(this.a, this.a, a2);
        this.a = new da(player);
        this.c = (baritone.a.a().allowPlace.value && ((a)a).a.a());
        this.b = (baritone.a.a().allowWaterBucketFall.value && aec.e(player.bv.b(ad.a)) && !this.a.s.n());
        this.d = (baritone.a.a().allowSprint.value && player.di().a() > 6);
        this.a = baritone.a.a().blockPlacementPenalty.value;
        this.e = baritone.a.a().allowBreak.value;
        this.f = baritone.a.a().allowParkour.value;
        this.g = baritone.a.a().allowParkourPlace.value;
        this.h = baritone.a.a().allowJumpAt256.value;
        this.i = baritone.a.a().assumeWalkOnWater.value;
        this.j = baritone.a.a().allowDiagonalDescend.value;
        this.a = baritone.a.a().maxFallHeightNoWater.value;
        this.b = baritone.a.a().maxFallHeightBucket.value;
        int e;
        if ((e = alm.e((vp)player)) > 3) {
            e = 3;
        }
        final float n = e / 3.0f;
        this.b = 9.09090909090909 * (1.0f - n) + 4.63284688441047 * n;
        this.c = baritone.a.a().blockBreakAdditionalPenalty.value;
        this.d = baritone.a.a().backtrackCostFavoringCoefficient.value;
        this.e = baritone.a.a().jumpPenalty.value;
        this.f = baritone.a.a().walkOnWaterOnePenalty.value;
        this.a = new df(this.a.al());
    }
    
    public final awt a(final int n, final int n2, final int n3) {
        return this.a.a(n, n2, n3);
    }
    
    public final awt a(final et et) {
        return this.a(et.p(), et.q(), et.r());
    }
    
    public final aow a(final int n, final int n2, final int n3) {
        return this.a(n, n2, n3).u();
    }
    
    public double a(final int n, final int n2, final int n3) {
        if (!this.c) {
            return 1000000.0;
        }
        if (!this.a.b(n, n3)) {
            return 1000000.0;
        }
        return this.a;
    }
    
    public double b(final int n, final int n2, final int n3) {
        if (!this.e) {
            return 1000000.0;
        }
        return 1.0;
    }
    
    public final double a() {
        return this.a;
    }
    
    static {
        a = new aip(air.aA);
    }
}
