package net.minecraft.server.management;

import com.mojang.authlib.*;
import java.util.*;

class ProfileEntry
{
    private final GameProfile field_152672_b;
    private final Date field_152673_c;
    
    private ProfileEntry(final GameProfile p_i46333_2_, final Date p_i46333_3_) {
        this.field_152672_b = p_i46333_2_;
        this.field_152673_c = p_i46333_3_;
    }
    
    ProfileEntry(final PlayerProfileCache this$0, final GameProfile p_i1166_2_, final Date p_i1166_3_, final Object p_i1166_4_) {
        this(this$0, p_i1166_2_, p_i1166_3_);
    }
    
    public GameProfile func_152668_a() {
        return this.field_152672_b;
    }
    
    public Date func_152670_b() {
        return this.field_152673_c;
    }
}
