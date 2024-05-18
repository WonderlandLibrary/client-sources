package net.minecraft.src;

import java.util.*;

public class WorldGenDungeons extends WorldGenerator
{
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        final byte var6 = 3;
        final int var7 = par2Random.nextInt(2) + 2;
        final int var8 = par2Random.nextInt(2) + 2;
        int var9 = 0;
        for (int var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; ++var10) {
            for (int var11 = par4 - 1; var11 <= par4 + var6 + 1; ++var11) {
                for (int var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; ++var12) {
                    final Material var13 = par1World.getBlockMaterial(var10, var11, var12);
                    if (var11 == par4 - 1 && !var13.isSolid()) {
                        return false;
                    }
                    if (var11 == par4 + var6 + 1 && !var13.isSolid()) {
                        return false;
                    }
                    if ((var10 == par3 - var7 - 1 || var10 == par3 + var7 + 1 || var12 == par5 - var8 - 1 || var12 == par5 + var8 + 1) && var11 == par4 && par1World.isAirBlock(var10, var11, var12) && par1World.isAirBlock(var10, var11 + 1, var12)) {
                        ++var9;
                    }
                }
            }
        }
        if (var9 >= 1 && var9 <= 5) {
            for (int var10 = par3 - var7 - 1; var10 <= par3 + var7 + 1; ++var10) {
                for (int var11 = par4 + var6; var11 >= par4 - 1; --var11) {
                    for (int var12 = par5 - var8 - 1; var12 <= par5 + var8 + 1; ++var12) {
                        if (var10 != par3 - var7 - 1 && var11 != par4 - 1 && var12 != par5 - var8 - 1 && var10 != par3 + var7 + 1 && var11 != par4 + var6 + 1 && var12 != par5 + var8 + 1) {
                            par1World.setBlockToAir(var10, var11, var12);
                        }
                        else if (var11 >= 0 && !par1World.getBlockMaterial(var10, var11 - 1, var12).isSolid()) {
                            par1World.setBlockToAir(var10, var11, var12);
                        }
                        else if (par1World.getBlockMaterial(var10, var11, var12).isSolid()) {
                            if (var11 == par4 - 1 && par2Random.nextInt(4) != 0) {
                                par1World.setBlock(var10, var11, var12, Block.cobblestoneMossy.blockID, 0, 2);
                            }
                            else {
                                par1World.setBlock(var10, var11, var12, Block.cobblestone.blockID, 0, 2);
                            }
                        }
                    }
                }
            }
            for (int var10 = 0; var10 < 2; ++var10) {
                for (int var11 = 0; var11 < 3; ++var11) {
                    final int var12 = par3 + par2Random.nextInt(var7 * 2 + 1) - var7;
                    final int var14 = par5 + par2Random.nextInt(var8 * 2 + 1) - var8;
                    if (par1World.isAirBlock(var12, par4, var14)) {
                        int var15 = 0;
                        if (par1World.getBlockMaterial(var12 - 1, par4, var14).isSolid()) {
                            ++var15;
                        }
                        if (par1World.getBlockMaterial(var12 + 1, par4, var14).isSolid()) {
                            ++var15;
                        }
                        if (par1World.getBlockMaterial(var12, par4, var14 - 1).isSolid()) {
                            ++var15;
                        }
                        if (par1World.getBlockMaterial(var12, par4, var14 + 1).isSolid()) {
                            ++var15;
                        }
                        if (var15 == 1) {
                            par1World.setBlock(var12, par4, var14, Block.chest.blockID, 0, 2);
                            final TileEntityChest var16 = (TileEntityChest)par1World.getBlockTileEntity(var12, par4, var14);
                            if (var16 != null) {
                                for (int var17 = 0; var17 < 8; ++var17) {
                                    final ItemStack var18 = this.pickCheckLootItem(par2Random);
                                    if (var18 != null) {
                                        var16.setInventorySlotContents(par2Random.nextInt(var16.getSizeInventory()), var18);
                                    }
                                }
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            par1World.setBlock(par3, par4, par5, Block.mobSpawner.blockID, 0, 2);
            final TileEntityMobSpawner var19 = (TileEntityMobSpawner)par1World.getBlockTileEntity(par3, par4, par5);
            if (var19 != null) {
                var19.func_98049_a().setMobID(this.pickMobSpawner(par2Random));
            }
            else {
                System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
            }
            return true;
        }
        return false;
    }
    
    private ItemStack pickCheckLootItem(final Random par1Random) {
        final int var2 = par1Random.nextInt(12);
        return (var2 == 0) ? new ItemStack(Item.saddle) : ((var2 == 1) ? new ItemStack(Item.ingotIron, par1Random.nextInt(4) + 1) : ((var2 == 2) ? new ItemStack(Item.bread) : ((var2 == 3) ? new ItemStack(Item.wheat, par1Random.nextInt(4) + 1) : ((var2 == 4) ? new ItemStack(Item.gunpowder, par1Random.nextInt(4) + 1) : ((var2 == 5) ? new ItemStack(Item.silk, par1Random.nextInt(4) + 1) : ((var2 == 6) ? new ItemStack(Item.bucketEmpty) : ((var2 == 7 && par1Random.nextInt(100) == 0) ? new ItemStack(Item.appleGold) : ((var2 == 8 && par1Random.nextInt(2) == 0) ? new ItemStack(Item.redstone, par1Random.nextInt(4) + 1) : ((var2 == 9 && par1Random.nextInt(10) == 0) ? new ItemStack(Item.itemsList[Item.record13.itemID + par1Random.nextInt(2)]) : ((var2 == 10) ? new ItemStack(Item.dyePowder, 1, 3) : ((var2 == 11) ? Item.enchantedBook.func_92109_a(par1Random) : null)))))))))));
    }
    
    private String pickMobSpawner(final Random par1Random) {
        final int var2 = par1Random.nextInt(4);
        return (var2 == 0) ? "Skeleton" : ((var2 == 1) ? "Zombie" : ((var2 == 2) ? "Zombie" : ((var2 == 3) ? "Spider" : "")));
    }
}
