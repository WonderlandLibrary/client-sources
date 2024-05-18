package org.spongepowered.asm.util;

public abstract static class ManifestAttributes
{
    public static final String TWEAKER = "TweakClass";
    public static final String MAINCLASS = "Main-Class";
    public static final String MIXINCONFIGS = "MixinConfigs";
    public static final String TOKENPROVIDERS = "MixinTokenProviders";
    @Deprecated
    public static final String COMPATIBILITY = "MixinCompatibilityLevel";
    
    private ManifestAttributes() {
        super();
    }
}
