package ClassSub;

import java.awt.*;

public enum Class15
{
    BLACK(-16711423), 
    BLUE(-12028161), 
    DARKBLUE(-12621684), 
    GREEN(-9830551), 
    DARKGREEN(-9320847), 
    WHITE(-65794), 
    AQUA(-7820064), 
    DARKAQUA(-12621684), 
    GREY(-9868951), 
    DARKGREY(-14342875), 
    RED(-65536), 
    DARKRED(-8388608), 
    ORANGE(-29696), 
    DARKORANGE(-2263808), 
    YELLOW(-256), 
    DARKYELLOW(-2702025), 
    MAGENTA(-18751), 
    DARKMAGENTA(-2252579);
    
    public int c;
    private static final Class15[] $VALUES;
    
    
    private Class15(final int c) {
        this.c = c;
    }
    
    public static int getColor(final Color color) {
        return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }
    
    public static int getColor(final int n) {
        return getColor(n, n, n, 255);
    }
    
    public static int getColor(final int n, final int n2) {
        return getColor(n, n, n, n2);
    }
    
    public static int getColor(final int n, final int n2, final int n3) {
        return getColor(n, n2, n3, 255);
    }
    
    public static int getColor(final int n, final int n2, final int n3, final int n4) {
        return 0x0 | n4 << 24 | n << 16 | n2 << 8 | n3;
    }
    
    static {
        $VALUES = new Class15[] { Class15.BLACK, Class15.BLUE, Class15.DARKBLUE, Class15.GREEN, Class15.DARKGREEN, Class15.WHITE, Class15.AQUA, Class15.DARKAQUA, Class15.GREY, Class15.DARKGREY, Class15.RED, Class15.DARKRED, Class15.ORANGE, Class15.DARKORANGE, Class15.YELLOW, Class15.DARKYELLOW, Class15.MAGENTA, Class15.DARKMAGENTA };
    }
}
