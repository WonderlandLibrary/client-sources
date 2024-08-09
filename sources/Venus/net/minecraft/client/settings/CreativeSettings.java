/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.settings;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.minecraft.client.settings.HotbarSnapshot;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DefaultTypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreativeSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    private final File dataFile;
    private final DataFixer dataFixer;
    private final HotbarSnapshot[] hotbarSnapshots = new HotbarSnapshot[9];
    private boolean loaded;

    public CreativeSettings(File file, DataFixer dataFixer) {
        this.dataFile = new File(file, "hotbar.nbt");
        this.dataFixer = dataFixer;
        for (int i = 0; i < 9; ++i) {
            this.hotbarSnapshots[i] = new HotbarSnapshot();
        }
    }

    private void load() {
        try {
            CompoundNBT compoundNBT = CompressedStreamTools.read(this.dataFile);
            if (compoundNBT == null) {
                return;
            }
            if (!compoundNBT.contains("DataVersion", 0)) {
                compoundNBT.putInt("DataVersion", 1343);
            }
            compoundNBT = NBTUtil.update(this.dataFixer, DefaultTypeReferences.HOTBAR, compoundNBT, compoundNBT.getInt("DataVersion"));
            for (int i = 0; i < 9; ++i) {
                this.hotbarSnapshots[i].fromTag(compoundNBT.getList(String.valueOf(i), 10));
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to load creative mode options", (Throwable)exception);
        }
    }

    public void save() {
        try {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
            for (int i = 0; i < 9; ++i) {
                compoundNBT.put(String.valueOf(i), this.getHotbarSnapshot(i).createTag());
            }
            CompressedStreamTools.write(compoundNBT, this.dataFile);
        } catch (Exception exception) {
            LOGGER.error("Failed to save creative mode options", (Throwable)exception);
        }
    }

    public HotbarSnapshot getHotbarSnapshot(int n) {
        if (!this.loaded) {
            this.load();
            this.loaded = true;
        }
        return this.hotbarSnapshots[n];
    }
}

