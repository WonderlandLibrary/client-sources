package net.minecraft.src;

import java.util.*;

public class StructureMineshaftPieces
{
    private static final WeightedRandomChestContent[] mineshaftChestContents;
    
    static {
        mineshaftChestContents = new WeightedRandomChestContent[] { new WeightedRandomChestContent(Item.ingotIron.itemID, 0, 1, 5, 10), new WeightedRandomChestContent(Item.ingotGold.itemID, 0, 1, 3, 5), new WeightedRandomChestContent(Item.redstone.itemID, 0, 4, 9, 5), new WeightedRandomChestContent(Item.dyePowder.itemID, 4, 4, 9, 5), new WeightedRandomChestContent(Item.diamond.itemID, 0, 1, 2, 3), new WeightedRandomChestContent(Item.coal.itemID, 0, 3, 8, 10), new WeightedRandomChestContent(Item.bread.itemID, 0, 1, 3, 15), new WeightedRandomChestContent(Item.pickaxeIron.itemID, 0, 1, 1, 1), new WeightedRandomChestContent(Block.rail.blockID, 0, 4, 8, 1), new WeightedRandomChestContent(Item.melonSeeds.itemID, 0, 2, 4, 10), new WeightedRandomChestContent(Item.pumpkinSeeds.itemID, 0, 2, 4, 10) };
    }
    
    private static StructureComponent getRandomComponent(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final int var7 = par1Random.nextInt(100);
        if (var7 >= 80) {
            final StructureBoundingBox var8 = ComponentMineshaftCross.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);
            if (var8 != null) {
                return new ComponentMineshaftCross(par6, par1Random, var8, par5);
            }
        }
        else if (var7 >= 70) {
            final StructureBoundingBox var8 = ComponentMineshaftStairs.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);
            if (var8 != null) {
                return new ComponentMineshaftStairs(par6, par1Random, var8, par5);
            }
        }
        else {
            final StructureBoundingBox var8 = ComponentMineshaftCorridor.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);
            if (var8 != null) {
                return new ComponentMineshaftCorridor(par6, par1Random, var8, par5);
            }
        }
        return null;
    }
    
    private static StructureComponent getNextMineShaftComponent(final StructureComponent par0StructureComponent, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        if (par7 > 8) {
            return null;
        }
        if (Math.abs(par3 - par0StructureComponent.getBoundingBox().minX) <= 80 && Math.abs(par5 - par0StructureComponent.getBoundingBox().minZ) <= 80) {
            final StructureComponent var8 = getRandomComponent(par1List, par2Random, par3, par4, par5, par6, par7 + 1);
            if (var8 != null) {
                par1List.add(var8);
                var8.buildComponent(par0StructureComponent, par1List, par2Random);
            }
            return var8;
        }
        return null;
    }
    
    static StructureComponent getNextComponent(final StructureComponent par0StructureComponent, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        return getNextMineShaftComponent(par0StructureComponent, par1List, par2Random, par3, par4, par5, par6, par7);
    }
    
    static WeightedRandomChestContent[] func_78816_a() {
        return StructureMineshaftPieces.mineshaftChestContents;
    }
}
