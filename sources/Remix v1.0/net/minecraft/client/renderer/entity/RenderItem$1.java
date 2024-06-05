package net.minecraft.client.renderer.entity;

import java.util.concurrent.*;
import net.minecraft.item.*;

class RenderItem$1 implements Callable {
    final /* synthetic */ ItemStack val$p_180450_1_;
    
    @Override
    public String call() {
        return String.valueOf(this.val$p_180450_1_.getItem());
    }
}