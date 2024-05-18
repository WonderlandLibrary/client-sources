package org.spongepowered.asm.mixin.injection.invoke;

static class ConstructorRedirectData
{
    public static final String KEY = "ctor";
    public int injected;
    
    ConstructorRedirectData() {
        super();
        this.injected = 0;
    }
}
