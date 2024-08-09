/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.LightDataMap;
import net.minecraft.world.lighting.SectionLightStorage;

public class BlockLightStorage
extends SectionLightStorage<StorageMap> {
    protected BlockLightStorage(IChunkLightProvider iChunkLightProvider) {
        super(LightType.BLOCK, iChunkLightProvider, new StorageMap(new Long2ObjectOpenHashMap<NibbleArray>()));
    }

    @Override
    protected int getLightOrDefault(long l) {
        long l2 = SectionPos.worldToSection(l);
        NibbleArray nibbleArray = this.getArray(l2, true);
        return nibbleArray == null ? 0 : nibbleArray.get(SectionPos.mask(BlockPos.unpackX(l)), SectionPos.mask(BlockPos.unpackY(l)), SectionPos.mask(BlockPos.unpackZ(l)));
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class StorageMap
    extends LightDataMap<StorageMap> {
        public StorageMap(Long2ObjectOpenHashMap<NibbleArray> long2ObjectOpenHashMap) {
            super(long2ObjectOpenHashMap);
        }

        @Override
        public StorageMap copy() {
            return new StorageMap((Long2ObjectOpenHashMap<NibbleArray>)this.arrays.clone());
        }

        @Override
        public LightDataMap copy() {
            return this.copy();
        }
    }
}

