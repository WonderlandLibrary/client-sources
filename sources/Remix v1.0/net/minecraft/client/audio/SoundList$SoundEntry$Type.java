package net.minecraft.client.audio;

public enum Type
{
    FILE("FILE", 0, "file"), 
    SOUND_EVENT("SOUND_EVENT", 1, "event");
    
    private static final Type[] $VALUES;
    private final String field_148583_c;
    
    private Type(final String p_i45109_1_, final int p_i45109_2_, final String p_i45109_3_) {
        this.field_148583_c = p_i45109_3_;
    }
    
    public static Type getType(final String p_148580_0_) {
        for (final Type var4 : values()) {
            if (var4.field_148583_c.equals(p_148580_0_)) {
                return var4;
            }
        }
        return null;
    }
    
    static {
        $VALUES = new Type[] { Type.FILE, Type.SOUND_EVENT };
    }
}
