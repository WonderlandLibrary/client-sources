package ClassSub;

public static class Class292
{
    final int numComponents;
    final boolean hasAlpha;
    
    
    private Class292(final int numComponents, final boolean hasAlpha) {
        this.numComponents = numComponents;
        this.hasAlpha = hasAlpha;
    }
    
    public int getNumComponents() {
        return this.numComponents;
    }
    
    public boolean isHasAlpha() {
        return this.hasAlpha;
    }
    
    Class292(final int n, final boolean b, final Class52 class52) {
        this(n, b);
    }
}
