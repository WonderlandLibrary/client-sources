package net.minecraft.src;

import java.util.concurrent.*;

final class CallableBlockLocation implements Callable
{
    final int blockXCoord;
    final int blockYCoord;
    final int blockZCoord;
    
    CallableBlockLocation(final int par1, final int par2, final int par3) {
        this.blockXCoord = par1;
        this.blockYCoord = par2;
        this.blockZCoord = par3;
    }
    
    public String callBlockLocationInfo() {
        return CrashReportCategory.getLocationInfo(this.blockXCoord, this.blockYCoord, this.blockZCoord);
    }
    
    @Override
    public Object call() {
        return this.callBlockLocationInfo();
    }
}
