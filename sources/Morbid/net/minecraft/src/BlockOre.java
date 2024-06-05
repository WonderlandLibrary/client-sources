package net.minecraft.src;

import java.util.*;

public class BlockOre extends Block
{
    public BlockOre(final int par1) {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return (this.blockID == Block.oreCoal.blockID) ? Item.coal.itemID : ((this.blockID == Block.oreDiamond.blockID) ? Item.diamond.itemID : ((this.blockID == Block.oreLapis.blockID) ? Item.dyePowder.itemID : ((this.blockID == Block.oreEmerald.blockID) ? Item.emerald.itemID : ((this.blockID == Block.oreNetherQuartz.blockID) ? Item.netherQuartz.itemID : this.blockID))));
    }
    
    @Override
    public int quantityDropped(final Random par1Random) {
        return (this.blockID == Block.oreLapis.blockID) ? (4 + par1Random.nextInt(5)) : 1;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int par1, final Random par2Random) {
        if (par1 > 0 && this.blockID != this.idDropped(0, par2Random, par1)) {
            int var3 = par2Random.nextInt(par1 + 2) - 1;
            if (var3 < 0) {
                var3 = 0;
            }
            return this.quantityDropped(par2Random) * (var3 + 1);
        }
        return this.quantityDropped(par2Random);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        if (this.idDropped(par5, par1World.rand, par7) != this.blockID) {
            int var8 = 0;
            if (this.blockID == Block.oreCoal.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 0, 2);
            }
            else if (this.blockID == Block.oreDiamond.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
            }
            else if (this.blockID == Block.oreEmerald.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
            }
            else if (this.blockID == Block.oreLapis.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
            }
            else if (this.blockID == Block.oreNetherQuartz.blockID) {
                var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
            }
            this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
        }
    }
    
    @Override
    public int damageDropped(final int par1) {
        return (this.blockID == Block.oreLapis.blockID) ? 4 : 0;
    }
}
