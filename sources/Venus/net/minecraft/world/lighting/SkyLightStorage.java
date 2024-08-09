/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.LightDataMap;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.lighting.NibbleArrayRepeater;
import net.minecraft.world.lighting.SectionLightStorage;

public class SkyLightStorage
extends SectionLightStorage<StorageMap> {
    private static final Direction[] field_215554_k = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private final LongSet sectionsWithLight = new LongOpenHashSet();
    private final LongSet pendingAdditions = new LongOpenHashSet();
    private final LongSet pendingRemovals = new LongOpenHashSet();
    private final LongSet enabledColumns = new LongOpenHashSet();
    private volatile boolean hasPendingUpdates;

    protected SkyLightStorage(IChunkLightProvider iChunkLightProvider) {
        super(LightType.SKY, iChunkLightProvider, new StorageMap(new Long2ObjectOpenHashMap<NibbleArray>(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
    }

    @Override
    protected int getLightOrDefault(long l) {
        long l2 = SectionPos.worldToSection(l);
        int n = SectionPos.extractY(l2);
        StorageMap storageMap = (StorageMap)this.uncachedLightData;
        int n2 = storageMap.surfaceSections.get(SectionPos.toSectionColumnPos(l2));
        if (n2 != storageMap.minY && n < n2) {
            NibbleArray nibbleArray = this.getArray(storageMap, l2);
            if (nibbleArray == null) {
                l = BlockPos.atSectionBottomY(l);
                while (nibbleArray == null) {
                    l2 = SectionPos.withOffset(l2, Direction.UP);
                    if (++n >= n2) {
                        return 0;
                    }
                    l = BlockPos.offset(l, 0, 16, 0);
                    nibbleArray = this.getArray(storageMap, l2);
                }
            }
            return nibbleArray.get(SectionPos.mask(BlockPos.unpackX(l)), SectionPos.mask(BlockPos.unpackY(l)), SectionPos.mask(BlockPos.unpackZ(l)));
        }
        return 0;
    }

    @Override
    protected void addSection(long l) {
        long l2;
        int n;
        int n2 = SectionPos.extractY(l);
        if (((StorageMap)this.cachedLightData).minY > n2) {
            ((StorageMap)this.cachedLightData).minY = n2;
            ((StorageMap)this.cachedLightData).surfaceSections.defaultReturnValue(((StorageMap)this.cachedLightData).minY);
        }
        if ((n = ((StorageMap)this.cachedLightData).surfaceSections.get(l2 = SectionPos.toSectionColumnPos(l))) < n2 + 1) {
            ((StorageMap)this.cachedLightData).surfaceSections.put(l2, n2 + 1);
            if (this.enabledColumns.contains(l2)) {
                this.scheduleFullUpdate(l);
                if (n > ((StorageMap)this.cachedLightData).minY) {
                    long l3 = SectionPos.asLong(SectionPos.extractX(l), n - 1, SectionPos.extractZ(l));
                    this.scheduleSurfaceUpdate(l3);
                }
                this.updateHasPendingUpdates();
            }
        }
    }

    private void scheduleSurfaceUpdate(long l) {
        this.pendingRemovals.add(l);
        this.pendingAdditions.remove(l);
    }

    private void scheduleFullUpdate(long l) {
        this.pendingAdditions.add(l);
        this.pendingRemovals.remove(l);
    }

    private void updateHasPendingUpdates() {
        this.hasPendingUpdates = !this.pendingAdditions.isEmpty() || !this.pendingRemovals.isEmpty();
    }

    @Override
    protected void removeSection(long l) {
        long l2 = SectionPos.toSectionColumnPos(l);
        boolean bl = this.enabledColumns.contains(l2);
        if (bl) {
            this.scheduleSurfaceUpdate(l);
        }
        int n = SectionPos.extractY(l);
        if (((StorageMap)this.cachedLightData).surfaceSections.get(l2) == n + 1) {
            long l3 = l;
            while (!this.hasSection(l3) && this.isAboveBottom(n)) {
                --n;
                l3 = SectionPos.withOffset(l3, Direction.DOWN);
            }
            if (this.hasSection(l3)) {
                ((StorageMap)this.cachedLightData).surfaceSections.put(l2, n + 1);
                if (bl) {
                    this.scheduleFullUpdate(l3);
                }
            } else {
                ((StorageMap)this.cachedLightData).surfaceSections.remove(l2);
            }
        }
        if (bl) {
            this.updateHasPendingUpdates();
        }
    }

    @Override
    protected void setColumnEnabled(long l, boolean bl) {
        this.processAllLevelUpdates();
        if (bl && this.enabledColumns.add(l)) {
            int n = ((StorageMap)this.cachedLightData).surfaceSections.get(l);
            if (n != ((StorageMap)this.cachedLightData).minY) {
                long l2 = SectionPos.asLong(SectionPos.extractX(l), n - 1, SectionPos.extractZ(l));
                this.scheduleFullUpdate(l2);
                this.updateHasPendingUpdates();
            }
        } else if (!bl) {
            this.enabledColumns.remove(l);
        }
    }

    @Override
    protected boolean hasSectionsToUpdate() {
        return super.hasSectionsToUpdate() || this.hasPendingUpdates;
    }

    @Override
    protected NibbleArray getOrCreateArray(long l) {
        NibbleArray nibbleArray = (NibbleArray)this.newArrays.get(l);
        if (nibbleArray != null) {
            return nibbleArray;
        }
        long l2 = SectionPos.withOffset(l, Direction.UP);
        int n = ((StorageMap)this.cachedLightData).surfaceSections.get(SectionPos.toSectionColumnPos(l));
        if (n != ((StorageMap)this.cachedLightData).minY && SectionPos.extractY(l2) < n) {
            NibbleArray nibbleArray2;
            while ((nibbleArray2 = this.getArray(l2, false)) == null) {
                l2 = SectionPos.withOffset(l2, Direction.UP);
            }
            return new NibbleArray(new NibbleArrayRepeater(nibbleArray2, 0).getData());
        }
        return new NibbleArray();
    }

    @Override
    protected void updateSections(LightEngine<StorageMap, ?> lightEngine, boolean bl, boolean bl2) {
        super.updateSections(lightEngine, bl, bl2);
        if (bl) {
            int n;
            int n2;
            long l;
            LongIterator longIterator;
            if (!this.pendingAdditions.isEmpty()) {
                longIterator = this.pendingAdditions.iterator();
                while (longIterator.hasNext()) {
                    int n3;
                    l = (Long)longIterator.next();
                    n2 = this.getLevel(l);
                    if (n2 == 2 || this.pendingRemovals.contains(l) || !this.sectionsWithLight.add(l)) continue;
                    if (n2 == 1) {
                        long l2;
                        this.cancelSectionUpdates(lightEngine, l);
                        if (this.dirtyCachedSections.add(l)) {
                            ((StorageMap)this.cachedLightData).copyArray(l);
                        }
                        Arrays.fill(this.getArray(l, false).getData(), (byte)-1);
                        n = SectionPos.toWorld(SectionPos.extractX(l));
                        n3 = SectionPos.toWorld(SectionPos.extractY(l));
                        int n4 = SectionPos.toWorld(SectionPos.extractZ(l));
                        Direction[] directionArray = field_215554_k;
                        int n5 = directionArray.length;
                        for (int i = 0; i < n5; ++i) {
                            Direction direction = directionArray[i];
                            l2 = SectionPos.withOffset(l, direction);
                            if (!this.pendingRemovals.contains(l2) && (this.sectionsWithLight.contains(l2) || this.pendingAdditions.contains(l2)) || !this.hasSection(l2)) continue;
                            for (int j = 0; j < 16; ++j) {
                                for (int k = 0; k < 16; ++k) {
                                    long l3;
                                    long l4 = switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                                        case 1 -> {
                                            l3 = BlockPos.pack(n + j, n3 + k, n4);
                                            yield BlockPos.pack(n + j, n3 + k, n4 - 1);
                                        }
                                        case 2 -> {
                                            l3 = BlockPos.pack(n + j, n3 + k, n4 + 16 - 1);
                                            yield BlockPos.pack(n + j, n3 + k, n4 + 16);
                                        }
                                        case 3 -> {
                                            l3 = BlockPos.pack(n, n3 + j, n4 + k);
                                            yield BlockPos.pack(n - 1, n3 + j, n4 + k);
                                        }
                                        default -> {
                                            l3 = BlockPos.pack(n + 16 - 1, n3 + j, n4 + k);
                                            yield BlockPos.pack(n + 16, n3 + j, n4 + k);
                                        }
                                    };
                                    lightEngine.scheduleUpdate(l3, l4, lightEngine.getEdgeLevel(l3, l4, 0), false);
                                }
                            }
                        }
                        for (int i = 0; i < 16; ++i) {
                            for (n5 = 0; n5 < 16; ++n5) {
                                long l5 = BlockPos.pack(SectionPos.toWorld(SectionPos.extractX(l)) + i, SectionPos.toWorld(SectionPos.extractY(l)), SectionPos.toWorld(SectionPos.extractZ(l)) + n5);
                                l2 = BlockPos.pack(SectionPos.toWorld(SectionPos.extractX(l)) + i, SectionPos.toWorld(SectionPos.extractY(l)) - 1, SectionPos.toWorld(SectionPos.extractZ(l)) + n5);
                                lightEngine.scheduleUpdate(l5, l2, lightEngine.getEdgeLevel(l5, l2, 0), false);
                            }
                        }
                        continue;
                    }
                    for (n = 0; n < 16; ++n) {
                        for (n3 = 0; n3 < 16; ++n3) {
                            long l6 = BlockPos.pack(SectionPos.toWorld(SectionPos.extractX(l)) + n, SectionPos.toWorld(SectionPos.extractY(l)) + 16 - 1, SectionPos.toWorld(SectionPos.extractZ(l)) + n3);
                            lightEngine.scheduleUpdate(Long.MAX_VALUE, l6, 0, false);
                        }
                    }
                }
            }
            this.pendingAdditions.clear();
            if (!this.pendingRemovals.isEmpty()) {
                longIterator = this.pendingRemovals.iterator();
                while (longIterator.hasNext()) {
                    l = (Long)longIterator.next();
                    if (!this.sectionsWithLight.remove(l) || !this.hasSection(l)) continue;
                    for (n2 = 0; n2 < 16; ++n2) {
                        for (n = 0; n < 16; ++n) {
                            long l7 = BlockPos.pack(SectionPos.toWorld(SectionPos.extractX(l)) + n2, SectionPos.toWorld(SectionPos.extractY(l)) + 16 - 1, SectionPos.toWorld(SectionPos.extractZ(l)) + n);
                            lightEngine.scheduleUpdate(Long.MAX_VALUE, l7, 15, true);
                        }
                    }
                }
            }
            this.pendingRemovals.clear();
            this.hasPendingUpdates = false;
        }
    }

    protected boolean isAboveBottom(int n) {
        return n >= ((StorageMap)this.cachedLightData).minY;
    }

    protected boolean func_215551_l(long l) {
        int n = BlockPos.unpackY(l);
        if ((n & 0xF) != 15) {
            return true;
        }
        long l2 = SectionPos.worldToSection(l);
        long l3 = SectionPos.toSectionColumnPos(l2);
        if (!this.enabledColumns.contains(l3)) {
            return true;
        }
        int n2 = ((StorageMap)this.cachedLightData).surfaceSections.get(l3);
        return SectionPos.toWorld(n2) == n + 16;
    }

    protected boolean isAboveWorld(long l) {
        long l2 = SectionPos.toSectionColumnPos(l);
        int n = ((StorageMap)this.cachedLightData).surfaceSections.get(l2);
        return n == ((StorageMap)this.cachedLightData).minY || SectionPos.extractY(l) >= n;
    }

    protected boolean isSectionEnabled(long l) {
        long l2 = SectionPos.toSectionColumnPos(l);
        return this.enabledColumns.contains(l2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class StorageMap
    extends LightDataMap<StorageMap> {
        private int minY;
        private final Long2IntOpenHashMap surfaceSections;

        public StorageMap(Long2ObjectOpenHashMap<NibbleArray> long2ObjectOpenHashMap, Long2IntOpenHashMap long2IntOpenHashMap, int n) {
            super(long2ObjectOpenHashMap);
            this.surfaceSections = long2IntOpenHashMap;
            long2IntOpenHashMap.defaultReturnValue(n);
            this.minY = n;
        }

        @Override
        public StorageMap copy() {
            return new StorageMap((Long2ObjectOpenHashMap<NibbleArray>)this.arrays.clone(), this.surfaceSections.clone(), this.minY);
        }

        @Override
        public LightDataMap copy() {
            return this.copy();
        }
    }
}

