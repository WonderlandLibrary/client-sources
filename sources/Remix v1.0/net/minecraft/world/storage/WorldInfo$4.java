package net.minecraft.world.storage;

import java.util.concurrent.*;
import net.minecraft.crash.*;

class WorldInfo$4 implements Callable {
    @Override
    public String call() {
        return CrashReportCategory.getCoordinateInfo(WorldInfo.access$300(WorldInfo.this), WorldInfo.access$400(WorldInfo.this), WorldInfo.access$500(WorldInfo.this));
    }
}