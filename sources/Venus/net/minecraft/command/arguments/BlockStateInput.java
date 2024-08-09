/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.command.arguments;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class BlockStateInput
implements Predicate<CachedBlockInfo> {
    private final BlockState state;
    private final Set<Property<?>> properties;
    @Nullable
    private final CompoundNBT tag;

    public BlockStateInput(BlockState blockState, Set<Property<?>> set, @Nullable CompoundNBT compoundNBT) {
        this.state = blockState;
        this.properties = set;
        this.tag = compoundNBT;
    }

    public BlockState getState() {
        return this.state;
    }

    @Override
    public boolean test(CachedBlockInfo cachedBlockInfo) {
        BlockState blockState = cachedBlockInfo.getBlockState();
        if (!blockState.isIn(this.state.getBlock())) {
            return true;
        }
        for (Property<?> property : this.properties) {
            if (blockState.get(property) == this.state.get(property)) continue;
            return true;
        }
        if (this.tag == null) {
            return false;
        }
        TileEntity tileEntity = cachedBlockInfo.getTileEntity();
        return tileEntity != null && NBTUtil.areNBTEquals(this.tag, tileEntity.write(new CompoundNBT()), true);
    }

    public boolean place(ServerWorld serverWorld, BlockPos blockPos, int n) {
        TileEntity tileEntity;
        BlockState blockState = Block.getValidBlockForPosition(this.state, serverWorld, blockPos);
        if (blockState.isAir()) {
            blockState = this.state;
        }
        if (!serverWorld.setBlockState(blockPos, blockState, n)) {
            return true;
        }
        if (this.tag != null && (tileEntity = serverWorld.getTileEntity(blockPos)) != null) {
            CompoundNBT compoundNBT = this.tag.copy();
            compoundNBT.putInt("x", blockPos.getX());
            compoundNBT.putInt("y", blockPos.getY());
            compoundNBT.putInt("z", blockPos.getZ());
            tileEntity.read(blockState, compoundNBT);
        }
        return false;
    }

    @Override
    public boolean test(Object object) {
        return this.test((CachedBlockInfo)object);
    }
}

