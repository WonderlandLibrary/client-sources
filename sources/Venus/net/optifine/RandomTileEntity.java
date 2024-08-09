/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.util.TileEntityUtils;

public class RandomTileEntity
implements IRandomEntity {
    private TileEntity tileEntity;

    @Override
    public int getId() {
        return Config.getRandom(this.tileEntity.getPos(), 0);
    }

    @Override
    public BlockPos getSpawnPosition() {
        return this.tileEntity.getPos();
    }

    @Override
    public String getName() {
        return TileEntityUtils.getTileEntityName(this.tileEntity);
    }

    @Override
    public Biome getSpawnBiome() {
        return this.tileEntity.getWorld().getBiome(this.tileEntity.getPos());
    }

    @Override
    public int getHealth() {
        return 1;
    }

    @Override
    public int getMaxHealth() {
        return 1;
    }

    public TileEntity getTileEntity() {
        return this.tileEntity;
    }

    public void setTileEntity(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }
}

