package net.minecraft.src;

import java.util.*;

public class BlockSilverfish extends Block
{
    public static final String[] silverfishStoneTypes;
    
    static {
        silverfishStoneTypes = new String[] { "stone", "cobble", "brick" };
    }
    
    public BlockSilverfish(final int par1) {
        super(par1, Material.clay);
        this.setHardness(0.0f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par2 == 1) ? Block.cobblestone.getBlockTextureFromSide(par1) : ((par2 == 2) ? Block.stoneBrick.getBlockTextureFromSide(par1) : Block.stone.getBlockTextureFromSide(par1));
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.isRemote) {
            final EntitySilverfish var6 = new EntitySilverfish(par1World);
            var6.setLocationAndAngles(par2 + 0.5, par3, par4 + 0.5, 0.0f, 0.0f);
            par1World.spawnEntityInWorld(var6);
            var6.spawnExplosionParticle();
        }
        super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return 0;
    }
    
    public static boolean getPosingIdByMetadata(final int par0) {
        return par0 == Block.stone.blockID || par0 == Block.cobblestone.blockID || par0 == Block.stoneBrick.blockID;
    }
    
    public static int getMetadataForBlockType(final int par0) {
        return (par0 == Block.cobblestone.blockID) ? 1 : ((par0 == Block.stoneBrick.blockID) ? 2 : 0);
    }
    
    @Override
    protected ItemStack createStackedBlock(final int par1) {
        Block var2 = Block.stone;
        if (par1 == 1) {
            var2 = Block.cobblestone;
        }
        if (par1 == 2) {
            var2 = Block.stoneBrick;
        }
        return new ItemStack(var2);
    }
    
    @Override
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }
    
    @Override
    public void getSubBlocks(final int par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int var4 = 0; var4 < 3; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
