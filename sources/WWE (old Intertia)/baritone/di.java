package baritone;

import baritone.api.pathing.calc.*;
import baritone.api.pathing.goals.*;
import baritone.api.*;
import baritone.api.utils.*;

public abstract class di implements IPath
{
    public di() {
        super();
    }
    
    @Override
    public IPath staticCutoff(final Goal goal) {
        final int intValue = BaritoneAPI.getSettings().pathCutoffMinimumLength.value;
        if (this.length() < intValue) {
            return this;
        }
        if (goal == null || goal.isInGoal(this.getDest())) {
            return this;
        }
        return new bp(this, (int)((this.length() - intValue) * BaritoneAPI.getSettings().pathCutoffFactor.value) + intValue - 1);
    }
    
    @Override
    public IPath cutoffAtLoadedChunks(final Object o) {
        if (a.a().cutoffAtLoadBoundary.value) {
            final cn cn = (cn)o;
            for (int i = 0; i < this.positions().size(); ++i) {
                final BetterBlockPos betterBlockPos = this.positions().get(i);
                if (!cn.a(betterBlockPos.p(), betterBlockPos.r())) {
                    return new bp(this, i);
                }
            }
        }
        return this;
    }
}
