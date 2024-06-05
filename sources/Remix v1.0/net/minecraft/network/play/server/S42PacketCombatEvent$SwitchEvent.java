package net.minecraft.network.play.server;

static final class SwitchEvent
{
    static final int[] field_179944_a;
    
    static {
        field_179944_a = new int[Event.values().length];
        try {
            SwitchEvent.field_179944_a[Event.END_COMBAT.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            SwitchEvent.field_179944_a[Event.ENTITY_DIED.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
