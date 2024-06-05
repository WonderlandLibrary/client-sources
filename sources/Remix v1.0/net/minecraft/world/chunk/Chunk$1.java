package net.minecraft.world.chunk;

import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

class Chunk$1 implements Callable {
    final /* synthetic */ int val$x;
    final /* synthetic */ int val$y;
    final /* synthetic */ int val$z;
    
    @Override
    public String call() {
        return CrashReportCategory.getCoordinateInfo(new BlockPos(Chunk.this.xPosition * 16 + this.val$x, this.val$y, Chunk.this.zPosition * 16 + this.val$z));
    }
}