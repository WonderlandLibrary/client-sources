/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.palette;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;

public interface IPalette<T> {
    public int idFor(T var1);

    public boolean func_230341_a_(Predicate<T> var1);

    @Nullable
    public T get(int var1);

    public void read(PacketBuffer var1);

    public void write(PacketBuffer var1);

    public int getSerializedSize();

    public void read(ListNBT var1);
}

