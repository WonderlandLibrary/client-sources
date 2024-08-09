/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import java.io.File;
import java.util.List;
import net.minecraft.profiler.DataPoint;

public interface IProfileResult {
    public List<DataPoint> getDataPoints(String var1);

    public boolean writeToFile(File var1);

    public long timeStop();

    public int ticksStop();

    public long timeStart();

    public int ticksStart();

    default public long nanoTime() {
        return this.timeStart() - this.timeStop();
    }

    default public int ticksSpend() {
        return this.ticksStart() - this.ticksStop();
    }

    public static String decodePath(String string) {
        return string.replace('\u001e', '.');
    }
}

