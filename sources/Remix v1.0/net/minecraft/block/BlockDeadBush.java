package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;

public class BlockDeadBush extends BlockBush
{
    protected BlockDeadBush() {
        super(Material.vine);
        final float var1 = 0.4f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.8f, 0.5f + var1);
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block ground) {
        return ground == Blocks.sand || ground == Blocks.hardened_clay || ground == Blocks.stained_hardened_clay || ground == Blocks.dirt;
    }
    
    @Override
    public boolean isReplaceable(final World worldIn, final BlockPos pos) {
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public void harvestBlock(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (!worldIn.isRemote && playerIn.getCurrentEquippedItem() != null && playerIn.getCurrentEquippedItem().getItem() == Items.shears) {
            playerIn.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(worldIn, pos, new ItemStack(Blocks.deadbush, 1, 0));
        }
        else {
            super.harvestBlock(worldIn, playerIn, pos, state, te);
        }
    }
}
