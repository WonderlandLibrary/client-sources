package net.minecraft.client.gui.fonts.providers;

import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.client.gui.fonts.IGlyphInfo;

import javax.annotation.Nullable;
import java.io.Closeable;

public interface IGlyphProvider extends Closeable
{
default void close()
    {
    }

    @Nullable

default IGlyphInfo getGlyphInfo(int character)
    {
        return null;
    }

    IntSet func_230428_a_();
}
