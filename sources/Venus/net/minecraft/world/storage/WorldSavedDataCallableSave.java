/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import net.minecraft.world.storage.WorldSavedData;

public class WorldSavedDataCallableSave
implements Runnable {
    private final WorldSavedData data;

    public WorldSavedDataCallableSave(WorldSavedData worldSavedData) {
        this.data = worldSavedData;
    }

    @Override
    public void run() {
        this.data.markDirty();
    }
}

