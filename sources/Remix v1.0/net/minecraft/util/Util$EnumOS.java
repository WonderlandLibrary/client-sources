package net.minecraft.util;

public enum EnumOS
{
    LINUX("LINUX", 0), 
    SOLARIS("SOLARIS", 1), 
    WINDOWS("WINDOWS", 2), 
    OSX("OSX", 3), 
    UNKNOWN("UNKNOWN", 4);
    
    private static final EnumOS[] $VALUES;
    
    private EnumOS(final String p_i1357_1_, final int p_i1357_2_) {
    }
    
    static {
        $VALUES = new EnumOS[] { EnumOS.LINUX, EnumOS.SOLARIS, EnumOS.WINDOWS, EnumOS.OSX, EnumOS.UNKNOWN };
    }
}
