package net.minecraft.network.play.server;

public enum Type
{
    TITLE("TITLE", 0), 
    SUBTITLE("SUBTITLE", 1), 
    TIMES("TIMES", 2), 
    CLEAR("CLEAR", 3), 
    RESET("RESET", 4);
    
    private static final Type[] $VALUES;
    
    private Type(final String p_i45952_1_, final int p_i45952_2_) {
    }
    
    public static Type func_179969_a(final String p_179969_0_) {
        for (final Type var4 : values()) {
            if (var4.name().equalsIgnoreCase(p_179969_0_)) {
                return var4;
            }
        }
        return Type.TITLE;
    }
    
    public static String[] func_179971_a() {
        final String[] var0 = new String[values().length];
        int var2 = 0;
        for (final Type var6 : values()) {
            var0[var2++] = var6.name().toLowerCase();
        }
        return var0;
    }
    
    static {
        $VALUES = new Type[] { Type.TITLE, Type.SUBTITLE, Type.TIMES, Type.CLEAR, Type.RESET };
    }
}
