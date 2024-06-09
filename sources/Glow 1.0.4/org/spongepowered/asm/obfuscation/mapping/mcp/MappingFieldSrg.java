package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.*;

public class MappingFieldSrg extends MappingField
{
    private final String srg;
    
    public MappingFieldSrg(final String srg) {
        super(getOwnerFromSrg(srg), getNameFromSrg(srg), null);
        this.srg = srg;
    }
    
    public MappingFieldSrg(final MappingField mappingField) {
        super(mappingField.getOwner(), mappingField.getName(), null);
        this.srg = mappingField.getOwner() + "/" + mappingField.getName();
    }
    
    @Override
    public String serialise() {
        return this.srg;
    }
    
    private static String getNameFromSrg(final String s) {
        if (s == null) {
            return null;
        }
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex > -1) ? s.substring(lastIndex + 1) : s;
    }
    
    private static String getOwnerFromSrg(final String s) {
        if (s == null) {
            return null;
        }
        final int lastIndex = s.lastIndexOf(47);
        return (lastIndex > -1) ? s.substring(0, lastIndex) : null;
    }
}
