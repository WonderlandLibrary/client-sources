package net.minecraft.src;

import java.util.*;

public class BlockLog extends Block
{
    public static final String[] woodType;
    public static final String[] treeTextureTypes;
    private Icon[] iconArray;
    private Icon tree_top;
    
    static {
        woodType = new String[] { "oak", "spruce", "birch", "jungle" };
        treeTextureTypes = new String[] { "tree_side", "tree_spruce", "tree_birch", "tree_jungle" };
    }
    
    protected BlockLog(final int par1) {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int getRenderType() {
        return 31;
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 1;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.wood.blockID;
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final byte var7 = 4;
        final int var8 = var7 + 1;
        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8)) {
            for (int var9 = -var7; var9 <= var7; ++var9) {
                for (int var10 = -var7; var10 <= var7; ++var10) {
                    for (int var11 = -var7; var11 <= var7; ++var11) {
                        final int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);
                        if (var12 == Block.leaves.blockID) {
                            final int var13 = par1World.getBlockMetadata(par2 + var9, par3 + var10, par4 + var11);
                            if ((var13 & 0x8) == 0x0) {
                                par1World.setBlockMetadataWithNotify(par2 + var9, par3 + var10, par4 + var11, var13 | 0x8, 4);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public int onBlockPlaced(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final float par7, final float par8, final int par9) {
        final int var10 = par9 & 0x3;
        byte var11 = 0;
        switch (par5) {
            case 0:
            case 1: {
                var11 = 0;
                break;
            }
            case 2:
            case 3: {
                var11 = 8;
                break;
            }
            case 4:
            case 5: {
                var11 = 4;
                break;
            }
        }
        return var10 | var11;
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        final int var3 = par2 & 0xC;
        final int var4 = par2 & 0x3;
        return (var3 == 0 && (par1 == 1 || par1 == 0)) ? this.tree_top : ((var3 == 4 && (par1 == 5 || par1 == 4)) ? this.tree_top : ((var3 == 8 && (par1 == 2 || par1 == 3)) ? this.tree_top : this.iconArray[var4]));
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1 & 0x3;
    }
    
    public static int limitToValidMetadata(final int par0) {
        return par0 & 0x3;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        return new ItemStack(this.blockID, 1, limitToValidMetadata(par1));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.tree_top = par1IconRegister.registerIcon("tree_top");
        this.iconArray = new Icon[BlockLog.treeTextureTypes.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(BlockLog.treeTextureTypes[var2]);
        }
    }
}
