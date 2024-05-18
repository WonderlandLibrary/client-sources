package net.minecraft.src;

import java.util.*;

public class BlockFlowerPot extends Block
{
    public BlockFlowerPot(final int par1) {
        super(par1, Material.circuits);
        this.setBlockBoundsForItemRender();
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.375f;
        final float var2 = var1 / 2.0f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var1, 0.5f + var2);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 33;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final ItemStack var10 = par5EntityPlayer.inventory.getCurrentItem();
        if (var10 == null) {
            return false;
        }
        if (par1World.getBlockMetadata(par2, par3, par4) != 0) {
            return false;
        }
        final int var11 = getMetaForPlant(var10);
        if (var11 > 0) {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 2);
            if (!par5EntityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = var10;
                if (--itemStack.stackSize <= 0) {
                    par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null);
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        final ItemStack var5 = getPlantForMeta(par1World.getBlockMetadata(par2, par3, par4));
        return (var5 == null) ? Item.flowerPot.itemID : var5.itemID;
    }
    
    @Override
    public int getDamageValue(final World par1World, final int par2, final int par3, final int par4) {
        final ItemStack var5 = getPlantForMeta(par1World.getBlockMetadata(par2, par3, par4));
        return (var5 == null) ? Item.flowerPot.itemID : var5.getItemDamage();
    }
    
    @Override
    public boolean isFlowerPot() {
        return true;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World par1World, final int par2, final int par3, final int par4) {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World par1World, final int par2, final int par3, final int par4, final int par5, final float par6, final int par7) {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
        if (par5 > 0) {
            final ItemStack var8 = getPlantForMeta(par5);
            if (var8 != null) {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, var8);
            }
        }
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.flowerPot.itemID;
    }
    
    public static ItemStack getPlantForMeta(final int par0) {
        switch (par0) {
            case 1: {
                return new ItemStack(Block.plantRed);
            }
            case 2: {
                return new ItemStack(Block.plantYellow);
            }
            case 3: {
                return new ItemStack(Block.sapling, 1, 0);
            }
            case 4: {
                return new ItemStack(Block.sapling, 1, 1);
            }
            case 5: {
                return new ItemStack(Block.sapling, 1, 2);
            }
            case 6: {
                return new ItemStack(Block.sapling, 1, 3);
            }
            case 7: {
                return new ItemStack(Block.mushroomRed);
            }
            case 8: {
                return new ItemStack(Block.mushroomBrown);
            }
            case 9: {
                return new ItemStack(Block.cactus);
            }
            case 10: {
                return new ItemStack(Block.deadBush);
            }
            case 11: {
                return new ItemStack(Block.tallGrass, 1, 2);
            }
            default: {
                return null;
            }
        }
    }
    
    public static int getMetaForPlant(final ItemStack par0ItemStack) {
        final int var1 = par0ItemStack.getItem().itemID;
        if (var1 == Block.plantRed.blockID) {
            return 1;
        }
        if (var1 == Block.plantYellow.blockID) {
            return 2;
        }
        if (var1 == Block.cactus.blockID) {
            return 9;
        }
        if (var1 == Block.mushroomBrown.blockID) {
            return 8;
        }
        if (var1 == Block.mushroomRed.blockID) {
            return 7;
        }
        if (var1 == Block.deadBush.blockID) {
            return 10;
        }
        if (var1 == Block.sapling.blockID) {
            switch (par0ItemStack.getItemDamage()) {
                case 0: {
                    return 3;
                }
                case 1: {
                    return 4;
                }
                case 2: {
                    return 5;
                }
                case 3: {
                    return 6;
                }
            }
        }
        if (var1 == Block.tallGrass.blockID) {
            switch (par0ItemStack.getItemDamage()) {
                case 2: {
                    return 11;
                }
            }
        }
        return 0;
    }
}
