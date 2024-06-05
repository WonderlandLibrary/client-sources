package net.minecraft.command;

public enum Type
{
    SUCCESS_COUNT("SUCCESS_COUNT", 0, 0, "SuccessCount"), 
    AFFECTED_BLOCKS("AFFECTED_BLOCKS", 1, 1, "AffectedBlocks"), 
    AFFECTED_ENTITIES("AFFECTED_ENTITIES", 2, 2, "AffectedEntities"), 
    AFFECTED_ITEMS("AFFECTED_ITEMS", 3, 3, "AffectedItems"), 
    QUERY_RESULT("QUERY_RESULT", 4, 4, "QueryResult");
    
    private static final Type[] $VALUES;
    final int field_179639_f;
    final String field_179640_g;
    
    private Type(final String p_i46050_1_, final int p_i46050_2_, final int p_i46050_3_, final String p_i46050_4_) {
        this.field_179639_f = p_i46050_3_;
        this.field_179640_g = p_i46050_4_;
    }
    
    public static String[] func_179634_c() {
        final String[] var0 = new String[values().length];
        int var2 = 0;
        for (final Type var6 : values()) {
            var0[var2++] = var6.func_179637_b();
        }
        return var0;
    }
    
    public static Type func_179635_a(final String p_179635_0_) {
        for (final Type var4 : values()) {
            if (var4.func_179637_b().equals(p_179635_0_)) {
                return var4;
            }
        }
        return null;
    }
    
    public int func_179636_a() {
        return this.field_179639_f;
    }
    
    public String func_179637_b() {
        return this.field_179640_g;
    }
    
    static {
        $VALUES = new Type[] { Type.SUCCESS_COUNT, Type.AFFECTED_BLOCKS, Type.AFFECTED_ENTITIES, Type.AFFECTED_ITEMS, Type.QUERY_RESULT };
    }
}
