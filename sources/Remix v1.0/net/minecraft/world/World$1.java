package net.minecraft.world;

import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

class World$1 implements Callable {
    final /* synthetic */ BlockPos val$pos;
    
    @Override
    public String call() {
        return CrashReportCategory.getCoordinateInfo(this.val$pos);
    }
}