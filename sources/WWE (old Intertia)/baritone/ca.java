package baritone;

import baritone.api.pathing.goals.*;
import baritone.api.process.*;

public final class ca extends ck implements ICustomGoalProcess
{
    private Goal a;
    private int a;
    
    public ca(final a a) {
        super(a);
    }
    
    @Override
    public final void setGoal(final Goal a) {
        this.a = a;
        if (this.a == cc.a) {
            this.a = cc.b;
        }
        if (this.a == cc.d) {
            this.a = cc.c;
        }
    }
    
    @Override
    public final void path() {
        this.a = cc.c;
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    @Override
    public final boolean isActive() {
        return this.a != cc.a;
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        switch (cb.a[this.a - 1]) {
            case 1: {
                return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
            case 2: {
                final PathingCommand pathingCommand = new PathingCommand(this.a, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
                this.a = cc.d;
                return pathingCommand;
            }
            case 3: {
                if (b) {
                    this.onLostControl();
                    return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                if (this.a == null || (this.a.isInGoal(this.a.playerFeet()) && this.a.isInGoal(this.a.a.a()))) {
                    this.onLostControl();
                    return new PathingCommand(this.a, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                return new PathingCommand(this.a, PathingCommandType.SET_GOAL_AND_PATH);
            }
            default: {
                throw new IllegalStateException();
            }
        }
    }
    
    @Override
    public final void onLostControl() {
        this.a = cc.a;
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        return "Custom Goal " + this.a;
    }
}
