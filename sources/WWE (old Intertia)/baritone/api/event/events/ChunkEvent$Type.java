package baritone.api.event.events;

public enum ChunkEvent$Type
{
    LOAD, 
    UNLOAD, 
    POPULATE_FULL, 
    POPULATE_PARTIAL;
    
    private static final ChunkEvent$Type[] $VALUES;
    
    public static ChunkEvent$Type[] values() {
        return ChunkEvent$Type.$VALUES.clone();
    }
    
    public static ChunkEvent$Type valueOf(final String s) {
        return Enum.valueOf(ChunkEvent$Type.class, s);
    }
    
    static {
        $VALUES = new ChunkEvent$Type[] { ChunkEvent$Type.LOAD, ChunkEvent$Type.UNLOAD, ChunkEvent$Type.POPULATE_FULL, ChunkEvent$Type.POPULATE_PARTIAL };
    }
}
