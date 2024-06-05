package net.minecraft.src;

import java.util.*;

public class BlockMycelium extends Block
{
    private Icon field_94422_a;
    private Icon field_94421_b;
    
    protected BlockMycelium(final int par1) {
        super(par1, Material.grass);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public Icon getIcon(final int par1, final int par2) {
        return (par1 == 1) ? this.field_94422_a : ((par1 == 0) ? Block.dirt.getBlockTextureFromSide(par1) : this.blockIcon);
    }
    
    @Override
    public Icon getBlockTexture(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (par5 == 1) {
            return this.field_94422_a;
        }
        if (par5 == 0) {
            return Block.dirt.getBlockTextureFromSide(par5);
        }
        final Material var6 = par1IBlockAccess.getBlockMaterial(par2, par3 + 1, par4);
        return (var6 != Material.snow && var6 != Material.craftedSnow) ? this.blockIcon : this.field_94421_b;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("mycel_side");
        this.field_94422_a = par1IconRegister.registerIcon("mycel_top");
        this.field_94421_b = par1IconRegister.registerIcon("snow_side");
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
                        par1World.setBlock(var7, var8, var9, this.blockID);
                    }
                }
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);
        if (par5Random.nextInt(10) == 0) {
            par1World.spawnParticle("townaura", par2 + par5Random.nextFloat(), par3 + 1.1f, par4 + par5Random.nextFloat(), 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.dirt.idDropped(0, par2Random, par3);
    }
}
