package baritone;

import baritone.api.utils.*;

public enum dn implements IPlayerController, cr
{
    a;
    
    private static final dn[] a;
    
    public static dn[] values() {
        return dn.a.clone();
    }
    
    public static dn valueOf(final String s) {
        return Enum.valueOf(dn.class, s);
    }
    
    @Override
    public final boolean onPlayerDamageBlock(final et et, final fa fa) {
        return dn.a.c.b(et, fa);
    }
    
    @Override
    public final void resetBlockRemoving() {
        dn.a.c.c();
    }
    
    @Override
    public final aip windowClick(final int n, final int n2, final int n3, final afw afw, final aed aed) {
        return dn.a.c.a(n, n2, n3, afw, aed);
    }
    
    @Override
    public final void setGameType(final ams ams) {
        dn.a.c.a(ams);
    }
    
    @Override
    public final ams getGameType() {
        return dn.a.c.l();
    }
    
    @Override
    public final ud processRightClickBlock(final bud bud, final amu amu, final et et, final fa fa, final bhe bhe, final ub ub) {
        return dn.a.c.a(bud, (bsb)amu, et, fa, bhe, ub);
    }
    
    @Override
    public final ud processRightClick(final bud bud, final amu amu, final ub ub) {
        return dn.a.c.a((aed)bud, amu, ub);
    }
    
    static {
        a = new dn[] { dn.a };
    }
}
