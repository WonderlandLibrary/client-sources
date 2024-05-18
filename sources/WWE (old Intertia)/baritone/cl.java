package baritone;

import baritone.api.utils.*;

public final class cl implements cr
{
    boolean a;
    final IPlayerContext a;
    
    public cl(final IPlayerContext a) {
        super();
        this.a = a;
    }
    
    public final void a() {
        if (this.a.player() != null) {
            this.a.playerController().resetBlockRemoving();
        }
    }
}
