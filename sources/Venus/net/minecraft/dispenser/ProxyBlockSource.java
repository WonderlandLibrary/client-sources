/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.dispenser;

import net.minecraft.block.BlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class ProxyBlockSource
implements IBlockSource {
    private final ServerWorld world;
    private final BlockPos pos;

    public ProxyBlockSource(ServerWorld serverWorld, BlockPos blockPos) {
        this.world = serverWorld;
        this.pos = blockPos;
    }

    @Override
    public ServerWorld getWorld() {
        return this.world;
    }

    @Override
    public double getX() {
        return (double)this.pos.getX() + 0.5;
    }

    @Override
    public double getY() {
        return (double)this.pos.getY() + 0.5;
    }

    @Override
    public double getZ() {
        return (double)this.pos.getZ() + 0.5;
    }

    @Override
    public BlockPos getBlockPos() {
        return this.pos;
    }

    @Override
    public BlockState getBlockState() {
        return this.world.getBlockState(this.pos);
    }

    @Override
    public <T extends TileEntity> T getBlockTileEntity() {
        return (T)this.world.getTileEntity(this.pos);
    }
}

