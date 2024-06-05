package net.minecraft.scoreboard;

import java.util.*;
import com.google.common.collect.*;

public enum EnumRenderType
{
    INTEGER("INTEGER", 0, "integer"), 
    HEARTS("HEARTS", 1, "hearts");
    
    private static final Map field_178801_c;
    private static final EnumRenderType[] $VALUES;
    private final String field_178798_d;
    
    private EnumRenderType(final String p_i45548_1_, final int p_i45548_2_, final String p_i45548_3_) {
        this.field_178798_d = p_i45548_3_;
    }
    
    public static EnumRenderType func_178795_a(final String p_178795_0_) {
        final EnumRenderType var1 = EnumRenderType.field_178801_c.get(p_178795_0_);
        return (var1 == null) ? EnumRenderType.INTEGER : var1;
    }
    
    public String func_178796_a() {
        return this.field_178798_d;
    }
    
    static {
        field_178801_c = Maps.newHashMap();
        $VALUES = new EnumRenderType[] { EnumRenderType.INTEGER, EnumRenderType.HEARTS };
        for (final EnumRenderType var4 : values()) {
            EnumRenderType.field_178801_c.put(var4.func_178796_a(), var4);
        }
    }
}
