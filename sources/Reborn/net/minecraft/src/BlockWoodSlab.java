package net.minecraft.src;

import java.util.*;

public class BlockWoodSlab extends BlockHalfSlab
{
    public static final String[] woodType;
    
    static {
        woodType = new String[] { "oak", "spruce", "birch", "jungle" };
    }
    
    public BlockWoodSlab(final int par1, final boolean par2) {
        super(par1, par2, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return Block.planks.getIcon(par1, par2 & 0x7);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.woodSingleSlab.blockID;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return new ItemStack(Block.woodSingleSlab.blockID, 2, par1 & 0x7);
    }
    
    @Override
    public String getFullSlabName(int par1) {
        if (par1 < 0 || par1 >= BlockWoodSlab.woodType.length) {
            par1 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + BlockWoodSlab.woodType[par1];
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        if (par1 != Block.woodDoubleSlab.blockID) {
            for (int var4 = 0; var4 < 4; ++var4) {
                par3List.add(new ItemStack(par1, 1, var4));
            }
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
}
