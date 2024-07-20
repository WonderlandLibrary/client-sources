/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import net.minecraft.profiler.Snooper;

public interface ISnooperInfo {
    public void addServerStatsToSnooper(Snooper var1);

    public void addServerTypeToSnooper(Snooper var1);

    public boolean isSnooperEnabled();
}

