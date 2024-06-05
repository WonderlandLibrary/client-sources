package net.minecraft.util;

import java.util.*;
import com.google.common.collect.*;

public enum Type
{
    LEGACY("LEGACY", 0, "legacy"), 
    MOJANG("MOJANG", 1, "mojang");
    
    private static final Map field_152425_c;
    private static final Type[] $VALUES;
    private final String sessionType;
    
    private Type(final String p_i1096_1_, final int p_i1096_2_, final String p_i1096_3_) {
        this.sessionType = p_i1096_3_;
    }
    
    public static Type setSessionType(final String p_152421_0_) {
        return Type.field_152425_c.get(p_152421_0_.toLowerCase());
    }
    
    static {
        field_152425_c = Maps.newHashMap();
        $VALUES = new Type[] { Type.LEGACY, Type.MOJANG };
        for (final Type var4 : values()) {
            Type.field_152425_c.put(var4.sessionType, var4);
        }
    }
}
