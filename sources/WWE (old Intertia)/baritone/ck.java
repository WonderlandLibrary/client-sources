package baritone;

import baritone.api.process.*;
import baritone.api.utils.*;

public abstract class ck implements IBaritoneProcess, cr
{
    public final a a;
    protected final IPlayerContext a;
    
    public ck(final a a) {
        super();
        this.a = a;
        this.a = a.getPlayerContext();
        a.a.registerProcess(this);
    }
    
    @Override
    public boolean isTemporary() {
        return false;
    }
}
