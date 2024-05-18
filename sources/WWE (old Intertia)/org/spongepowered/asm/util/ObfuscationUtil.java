package org.spongepowered.asm.util;

public abstract class ObfuscationUtil
{
    private ObfuscationUtil() {
        super();
    }
    
    public static String mapDescriptor(final String s, final IClassRemapper classRemapper) {
        return remapDescriptor(s, classRemapper, false);
    }
    
    public static String unmapDescriptor(final String s, final IClassRemapper classRemapper) {
        return remapDescriptor(s, classRemapper, true);
    }
    
    private static String remapDescriptor(final String s, final IClassRemapper classRemapper, final boolean b) {
        final StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = null;
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            if (sb2 != null) {
                if (char1 == ';') {
                    sb.append('L').append(remap(sb2.toString(), classRemapper, b)).append(';');
                    sb2 = null;
                }
                else {
                    sb2.append(char1);
                }
            }
            else if (char1 == 'L') {
                sb2 = new StringBuilder();
            }
            else {
                sb.append(char1);
            }
        }
        if (sb2 != null) {
            throw new IllegalArgumentException("Invalid descriptor '" + s + "', missing ';'");
        }
        return sb.toString();
    }
    
    private static Object remap(final String s, final IClassRemapper classRemapper, final boolean b) {
        final String s2 = b ? classRemapper.unmap(s) : classRemapper.map(s);
        return (s2 != null) ? s2 : s;
    }
    
    public interface IClassRemapper
    {
        String map(final String p0);
        
        String unmap(final String p0);
    }
}
