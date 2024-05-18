package baritone;

import baritone.api.pathing.calc.*;
import baritone.api.process.*;
import baritone.api.event.listener.*;
import baritone.api.pathing.goals.*;
import baritone.api.utils.*;
import java.util.*;

public final class cw implements IPathingControlManager
{
    public final a a;
    private final HashSet<IBaritoneProcess> a;
    private final List<IBaritoneProcess> a;
    public IBaritoneProcess a;
    public IBaritoneProcess b;
    public PathingCommand a;
    
    public cw(final a a) {
        super();
        this.a = a;
        this.a = new HashSet<IBaritoneProcess>();
        this.a = new ArrayList<IBaritoneProcess>();
        a.getGameEventHandler().registerEventListener(new cx(this));
    }
    
    @Override
    public final void registerProcess(final IBaritoneProcess baritoneProcess) {
        baritoneProcess.onLostControl();
        this.a.add(baritoneProcess);
    }
    
    public final void a() {
        this.a = null;
        this.b = null;
        ((List)(this.a = null)).clear();
        final Iterator<IBaritoneProcess> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final IBaritoneProcess baritoneProcess;
            (baritoneProcess = iterator.next()).onLostControl();
            if (baritoneProcess.isActive() && !baritoneProcess.isTemporary()) {
                throw new IllegalStateException(baritoneProcess.displayName());
            }
        }
    }
    
    @Override
    public final Optional<IBaritoneProcess> mostRecentInControl() {
        return Optional.ofNullable(this.b);
    }
    
    @Override
    public final Optional<PathingCommand> mostRecentCommand() {
        return Optional.ofNullable(this.a);
    }
    
    public final boolean a(final Goal goal) {
        final bq a;
        if ((a = this.a.a.a) != null) {
            final Goal goal2 = a.getPath().getGoal();
            final BetterBlockPos dest = a.getPath().getDest();
            if (goal2.isInGoal(dest) && !goal.isInGoal(dest)) {
                return true;
            }
        }
        return false;
    }
    
    public final PathingCommand a() {
        final Iterator<IBaritoneProcess> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final IBaritoneProcess baritoneProcess;
            if ((baritoneProcess = iterator.next()).isActive()) {
                if (this.a.contains(baritoneProcess)) {
                    continue;
                }
                this.a.add(0, baritoneProcess);
            }
            else {
                this.a.remove(baritoneProcess);
            }
        }
        this.a.sort(Comparator.comparingDouble(IBaritoneProcess::priority).reversed());
        final Iterator<IBaritoneProcess> iterator2 = this.a.iterator();
        while (iterator2.hasNext()) {
            final IBaritoneProcess b;
            final IBaritoneProcess baritoneProcess2 = b = iterator2.next();
            final PathingCommand onTick;
            if ((onTick = baritoneProcess2.onTick(Objects.equals(baritoneProcess2, this.a) && this.a.a.a, this.a.a.a())) != null) {
                this.b = b;
                if (!b.isTemporary()) {
                    iterator2.forEachRemaining(IBaritoneProcess::onLostControl);
                }
                return onTick;
            }
            if (b.isActive()) {
                throw new IllegalStateException(b.displayName() + " returned null PathingCommand");
            }
            b.onLostControl();
        }
        return null;
    }
}
