/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.BiomeContainer;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.chunk.storage.NibbleArrayReader;

public class ChunkLoaderUtil {
    public static AnvilConverterData load(CompoundNBT compoundNBT) {
        int n = compoundNBT.getInt("xPos");
        int n2 = compoundNBT.getInt("zPos");
        AnvilConverterData anvilConverterData = new AnvilConverterData(n, n2);
        anvilConverterData.blocks = compoundNBT.getByteArray("Blocks");
        anvilConverterData.data = new NibbleArrayReader(compoundNBT.getByteArray("Data"), 7);
        anvilConverterData.skyLight = new NibbleArrayReader(compoundNBT.getByteArray("SkyLight"), 7);
        anvilConverterData.blockLight = new NibbleArrayReader(compoundNBT.getByteArray("BlockLight"), 7);
        anvilConverterData.heightmap = compoundNBT.getByteArray("HeightMap");
        anvilConverterData.terrainPopulated = compoundNBT.getBoolean("TerrainPopulated");
        anvilConverterData.entities = compoundNBT.getList("Entities", 10);
        anvilConverterData.tileEntities = compoundNBT.getList("TileEntities", 10);
        anvilConverterData.tileTicks = compoundNBT.getList("TileTicks", 10);
        try {
            anvilConverterData.lastUpdated = compoundNBT.getLong("LastUpdate");
        } catch (ClassCastException classCastException) {
            anvilConverterData.lastUpdated = compoundNBT.getInt("LastUpdate");
        }
        return anvilConverterData;
    }

    public static void func_242708_a(DynamicRegistries.Impl impl, AnvilConverterData anvilConverterData, CompoundNBT compoundNBT, BiomeProvider biomeProvider) {
        compoundNBT.putInt("xPos", anvilConverterData.x);
        compoundNBT.putInt("zPos", anvilConverterData.z);
        compoundNBT.putLong("LastUpdate", anvilConverterData.lastUpdated);
        int[] nArray = new int[anvilConverterData.heightmap.length];
        for (int i = 0; i < anvilConverterData.heightmap.length; ++i) {
            nArray[i] = anvilConverterData.heightmap[i];
        }
        compoundNBT.putIntArray("HeightMap", nArray);
        compoundNBT.putBoolean("TerrainPopulated", anvilConverterData.terrainPopulated);
        ListNBT listNBT = new ListNBT();
        for (int i = 0; i < 8; ++i) {
            int n;
            boolean bl = true;
            for (int j = 0; j < 16 && bl; ++j) {
                block3: for (int k = 0; k < 16 && bl; ++k) {
                    for (int i2 = 0; i2 < 16; ++i2) {
                        int n2 = j << 11 | i2 << 7 | k + (i << 4);
                        n = anvilConverterData.blocks[n2];
                        if (n == 0) continue;
                        bl = false;
                        continue block3;
                    }
                }
            }
            if (bl) continue;
            byte[] byArray = new byte[4096];
            NibbleArray nibbleArray = new NibbleArray();
            NibbleArray nibbleArray2 = new NibbleArray();
            NibbleArray nibbleArray3 = new NibbleArray();
            for (n = 0; n < 16; ++n) {
                for (int j = 0; j < 16; ++j) {
                    for (int k = 0; k < 16; ++k) {
                        int n3 = n << 11 | k << 7 | j + (i << 4);
                        byte by = anvilConverterData.blocks[n3];
                        byArray[j << 8 | k << 4 | n] = (byte)(by & 0xFF);
                        nibbleArray.set(n, j, k, anvilConverterData.data.get(n, j + (i << 4), k));
                        nibbleArray2.set(n, j, k, anvilConverterData.skyLight.get(n, j + (i << 4), k));
                        nibbleArray3.set(n, j, k, anvilConverterData.blockLight.get(n, j + (i << 4), k));
                    }
                }
            }
            CompoundNBT compoundNBT2 = new CompoundNBT();
            compoundNBT2.putByte("Y", (byte)(i & 0xFF));
            compoundNBT2.putByteArray("Blocks", byArray);
            compoundNBT2.putByteArray("Data", nibbleArray.getData());
            compoundNBT2.putByteArray("SkyLight", nibbleArray2.getData());
            compoundNBT2.putByteArray("BlockLight", nibbleArray3.getData());
            listNBT.add(compoundNBT2);
        }
        compoundNBT.put("Sections", listNBT);
        compoundNBT.putIntArray("Biomes", new BiomeContainer(impl.getRegistry(Registry.BIOME_KEY), new ChunkPos(anvilConverterData.x, anvilConverterData.z), biomeProvider).getBiomeIds());
        compoundNBT.put("Entities", anvilConverterData.entities);
        compoundNBT.put("TileEntities", anvilConverterData.tileEntities);
        if (anvilConverterData.tileTicks != null) {
            compoundNBT.put("TileTicks", anvilConverterData.tileTicks);
        }
        compoundNBT.putBoolean("convertedFromAlphaFormat", false);
    }

    public static class AnvilConverterData {
        public long lastUpdated;
        public boolean terrainPopulated;
        public byte[] heightmap;
        public NibbleArrayReader blockLight;
        public NibbleArrayReader skyLight;
        public NibbleArrayReader data;
        public byte[] blocks;
        public ListNBT entities;
        public ListNBT tileEntities;
        public ListNBT tileTicks;
        public final int x;
        public final int z;

        public AnvilConverterData(int n, int n2) {
            this.x = n;
            this.z = n2;
        }
    }
}

