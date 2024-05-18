package baritone;

import baritone.api.behavior.*;
import baritone.api.utils.*;
import baritone.api.event.listener.*;

public class b implements IBehavior
{
    public final a a;
    public final IPlayerContext a;
    
    protected b(a a) {
        super();
        this.a = a;
        this.a = a.getPlayerContext();
        (a = a).a.add(this);
        a.a.registerEventListener(this);
    }
}
