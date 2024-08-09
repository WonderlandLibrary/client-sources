/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import java.io.File;
import java.io.IOException;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.SharedConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class WorldSavedData {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String name;
    private boolean dirty;

    public WorldSavedData(String string) {
        this.name = string;
    }

    public abstract void read(CompoundNBT var1);

    public abstract CompoundNBT write(CompoundNBT var1);

    public void markDirty() {
        this.setDirty(false);
    }

    public void setDirty(boolean bl) {
        this.dirty = bl;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public String getName() {
        return this.name;
    }

    public void save(File file) {
        if (this.isDirty()) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("data", this.write(new CompoundNBT()));
            compoundNBT.putInt("DataVersion", SharedConstants.getVersion().getWorldVersion());
            try {
                CompressedStreamTools.writeCompressed(compoundNBT, file);
            } catch (IOException iOException) {
                LOGGER.error("Could not save data {}", (Object)this, (Object)iOException);
            }
            this.setDirty(true);
        }
    }
}

