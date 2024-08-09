/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.DefaultGlyph;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;

public class DefaultGlyphProvider
implements IGlyphProvider {
    @Override
    @Nullable
    public IGlyphInfo getGlyphInfo(int n) {
        return DefaultGlyph.INSTANCE;
    }

    @Override
    public IntSet func_230428_a_() {
        return IntSets.EMPTY_SET;
    }
}

