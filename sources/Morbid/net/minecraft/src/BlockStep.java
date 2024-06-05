package net.minecraft.src;

import java.util.*;

public class BlockStep extends BlockHalfSlab
{
    public static final String[] blockStepTypes;
    private Icon theIcon;
    
    static {
        blockStepTypes = new String[] { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
    }
    
    public BlockStep(final int par1, final boolean par2) {
        super(par1, par2, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(int par1, final int par2) {
        final int var3 = par2 & 0x7;
        if (this.isDoubleSlab && (par2 & 0x8) != 0x0) {
            par1 = 1;
        }
        return (var3 == 0) ? ((par1 != 1 && par1 != 0) ? this.theIcon : this.blockIcon) : ((var3 == 1) ? Block.sandStone.getBlockTextureFromSide(par1) : ((var3 == 2) ? Block.planks.getBlockTextureFromSide(par1) : ((var3 == 3) ? Block.cobblestone.getBlockTextureFromSide(par1) : ((var3 == 4) ? Block.brick.getBlockTextureFromSide(par1) : ((var3 == 5) ? Block.stoneBrick.getIcon(par1, 0) : ((var3 == 6) ? Block.netherBrick.getBlockTextureFromSide(1) : ((var3 == 7) ? Block.blockNetherQuartz.getBlockTextureFromSide(par1) : this.blockIcon)))))));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("stoneslab_top");
        this.theIcon = par1IconRegister.registerIcon("stoneslab_side");
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.stoneSingleSlab.blockID;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return new ItemStack(Block.stoneSingleSlab.blockID, 2, par1 & 0x7);
    }
    
    @Override
    public String getFullSlabName(int par1) {
        if (par1 < 0 || par1 >= BlockStep.blockStepTypes.length) {
            par1 = 0;
        }
        return String.valueOf(super.getUnlocalizedName()) + "." + BlockStep.blockStepTypes[par1];
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        if (par1 != Block.stoneDoubleSlab.blockID) {
            for (int var4 = 0; var4 <= 7; ++var4) {
                if (var4 != 2) {
                    par3List.add(new ItemStack(par1, 1, var4));
                }
            }
        }
    }
}
