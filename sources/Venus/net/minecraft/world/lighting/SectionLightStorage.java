/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.Direction;
import net.minecraft.util.SectionDistanceGraph;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.LightDataMap;
import net.minecraft.world.lighting.LightEngine;

public abstract class SectionLightStorage<M extends LightDataMap<M>>
extends SectionDistanceGraph {
    protected static final NibbleArray EMPTY_ARRAY = new NibbleArray();
    private static final Direction[] DIRECTIONS = Direction.values();
    private final LightType type;
    private final IChunkLightProvider chunkProvider;
    protected final LongSet activeLightSections = new LongOpenHashSet();
    protected final LongSet addedEmptySections = new LongOpenHashSet();
    protected final LongSet addedActiveLightSections = new LongOpenHashSet();
    protected volatile M uncachedLightData;
    protected final M cachedLightData;
    protected final LongSet dirtyCachedSections = new LongOpenHashSet();
    protected final LongSet changedLightPositions = new LongOpenHashSet();
    protected final Long2ObjectMap<NibbleArray> newArrays = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());
    private final LongSet field_241536_n_ = new LongOpenHashSet();
    private final LongSet chunksToRetain = new LongOpenHashSet();
    private final LongSet noLightSections = new LongOpenHashSet();
    protected volatile boolean hasSectionsToUpdate;

    protected SectionLightStorage(LightType lightType, IChunkLightProvider iChunkLightProvider, M m) {
        super(3, 16, 256);
        this.type = lightType;
        this.chunkProvider = iChunkLightProvider;
        this.cachedLightData = m;
        this.uncachedLightData = ((LightDataMap)m).copy();
        ((LightDataMap)this.uncachedLightData).disableCaching();
    }

    protected boolean hasSection(long l) {
        return this.getArray(l, false) != null;
    }

    @Nullable
    protected NibbleArray getArray(long l, boolean bl) {
        return this.getArray(bl ? this.cachedLightData : this.uncachedLightData, l);
    }

    @Nullable
    protected NibbleArray getArray(M m, long l) {
        return ((LightDataMap)m).getArray(l);
    }

    @Nullable
    public NibbleArray getArray(long l) {
        NibbleArray nibbleArray = (NibbleArray)this.newArrays.get(l);
        return nibbleArray != null ? nibbleArray : this.getArray(l, true);
    }

    protected abstract int getLightOrDefault(long var1);

    protected int getLight(long l) {
        long l2 = SectionPos.worldToSection(l);
        NibbleArray nibbleArray = this.getArray(l2, false);
        return nibbleArray.get(SectionPos.mask(BlockPos.unpackX(l)), SectionPos.mask(BlockPos.unpackY(l)), SectionPos.mask(BlockPos.unpackZ(l)));
    }

    protected void setLight(long l, int n) {
        long l2 = SectionPos.worldToSection(l);
        if (this.dirtyCachedSections.add(l2)) {
            ((LightDataMap)this.cachedLightData).copyArray(l2);
        }
        NibbleArray nibbleArray = this.getArray(l2, false);
        nibbleArray.set(SectionPos.mask(BlockPos.unpackX(l)), SectionPos.mask(BlockPos.unpackY(l)), SectionPos.mask(BlockPos.unpackZ(l)), n);
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    this.changedLightPositions.add(SectionPos.worldToSection(BlockPos.offset(l, j, k, i)));
                }
            }
        }
    }

    @Override
    protected int getLevel(long l) {
        if (l == Long.MAX_VALUE) {
            return 1;
        }
        if (this.activeLightSections.contains(l)) {
            return 1;
        }
        return !this.noLightSections.contains(l) && ((LightDataMap)this.cachedLightData).hasArray(l) ? 1 : 2;
    }

    @Override
    protected int getSourceLevel(long l) {
        if (this.addedEmptySections.contains(l)) {
            return 1;
        }
        return !this.activeLightSections.contains(l) && !this.addedActiveLightSections.contains(l) ? 2 : 0;
    }

    @Override
    protected void setLevel(long l, int n) {
        int n2 = this.getLevel(l);
        if (n2 != 0 && n == 0) {
            this.activeLightSections.add(l);
            this.addedActiveLightSections.remove(l);
        }
        if (n2 == 0 && n != 0) {
            this.activeLightSections.remove(l);
            this.addedEmptySections.remove(l);
        }
        if (n2 >= 2 && n != 2) {
            if (this.noLightSections.contains(l)) {
                this.noLightSections.remove(l);
            } else {
                ((LightDataMap)this.cachedLightData).setArray(l, this.getOrCreateArray(l));
                this.dirtyCachedSections.add(l);
                this.addSection(l);
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        for (int k = -1; k <= 1; ++k) {
                            this.changedLightPositions.add(SectionPos.worldToSection(BlockPos.offset(l, j, k, i)));
                        }
                    }
                }
            }
        }
        if (n2 != 2 && n >= 2) {
            this.noLightSections.add(l);
        }
        this.hasSectionsToUpdate = !this.noLightSections.isEmpty();
    }

    protected NibbleArray getOrCreateArray(long l) {
        NibbleArray nibbleArray = (NibbleArray)this.newArrays.get(l);
        return nibbleArray != null ? nibbleArray : new NibbleArray();
    }

    protected void cancelSectionUpdates(LightEngine<?, ?> lightEngine, long l) {
        if (lightEngine.func_227467_c_() < 8192) {
            lightEngine.func_227465_a_(arg_0 -> SectionLightStorage.lambda$cancelSectionUpdates$0(l, arg_0));
        } else {
            int n = SectionPos.toWorld(SectionPos.extractX(l));
            int n2 = SectionPos.toWorld(SectionPos.extractY(l));
            int n3 = SectionPos.toWorld(SectionPos.extractZ(l));
            for (int i = 0; i < 16; ++i) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        long l2 = BlockPos.pack(n + i, n2 + j, n3 + k);
                        lightEngine.cancelUpdate(l2);
                    }
                }
            }
        }
    }

    protected boolean hasSectionsToUpdate() {
        return this.hasSectionsToUpdate;
    }

    protected void updateSections(LightEngine<M, ?> lightEngine, boolean bl, boolean bl2) {
        if (this.hasSectionsToUpdate() || !this.newArrays.isEmpty()) {
            long l;
            NibbleArray nibbleArray;
            long l2;
            Iterator<Long> iterator2 = this.noLightSections.iterator();
            while (iterator2.hasNext()) {
                l2 = (Long)iterator2.next();
                this.cancelSectionUpdates(lightEngine, l2);
                NibbleArray nibbleArray2 = (NibbleArray)this.newArrays.remove(l2);
                nibbleArray = ((LightDataMap)this.cachedLightData).removeArray(l2);
                if (!this.chunksToRetain.contains(SectionPos.toSectionColumnPos(l2))) continue;
                if (nibbleArray2 != null) {
                    this.newArrays.put(l2, nibbleArray2);
                    continue;
                }
                if (nibbleArray == null) continue;
                this.newArrays.put(l2, nibbleArray);
            }
            ((LightDataMap)this.cachedLightData).invalidateCaches();
            iterator2 = this.noLightSections.iterator();
            while (iterator2.hasNext()) {
                l2 = (Long)iterator2.next();
                this.removeSection(l2);
            }
            this.noLightSections.clear();
            this.hasSectionsToUpdate = false;
            for (Long2ObjectMap.Entry entry : this.newArrays.long2ObjectEntrySet()) {
                l = entry.getLongKey();
                if (!this.hasSection(l)) continue;
                nibbleArray = (NibbleArray)entry.getValue();
                if (((LightDataMap)this.cachedLightData).getArray(l) == nibbleArray) continue;
                this.cancelSectionUpdates(lightEngine, l);
                ((LightDataMap)this.cachedLightData).setArray(l, nibbleArray);
                this.dirtyCachedSections.add(l);
            }
            ((LightDataMap)this.cachedLightData).invalidateCaches();
            if (!bl2) {
                for (long l3 : this.newArrays.keySet()) {
                    this.func_241538_b_(lightEngine, l3);
                }
            } else {
                for (long l4 : this.field_241536_n_) {
                    this.func_241538_b_(lightEngine, l4);
                }
            }
            this.field_241536_n_.clear();
            iterator2 = this.newArrays.long2ObjectEntrySet().iterator();
            while (iterator2.hasNext()) {
                Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)((Object)iterator2.next());
                l = entry.getLongKey();
                if (!this.hasSection(l)) continue;
                iterator2.remove();
            }
        }
    }

    private void func_241538_b_(LightEngine<M, ?> lightEngine, long l) {
        if (this.hasSection(l)) {
            int n = SectionPos.toWorld(SectionPos.extractX(l));
            int n2 = SectionPos.toWorld(SectionPos.extractY(l));
            int n3 = SectionPos.toWorld(SectionPos.extractZ(l));
            for (Direction direction : DIRECTIONS) {
                long l2 = SectionPos.withOffset(l, direction);
                if (this.newArrays.containsKey(l2) || !this.hasSection(l2)) continue;
                for (int i = 0; i < 16; ++i) {
                    for (int j = 0; j < 16; ++j) {
                        long l3;
                        long l4 = switch (1.$SwitchMap$net$minecraft$util$Direction[direction.ordinal()]) {
                            case 1 -> {
                                l3 = BlockPos.pack(n + j, n2, n3 + i);
                                yield BlockPos.pack(n + j, n2 - 1, n3 + i);
                            }
                            case 2 -> {
                                l3 = BlockPos.pack(n + j, n2 + 16 - 1, n3 + i);
                                yield BlockPos.pack(n + j, n2 + 16, n3 + i);
                            }
                            case 3 -> {
                                l3 = BlockPos.pack(n + i, n2 + j, n3);
                                yield BlockPos.pack(n + i, n2 + j, n3 - 1);
                            }
                            case 4 -> {
                                l3 = BlockPos.pack(n + i, n2 + j, n3 + 16 - 1);
                                yield BlockPos.pack(n + i, n2 + j, n3 + 16);
                            }
                            case 5 -> {
                                l3 = BlockPos.pack(n, n2 + i, n3 + j);
                                yield BlockPos.pack(n - 1, n2 + i, n3 + j);
                            }
                            default -> {
                                l3 = BlockPos.pack(n + 16 - 1, n2 + i, n3 + j);
                                yield BlockPos.pack(n + 16, n2 + i, n3 + j);
                            }
                        };
                        lightEngine.scheduleUpdate(l3, l4, lightEngine.getEdgeLevel(l3, l4, lightEngine.getLevel(l3)), true);
                        lightEngine.scheduleUpdate(l4, l3, lightEngine.getEdgeLevel(l4, l3, lightEngine.getLevel(l4)), true);
                    }
                }
            }
        }
    }

    protected void addSection(long l) {
    }

    protected void removeSection(long l) {
    }

    protected void setColumnEnabled(long l, boolean bl) {
    }

    public void retainChunkData(long l, boolean bl) {
        if (bl) {
            this.chunksToRetain.add(l);
        } else {
            this.chunksToRetain.remove(l);
        }
    }

    protected void setData(long l, @Nullable NibbleArray nibbleArray, boolean bl) {
        if (nibbleArray != null) {
            this.newArrays.put(l, nibbleArray);
            if (!bl) {
                this.field_241536_n_.add(l);
            }
        } else {
            this.newArrays.remove(l);
        }
    }

    protected void updateSectionStatus(long l, boolean bl) {
        boolean bl2 = this.activeLightSections.contains(l);
        if (!bl2 && !bl) {
            this.addedActiveLightSections.add(l);
            this.scheduleUpdate(Long.MAX_VALUE, l, 0, false);
        }
        if (bl2 && bl) {
            this.addedEmptySections.add(l);
            this.scheduleUpdate(Long.MAX_VALUE, l, 2, true);
        }
    }

    protected void processAllLevelUpdates() {
        if (this.needsUpdate()) {
            this.processUpdates(Integer.MAX_VALUE);
        }
    }

    protected void updateAndNotify() {
        Object object;
        if (!this.dirtyCachedSections.isEmpty()) {
            object = ((LightDataMap)this.cachedLightData).copy();
            ((LightDataMap)object).disableCaching();
            this.uncachedLightData = object;
            this.dirtyCachedSections.clear();
        }
        if (!this.changedLightPositions.isEmpty()) {
            object = this.changedLightPositions.iterator();
            while (object.hasNext()) {
                long l = object.nextLong();
                this.chunkProvider.markLightChanged(this.type, SectionPos.from(l));
            }
            this.changedLightPositions.clear();
        }
    }

    private static boolean lambda$cancelSectionUpdates$0(long l, long l2) {
        return SectionPos.worldToSection(l2) == l;
    }
}

