package net.minecraft.src;

import java.util.*;

public class ComponentVillageHall extends ComponentVillage
{
    private int averageGroundLevel;
    
    public ComponentVillageHall(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final int par2, final Random par3Random, final StructureBoundingBox par4StructureBoundingBox, final int par5) {
        super(par1ComponentVillageStartPiece, par2);
        this.averageGroundLevel = -1;
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
    }
    
    public static ComponentVillageHall func_74906_a(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 7, 11, par6);
        return (ComponentVillage.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null) ? new ComponentVillageHall(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 7 - 1, 0);
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 4, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 2, 1, 6, 8, 4, 10, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 2, 0, 6, 8, 0, 10, Block.dirt.blockID, Block.dirt.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 6, 0, 6, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 2, 1, 6, 2, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 1, 6, 8, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 10, 7, 1, 10, Block.fence.blockID, Block.fence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 7, 0, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 0, 0, 8, 3, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 0, 7, 1, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 5, 7, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 3, 5, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 1, 8, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 4, 8, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 2, 8, 5, 3, Block.planks.blockID, Block.planks.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 0, 4, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 0, 4, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 4, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 8, 4, 3, par3StructureBoundingBox);
        final int var4 = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
        final int var5 = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
        for (int var6 = -1; var6 <= 2; ++var6) {
            for (int var7 = 0; var7 <= 8; ++var7) {
                this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var4, var7, 4 + var6, var6, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var5, var7, 4 + var6, 5 - var6, par3StructureBoundingBox);
            }
        }
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 2, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 2, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 8, 2, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 8, 2, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 3, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 5, 2, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 6, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 2, 1, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, 2, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 1, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 3), 2, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 1), 1, 1, 3, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 5, 0, 1, 7, 0, 3, Block.stoneDoubleSlab.blockID, Block.stoneDoubleSlab.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.stoneDoubleSlab.blockID, 0, 6, 1, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stoneDoubleSlab.blockID, 0, 6, 1, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, 1, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, 2, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 3, 1, par3StructureBoundingBox);
        this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 2, 1, 0, this.getMetadataWithOffset(Block.doorWood.blockID, 1));
        if (this.getBlockIdAtCurrentPosition(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && this.getBlockIdAtCurrentPosition(par1World, 2, -1, -1, par3StructureBoundingBox) != 0) {
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 2, 0, -1, par3StructureBoundingBox);
        }
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 6, 1, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 6, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 6, 3, 4, par3StructureBoundingBox);
        this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 6, 1, 5, this.getMetadataWithOffset(Block.doorWood.blockID, 1));
        for (int var6 = 0; var6 < 5; ++var6) {
            for (int var7 = 0; var7 < 9; ++var7) {
                this.clearCurrentPositionBlocksUpwards(par1World, var7, 7, var6, par3StructureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, var7, -1, var6, par3StructureBoundingBox);
            }
        }
        this.spawnVillagers(par1World, par3StructureBoundingBox, 4, 1, 2, 2);
        return true;
    }
    
    @Override
    protected int getVillagerType(final int par1) {
        return (par1 == 0) ? 4 : 0;
    }
}
