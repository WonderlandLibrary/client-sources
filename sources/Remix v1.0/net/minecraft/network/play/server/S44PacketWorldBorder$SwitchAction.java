package net.minecraft.network.play.server;

static final class SwitchAction
{
    static final int[] field_179947_a;
    
    static {
        field_179947_a = new int[Action.values().length];
        try {
            SwitchAction.field_179947_a[Action.SET_SIZE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchAction.field_179947_a[Action.LERP_SIZE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchAction.field_179947_a[Action.SET_CENTER.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchAction.field_179947_a[Action.SET_WARNING_BLOCKS.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchAction.field_179947_a[Action.SET_WARNING_TIME.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            SwitchAction.field_179947_a[Action.INITIALIZE.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
    }
}
