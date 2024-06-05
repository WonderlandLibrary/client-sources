package net.minecraft.util;

public enum AxisDirection
{
    POSITIVE("POSITIVE", 0, "POSITIVE", 0, 1, "Towards positive"), 
    NEGATIVE("NEGATIVE", 1, "NEGATIVE", 1, -1, "Towards negative");
    
    private static final AxisDirection[] $VALUES;
    private final int offset;
    private final String description;
    
    private AxisDirection(final String p_i46391_1_, final int p_i46391_2_, final String p_i46014_1_, final int p_i46014_2_, final int offset, final String description) {
        this.offset = offset;
        this.description = description;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    static {
        $VALUES = new AxisDirection[] { AxisDirection.POSITIVE, AxisDirection.NEGATIVE };
    }
}
