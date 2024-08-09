package dev.luvbeeq.baritone.utils.accessor;

import net.minecraft.util.BitArray;
import net.minecraft.util.palette.IPalette;

public interface IPalettedContainer<T> {

    IPalette<T> getPalette();

    BitArray getStorage();
}
