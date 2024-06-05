package net.minecraft.server.management;

import com.google.common.base.*;
import net.minecraft.util.*;

static final class PreYggdrasilConverter$1 implements Predicate {
    public boolean func_152733_a(final String p_152733_1_) {
        return !StringUtils.isNullOrEmpty(p_152733_1_);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_152733_a((String)p_apply_1_);
    }
}