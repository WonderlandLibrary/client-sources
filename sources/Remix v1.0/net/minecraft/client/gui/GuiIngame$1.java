package net.minecraft.client.gui;

import com.google.common.base.*;
import net.minecraft.scoreboard.*;

class GuiIngame$1 implements Predicate {
    public boolean func_178903_a(final Score p_178903_1_) {
        return p_178903_1_.getPlayerName() != null && !p_178903_1_.getPlayerName().startsWith("#");
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_178903_a((Score)p_apply_1_);
    }
}