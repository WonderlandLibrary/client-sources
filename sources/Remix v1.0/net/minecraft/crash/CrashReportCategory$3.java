package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.util.*;

static final class CrashReportCategory$3 implements Callable {
    final /* synthetic */ BlockPos val$pos;
    
    @Override
    public String call() {
        return CrashReportCategory.getCoordinateInfo(this.val$pos);
    }
}