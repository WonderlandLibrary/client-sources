package baritone;

import baritone.api.cache.*;
import baritone.api.*;
import baritone.api.utils.*;

public enum dm implements IPlayerContext, cr
{
    a;
    
    private static final dm[] a;
    
    public static dm[] values() {
        return dm.a.clone();
    }
    
    public static dm valueOf(final String s) {
        return Enum.valueOf(dm.class, s);
    }
    
    @Override
    public final bud player() {
        return dm.a.h;
    }
    
    @Override
    public final IPlayerController playerController() {
        return dn.a;
    }
    
    @Override
    public final amu world() {
        return (amu)dm.a.f;
    }
    
    @Override
    public final IWorldData worldData() {
        return BaritoneAPI.getProvider().getPrimaryBaritone().getWorldProvider().getCurrentWorld();
    }
    
    @Override
    public final bhc objectMouseOver() {
        return RayTraceUtils.rayTraceTowards((vg)this.player(), this.playerRotations(), this.playerController().getBlockReachDistance());
    }
    
    static {
        a = new dm[] { dm.a };
    }
}
