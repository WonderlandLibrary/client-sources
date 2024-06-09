package org.spongepowered.asm.mixin.gen;

import java.util.*;
import com.google.common.collect.*;

public enum AccessorType
{
    FIELD_GETTER((Set)ImmutableSet.of((Object)"get", (Object)"is")) {
        AccessorInfo$AccessorType$1(final String s, final int n, final Set set) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo accessorInfo) {
            return new AccessorGeneratorFieldGetter(accessorInfo);
        }
    }, 
    FIELD_SETTER((Set)ImmutableSet.of("set")) {
        AccessorInfo$AccessorType$2(final String s, final int n, final Set set) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo accessorInfo) {
            return new AccessorGeneratorFieldSetter(accessorInfo);
        }
    }, 
    METHOD_PROXY((Set)ImmutableSet.of((Object)"call", (Object)"invoke")) {
        AccessorInfo$AccessorType$3(final String s, final int n, final Set set) {
        }
        
        @Override
        AccessorGenerator getGenerator(final AccessorInfo accessorInfo) {
            return new AccessorGeneratorMethodProxy(accessorInfo);
        }
    };
    
    private final Set<String> expectedPrefixes;
    private static final AccessorType[] $VALUES;
    
    public static AccessorType[] values() {
        return AccessorType.$VALUES.clone();
    }
    
    public static AccessorType valueOf(final String s) {
        return Enum.valueOf(AccessorType.class, s);
    }
    
    private AccessorType(final Set<String> expectedPrefixes) {
        this.expectedPrefixes = expectedPrefixes;
    }
    
    public boolean isExpectedPrefix(final String s) {
        return this.expectedPrefixes.contains(s);
    }
    
    public String getExpectedPrefixes() {
        return this.expectedPrefixes.toString();
    }
    
    abstract AccessorGenerator getGenerator(final AccessorInfo p0);
    
    AccessorType(final String s, final int n, final Set set, final AccessorInfo$1 object) {
        this(set);
    }
    
    static {
        $VALUES = new AccessorType[] { AccessorType.FIELD_GETTER, AccessorType.FIELD_SETTER, AccessorType.METHOD_PROXY };
    }
}
