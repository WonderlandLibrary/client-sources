package net.minecraft.block.state;

import com.google.common.base.*;
import java.util.*;
import net.minecraft.block.properties.*;

static final class BlockStateBase$1 implements Function {
    public String func_177225_a(final Map.Entry p_177225_1_) {
        if (p_177225_1_ == null) {
            return "<NULL>";
        }
        final IProperty var2 = p_177225_1_.getKey();
        return var2.getName() + "=" + var2.getName((Comparable)p_177225_1_.getValue());
    }
    
    public Object apply(final Object p_apply_1_) {
        return this.func_177225_a((Map.Entry)p_apply_1_);
    }
}