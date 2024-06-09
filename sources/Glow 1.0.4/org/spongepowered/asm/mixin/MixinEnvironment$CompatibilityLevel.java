package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.*;

public enum CompatibilityLevel
{
    JAVA_6(6, 50, false), 
    JAVA_7(7, 51, false) {
        MixinEnvironment$CompatibilityLevel$1(final String s, final int n, final int n2, final int n3, final boolean b) {
        }
        
        @Override
        boolean isSupported() {
            return JavaVersion.current() >= 1.7;
        }
    }, 
    JAVA_8(8, 52, true) {
        MixinEnvironment$CompatibilityLevel$2(final String s, final int n, final int n2, final int n3, final boolean b) {
        }
        
        @Override
        boolean isSupported() {
            return JavaVersion.current() >= 1.8;
        }
    }, 
    JAVA_9(9, 53, true) {
        MixinEnvironment$CompatibilityLevel$3(final String s, final int n, final int n2, final int n3, final boolean b) {
        }
        
        @Override
        boolean isSupported() {
            return false;
        }
    };
    
    private static final int CLASS_V1_9 = 53;
    private final int ver;
    private final int classVersion;
    private final boolean supportsMethodsInInterfaces;
    private CompatibilityLevel maxCompatibleLevel;
    private static final CompatibilityLevel[] $VALUES;
    
    public static CompatibilityLevel[] values() {
        return CompatibilityLevel.$VALUES.clone();
    }
    
    public static CompatibilityLevel valueOf(final String s) {
        return Enum.valueOf(CompatibilityLevel.class, s);
    }
    
    private CompatibilityLevel(final int ver, final int classVersion, final boolean supportsMethodsInInterfaces) {
        this.ver = ver;
        this.classVersion = classVersion;
        this.supportsMethodsInInterfaces = supportsMethodsInInterfaces;
    }
    
    private void setMaxCompatibleLevel(final CompatibilityLevel maxCompatibleLevel) {
        this.maxCompatibleLevel = maxCompatibleLevel;
    }
    
    boolean isSupported() {
        return true;
    }
    
    public int classVersion() {
        return this.classVersion;
    }
    
    public boolean supportsMethodsInInterfaces() {
        return this.supportsMethodsInInterfaces;
    }
    
    public boolean isAtLeast(final CompatibilityLevel compatibilityLevel) {
        return compatibilityLevel == null || this.ver >= compatibilityLevel.ver;
    }
    
    public boolean canElevateTo(final CompatibilityLevel compatibilityLevel) {
        return compatibilityLevel == null || this.maxCompatibleLevel == null || compatibilityLevel.ver <= this.maxCompatibleLevel.ver;
    }
    
    public boolean canSupport(final CompatibilityLevel compatibilityLevel) {
        return compatibilityLevel == null || compatibilityLevel.canElevateTo(this);
    }
    
    CompatibilityLevel(final String s, final int n, final int n2, final int n3, final boolean b, final MixinEnvironment$1 object) {
        this(n2, n3, b);
    }
    
    static {
        $VALUES = new CompatibilityLevel[] { CompatibilityLevel.JAVA_6, CompatibilityLevel.JAVA_7, CompatibilityLevel.JAVA_8, CompatibilityLevel.JAVA_9 };
    }
}
