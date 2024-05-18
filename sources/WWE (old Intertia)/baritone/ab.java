package baritone;

import baritone.api.utils.*;
import baritone.api.pathing.goals.*;
import baritone.api.pathing.calc.*;
import java.util.function.*;
import baritone.api.pathing.movement.*;
import java.util.*;

final class ab extends di
{
    private final BetterBlockPos a;
    private final BetterBlockPos b;
    private final List<BetterBlockPos> a;
    private final List<ae> b;
    private final List<g> c;
    private final Goal a;
    private final int a;
    private final ad a;
    private boolean a;
    
    ab(final g g, final g g2, final int a, final Goal a2, final ad a3) {
        super();
        this.a = new BetterBlockPos(g.a, g.b, g.c);
        this.b = new BetterBlockPos(g2.a, g2.b, g2.c);
        this.a = a;
        this.a = new ArrayList<BetterBlockPos>();
        this.b = new ArrayList<ae>();
        this.c = new ArrayList<g>();
        this.a = a2;
        this.a = a3;
        this.a(g2);
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    private void a(g a) {
        if (!this.a.isEmpty() || !this.b.isEmpty()) {
            throw new IllegalStateException();
        }
        final LinkedList<BetterBlockPos> list = new LinkedList<BetterBlockPos>();
        final LinkedList<g> list2 = new LinkedList<g>();
        while (a != null) {
            list2.addFirst(a);
            list.addFirst(new BetterBlockPos(a.a, a.b, a.c));
            a = a.a;
        }
        this.a.addAll(list);
        this.c.addAll(list2);
    }
    
    private boolean a() {
        if (this.a.isEmpty() || !this.b.isEmpty()) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < this.a.size() - 1; ++i) {
            final ae a;
            if ((a = this.a(this.a.get(i), this.a.get(i + 1), this.c.get(i + 1).b - this.c.get(i).b)) == null) {
                return true;
            }
            this.b.add(a);
        }
        return false;
    }
    
    private ae a(final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final double n) {
        aj[] values;
        for (int length = (values = aj.values()).length, i = 0; i < length; ++i) {
            final ae a;
            if ((a = values[i].a(this.a, betterBlockPos)).getDest().equals(betterBlockPos2)) {
                final ae ae = a;
                ae.a = Math.min(ae.b(this.a), n);
                return a;
            }
        }
        cr.a.a("Movement became impossible during calculation " + betterBlockPos + " " + betterBlockPos2 + " " + betterBlockPos2.b((fq)betterBlockPos));
        return null;
    }
    
    @Override
    public final IPath postProcess() {
        if (this.a) {
            throw new IllegalStateException();
        }
        this.a = true;
        final boolean a = this.a();
        this.b.forEach(this::a);
        if (!a) {
            this.sanityCheck();
            return this;
        }
        final bp bp;
        if ((bp = new bp(this, this.movements().size())).movements().size() != this.b.size()) {
            throw new IllegalStateException();
        }
        return bp;
    }
    
    @Override
    public final List<IMovement> movements() {
        if (!this.a) {
            throw new IllegalStateException();
        }
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
    
    @Override
    public final BetterBlockPos getSrc() {
        return this.a;
    }
    
    @Override
    public final BetterBlockPos getDest() {
        return this.b;
    }
    
    private void a(final ae ae) {
        ae.a(this.a);
    }
}
