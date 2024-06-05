package net.minecraft.world;

import java.util.concurrent.*;
import net.minecraft.block.*;

class World$2 implements Callable {
    final /* synthetic */ Block val$blockIn;
    
    @Override
    public String call() {
        try {
            return String.format("ID #%d (%s // %s)", Block.getIdFromBlock(this.val$blockIn), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName());
        }
        catch (Throwable var2) {
            return "ID #" + Block.getIdFromBlock(this.val$blockIn);
        }
    }
}