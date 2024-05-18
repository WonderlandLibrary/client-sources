package net.minecraft.entity.ai;

import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;

public class EntityAIOcelotSit extends EntityAIMoveToBlock
{
    private final EntityOcelot field_151493_a;
    
    @Override
    public boolean continueExecuting() {
        return super.continueExecuting();
    }
    
    @Override
    public void updateTask() {
        super.updateTask();
        this.field_151493_a.getAISit().setSitting("".length() != 0);
        if (!this.getIsAboveDestination()) {
            this.field_151493_a.setSitting("".length() != 0);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (!this.field_151493_a.isSitting()) {
            this.field_151493_a.setSitting(" ".length() != 0);
        }
    }
    
    @Override
    public boolean shouldExecute() {
        if (this.field_151493_a.isTamed() && !this.field_151493_a.isSitting() && super.shouldExecute()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void resetTask() {
        super.resetTask();
        this.field_151493_a.setSitting("".length() != 0);
    }
    
    @Override
    protected boolean shouldMoveTo(final World world, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos.up())) {
            return "".length() != 0;
        }
        final IBlockState blockState = world.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (block == Blocks.chest) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest && ((TileEntityChest)tileEntity).numPlayersUsing < " ".length()) {
                return " ".length() != 0;
            }
        }
        else {
            if (block == Blocks.lit_furnace) {
                return " ".length() != 0;
            }
            if (block == Blocks.bed && blockState.getValue(BlockBed.PART) != BlockBed.EnumPartType.HEAD) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public void startExecuting() {
        super.startExecuting();
        this.field_151493_a.getAISit().setSitting("".length() != 0);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAIOcelotSit(final EntityOcelot field_151493_a, final double n) {
        super(field_151493_a, n, 0x55 ^ 0x5D);
        this.field_151493_a = field_151493_a;
    }
}
