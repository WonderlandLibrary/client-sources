package baritone;

import baritone.api.utils.*;
import baritone.api.pathing.movement.*;
import baritone.api.pathing.goals.*;
import baritone.api.pathing.calc.*;
import java.util.*;

public final class bp extends di
{
    private final List<BetterBlockPos> a;
    private final List<IMovement> b;
    private final int a;
    private final Goal a;
    
    public bp(final IPath path, final int n, final int n2) {
        super();
        this.a = path.positions().subList(n, n2 + 1);
        this.b = path.movements().subList(n, n2);
        this.a = path.getNumNodesConsidered();
        this.a = path.getGoal();
        this.sanityCheck();
    }
    
    public bp(final IPath path, final int n) {
        this(path, 0, n);
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    @Override
    public final List<IMovement> movements() {
        return Collections.unmodifiableList((List<? extends IMovement>)this.b);
    }
    
    @Override
    public final List<BetterBlockPos> positions() {
        return Collections.unmodifiableList((List<? extends BetterBlockPos>)this.a);
    }
    
    @Override
    public final int getNumNodesConsidered() {
        return this.a;
    }
}
