package net.minecraft.client.renderer.chunk;

public enum Type
{
    REBUILD_CHUNK("REBUILD_CHUNK", 0, "REBUILD_CHUNK", 0), 
    RESORT_TRANSPARENCY("RESORT_TRANSPARENCY", 1, "RESORT_TRANSPARENCY", 1);
    
    private static final Type[] $VALUES;
    
    private Type(final String p_i46386_1_, final int p_i46386_2_, final String p_i46206_1_, final int p_i46206_2_) {
    }
    
    static {
        $VALUES = new Type[] { Type.REBUILD_CHUNK, Type.RESORT_TRANSPARENCY };
    }
}
