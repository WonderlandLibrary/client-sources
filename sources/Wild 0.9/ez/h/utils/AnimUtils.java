package ez.h.utils;

public class AnimUtils
{
    protected static bib mc;
    
    static {
        AnimUtils.mc = bib.z();
    }
    
    public static float lerp(final float n, final float n2, final float n3) {
        return n + (n2 - n) * n3;
    }
    
    public static float getFixedSpeed(final float n) {
        return AnimUtils.mc.Y.renderPartialTicks / bib.af() * n;
    }
}
