/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.RegionFile;
import net.minecraft.world.chunk.storage.SuppressedExceptions;

public final class RegionFileCache
implements AutoCloseable {
    private final Long2ObjectLinkedOpenHashMap<RegionFile> cache = new Long2ObjectLinkedOpenHashMap();
    private final File folder;
    private final boolean field_235986_c_;

    RegionFileCache(File file, boolean bl) {
        this.folder = file;
        this.field_235986_c_ = bl;
    }

    private RegionFile loadFile(ChunkPos chunkPos) throws IOException {
        long l = ChunkPos.asLong(chunkPos.getRegionCoordX(), chunkPos.getRegionCoordZ());
        RegionFile regionFile = this.cache.getAndMoveToFirst(l);
        if (regionFile != null) {
            return regionFile;
        }
        if (this.cache.size() >= 256) {
            this.cache.removeLast().close();
        }
        if (!this.folder.exists()) {
            this.folder.mkdirs();
        }
        File file = new File(this.folder, "r." + chunkPos.getRegionCoordX() + "." + chunkPos.getRegionCoordZ() + ".mca");
        RegionFile regionFile2 = new RegionFile(file, this.folder, this.field_235986_c_);
        this.cache.putAndMoveToFirst(l, regionFile2);
        return regionFile2;
    }

    @Nullable
    public CompoundNBT readChunk(ChunkPos chunkPos) throws IOException {
        Object var3_6;
        RegionFile regionFile = this.loadFile(chunkPos);
        try (DataInputStream dataInputStream = regionFile.func_222666_a(chunkPos);){
            if (dataInputStream != null) {
                CompoundNBT compoundNBT = CompressedStreamTools.read(dataInputStream);
                return compoundNBT;
            }
            var3_6 = null;
        }
        return var3_6;
    }

    protected void writeChunk(ChunkPos chunkPos, CompoundNBT compoundNBT) throws IOException {
        RegionFile regionFile = this.loadFile(chunkPos);
        try (DataOutputStream dataOutputStream = regionFile.func_222661_c(chunkPos);){
            CompressedStreamTools.write(compoundNBT, dataOutputStream);
        }
    }

    @Override
    public void close() throws IOException {
        SuppressedExceptions<IOException> suppressedExceptions = new SuppressedExceptions<IOException>();
        for (RegionFile regionFile : this.cache.values()) {
            try {
                regionFile.close();
            } catch (IOException iOException) {
                suppressedExceptions.func_233003_a_(iOException);
            }
        }
        suppressedExceptions.func_233002_a_();
    }

    public void func_235987_a_() throws IOException {
        for (RegionFile regionFile : this.cache.values()) {
            regionFile.func_235985_a_();
        }
    }
}

