package baritone;

import baritone.api.event.listener.*;
import baritone.api.event.events.*;
import baritone.api.pathing.goals.*;

final class cx implements AbstractGameEventListener
{
    private cw a;
    
    cx(final cw a) {
        this.a = a;
        super();
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        final cw a;
        if (tickEvent.getType() == TickEvent$Type.IN && (a = this.a).a != null) {
            final j a2 = a.a.a;
            switch (cy.a[a.a.commandType.ordinal()]) {
                case 3: {
                    Label_0178: {
                        if (a.a.goal != null) {
                            final cw cw = a;
                            final Goal goal = cw.a.goal;
                            final bq a3;
                            if ((a3 = cw.a.a.a) == null || goal.isInGoal(a3.getPath().getDest()) || goal.toString().equals(a3.getPath().getGoal().toString())) {
                                final cw cw2 = a;
                                if (!cw2.a(cw2.a.goal)) {
                                    break Label_0178;
                                }
                            }
                        }
                        a2.a();
                    }
                    a2.a(a.a);
                }
                case 4: {
                    Label_0234: {
                        if (baritone.a.a().cancelOnGoalInvalidation.value) {
                            if (a.a.goal != null) {
                                final cw cw3 = a;
                                if (!cw3.a(cw3.a.goal)) {
                                    break Label_0234;
                                }
                            }
                            a2.a();
                        }
                    }
                    a2.a(a.a);
                    break;
                }
            }
        }
    }
}
