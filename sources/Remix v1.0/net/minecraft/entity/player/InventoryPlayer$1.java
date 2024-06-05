package net.minecraft.entity.player;

import java.util.concurrent.*;
import net.minecraft.item.*;

class InventoryPlayer$1 implements Callable {
    final /* synthetic */ ItemStack val$p_70441_1_;
    
    @Override
    public String call() {
        return this.val$p_70441_1_.getDisplayName();
    }
}