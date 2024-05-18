package net.minecraft.src;

import java.util.*;

public class BlockGrass extends Block
{
    private Icon iconGrassTop;
    private Icon iconSnowSide;
    private Icon iconGrassSideOverlay;
    
    protected BlockGrass(final int par1) {
        super(par1, Material.grass);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.iconGrassTop : ((par1 == 0) ? Block.dirt.getBlockTextureFromSide(par1) : this.blockIcon);
    }
    
    @Override
    public Icon getBlockTexture(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (par5 == 1) {
            return this.iconGrassTop;
        }
        if (par5 == 0) {
            return Block.dirt.getBlockTextureFromSide(par5);
        }
        final Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
        return (var6 != Material.snow && var6 != Material.craftedSnow) ? this.blockIcon : this.iconSnowSide;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("grass_side");
        this.iconGrassTop = par1IconRegister.registerIcon("grass_top");
        this.iconSnowSide = par1IconRegister.registerIcon("snow_side");
        this.iconGrassSideOverlay = par1IconRegister.registerIcon("grass_side_overlay");
    }
    
    @Override
    public int getBlockColor() {
        final double var1 = 0.5;
        final double var2 = 1.0;
        return ColorizerGrass.getGrassColor(var1, var2);
    }
    
    @Override
    public int getRenderColor(final int par1) {
        return this.getBlockColor();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                final int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeGrassColor();
                var5 += (var10 & 0xFF0000) >> 16;
                var6 += (var10 & 0xFF00) >> 8;
                var7 += (var10 & 0xFF);
            }
        }
        return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | (var7 / 9 & 0xFF);
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (!par1World.isRemote) {
            if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[par1World.getBlockId(par2, par3 + 1, par4)] > 2) {
                par1World.setBlock(par2, par3, par4, Block.dirt.blockID);
            }
            else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9) {
                for (int var6 = 0; var6 < 4; ++var6) {
                    final int var7 = par2 + par5Random.nextInt(3) - 1;
                    final int var8 = par3 + par5Random.nextInt(5) - 3;
                    final int var9 = par4 + par5Random.nextInt(3) - 1;
                    final int var10 = par1World.getBlockId(var7, var8 + 1, var9);
                    if (par1World.getBlockId(var7, var8, var9) == Block.dirt.blockID && par1World.getBlockLightValue(var7, var8 + 1, var9) >= 4 && Block.lightOpacity[var10] <= 2) {
                        par1World.setBlock(var7, var8, var9, Block.grass.blockID);
                    }
                }
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.dirt.idDropped(0, par2Random, par3);
    }
    
    public static Icon getIconSideOverlay() {
        return Block.grass.iconGrassSideOverlay;
    }
}
