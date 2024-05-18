package net.minecraft.src;

import java.util.*;

public class BlockQuartz extends Block
{
    public static final String[] quartzBlockTypes;
    private static final String[] quartzBlockTextureTypes;
    private Icon[] quartzblockIcons;
    private Icon quartzblock_chiseled_top;
    private Icon quartzblock_lines_top;
    private Icon quartzblock_top;
    private Icon quartzblock_bottom;
    
    static {
        quartzBlockTypes = new String[] { "default", "chiseled", "lines" };
        quartzBlockTextureTypes = new String[] { "quartzblock_side", "quartzblock_chiseled", "quartzblock_lines", null, null };
    }
    
    public BlockQuartz(final int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        if (par2 == 2 || par2 == 3 || par2 == 4) {
            return (par2 == 2 && (par1 == 1 || par1 == 0)) ? this.quartzblock_lines_top : ((par2 == 3 && (par1 == 5 || par1 == 4)) ? this.quartzblock_lines_top : ((par2 == 4 && (par1 == 2 || par1 == 3)) ? this.quartzblock_lines_top : this.quartzblockIcons[par2]));
        }
        if (par1 == 1 || (par1 == 0 && par2 == 1)) {
            return (par2 == 1) ? this.quartzblock_chiseled_top : this.quartzblock_top;
        }
        if (par1 == 0) {
            return this.quartzblock_bottom;
        }
        if (par2 < 0 || par2 >= this.quartzblockIcons.length) {
            par2 = 0;
        }
        return this.quartzblockIcons[par2];
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, int par9) {
        if (par9 == 2) {
            switch (par5) {
                case 0:
                case 1: {
                    par9 = 2;
                    break;
                }
                case 2:
                case 3: {
                    par9 = 4;
                    break;
                }
                case 4:
                case 5: {
                    par9 = 3;
                    break;
                }
            }
        }
        return par9;
    }
    
    @Override
    public int damageDropped(final int par1) {
        return (par1 != 3 && par1 != 4) ? par1 : 2;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return (par1 != 3 && par1 != 4) ? super.createStackedBlock(par1) : new ItemStack(this.blockID, 1, 2);
    }
    
    @Override
    public int getRenderType() {
        return 39;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.quartzblockIcons = new Icon[BlockQuartz.quartzBlockTextureTypes.length];
        for (int var2 = 0; var2 < this.quartzblockIcons.length; ++var2) {
            if (BlockQuartz.quartzBlockTextureTypes[var2] == null) {
                this.quartzblockIcons[var2] = this.quartzblockIcons[var2 - 1];
            }
            else {
                this.quartzblockIcons[var2] = par1IconRegister.registerIcon(BlockQuartz.quartzBlockTextureTypes[var2]);
            }
        }
        this.quartzblock_top = par1IconRegister.registerIcon("quartzblock_top");
        this.quartzblock_chiseled_top = par1IconRegister.registerIcon("quartzblock_chiseled_top");
        this.quartzblock_lines_top = par1IconRegister.registerIcon("quartzblock_lines_top");
        this.quartzblock_bottom = par1IconRegister.registerIcon("quartzblock_bottom");
    }
}
