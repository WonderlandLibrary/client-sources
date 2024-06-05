package net.minecraft.world.chunk;

import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

class Chunk$3 implements Callable {
    final /* synthetic */ BlockPos val$pos;
    
    public String func_177448_a() {
        return CrashReportCategory.getCoordinateInfo(this.val$pos);
    }
    
    @Override
    public Object call() {
        return this.func_177448_a();
    }
}