package net.minecraft.entity;

import java.util.concurrent.*;

class EntityTracker$1 implements Callable {
    final /* synthetic */ int val$p_72785_3_;
    
    @Override
    public String call() {
        String var1 = "Once per " + this.val$p_72785_3_ + " ticks";
        if (this.val$p_72785_3_ == Integer.MAX_VALUE) {
            var1 = "Maximum (" + var1 + ")";
        }
        return var1;
    }
}