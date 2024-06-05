package net.minecraft.src;

import java.util.*;

public class BlockDeadBush extends BlockFlower
{
    protected BlockDeadBush(final int par1) {
        super(par1, Material.vine);
        final float var2 = 0.4f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 0.8f, 0.5f + var2);
    }
    
    @Override
    protected boolean canThisPlantGrowOnThisBlockID(final int par1) {
        return par1 == Block.sand.blockID;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return -1;
    }
    
    @Override
    public void harvestBlock(final World par1World, final EntityPlayer par2EntityPlayer, final int par3, final int par4, final int par5, final int par6) {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID) {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.deadBush, 1, par6));
        }
        else {
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }
}
