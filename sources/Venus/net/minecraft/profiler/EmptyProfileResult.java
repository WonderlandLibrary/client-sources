/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import java.io.File;
import java.util.Collections;
import java.util.List;
import net.minecraft.profiler.DataPoint;
import net.minecraft.profiler.IProfileResult;

public class EmptyProfileResult
implements IProfileResult {
    public static final EmptyProfileResult INSTANCE = new EmptyProfileResult();

    private EmptyProfileResult() {
    }

    @Override
    public List<DataPoint> getDataPoints(String string) {
        return Collections.emptyList();
    }

    @Override
    public boolean writeToFile(File file) {
        return true;
    }

    @Override
    public long timeStop() {
        return 0L;
    }

    @Override
    public int ticksStop() {
        return 1;
    }

    @Override
    public long timeStart() {
        return 0L;
    }

    @Override
    public int ticksStart() {
        return 1;
    }
}

