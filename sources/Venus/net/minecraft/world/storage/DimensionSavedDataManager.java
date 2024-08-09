/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.world.storage.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DimensionSavedDataManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, WorldSavedData> savedDatum = Maps.newHashMap();
    private final DataFixer dataFixer;
    private final File folder;

    public DimensionSavedDataManager(File file, DataFixer dataFixer) {
        this.dataFixer = dataFixer;
        this.folder = file;
    }

    private File getDataFile(String string) {
        return new File(this.folder, string + ".dat");
    }

    public <T extends WorldSavedData> T getOrCreate(Supplier<T> supplier, String string) {
        T t = this.get(supplier, string);
        if (t != null) {
            return t;
        }
        WorldSavedData worldSavedData = (WorldSavedData)supplier.get();
        this.set(worldSavedData);
        return (T)worldSavedData;
    }

    @Nullable
    public <T extends WorldSavedData> T get(Supplier<T> supplier, String string) {
        WorldSavedData worldSavedData = this.savedDatum.get(string);
        if (worldSavedData == null && !this.savedDatum.containsKey(string)) {
            worldSavedData = this.loadSavedData(supplier, string);
            this.savedDatum.put(string, worldSavedData);
        }
        return (T)worldSavedData;
    }

    @Nullable
    private <T extends WorldSavedData> T loadSavedData(Supplier<T> supplier, String string) {
        try {
            File file = this.getDataFile(string);
            if (file.exists()) {
                WorldSavedData worldSavedData = (WorldSavedData)supplier.get();
                CompoundNBT compoundNBT = this.load(string, SharedConstants.getVersion().getWorldVersion());
                worldSavedData.read(compoundNBT.getCompound("data"));
                return (T)worldSavedData;
            }
        } catch (Exception exception) {
            LOGGER.error("Error loading saved data: {}", (Object)string, (Object)exception);
        }
        return (T)((WorldSavedData)null);
    }

    public void set(WorldSavedData worldSavedData) {
        this.savedDatum.put(worldSavedData.getName(), worldSavedData);
    }

    public CompoundNBT load(String string, int n) throws IOException {
        CompoundNBT compoundNBT;
        File file = this.getDataFile(string);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             PushbackInputStream pushbackInputStream = new PushbackInputStream(fileInputStream, 2);){
            CompoundNBT compoundNBT2;
            if (this.isCompressed(pushbackInputStream)) {
                compoundNBT2 = CompressedStreamTools.readCompressed(pushbackInputStream);
            } else {
                try (DataInputStream dataInputStream = new DataInputStream(pushbackInputStream);){
                    compoundNBT2 = CompressedStreamTools.read(dataInputStream);
                }
            }
            int n2 = compoundNBT2.contains("DataVersion", 0) ? compoundNBT2.getInt("DataVersion") : 1343;
            compoundNBT = NBTUtil.update(this.dataFixer, DefaultTypeReferences.SAVED_DATA, compoundNBT2, n2, n);
        }
        return compoundNBT;
    }

    private boolean isCompressed(PushbackInputStream pushbackInputStream) throws IOException {
        int n;
        byte[] byArray = new byte[2];
        boolean bl = false;
        int n2 = pushbackInputStream.read(byArray, 0, 2);
        if (n2 == 2 && (n = (byArray[1] & 0xFF) << 8 | byArray[0] & 0xFF) == 35615) {
            bl = true;
        }
        if (n2 != 0) {
            pushbackInputStream.unread(byArray, 0, n2);
        }
        return bl;
    }

    public void save() {
        for (WorldSavedData worldSavedData : this.savedDatum.values()) {
            if (worldSavedData == null) continue;
            worldSavedData.save(this.getDataFile(worldSavedData.getName()));
        }
    }
}

