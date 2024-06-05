package net.minecraft.network.play.server;

static final class SwitchAction
{
    static final int[] field_179938_a;
    
    static {
        field_179938_a = new int[Action.values().length];
        try {
            SwitchAction.field_179938_a[Action.ADD_PLAYER.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchAction.field_179938_a[Action.UPDATE_GAME_MODE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            SwitchAction.field_179938_a[Action.UPDATE_LATENCY.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            SwitchAction.field_179938_a[Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            SwitchAction.field_179938_a[Action.REMOVE_PLAYER.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
    }
}
