package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

public enum Side
{
    UNKNOWN {
        MixinEnvironment$Side$1(final String s, final int n) {
        }
        
        @Override
        protected boolean detect() {
            return false;
        }
    }, 
    CLIENT {
        MixinEnvironment$Side$2(final String s, final int n) {
        }
        
        @Override
        protected boolean detect() {
            return "CLIENT".equals(MixinService.getService().getSideName());
        }
    }, 
    SERVER {
        MixinEnvironment$Side$3(final String s, final int n) {
        }
        
        @Override
        protected boolean detect() {
            final String sideName = MixinService.getService().getSideName();
            return "SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName);
        }
    };
    
    private static final Side[] $VALUES;
    
    public static Side[] values() {
        return Side.$VALUES.clone();
    }
    
    public static Side valueOf(final String s) {
        return Enum.valueOf(Side.class, s);
    }
    
    protected abstract boolean detect();
    
    Side(final String s, final int n, final MixinEnvironment$1 object) {
        this();
    }
    
    static {
        $VALUES = new Side[] { Side.UNKNOWN, Side.CLIENT, Side.SERVER };
    }
}
