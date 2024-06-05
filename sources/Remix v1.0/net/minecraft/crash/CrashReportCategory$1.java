package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.block.*;

static final class CrashReportCategory$1 implements Callable {
    final /* synthetic */ int val$var4;
    final /* synthetic */ Block val$blockIn;
    
    @Override
    public String call() {
        try {
            return String.format("ID #%d (%s // %s)", this.val$var4, this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName());
        }
        catch (Throwable var2) {
            return "ID #" + this.val$var4;
        }
    }
}