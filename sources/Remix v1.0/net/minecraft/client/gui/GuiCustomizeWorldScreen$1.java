package net.minecraft.client.gui;

import com.google.common.base.*;
import com.google.common.primitives.*;

class GuiCustomizeWorldScreen$1 implements Predicate {
    public boolean func_178956_a(final String p_178956_1_) {
        final Float var2 = Floats.tryParse(p_178956_1_);
        return p_178956_1_.length() == 0 || (var2 != null && Floats.isFinite((float)var2) && var2 >= 0.0f);
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_178956_a((String)p_apply_1_);
    }
}