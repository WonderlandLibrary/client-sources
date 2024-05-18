package baritone;

import baritone.api.event.events.type.*;

final class k
{
    static final int[] a;
    
    static {
        a = new int[EventState.values().length];
        try {
            k.a[EventState.PRE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            k.a[EventState.POST.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
