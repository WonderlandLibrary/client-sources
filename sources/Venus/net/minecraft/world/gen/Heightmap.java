/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.gen;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.BitArray;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.IChunk;

public class Heightmap {
    private static final Predicate<BlockState> IS_NOT_AIR = Heightmap::lambda$static$0;
    private static final Predicate<BlockState> BLOCKS_MOVEMENT = Heightmap::lambda$static$1;
    private final BitArray data = new BitArray(9, 256);
    private final Predicate<BlockState> heightLimitPredicate;
    private final IChunk chunk;

    public Heightmap(IChunk iChunk, Type type) {
        this.heightLimitPredicate = type.getHeightLimitPredicate();
        this.chunk = iChunk;
    }

    public static void updateChunkHeightmaps(IChunk iChunk, Set<Type> set) {
        int n = set.size();
        ObjectArrayList objectArrayList = new ObjectArrayList(n);
        Iterator iterator2 = objectArrayList.iterator();
        int n2 = iChunk.getTopFilledSegment() + 16;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            block1: for (int j = 0; j < 16; ++j) {
                for (Type type : set) {
                    objectArrayList.add(iChunk.getHeightmap(type));
                }
                for (int k = n2 - 1; k >= 0; --k) {
                    mutable.setPos(i, k, j);
                    BlockState blockState = iChunk.getBlockState(mutable);
                    if (blockState.isIn(Blocks.AIR)) continue;
                    while (iterator2.hasNext()) {
                        Heightmap heightmap = (Heightmap)iterator2.next();
                        if (!heightmap.heightLimitPredicate.test(blockState)) continue;
                        heightmap.set(i, j, k + 1);
                        iterator2.remove();
                    }
                    if (objectArrayList.isEmpty()) continue block1;
                    iterator2.back(n);
                }
            }
        }
    }

    public boolean update(int n, int n2, int n3, BlockState blockState) {
        int n4 = this.getHeight(n, n3);
        if (n2 <= n4 - 2) {
            return true;
        }
        if (this.heightLimitPredicate.test(blockState)) {
            if (n2 >= n4) {
                this.set(n, n3, n2 + 1);
                return false;
            }
        } else if (n4 - 1 == n2) {
            BlockPos.Mutable mutable = new BlockPos.Mutable();
            for (int i = n2 - 1; i >= 0; --i) {
                mutable.setPos(n, i, n3);
                if (!this.heightLimitPredicate.test(this.chunk.getBlockState(mutable))) continue;
                this.set(n, n3, i + 1);
                return false;
            }
            this.set(n, n3, 0);
            return false;
        }
        return true;
    }

    public int getHeight(int n, int n2) {
        return this.getHeight(Heightmap.getDataArrayIndex(n, n2));
    }

    private int getHeight(int n) {
        return this.data.getAt(n);
    }

    private void set(int n, int n2, int n3) {
        this.data.setAt(Heightmap.getDataArrayIndex(n, n2), n3);
    }

    public void setDataArray(long[] lArray) {
        System.arraycopy(lArray, 0, this.data.getBackingLongArray(), 0, lArray.length);
    }

    public long[] getDataArray() {
        return this.data.getBackingLongArray();
    }

    private static int getDataArrayIndex(int n, int n2) {
        return n + n2 * 16;
    }

    private static boolean lambda$static$1(BlockState blockState) {
        return blockState.getMaterial().blocksMovement();
    }

    private static boolean lambda$static$0(BlockState blockState) {
        return !blockState.isAir();
    }

    public static enum Type implements IStringSerializable
    {
        WORLD_SURFACE_WG("WORLD_SURFACE_WG", Usage.WORLDGEN, IS_NOT_AIR),
        WORLD_SURFACE("WORLD_SURFACE", Usage.CLIENT, IS_NOT_AIR),
        OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Usage.WORLDGEN, BLOCKS_MOVEMENT),
        OCEAN_FLOOR("OCEAN_FLOOR", Usage.LIVE_WORLD, BLOCKS_MOVEMENT),
        MOTION_BLOCKING("MOTION_BLOCKING", Usage.CLIENT, Type::lambda$static$0),
        MOTION_BLOCKING_NO_LEAVES("MOTION_BLOCKING_NO_LEAVES", Usage.LIVE_WORLD, Type::lambda$static$1);

        public static final Codec<Type> field_236078_g_;
        private final String id;
        private final Usage usage;
        private final Predicate<BlockState> heightLimitPredicate;
        private static final Map<String, Type> BY_ID;

        private Type(String string2, Usage usage, Predicate<BlockState> predicate) {
            this.id = string2;
            this.usage = usage;
            this.heightLimitPredicate = predicate;
        }

        public String getId() {
            return this.id;
        }

        public boolean isUsageClient() {
            return this.usage == Usage.CLIENT;
        }

        public boolean isUsageNotWorldgen() {
            return this.usage != Usage.WORLDGEN;
        }

        @Nullable
        public static Type getTypeFromId(String string) {
            return BY_ID.get(string);
        }

        public Predicate<BlockState> getHeightLimitPredicate() {
            return this.heightLimitPredicate;
        }

        @Override
        public String getString() {
            return this.id;
        }

        private static void lambda$static$2(HashMap hashMap) {
            for (Type type : Type.values()) {
                hashMap.put(type.id, type);
            }
        }

        private static boolean lambda$static$1(BlockState blockState) {
            return (blockState.getMaterial().blocksMovement() || !blockState.getFluidState().isEmpty()) && !(blockState.getBlock() instanceof LeavesBlock);
        }

        private static boolean lambda$static$0(BlockState blockState) {
            return blockState.getMaterial().blocksMovement() || !blockState.getFluidState().isEmpty();
        }

        static {
            field_236078_g_ = IStringSerializable.createEnumCodec(Type::values, Type::getTypeFromId);
            BY_ID = Util.make(Maps.newHashMap(), Type::lambda$static$2);
        }
    }

    public static enum Usage {
        WORLDGEN,
        LIVE_WORLD,
        CLIENT;

    }
}

