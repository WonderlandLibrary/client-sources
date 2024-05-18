package net.minecraft.src;

import java.util.*;

public class BlockSapling extends BlockFlower
{
    public static final String[] WOOD_TYPES;
    private static final String[] field_94370_b;
    private Icon[] saplingIcon;
    
    static {
        WOOD_TYPES = new String[] { "oak", "spruce", "birch", "jungle" };
        field_94370_b = new String[] { "sapling", "sapling_spruce", "sapling_birch", "sapling_jungle" };
    }
    
    protected BlockSapling(final int par1) {
        super(par1);
        final float var2 = 0.4f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var2 * 2.0f, 0.5f + var2);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            super.updateTick(par1World, par2, par3, par4, par5Random);
            if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0) {
                this.markOrGrowMarked(par1World, par2, par3, par4, par5Random);
            }
        }
    }
    
    @Override
    public Icon getIcon(final int par1, int par2) {
        par2 &= 0x3;
        return this.saplingIcon[par2];
    }
    
    public void markOrGrowMarked(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4);
        if ((var6 & 0x8) == 0x0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 | 0x8, 4);
        }
        else {
            this.growTree(par1World, par2, par3, par4, par5Random);
        }
    }
    
    public void growTree(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final int var6 = par1World.getBlockMetadata(par2, par3, par4) & 0x3;
        Object var7 = null;
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;
        if (var6 == 1) {
            var7 = new WorldGenTaiga2(true);
        }
        else if (var6 == 2) {
            var7 = new WorldGenForest(true);
        }
        else if (var6 == 3) {
            for (var8 = 0; var8 >= -1; --var8) {
                for (var9 = 0; var9 >= -1; --var9) {
                    if (this.isSameSapling(par1World, par2 + var8, par3, par4 + var9, 3) && this.isSameSapling(par1World, par2 + var8 + 1, par3, par4 + var9, 3) && this.isSameSapling(par1World, par2 + var8, par3, par4 + var9 + 1, 3) && this.isSameSapling(par1World, par2 + var8 + 1, par3, par4 + var9 + 1, 3)) {
                        var7 = new WorldGenHugeTrees(true, 10 + par5Random.nextInt(20), 3, 3);
                        var10 = true;
                        break;
                    }
                }
                if (var7 != null) {
                    break;
                }
            }
            if (var7 == null) {
                var9 = 0;
                var8 = 0;
                var7 = new WorldGenTrees(true, 4 + par5Random.nextInt(7), 3, 3, false);
            }
        }
        else {
            var7 = new WorldGenTrees(true);
            if (par5Random.nextInt(10) == 0) {
                var7 = new WorldGenBigTree(true);
            }
        }
        if (var10) {
            par1World.setBlock(par2 + var8, par3, par4 + var9, 0, 0, 4);
            par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, 0, 0, 4);
            par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, 0, 0, 4);
            par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, 0, 0, 4);
        }
        else {
            par1World.setBlock(par2, par3, par4, 0, 0, 4);
        }
        if (!((WorldGenerator)var7).generate(par1World, par5Random, par2 + var8, par3, par4 + var9)) {
            if (var10) {
                par1World.setBlock(par2 + var8, par3, par4 + var9, this.blockID, var6, 4);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9, this.blockID, var6, 4);
                par1World.setBlock(par2 + var8, par3, par4 + var9 + 1, this.blockID, var6, 4);
                par1World.setBlock(par2 + var8 + 1, par3, par4 + var9 + 1, this.blockID, var6, 4);
            }
            else {
                par1World.setBlock(par2, par3, par4, this.blockID, var6, 4);
            }
        }
    }
    
    public boolean isSameSapling(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        return par1World.getBlockId(par2, par3, par4) == this.blockID && (par1World.getBlockMetadata(par2, par3, par4) & 0x3) == par5;
    }
    
    @Override
    public int damageDropped(final int par1) {
        return par1 & 0x3;
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.saplingIcon = new Icon[BlockSapling.field_94370_b.length];
        for (int var2 = 0; var2 < this.saplingIcon.length; ++var2) {
            this.saplingIcon[var2] = par1IconRegister.registerIcon(BlockSapling.field_94370_b[var2]);
        }
    }
}
