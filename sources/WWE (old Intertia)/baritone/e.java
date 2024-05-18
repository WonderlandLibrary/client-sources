package baritone;

import baritone.api.event.events.type.*;

final class e
{
    static final int[] a;
    
    static {
        a = new int[EventState.values().length];
        try {
            e.a[EventState.PRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            e.a[EventState.POST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
