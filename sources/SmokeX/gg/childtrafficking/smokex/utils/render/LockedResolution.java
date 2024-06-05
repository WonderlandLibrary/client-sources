// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.render;

public final class LockedResolution
{
    public static final int SCALE_FACTOR = 2;
    private final int width;
    private final int height;
    
    public LockedResolution(final int width, final int height) {
        this.width = width;
        this.height = height;
    }
    
    public int width() {
        return this.width;
    }
    
    public int height() {
        return this.height;
    }
}
