/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ITickList;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.TickPriority;

public class SerializableTickList<T>
implements ITickList<T> {
    private final List<TickHolder<T>> ticks;
    private final Function<T, ResourceLocation> toId;

    public SerializableTickList(Function<T, ResourceLocation> function, List<NextTickListEntry<T>> list, long l) {
        this(function, list.stream().map(arg_0 -> SerializableTickList.lambda$new$0(l, arg_0)).collect(Collectors.toList()));
    }

    private SerializableTickList(Function<T, ResourceLocation> function, List<TickHolder<T>> list) {
        this.ticks = list;
        this.toId = function;
    }

    @Override
    public boolean isTickScheduled(BlockPos blockPos, T t) {
        return true;
    }

    @Override
    public void scheduleTick(BlockPos blockPos, T t, int n, TickPriority tickPriority) {
        this.ticks.add(new TickHolder<T>(t, blockPos, n, tickPriority));
    }

    @Override
    public boolean isTickPending(BlockPos blockPos, T t) {
        return true;
    }

    public ListNBT func_234857_b_() {
        ListNBT listNBT = new ListNBT();
        for (TickHolder<T> tickHolder : this.ticks) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("i", this.toId.apply(tickHolder.field_234861_d_).toString());
            compoundNBT.putInt("x", tickHolder.field_234858_a_.getX());
            compoundNBT.putInt("y", tickHolder.field_234858_a_.getY());
            compoundNBT.putInt("z", tickHolder.field_234858_a_.getZ());
            compoundNBT.putInt("t", tickHolder.field_234859_b_);
            compoundNBT.putInt("p", tickHolder.field_234860_c_.getPriority());
            listNBT.add(compoundNBT);
        }
        return listNBT;
    }

    public static <T> SerializableTickList<T> create(ListNBT listNBT, Function<T, ResourceLocation> function, Function<ResourceLocation, T> function2) {
        ArrayList<TickHolder<T>> arrayList = Lists.newArrayList();
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundNBT compoundNBT = listNBT.getCompound(i);
            T t = function2.apply(new ResourceLocation(compoundNBT.getString("i")));
            if (t == null) continue;
            BlockPos blockPos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z"));
            arrayList.add(new TickHolder<T>(t, blockPos, compoundNBT.getInt("t"), TickPriority.getPriority(compoundNBT.getInt("p"))));
        }
        return new SerializableTickList<T>(function, arrayList);
    }

    public void func_234855_a_(ITickList<T> iTickList) {
        this.ticks.forEach(arg_0 -> SerializableTickList.lambda$func_234855_a_$1(iTickList, arg_0));
    }

    private static void lambda$func_234855_a_$1(ITickList iTickList, TickHolder tickHolder) {
        iTickList.scheduleTick(tickHolder.field_234858_a_, tickHolder.field_234861_d_, tickHolder.field_234859_b_, tickHolder.field_234860_c_);
    }

    private static TickHolder lambda$new$0(long l, NextTickListEntry nextTickListEntry) {
        return new TickHolder(nextTickListEntry.getTarget(), nextTickListEntry.position, (int)(nextTickListEntry.field_235017_b_ - l), nextTickListEntry.priority);
    }

    static class TickHolder<T> {
        private final T field_234861_d_;
        public final BlockPos field_234858_a_;
        public final int field_234859_b_;
        public final TickPriority field_234860_c_;

        private TickHolder(T t, BlockPos blockPos, int n, TickPriority tickPriority) {
            this.field_234861_d_ = t;
            this.field_234858_a_ = blockPos;
            this.field_234859_b_ = n;
            this.field_234860_c_ = tickPriority;
        }

        public String toString() {
            return this.field_234861_d_ + ": " + this.field_234858_a_ + ", " + this.field_234859_b_ + ", " + this.field_234860_c_;
        }
    }
}

