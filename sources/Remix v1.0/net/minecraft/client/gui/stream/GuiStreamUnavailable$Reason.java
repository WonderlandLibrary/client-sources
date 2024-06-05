package net.minecraft.client.gui.stream;

import net.minecraft.util.*;

public enum Reason
{
    NO_FBO("NO_FBO", 0, (IChatComponent)new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])), 
    LIBRARY_ARCH_MISMATCH("LIBRARY_ARCH_MISMATCH", 1, (IChatComponent)new ChatComponentTranslation("stream.unavailable.library_arch_mismatch", new Object[0])), 
    LIBRARY_FAILURE("LIBRARY_FAILURE", 2, (IChatComponent)new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])), 
    UNSUPPORTED_OS_WINDOWS("UNSUPPORTED_OS_WINDOWS", 3, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.windows", new Object[0])), 
    UNSUPPORTED_OS_MAC("UNSUPPORTED_OS_MAC", 4, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay", new Object[0])), 
    UNSUPPORTED_OS_OTHER("UNSUPPORTED_OS_OTHER", 5, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])), 
    ACCOUNT_NOT_MIGRATED("ACCOUNT_NOT_MIGRATED", 6, (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay", new Object[0])), 
    ACCOUNT_NOT_BOUND("ACCOUNT_NOT_BOUND", 7, (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_bound.okay", new Object[0])), 
    FAILED_TWITCH_AUTH("FAILED_TWITCH_AUTH", 8, (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0])), 
    FAILED_TWITCH_AUTH_ERROR("FAILED_TWITCH_AUTH_ERROR", 9, (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])), 
    INITIALIZATION_FAILURE("INITIALIZATION_FAILURE", 10, (IChatComponent)new ChatComponentTranslation("stream.unavailable.initialization_failure", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])), 
    UNKNOWN("UNKNOWN", 11, (IChatComponent)new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));
    
    private static final Reason[] $VALUES;
    private final IChatComponent field_152574_m;
    private final IChatComponent field_152575_n;
    
    private Reason(final String p_i1066_1_, final int p_i1066_2_, final IChatComponent p_i1066_3_) {
        this(p_i1066_1_, p_i1066_2_, p_i1066_3_, null);
    }
    
    private Reason(final String p_i1067_1_, final int p_i1067_2_, final IChatComponent p_i1067_3_, final IChatComponent p_i1067_4_) {
        this.field_152574_m = p_i1067_3_;
        this.field_152575_n = p_i1067_4_;
    }
    
    public IChatComponent func_152561_a() {
        return this.field_152574_m;
    }
    
    public IChatComponent func_152559_b() {
        return this.field_152575_n;
    }
    
    static {
        $VALUES = new Reason[] { Reason.NO_FBO, Reason.LIBRARY_ARCH_MISMATCH, Reason.LIBRARY_FAILURE, Reason.UNSUPPORTED_OS_WINDOWS, Reason.UNSUPPORTED_OS_MAC, Reason.UNSUPPORTED_OS_OTHER, Reason.ACCOUNT_NOT_MIGRATED, Reason.ACCOUNT_NOT_BOUND, Reason.FAILED_TWITCH_AUTH, Reason.FAILED_TWITCH_AUTH_ERROR, Reason.INITIALIZATION_FAILURE, Reason.UNKNOWN };
    }
}
