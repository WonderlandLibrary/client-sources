/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ITickList;
import net.minecraft.world.TickPriority;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.storage.ChunkSerializer;

public class ChunkPrimerTickList<T>
implements ITickList<T> {
    protected final Predicate<T> filter;
    private final ChunkPos pos;
    private final ShortList[] packedPositions = new ShortList[16];

    public ChunkPrimerTickList(Predicate<T> predicate, ChunkPos chunkPos) {
        this(predicate, chunkPos, new ListNBT());
    }

    public ChunkPrimerTickList(Predicate<T> predicate, ChunkPos chunkPos, ListNBT listNBT) {
        this.filter = predicate;
        this.pos = chunkPos;
        for (int i = 0; i < listNBT.size(); ++i) {
            ListNBT listNBT2 = listNBT.getList(i);
            for (int j = 0; j < listNBT2.size(); ++j) {
                IChunk.getList(this.packedPositions, i).add(listNBT2.getShort(j));
            }
        }
    }

    public ListNBT write() {
        return ChunkSerializer.toNbt(this.packedPositions);
    }

    public void postProcess(ITickList<T> iTickList, Function<BlockPos, T> function) {
        for (int i = 0; i < this.packedPositions.length; ++i) {
            if (this.packedPositions[i] == null) continue;
            for (Short s : this.packedPositions[i]) {
                BlockPos blockPos = ChunkPrimer.unpackToWorld(s, i, this.pos);
                iTickList.scheduleTick(blockPos, function.apply(blockPos), 0);
            }
            this.packedPositions[i].clear();
        }
    }

    @Override
    public boolean isTickScheduled(BlockPos blockPos, T t) {
        return true;
    }

    @Override
    public void scheduleTick(BlockPos blockPos, T t, int n, TickPriority tickPriority) {
        IChunk.getList(this.packedPositions, blockPos.getY() >> 4).add(ChunkPrimer.packToLocal(blockPos));
    }

    @Override
    public boolean isTickPending(BlockPos blockPos, T t) {
        return true;
    }
}

