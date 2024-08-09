/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.math.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

interface IDoubleListMerger {
    public DoubleList func_212435_a();

    public boolean forMergedIndexes(IConsumer var1);

    public static interface IConsumer {
        public boolean merge(int var1, int var2, int var3);
    }
}

