package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.util.*;

static final class CrashReportCategory$5 implements Callable {
    final /* synthetic */ BlockPos val$pos;
    
    public String func_175751_a() {
        return CrashReportCategory.getCoordinateInfo(this.val$pos);
    }
    
    @Override
    public Object call() {
        return this.func_175751_a();
    }
}