/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.server;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.DelegatedTaskExecutor;
import net.minecraft.util.concurrent.ITaskExecutor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.SectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkTaskPriorityQueueSorter;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.IChunkLightProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.lighting.WorldLightManager;
import net.minecraft.world.server.ChunkManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWorldLightManager
extends WorldLightManager
implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DelegatedTaskExecutor<Runnable> field_215605_b;
    private final ObjectList<Pair<Phase, Runnable>> field_215606_c = new ObjectArrayList<Pair<Phase, Runnable>>();
    private final ChunkManager chunkManager;
    private final ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> field_215608_e;
    private volatile int field_215609_f = 5;
    private final AtomicBoolean field_215610_g = new AtomicBoolean();

    public ServerWorldLightManager(IChunkLightProvider iChunkLightProvider, ChunkManager chunkManager, boolean bl, DelegatedTaskExecutor<Runnable> delegatedTaskExecutor, ITaskExecutor<ChunkTaskPriorityQueueSorter.FunctionEntry<Runnable>> iTaskExecutor) {
        super(iChunkLightProvider, true, bl);
        this.chunkManager = chunkManager;
        this.field_215608_e = iTaskExecutor;
        this.field_215605_b = delegatedTaskExecutor;
    }

    @Override
    public void close() {
    }

    @Override
    public int tick(int n, boolean bl, boolean bl2) {
        throw Util.pauseDevMode(new UnsupportedOperationException("Ran authomatically on a different thread!"));
    }

    @Override
    public void onBlockEmissionIncrease(BlockPos blockPos, int n) {
        throw Util.pauseDevMode(new UnsupportedOperationException("Ran authomatically on a different thread!"));
    }

    @Override
    public void checkBlock(BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.toImmutable();
        this.func_215586_a(blockPos.getX() >> 4, blockPos.getZ() >> 4, Phase.POST_UPDATE, Util.namedRunnable(() -> this.lambda$checkBlock$0(blockPos2), () -> ServerWorldLightManager.lambda$checkBlock$1(blockPos2)));
    }

    protected void updateChunkStatus(ChunkPos chunkPos) {
        this.func_215600_a(chunkPos.x, chunkPos.z, ServerWorldLightManager::lambda$updateChunkStatus$2, Phase.PRE_UPDATE, Util.namedRunnable(() -> this.lambda$updateChunkStatus$3(chunkPos), () -> ServerWorldLightManager.lambda$updateChunkStatus$4(chunkPos)));
    }

    @Override
    public void updateSectionStatus(SectionPos sectionPos, boolean bl) {
        this.func_215600_a(sectionPos.getSectionX(), sectionPos.getSectionZ(), ServerWorldLightManager::lambda$updateSectionStatus$5, Phase.PRE_UPDATE, Util.namedRunnable(() -> this.lambda$updateSectionStatus$6(sectionPos, bl), () -> ServerWorldLightManager.lambda$updateSectionStatus$7(sectionPos, bl)));
    }

    @Override
    public void enableLightSources(ChunkPos chunkPos, boolean bl) {
        this.func_215586_a(chunkPos.x, chunkPos.z, Phase.PRE_UPDATE, Util.namedRunnable(() -> this.lambda$enableLightSources$8(chunkPos, bl), () -> ServerWorldLightManager.lambda$enableLightSources$9(chunkPos, bl)));
    }

    @Override
    public void setData(LightType lightType, SectionPos sectionPos, @Nullable NibbleArray nibbleArray, boolean bl) {
        this.func_215600_a(sectionPos.getSectionX(), sectionPos.getSectionZ(), ServerWorldLightManager::lambda$setData$10, Phase.PRE_UPDATE, Util.namedRunnable(() -> this.lambda$setData$11(lightType, sectionPos, nibbleArray, bl), () -> ServerWorldLightManager.lambda$setData$12(sectionPos)));
    }

    private void func_215586_a(int n, int n2, Phase phase, Runnable runnable) {
        this.func_215600_a(n, n2, this.chunkManager.func_219191_c(ChunkPos.asLong(n, n2)), phase, runnable);
    }

    private void func_215600_a(int n, int n2, IntSupplier intSupplier, Phase phase, Runnable runnable) {
        this.field_215608_e.enqueue(ChunkTaskPriorityQueueSorter.func_219069_a(() -> this.lambda$func_215600_a$13(phase, runnable), ChunkPos.asLong(n, n2), intSupplier));
    }

    @Override
    public void retainData(ChunkPos chunkPos, boolean bl) {
        this.func_215600_a(chunkPos.x, chunkPos.z, ServerWorldLightManager::lambda$retainData$14, Phase.PRE_UPDATE, Util.namedRunnable(() -> this.lambda$retainData$15(chunkPos, bl), () -> ServerWorldLightManager.lambda$retainData$16(chunkPos)));
    }

    public CompletableFuture<IChunk> lightChunk(IChunk iChunk, boolean bl) {
        ChunkPos chunkPos = iChunk.getPos();
        iChunk.setLight(false);
        this.func_215586_a(chunkPos.x, chunkPos.z, Phase.PRE_UPDATE, Util.namedRunnable(() -> this.lambda$lightChunk$18(iChunk, chunkPos, bl), () -> ServerWorldLightManager.lambda$lightChunk$19(chunkPos, bl)));
        return CompletableFuture.supplyAsync(() -> this.lambda$lightChunk$20(iChunk, chunkPos), arg_0 -> this.lambda$lightChunk$21(chunkPos, arg_0));
    }

    public void func_215588_z_() {
        if ((!this.field_215606_c.isEmpty() || super.hasLightWork()) && this.field_215610_g.compareAndSet(false, false)) {
            this.field_215605_b.enqueue(this::lambda$func_215588_z_$22);
        }
    }

    private void func_215603_b() {
        int n;
        int n2 = Math.min(this.field_215606_c.size(), this.field_215609_f);
        Iterator iterator2 = this.field_215606_c.iterator();
        for (n = 0; iterator2.hasNext() && n < n2; ++n) {
            Pair pair = (Pair)iterator2.next();
            if (pair.getFirst() != Phase.PRE_UPDATE) continue;
            ((Runnable)pair.getSecond()).run();
        }
        iterator2.back(n);
        super.tick(Integer.MAX_VALUE, true, true);
        for (int i = 0; iterator2.hasNext() && i < n2; ++i) {
            Pair pair = (Pair)iterator2.next();
            if (pair.getFirst() == Phase.POST_UPDATE) {
                ((Runnable)pair.getSecond()).run();
            }
            iterator2.remove();
        }
    }

    public void func_215598_a(int n) {
        this.field_215609_f = n;
    }

    private void lambda$func_215588_z_$22() {
        this.func_215603_b();
        this.field_215610_g.set(true);
    }

    private void lambda$lightChunk$21(ChunkPos chunkPos, Runnable runnable) {
        this.func_215586_a(chunkPos.x, chunkPos.z, Phase.POST_UPDATE, runnable);
    }

    private IChunk lambda$lightChunk$20(IChunk iChunk, ChunkPos chunkPos) {
        iChunk.setLight(true);
        super.retainData(chunkPos, false);
        return iChunk;
    }

    private static String lambda$lightChunk$19(ChunkPos chunkPos, boolean bl) {
        return "lightChunk " + chunkPos + " " + bl;
    }

    private void lambda$lightChunk$18(IChunk iChunk, ChunkPos chunkPos, boolean bl) {
        ChunkSection[] chunkSectionArray = iChunk.getSections();
        for (int i = 0; i < 16; ++i) {
            ChunkSection chunkSection = chunkSectionArray[i];
            if (ChunkSection.isEmpty(chunkSection)) continue;
            super.updateSectionStatus(SectionPos.from(chunkPos, i), false);
        }
        super.enableLightSources(chunkPos, true);
        if (!bl) {
            iChunk.getLightSources().forEach(arg_0 -> this.lambda$lightChunk$17(iChunk, arg_0));
        }
        this.chunkManager.func_219209_c(chunkPos);
    }

    private void lambda$lightChunk$17(IChunk iChunk, BlockPos blockPos) {
        super.onBlockEmissionIncrease(blockPos, iChunk.getLightValue(blockPos));
    }

    private static String lambda$retainData$16(ChunkPos chunkPos) {
        return "retainData " + chunkPos;
    }

    private void lambda$retainData$15(ChunkPos chunkPos, boolean bl) {
        super.retainData(chunkPos, bl);
    }

    private static int lambda$retainData$14() {
        return 1;
    }

    private void lambda$func_215600_a$13(Phase phase, Runnable runnable) {
        this.field_215606_c.add(Pair.of(phase, runnable));
        if (this.field_215606_c.size() >= this.field_215609_f) {
            this.func_215603_b();
        }
    }

    private static String lambda$setData$12(SectionPos sectionPos) {
        return "queueData " + sectionPos;
    }

    private void lambda$setData$11(LightType lightType, SectionPos sectionPos, NibbleArray nibbleArray, boolean bl) {
        super.setData(lightType, sectionPos, nibbleArray, bl);
    }

    private static int lambda$setData$10() {
        return 1;
    }

    private static String lambda$enableLightSources$9(ChunkPos chunkPos, boolean bl) {
        return "enableLight " + chunkPos + " " + bl;
    }

    private void lambda$enableLightSources$8(ChunkPos chunkPos, boolean bl) {
        super.enableLightSources(chunkPos, bl);
    }

    private static String lambda$updateSectionStatus$7(SectionPos sectionPos, boolean bl) {
        return "updateSectionStatus " + sectionPos + " " + bl;
    }

    private void lambda$updateSectionStatus$6(SectionPos sectionPos, boolean bl) {
        super.updateSectionStatus(sectionPos, bl);
    }

    private static int lambda$updateSectionStatus$5() {
        return 1;
    }

    private static String lambda$updateChunkStatus$4(ChunkPos chunkPos) {
        return "updateChunkStatus " + chunkPos + " true";
    }

    private void lambda$updateChunkStatus$3(ChunkPos chunkPos) {
        int n;
        super.retainData(chunkPos, false);
        super.enableLightSources(chunkPos, false);
        for (n = -1; n < 17; ++n) {
            super.setData(LightType.BLOCK, SectionPos.from(chunkPos, n), null, true);
            super.setData(LightType.SKY, SectionPos.from(chunkPos, n), null, true);
        }
        for (n = 0; n < 16; ++n) {
            super.updateSectionStatus(SectionPos.from(chunkPos, n), true);
        }
    }

    private static int lambda$updateChunkStatus$2() {
        return 1;
    }

    private static String lambda$checkBlock$1(BlockPos blockPos) {
        return "checkBlock " + blockPos;
    }

    private void lambda$checkBlock$0(BlockPos blockPos) {
        super.checkBlock(blockPos);
    }

    static enum Phase {
        PRE_UPDATE,
        POST_UPDATE;

    }
}

