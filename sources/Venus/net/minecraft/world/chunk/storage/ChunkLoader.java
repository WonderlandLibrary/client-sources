/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.IOWorker;
import net.minecraft.world.gen.feature.structure.LegacyStructureDataUtil;
import net.minecraft.world.storage.DimensionSavedDataManager;

public class ChunkLoader
implements AutoCloseable {
    private final IOWorker field_227077_a_;
    protected final DataFixer dataFixer;
    @Nullable
    private LegacyStructureDataUtil field_219167_a;

    public ChunkLoader(File file, DataFixer dataFixer, boolean bl) {
        this.dataFixer = dataFixer;
        this.field_227077_a_ = new IOWorker(file, bl, "chunk");
    }

    public CompoundNBT func_235968_a_(RegistryKey<World> registryKey, Supplier<DimensionSavedDataManager> supplier, CompoundNBT compoundNBT) {
        int n = ChunkLoader.getDataVersion(compoundNBT);
        int n2 = 1493;
        if (n < 1493 && (compoundNBT = NBTUtil.update(this.dataFixer, DefaultTypeReferences.CHUNK, compoundNBT, n, 1493)).getCompound("Level").getBoolean("hasLegacyStructureData")) {
            if (this.field_219167_a == null) {
                this.field_219167_a = LegacyStructureDataUtil.func_236992_a_(registryKey, supplier.get());
            }
            compoundNBT = this.field_219167_a.func_212181_a(compoundNBT);
        }
        compoundNBT = NBTUtil.update(this.dataFixer, DefaultTypeReferences.CHUNK, compoundNBT, Math.max(1493, n));
        if (n < SharedConstants.getVersion().getWorldVersion()) {
            compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
        }
        return compoundNBT;
    }

    public static int getDataVersion(CompoundNBT compoundNBT) {
        return compoundNBT.contains("DataVersion", 0) ? compoundNBT.getInt("DataVersion") : -1;
    }

    @Nullable
    public CompoundNBT readChunk(ChunkPos chunkPos) throws IOException {
        return this.field_227077_a_.func_227090_a_(chunkPos);
    }

    public void writeChunk(ChunkPos chunkPos, CompoundNBT compoundNBT) {
        this.field_227077_a_.func_227093_a_(chunkPos, compoundNBT);
        if (this.field_219167_a != null) {
            this.field_219167_a.func_208216_a(chunkPos.asLong());
        }
    }

    public void func_227079_i_() {
        this.field_227077_a_.func_227088_a_().join();
    }

    @Override
    public void close() throws IOException {
        this.field_227077_a_.close();
    }
}

