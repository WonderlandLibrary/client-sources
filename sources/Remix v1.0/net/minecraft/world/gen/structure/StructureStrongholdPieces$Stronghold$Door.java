package net.minecraft.world.gen.structure;

public enum Door
{
    OPENING("OPENING", 0), 
    WOOD_DOOR("WOOD_DOOR", 1), 
    GRATES("GRATES", 2), 
    IRON_DOOR("IRON_DOOR", 3);
    
    private static final Door[] $VALUES;
    
    private Door(final String p_i2086_1_, final int p_i2086_2_) {
    }
    
    static {
        $VALUES = new Door[] { Door.OPENING, Door.WOOD_DOOR, Door.GRATES, Door.IRON_DOOR };
    }
}
