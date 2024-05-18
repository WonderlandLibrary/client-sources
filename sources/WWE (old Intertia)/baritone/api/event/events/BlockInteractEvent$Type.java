package baritone.api.event.events;

public enum BlockInteractEvent$Type
{
    START_BREAK, 
    USE;
    
    private static final BlockInteractEvent$Type[] $VALUES;
    
    public static BlockInteractEvent$Type[] values() {
        return BlockInteractEvent$Type.$VALUES.clone();
    }
    
    public static BlockInteractEvent$Type valueOf(final String s) {
        return Enum.valueOf(BlockInteractEvent$Type.class, s);
    }
    
    static {
        $VALUES = new BlockInteractEvent$Type[] { BlockInteractEvent$Type.START_BREAK, BlockInteractEvent$Type.USE };
    }
}
