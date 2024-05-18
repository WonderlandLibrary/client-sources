package baritone;

import baritone.api.utils.*;

final class dl
{
    static final int[] a;
    
    static {
        a = new int[PathCalculationResult$Type.values().length];
        try {
            dl.a[PathCalculationResult$Type.SUCCESS_SEGMENT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            dl.a[PathCalculationResult$Type.SUCCESS_TO_GOAL.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            dl.a[PathCalculationResult$Type.FAILURE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            dl.a[PathCalculationResult$Type.EXCEPTION.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
    }
}
