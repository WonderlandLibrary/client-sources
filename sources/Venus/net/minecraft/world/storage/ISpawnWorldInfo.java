/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.IWorldInfo;

public interface ISpawnWorldInfo
extends IWorldInfo {
    public void setSpawnX(int var1);

    public void setSpawnY(int var1);

    public void setSpawnZ(int var1);

    public void setSpawnAngle(float var1);

    default public void setSpawn(BlockPos blockPos, float f) {
        this.setSpawnX(blockPos.getX());
        this.setSpawnY(blockPos.getY());
        this.setSpawnZ(blockPos.getZ());
        this.setSpawnAngle(f);
    }
}

