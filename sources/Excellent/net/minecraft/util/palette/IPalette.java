package net.minecraft.util.palette;

import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public interface IPalette<T>
{
    int idFor(T state);

    boolean func_230341_a_(Predicate<T> p_230341_1_);

    @Nullable

    /**
     * Gets the block state by the palette id.
     */
    T get(int indexKey);

    void read(PacketBuffer buf);

    void write(PacketBuffer buf);

    int getSerializedSize();

    void read(ListNBT nbt);
}
