package net.minecraft.crash;

import java.util.concurrent.*;

static final class CrashReportCategory$2 implements Callable {
    final /* synthetic */ int val$blockData;
    
    @Override
    public String call() {
        if (this.val$blockData < 0) {
            return "Unknown? (Got " + this.val$blockData + ")";
        }
        final String var1 = String.format("%4s", Integer.toBinaryString(this.val$blockData)).replace(" ", "0");
        return String.format("%1$d / 0x%1$X / 0b%2$s", this.val$blockData, var1);
    }
}