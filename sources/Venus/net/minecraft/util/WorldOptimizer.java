/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.crash.ReportedException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldOptimizer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setDaemon(false).build();
    private final ImmutableSet<RegistryKey<World>> field_233529_c_;
    private final boolean field_219957_d;
    private final SaveFormat.LevelSave worldStorage;
    private final Thread thread;
    private final DataFixer field_233530_g_;
    private volatile boolean active = true;
    private volatile boolean done;
    private volatile float totalProgress;
    private volatile int totalChunks;
    private volatile int converted;
    private volatile int skipped;
    private final Object2FloatMap<RegistryKey<World>> progress = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap(Util.identityHashStrategy()));
    private volatile ITextComponent statusText = new TranslationTextComponent("optimizeWorld.stage.counting");
    private static final Pattern REGION_FILE_PATTERN = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
    private final DimensionSavedDataManager savedDataManager;

    public WorldOptimizer(SaveFormat.LevelSave levelSave, DataFixer dataFixer, ImmutableSet<RegistryKey<World>> immutableSet, boolean bl) {
        this.field_233529_c_ = immutableSet;
        this.field_219957_d = bl;
        this.field_233530_g_ = dataFixer;
        this.worldStorage = levelSave;
        this.savedDataManager = new DimensionSavedDataManager(new File(this.worldStorage.getDimensionFolder(World.OVERWORLD), "data"), dataFixer);
        this.thread = THREAD_FACTORY.newThread(this::optimize);
        this.thread.setUncaughtExceptionHandler(this::lambda$new$0);
        this.thread.start();
    }

    public void cancel() {
        this.active = false;
        try {
            this.thread.join();
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private void optimize() {
        Object object;
        this.totalChunks = 0;
        ImmutableMap.Builder<RegistryKey, ListIterator<ChunkPos>> builder = ImmutableMap.builder();
        for (RegistryKey object2 : this.field_233529_c_) {
            object = this.func_233532_b_(object2);
            builder.put(object2, object.listIterator());
            this.totalChunks += object.size();
        }
        if (this.totalChunks == 0) {
            this.done = true;
        } else {
            float f = this.totalChunks;
            ImmutableMap immutableMap = builder.build();
            object = ImmutableMap.builder();
            for (RegistryKey l : this.field_233529_c_) {
                File file = this.worldStorage.getDimensionFolder(l);
                ((ImmutableMap.Builder)object).put(l, new ChunkLoader(new File(file, "region"), this.field_233530_g_, true));
            }
            ImmutableMap immutableMap2 = ((ImmutableMap.Builder)object).build();
            long l = Util.milliTime();
            this.statusText = new TranslationTextComponent("optimizeWorld.stage.upgrading");
            while (this.active) {
                boolean bl = false;
                float f2 = 0.0f;
                for (RegistryKey registryKey : this.field_233529_c_) {
                    ListIterator listIterator2 = (ListIterator)immutableMap.get(registryKey);
                    ChunkLoader chunkLoader = (ChunkLoader)immutableMap2.get(registryKey);
                    if (listIterator2.hasNext()) {
                        ChunkPos chunkPos = (ChunkPos)listIterator2.next();
                        boolean bl2 = false;
                        try {
                            CompoundNBT reportedException = chunkLoader.readChunk(chunkPos);
                            if (reportedException != null) {
                                boolean bl3;
                                int throwable = ChunkLoader.getDataVersion(reportedException);
                                CompoundNBT compoundNBT = chunkLoader.func_235968_a_(registryKey, this::lambda$optimize$1, reportedException);
                                CompoundNBT compoundNBT2 = compoundNBT.getCompound("Level");
                                ChunkPos chunkPos2 = new ChunkPos(compoundNBT2.getInt("xPos"), compoundNBT2.getInt("zPos"));
                                if (!chunkPos2.equals(chunkPos)) {
                                    LOGGER.warn("Chunk {} has invalid position {}", (Object)chunkPos, (Object)chunkPos2);
                                }
                                boolean bl4 = bl3 = throwable < SharedConstants.getVersion().getWorldVersion();
                                if (this.field_219957_d) {
                                    bl3 = bl3 || compoundNBT2.contains("Heightmaps");
                                    compoundNBT2.remove("Heightmaps");
                                    bl3 = bl3 || compoundNBT2.contains("isLightOn");
                                    compoundNBT2.remove("isLightOn");
                                }
                                if (bl3) {
                                    chunkLoader.writeChunk(chunkPos, compoundNBT);
                                    bl2 = true;
                                }
                            }
                        } catch (ReportedException iOException) {
                            Throwable throwable = iOException.getCause();
                            if (!(throwable instanceof IOException)) {
                                throw iOException;
                            }
                            LOGGER.error("Error upgrading chunk {}", (Object)chunkPos, (Object)throwable);
                        } catch (IOException iOException) {
                            LOGGER.error("Error upgrading chunk {}", (Object)chunkPos, (Object)iOException);
                        }
                        if (bl2) {
                            ++this.converted;
                        } else {
                            ++this.skipped;
                        }
                        bl = true;
                    }
                    float chunkPos = (float)listIterator2.nextIndex() / f;
                    this.progress.put((RegistryKey<World>)registryKey, chunkPos);
                    f2 += chunkPos;
                }
                this.totalProgress = f2;
                if (bl) continue;
                this.active = false;
            }
            this.statusText = new TranslationTextComponent("optimizeWorld.stage.finished");
            for (ChunkLoader chunkLoader : immutableMap2.values()) {
                try {
                    chunkLoader.close();
                } catch (IOException iOException) {
                    LOGGER.error("Error upgrading chunk", (Throwable)iOException);
                }
            }
            this.savedDataManager.save();
            l = Util.milliTime() - l;
            LOGGER.info("World optimizaton finished after {} ms", (Object)l);
            this.done = true;
        }
    }

    private List<ChunkPos> func_233532_b_(RegistryKey<World> registryKey) {
        File file = this.worldStorage.getDimensionFolder(registryKey);
        File file2 = new File(file, "region");
        File[] fileArray = file2.listFiles(WorldOptimizer::lambda$func_233532_b_$2);
        if (fileArray == null) {
            return ImmutableList.of();
        }
        ArrayList<ChunkPos> arrayList = Lists.newArrayList();
        for (File file3 : fileArray) {
            Matcher matcher = REGION_FILE_PATTERN.matcher(file3.getName());
            if (!matcher.matches()) continue;
            int n = Integer.parseInt(matcher.group(1)) << 5;
            int n2 = Integer.parseInt(matcher.group(2)) << 5;
            try (RegionFile regionFile = new RegionFile(file3, file2, true);){
                for (int i = 0; i < 32; ++i) {
                    for (int j = 0; j < 32; ++j) {
                        ChunkPos chunkPos = new ChunkPos(i + n, j + n2);
                        if (!regionFile.func_222662_b(chunkPos)) continue;
                        arrayList.add(chunkPos);
                    }
                }
            } catch (Throwable throwable) {
                // empty catch block
            }
        }
        return arrayList;
    }

    public boolean isFinished() {
        return this.done;
    }

    public ImmutableSet<RegistryKey<World>> func_233533_c_() {
        return this.field_233529_c_;
    }

    public float func_233531_a_(RegistryKey<World> registryKey) {
        return this.progress.getFloat(registryKey);
    }

    public float getTotalProgress() {
        return this.totalProgress;
    }

    public int getTotalChunks() {
        return this.totalChunks;
    }

    public int getConverted() {
        return this.converted;
    }

    public int getSkipped() {
        return this.skipped;
    }

    public ITextComponent getStatusText() {
        return this.statusText;
    }

    private static boolean lambda$func_233532_b_$2(File file, String string) {
        return string.endsWith(".mca");
    }

    private DimensionSavedDataManager lambda$optimize$1() {
        return this.savedDataManager;
    }

    private void lambda$new$0(Thread thread2, Throwable throwable) {
        LOGGER.error("Error upgrading world", throwable);
        this.statusText = new TranslationTextComponent("optimizeWorld.stage.failed");
        this.done = true;
    }
}

