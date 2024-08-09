/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.datafix.codec.DatapackCodec;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldSettingsImport;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.biome.provider.SingleBiomeProvider;
import net.minecraft.world.chunk.storage.ChunkLoaderUtil;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.IServerConfiguration;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    static boolean convertRegions(SaveFormat.LevelSave levelSave, IProgressUpdate iProgressUpdate) {
        iProgressUpdate.setLoadingProgress(0);
        ArrayList<File> arrayList = Lists.newArrayList();
        ArrayList<File> arrayList2 = Lists.newArrayList();
        ArrayList<File> arrayList3 = Lists.newArrayList();
        File file = levelSave.getDimensionFolder(World.OVERWORLD);
        File file2 = levelSave.getDimensionFolder(World.THE_NETHER);
        File file3 = levelSave.getDimensionFolder(World.THE_END);
        LOGGER.info("Scanning folders...");
        AnvilSaveConverter.collectRegionFiles(file, arrayList);
        if (file2.exists()) {
            AnvilSaveConverter.collectRegionFiles(file2, arrayList2);
        }
        if (file3.exists()) {
            AnvilSaveConverter.collectRegionFiles(file3, arrayList3);
        }
        int n = arrayList.size() + arrayList2.size() + arrayList3.size();
        LOGGER.info("Total conversion count is {}", (Object)n);
        DynamicRegistries.Impl impl = DynamicRegistries.func_239770_b_();
        WorldSettingsImport<INBT> worldSettingsImport = WorldSettingsImport.create(NBTDynamicOps.INSTANCE, IResourceManager.Instance.INSTANCE, impl);
        IServerConfiguration iServerConfiguration = levelSave.readServerConfiguration(worldSettingsImport, DatapackCodec.VANILLA_CODEC);
        long l = iServerConfiguration != null ? iServerConfiguration.getDimensionGeneratorSettings().getSeed() : 0L;
        MutableRegistry<Biome> mutableRegistry = impl.getRegistry(Registry.BIOME_KEY);
        BiomeProvider biomeProvider = iServerConfiguration != null && iServerConfiguration.getDimensionGeneratorSettings().func_236228_i_() ? new SingleBiomeProvider(mutableRegistry.getOrThrow(Biomes.PLAINS)) : new OverworldBiomeProvider(l, false, false, mutableRegistry);
        AnvilSaveConverter.func_242983_a(impl, new File(file, "region"), arrayList, biomeProvider, 0, n, iProgressUpdate);
        AnvilSaveConverter.func_242983_a(impl, new File(file2, "region"), arrayList2, new SingleBiomeProvider(mutableRegistry.getOrThrow(Biomes.NETHER_WASTES)), arrayList.size(), n, iProgressUpdate);
        AnvilSaveConverter.func_242983_a(impl, new File(file3, "region"), arrayList3, new SingleBiomeProvider(mutableRegistry.getOrThrow(Biomes.THE_END)), arrayList.size() + arrayList2.size(), n, iProgressUpdate);
        AnvilSaveConverter.backupLevelData(levelSave);
        levelSave.saveLevel(impl, iServerConfiguration);
        return false;
    }

    private static void backupLevelData(SaveFormat.LevelSave levelSave) {
        File file = levelSave.resolveFilePath(FolderName.LEVEL_DAT).toFile();
        if (!file.exists()) {
            LOGGER.warn("Unable to create level.dat_mcr backup");
        } else {
            File file2 = new File(file.getParent(), "level.dat_mcr");
            if (!file.renameTo(file2)) {
                LOGGER.warn("Unable to create level.dat_mcr backup");
            }
        }
    }

    private static void func_242983_a(DynamicRegistries.Impl impl, File file, Iterable<File> iterable, BiomeProvider biomeProvider, int n, int n2, IProgressUpdate iProgressUpdate) {
        for (File file2 : iterable) {
            AnvilSaveConverter.func_242982_a(impl, file, file2, biomeProvider, n, n2, iProgressUpdate);
            int n3 = (int)Math.round(100.0 * (double)(++n) / (double)n2);
            iProgressUpdate.setLoadingProgress(n3);
        }
    }

    private static void func_242982_a(DynamicRegistries.Impl impl, File file, File file2, BiomeProvider biomeProvider, int n, int n2, IProgressUpdate iProgressUpdate) {
        String string = file2.getName();
        try (RegionFile regionFile = new RegionFile(file2, file, true);
             RegionFile regionFile2 = new RegionFile(new File(file, string.substring(0, string.length() - 4) + ".mca"), file, true);){
            for (int i = 0; i < 32; ++i) {
                int n3;
                for (n3 = 0; n3 < 32; ++n3) {
                    CompoundNBT compoundNBT;
                    Object object;
                    ChunkPos chunkPos = new ChunkPos(i, n3);
                    if (!regionFile.contains(chunkPos) || regionFile2.contains(chunkPos)) continue;
                    try {
                        object = regionFile.func_222666_a(chunkPos);
                        try {
                            if (object == null) {
                                LOGGER.warn("Failed to fetch input stream for chunk {}", (Object)chunkPos);
                                continue;
                            }
                            compoundNBT = CompressedStreamTools.read((DataInput)object);
                        } finally {
                            if (object != null) {
                                ((FilterInputStream)object).close();
                            }
                        }
                    } catch (IOException iOException) {
                        LOGGER.warn("Failed to read data for chunk {}", (Object)chunkPos, (Object)iOException);
                        continue;
                    }
                    object = compoundNBT.getCompound("Level");
                    ChunkLoaderUtil.AnvilConverterData anvilConverterData = ChunkLoaderUtil.load((CompoundNBT)object);
                    CompoundNBT compoundNBT2 = new CompoundNBT();
                    CompoundNBT compoundNBT3 = new CompoundNBT();
                    compoundNBT2.put("Level", compoundNBT3);
                    ChunkLoaderUtil.func_242708_a(impl, anvilConverterData, compoundNBT3, biomeProvider);
                    try (DataOutputStream dataOutputStream = regionFile2.func_222661_c(chunkPos);){
                        CompressedStreamTools.write(compoundNBT2, dataOutputStream);
                        continue;
                    }
                }
                n3 = (int)Math.round(100.0 * (double)(n * 1024) / (double)(n2 * 1024));
                int n4 = (int)Math.round(100.0 * (double)((i + 1) * 32 + n * 1024) / (double)(n2 * 1024));
                if (n4 <= n3) continue;
                iProgressUpdate.setLoadingProgress(n4);
            }
        } catch (IOException iOException) {
            LOGGER.error("Failed to upgrade region file {}", (Object)file2, (Object)iOException);
        }
    }

    private static void collectRegionFiles(File file, Collection<File> collection) {
        File file2 = new File(file, "region");
        File[] fileArray = file2.listFiles(AnvilSaveConverter::lambda$collectRegionFiles$0);
        if (fileArray != null) {
            Collections.addAll(collection, fileArray);
        }
    }

    private static boolean lambda$collectRegionFiles$0(File file, String string) {
        return string.endsWith(".mcr");
    }
}

