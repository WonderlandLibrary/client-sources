/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.village;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.util.SectionDistanceGraph;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestData;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.storage.RegionSectionCache;

public class PointOfInterestManager
extends RegionSectionCache<PointOfInterestData> {
    private final DistanceGraph distanceTracker;
    private final LongSet loadedChunks = new LongOpenHashSet();

    public PointOfInterestManager(File file, DataFixer dataFixer, boolean bl) {
        super(file, PointOfInterestData::func_234158_a_, PointOfInterestData::new, dataFixer, DefaultTypeReferences.POI_CHUNK, bl);
        this.distanceTracker = new DistanceGraph(this);
    }

    public void add(BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        ((PointOfInterestData)this.func_235995_e_(SectionPos.from(blockPos).asLong())).add(blockPos, pointOfInterestType);
    }

    public void remove(BlockPos blockPos) {
        ((PointOfInterestData)this.func_235995_e_(SectionPos.from(blockPos).asLong())).remove(blockPos);
    }

    public long getCountInRange(Predicate<PointOfInterestType> predicate, BlockPos blockPos, int n, Status status2) {
        return this.func_219146_b(predicate, blockPos, n, status2).count();
    }

    public boolean hasTypeAtPosition(PointOfInterestType pointOfInterestType, BlockPos blockPos) {
        Optional<PointOfInterestType> optional = ((PointOfInterestData)this.func_235995_e_(SectionPos.from(blockPos).asLong())).getType(blockPos);
        return optional.isPresent() && optional.get().equals(pointOfInterestType);
    }

    public Stream<PointOfInterest> getInSquare(Predicate<PointOfInterestType> predicate, BlockPos blockPos, int n, Status status2) {
        int n2 = Math.floorDiv(n, 16) + 1;
        return ChunkPos.getAllInBox(new ChunkPos(blockPos), n2).flatMap(arg_0 -> this.lambda$getInSquare$0(predicate, status2, arg_0)).filter(arg_0 -> PointOfInterestManager.lambda$getInSquare$1(blockPos, n, arg_0));
    }

    public Stream<PointOfInterest> func_219146_b(Predicate<PointOfInterestType> predicate, BlockPos blockPos, int n, Status status2) {
        int n2 = n * n;
        return this.getInSquare(predicate, blockPos, n, status2).filter(arg_0 -> PointOfInterestManager.lambda$func_219146_b$2(blockPos, n2, arg_0));
    }

    public Stream<PointOfInterest> getInChunk(Predicate<PointOfInterestType> predicate, ChunkPos chunkPos, Status status2) {
        return IntStream.range(0, 16).boxed().map(arg_0 -> this.lambda$getInChunk$3(chunkPos, arg_0)).filter(Optional::isPresent).flatMap(arg_0 -> PointOfInterestManager.lambda$getInChunk$4(predicate, status2, arg_0));
    }

    public Stream<BlockPos> findAll(Predicate<PointOfInterestType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int n, Status status2) {
        return this.func_219146_b(predicate, blockPos, n, status2).map(PointOfInterest::getPos).filter(predicate2);
    }

    public Stream<BlockPos> func_242324_b(Predicate<PointOfInterestType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int n, Status status2) {
        return this.findAll(predicate, predicate2, blockPos, n, status2).sorted(Comparator.comparingDouble(arg_0 -> PointOfInterestManager.lambda$func_242324_b$5(blockPos, arg_0)));
    }

    public Optional<BlockPos> find(Predicate<PointOfInterestType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int n, Status status2) {
        return this.findAll(predicate, predicate2, blockPos, n, status2).findFirst();
    }

    public Optional<BlockPos> func_234148_d_(Predicate<PointOfInterestType> predicate, BlockPos blockPos, int n, Status status2) {
        return this.func_219146_b(predicate, blockPos, n, status2).map(PointOfInterest::getPos).min(Comparator.comparingDouble(arg_0 -> PointOfInterestManager.lambda$func_234148_d_$6(blockPos, arg_0)));
    }

    public Optional<BlockPos> take(Predicate<PointOfInterestType> predicate, Predicate<BlockPos> predicate2, BlockPos blockPos, int n) {
        return this.func_219146_b(predicate, blockPos, n, Status.HAS_SPACE).filter(arg_0 -> PointOfInterestManager.lambda$take$7(predicate2, arg_0)).findFirst().map(PointOfInterestManager::lambda$take$8);
    }

    public Optional<BlockPos> getRandom(Predicate<PointOfInterestType> predicate, Predicate<BlockPos> predicate2, Status status2, BlockPos blockPos, int n, Random random2) {
        List list = this.func_219146_b(predicate, blockPos, n, status2).collect(Collectors.toList());
        Collections.shuffle(list, random2);
        return list.stream().filter(arg_0 -> PointOfInterestManager.lambda$getRandom$9(predicate2, arg_0)).findFirst().map(PointOfInterest::getPos);
    }

    public boolean release(BlockPos blockPos) {
        return ((PointOfInterestData)this.func_235995_e_(SectionPos.from(blockPos).asLong())).release(blockPos);
    }

    public boolean exists(BlockPos blockPos, Predicate<PointOfInterestType> predicate) {
        return this.func_219113_d(SectionPos.from(blockPos).asLong()).map(arg_0 -> PointOfInterestManager.lambda$exists$10(blockPos, predicate, arg_0)).orElse(false);
    }

    public Optional<PointOfInterestType> getType(BlockPos blockPos) {
        PointOfInterestData pointOfInterestData = (PointOfInterestData)this.func_235995_e_(SectionPos.from(blockPos).asLong());
        return pointOfInterestData.getType(blockPos);
    }

    public int sectionsToVillage(SectionPos sectionPos) {
        this.distanceTracker.runAllUpdates();
        return this.distanceTracker.getLevel(sectionPos.asLong());
    }

    private boolean isVillageCenter(long l) {
        Optional optional = this.func_219106_c(l);
        return optional == null ? false : optional.map(PointOfInterestManager::lambda$isVillageCenter$11).orElse(false);
    }

    @Override
    public void tick(BooleanSupplier booleanSupplier) {
        super.tick(booleanSupplier);
        this.distanceTracker.runAllUpdates();
    }

    @Override
    protected void markDirty(long l) {
        super.markDirty(l);
        this.distanceTracker.updateSourceLevel(l, this.distanceTracker.getSourceLevel(l), true);
    }

    @Override
    protected void onSectionLoad(long l) {
        this.distanceTracker.updateSourceLevel(l, this.distanceTracker.getSourceLevel(l), true);
    }

    public void checkConsistencyWithBlocks(ChunkPos chunkPos, ChunkSection chunkSection) {
        SectionPos sectionPos = SectionPos.from(chunkPos, chunkSection.getYLocation() >> 4);
        Util.acceptOrElse(this.func_219113_d(sectionPos.asLong()), arg_0 -> this.lambda$checkConsistencyWithBlocks$13(chunkSection, sectionPos, arg_0), () -> this.lambda$checkConsistencyWithBlocks$14(chunkSection, sectionPos));
    }

    private static boolean hasAnyPOI(ChunkSection chunkSection) {
        return chunkSection.isValidPOIState(PointOfInterestType.BLOCKS_OF_INTEREST::contains);
    }

    private void updateFromSelection(ChunkSection chunkSection, SectionPos sectionPos, BiConsumer<BlockPos, PointOfInterestType> biConsumer) {
        sectionPos.allBlocksWithin().forEach(arg_0 -> PointOfInterestManager.lambda$updateFromSelection$16(chunkSection, biConsumer, arg_0));
    }

    public void ensureLoadedAndValid(IWorldReader iWorldReader, BlockPos blockPos, int n) {
        SectionPos.func_229421_b_(new ChunkPos(blockPos), Math.floorDiv(n, 16)).map(this::lambda$ensureLoadedAndValid$17).filter(PointOfInterestManager::lambda$ensureLoadedAndValid$18).map(PointOfInterestManager::lambda$ensureLoadedAndValid$19).filter(this::lambda$ensureLoadedAndValid$20).forEach(arg_0 -> PointOfInterestManager.lambda$ensureLoadedAndValid$21(iWorldReader, arg_0));
    }

    private static void lambda$ensureLoadedAndValid$21(IWorldReader iWorldReader, ChunkPos chunkPos) {
        iWorldReader.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.EMPTY);
    }

    private boolean lambda$ensureLoadedAndValid$20(ChunkPos chunkPos) {
        return this.loadedChunks.add(chunkPos.asLong());
    }

    private static ChunkPos lambda$ensureLoadedAndValid$19(Pair pair) {
        return ((SectionPos)pair.getFirst()).asChunkPos();
    }

    private static boolean lambda$ensureLoadedAndValid$18(Pair pair) {
        return ((Optional)pair.getSecond()).map(PointOfInterestData::isValid).orElse(false) == false;
    }

    private Pair lambda$ensureLoadedAndValid$17(SectionPos sectionPos) {
        return Pair.of(sectionPos, this.func_219113_d(sectionPos.asLong()));
    }

    private static void lambda$updateFromSelection$16(ChunkSection chunkSection, BiConsumer biConsumer, BlockPos blockPos) {
        BlockState blockState = chunkSection.getBlockState(SectionPos.mask(blockPos.getX()), SectionPos.mask(blockPos.getY()), SectionPos.mask(blockPos.getZ()));
        PointOfInterestType.forState(blockState).ifPresent(arg_0 -> PointOfInterestManager.lambda$updateFromSelection$15(biConsumer, blockPos, arg_0));
    }

    private static void lambda$updateFromSelection$15(BiConsumer biConsumer, BlockPos blockPos, PointOfInterestType pointOfInterestType) {
        biConsumer.accept(blockPos, pointOfInterestType);
    }

    private void lambda$checkConsistencyWithBlocks$14(ChunkSection chunkSection, SectionPos sectionPos) {
        if (PointOfInterestManager.hasAnyPOI(chunkSection)) {
            PointOfInterestData pointOfInterestData = (PointOfInterestData)this.func_235995_e_(sectionPos.asLong());
            this.updateFromSelection(chunkSection, sectionPos, pointOfInterestData::add);
        }
    }

    private void lambda$checkConsistencyWithBlocks$13(ChunkSection chunkSection, SectionPos sectionPos, PointOfInterestData pointOfInterestData) {
        pointOfInterestData.refresh(arg_0 -> this.lambda$checkConsistencyWithBlocks$12(chunkSection, sectionPos, arg_0));
    }

    private void lambda$checkConsistencyWithBlocks$12(ChunkSection chunkSection, SectionPos sectionPos, BiConsumer biConsumer) {
        if (PointOfInterestManager.hasAnyPOI(chunkSection)) {
            this.updateFromSelection(chunkSection, sectionPos, biConsumer);
        }
    }

    private static Boolean lambda$isVillageCenter$11(PointOfInterestData pointOfInterestData) {
        return pointOfInterestData.getRecords(PointOfInterestType.MATCH_ANY, Status.IS_OCCUPIED).count() > 0L;
    }

    private static Boolean lambda$exists$10(BlockPos blockPos, Predicate predicate, PointOfInterestData pointOfInterestData) {
        return pointOfInterestData.exists(blockPos, predicate);
    }

    private static boolean lambda$getRandom$9(Predicate predicate, PointOfInterest pointOfInterest) {
        return predicate.test(pointOfInterest.getPos());
    }

    private static BlockPos lambda$take$8(PointOfInterest pointOfInterest) {
        pointOfInterest.claim();
        return pointOfInterest.getPos();
    }

    private static boolean lambda$take$7(Predicate predicate, PointOfInterest pointOfInterest) {
        return predicate.test(pointOfInterest.getPos());
    }

    private static double lambda$func_234148_d_$6(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos2.distanceSq(blockPos);
    }

    private static double lambda$func_242324_b$5(BlockPos blockPos, BlockPos blockPos2) {
        return blockPos2.distanceSq(blockPos);
    }

    private static Stream lambda$getInChunk$4(Predicate predicate, Status status2, Optional optional) {
        return ((PointOfInterestData)optional.get()).getRecords(predicate, status2);
    }

    private Optional lambda$getInChunk$3(ChunkPos chunkPos, Integer n) {
        return this.func_219113_d(SectionPos.from(chunkPos, n).asLong());
    }

    private static boolean lambda$func_219146_b$2(BlockPos blockPos, int n, PointOfInterest pointOfInterest) {
        return pointOfInterest.getPos().distanceSq(blockPos) <= (double)n;
    }

    private static boolean lambda$getInSquare$1(BlockPos blockPos, int n, PointOfInterest pointOfInterest) {
        BlockPos blockPos2 = pointOfInterest.getPos();
        return Math.abs(blockPos2.getX() - blockPos.getX()) <= n && Math.abs(blockPos2.getZ() - blockPos.getZ()) <= n;
    }

    private Stream lambda$getInSquare$0(Predicate predicate, Status status2, ChunkPos chunkPos) {
        return this.getInChunk(predicate, chunkPos, status2);
    }

    final class DistanceGraph
    extends SectionDistanceGraph {
        private final Long2ByteMap levels;
        final PointOfInterestManager this$0;

        protected DistanceGraph(PointOfInterestManager pointOfInterestManager) {
            this.this$0 = pointOfInterestManager;
            super(7, 16, 256);
            this.levels = new Long2ByteOpenHashMap();
            this.levels.defaultReturnValue((byte)7);
        }

        @Override
        protected int getSourceLevel(long l) {
            return this.this$0.isVillageCenter(l) ? 0 : 7;
        }

        @Override
        protected int getLevel(long l) {
            return this.levels.get(l);
        }

        @Override
        protected void setLevel(long l, int n) {
            if (n > 6) {
                this.levels.remove(l);
            } else {
                this.levels.put(l, (byte)n);
            }
        }

        public void runAllUpdates() {
            super.processUpdates(Integer.MAX_VALUE);
        }
    }

    public static enum Status {
        HAS_SPACE(PointOfInterest::hasSpace),
        IS_OCCUPIED(PointOfInterest::isOccupied),
        ANY(Status::lambda$static$0);

        private final Predicate<? super PointOfInterest> test;

        private Status(Predicate<? super PointOfInterest> predicate) {
            this.test = predicate;
        }

        public Predicate<? super PointOfInterest> getTest() {
            return this.test;
        }

        private static boolean lambda$static$0(PointOfInterest pointOfInterest) {
            return false;
        }
    }
}

