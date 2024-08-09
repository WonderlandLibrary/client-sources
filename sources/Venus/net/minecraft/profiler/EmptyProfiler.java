/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import java.util.function.Supplier;
import net.minecraft.profiler.EmptyProfileResult;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IResultableProfiler;

public class EmptyProfiler
implements IResultableProfiler {
    public static final EmptyProfiler INSTANCE = new EmptyProfiler();

    private EmptyProfiler() {
    }

    @Override
    public void startTick() {
    }

    @Override
    public void endTick() {
    }

    @Override
    public void startSection(String string) {
    }

    @Override
    public void startSection(Supplier<String> supplier) {
    }

    @Override
    public void endSection() {
    }

    @Override
    public void endStartSection(String string) {
    }

    @Override
    public void endStartSection(Supplier<String> supplier) {
    }

    @Override
    public void func_230035_c_(String string) {
    }

    @Override
    public void func_230036_c_(Supplier<String> supplier) {
    }

    @Override
    public IProfileResult getResults() {
        return EmptyProfileResult.INSTANCE;
    }
}

