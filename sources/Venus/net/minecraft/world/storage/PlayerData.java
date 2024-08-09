/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DefaultTypeReferences;
import net.minecraft.world.storage.FolderName;
import net.minecraft.world.storage.SaveFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final File playerDataFolder;
    protected final DataFixer fixer;

    public PlayerData(SaveFormat.LevelSave levelSave, DataFixer dataFixer) {
        this.fixer = dataFixer;
        this.playerDataFolder = levelSave.resolveFilePath(FolderName.PLAYERDATA).toFile();
        this.playerDataFolder.mkdirs();
    }

    public void savePlayerData(PlayerEntity playerEntity) {
        try {
            CompoundNBT compoundNBT = playerEntity.writeWithoutTypeId(new CompoundNBT());
            File file = File.createTempFile(playerEntity.getCachedUniqueIdString() + "-", ".dat", this.playerDataFolder);
            CompressedStreamTools.writeCompressed(compoundNBT, file);
            File file2 = new File(this.playerDataFolder, playerEntity.getCachedUniqueIdString() + ".dat");
            File file3 = new File(this.playerDataFolder, playerEntity.getCachedUniqueIdString() + ".dat_old");
            Util.backupThenUpdate(file2, file, file3);
        } catch (Exception exception) {
            LOGGER.warn("Failed to save player data for {}", (Object)playerEntity.getName().getString());
        }
    }

    @Nullable
    public CompoundNBT loadPlayerData(PlayerEntity playerEntity) {
        CompoundNBT compoundNBT = null;
        try {
            File file = new File(this.playerDataFolder, playerEntity.getCachedUniqueIdString() + ".dat");
            if (file.exists() && file.isFile()) {
                compoundNBT = CompressedStreamTools.readCompressed(file);
            }
        } catch (Exception exception) {
            LOGGER.warn("Failed to load player data for {}", (Object)playerEntity.getName().getString());
        }
        if (compoundNBT != null) {
            int n = compoundNBT.contains("DataVersion", 0) ? compoundNBT.getInt("DataVersion") : -1;
            playerEntity.read(NBTUtil.update(this.fixer, DefaultTypeReferences.PLAYER, compoundNBT, n));
        }
        return compoundNBT;
    }

    public String[] getSeenPlayerUUIDs() {
        String[] stringArray = this.playerDataFolder.list();
        if (stringArray == null) {
            stringArray = new String[]{};
        }
        for (int i = 0; i < stringArray.length; ++i) {
            if (!stringArray[i].endsWith(".dat")) continue;
            stringArray[i] = stringArray[i].substring(0, stringArray[i].length() - 4);
        }
        return stringArray;
    }
}

