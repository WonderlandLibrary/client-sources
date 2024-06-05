package net.minecraft.network.play.server;

public enum Event
{
    ENTER_COMBAT("ENTER_COMBAT", 0), 
    END_COMBAT("END_COMBAT", 1), 
    ENTITY_DIED("ENTITY_DIED", 2);
    
    private static final Event[] $VALUES;
    
    private Event(final String p_i45969_1_, final int p_i45969_2_) {
    }
    
    static {
        $VALUES = new Event[] { Event.ENTER_COMBAT, Event.END_COMBAT, Event.ENTITY_DIED };
    }
}
