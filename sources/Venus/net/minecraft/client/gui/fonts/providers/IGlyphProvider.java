/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.Closeable;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.IGlyphInfo;

public interface IGlyphProvider
extends Closeable {
    @Override
    default public void close() {
    }

    @Nullable
    default public IGlyphInfo getGlyphInfo(int n) {
        return null;
    }

    public IntSet func_230428_a_();
}

