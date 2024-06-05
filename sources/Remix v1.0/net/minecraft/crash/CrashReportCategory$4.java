package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.block.state.*;

static final class CrashReportCategory$4 implements Callable {
    final /* synthetic */ IBlockState val$state;
    
    public String func_175753_a() {
        return this.val$state.toString();
    }
    
    @Override
    public Object call() {
        return this.func_175753_a();
    }
}