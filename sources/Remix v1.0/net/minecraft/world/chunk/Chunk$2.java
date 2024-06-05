package net.minecraft.world.chunk;

import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

class Chunk$2 implements Callable {
    final /* synthetic */ BlockPos val$pos;
    
    public String func_177455_a() {
        return CrashReportCategory.getCoordinateInfo(this.val$pos);
    }
    
    @Override
    public Object call() {
        return this.func_177455_a();
    }
}