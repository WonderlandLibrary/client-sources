package net.minecraft.src;

import java.util.*;

public class WorldGeneratorBonusChest extends WorldGenerator
{
    private final WeightedRandomChestContent[] theBonusChestGenerator;
    private final int itemsToGenerateInBonusChest;
    
    public WorldGeneratorBonusChest(final WeightedRandomChestContent[] par1ArrayOfWeightedRandomChestContent, final int par2) {
        this.theBonusChestGenerator = par1ArrayOfWeightedRandomChestContent;
        this.itemsToGenerateInBonusChest = par2;
    }
    
    @Override
    public boolean generate(final World par1World, final Random par2Random, final int par3, int par4, final int par5) {
        final boolean var6 = false;
        int var7;
        while (((var7 = par1World.getBlockId(par3, par4, par5)) == 0 || var7 == Block.leaves.blockID) && par4 > 1) {
            --par4;
        }
        if (par4 < 1) {
            return false;
        }
        ++par4;
        for (int var8 = 0; var8 < 4; ++var8) {
            final int var9 = par3 + par2Random.nextInt(4) - par2Random.nextInt(4);
            final int var10 = par4 + par2Random.nextInt(3) - par2Random.nextInt(3);
            final int var11 = par5 + par2Random.nextInt(4) - par2Random.nextInt(4);
            if (par1World.isAirBlock(var9, var10, var11) && par1World.doesBlockHaveSolidTopSurface(var9, var10 - 1, var11)) {
                par1World.setBlock(var9, var10, var11, Block.chest.blockID, 0, 2);
                final TileEntityChest var12 = (TileEntityChest)par1World.getBlockTileEntity(var9, var10, var11);
                if (var12 != null && var12 != null) {
                    WeightedRandomChestContent.generateChestContents(par2Random, this.theBonusChestGenerator, var12, this.itemsToGenerateInBonusChest);
                }
                if (par1World.isAirBlock(var9 - 1, var10, var11) && par1World.doesBlockHaveSolidTopSurface(var9 - 1, var10 - 1, var11)) {
                    par1World.setBlock(var9 - 1, var10, var11, Block.torchWood.blockID, 0, 2);
                }
                if (par1World.isAirBlock(var9 + 1, var10, var11) && par1World.doesBlockHaveSolidTopSurface(var9 - 1, var10 - 1, var11)) {
                    par1World.setBlock(var9 + 1, var10, var11, Block.torchWood.blockID, 0, 2);
                }
                if (par1World.isAirBlock(var9, var10, var11 - 1) && par1World.doesBlockHaveSolidTopSurface(var9 - 1, var10 - 1, var11)) {
                    par1World.setBlock(var9, var10, var11 - 1, Block.torchWood.blockID, 0, 2);
                }
                if (par1World.isAirBlock(var9, var10, var11 + 1) && par1World.doesBlockHaveSolidTopSurface(var9 - 1, var10 - 1, var11)) {
                    par1World.setBlock(var9, var10, var11 + 1, Block.torchWood.blockID, 0, 2);
                }
                return true;
            }
        }
        return false;
    }
}
