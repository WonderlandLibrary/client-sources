package org.spongepowered.asm.mixin.injection.invoke;

static class ConstructorRedirectData
{
    public static final String KEY = "ctor";
    public boolean wildcard;
    public int injected;
    
    ConstructorRedirectData() {
        super();
        this.wildcard = false;
        this.injected = 0;
    }
}
