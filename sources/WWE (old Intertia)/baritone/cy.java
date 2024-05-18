package baritone;

import baritone.api.process.*;

public final class cy
{
    public static final int[] a;
    
    static {
        a = new int[PathingCommandType.values().length];
        try {
            cy.a[PathingCommandType.REQUEST_PAUSE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            cy.a[PathingCommandType.CANCEL_AND_SET_GOAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            cy.a[PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            cy.a[PathingCommandType.REVALIDATE_GOAL_AND_PATH.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            cy.a[PathingCommandType.SET_GOAL_AND_PATH.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
