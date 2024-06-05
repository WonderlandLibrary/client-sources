package net.minecraft.src;

import java.util.*;

public class ComponentVillageHouse1 extends ComponentVillage
{
    private int averageGroundLevel;
    
    public ComponentVillageHouse1(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final int par2, final Random par3Random, final StructureBoundingBox par4StructureBoundingBox, final int par5) {
        super(par1ComponentVillageStartPiece, par2);
        this.averageGroundLevel = -1;
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
    }
    
    public static ComponentVillageHouse1 func_74898_a(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 9, 9, 6, par6);
        return (ComponentVillage.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null) ? new ComponentVillageHouse1(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 9 - 1, 0);
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 7, 5, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 0, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 8, 5, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 6, 1, 8, 6, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 7, 2, 8, 7, 3, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        final int var4 = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
        final int var5 = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 2);
        for (int var6 = -1; var6 <= 2; ++var6) {
            for (int var7 = 0; var7 <= 8; ++var7) {
                this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var4, var7, 6 + var6, var6, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var5, var7, 6 + var6, 5 - var6, par3StructureBoundingBox);
            }
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 5, 8, 1, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 1, 0, 8, 1, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 2, 1, 0, 7, 1, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 4, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 5, 0, 4, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 2, 5, 8, 4, 5, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 2, 0, 8, 4, 0, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 1, 0, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 5, 7, 4, 5, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 2, 1, 8, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 2, 0, 7, 4, 0, Block.planks.blockID, Block.planks.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 2, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 5, 2, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 6, 2, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 3, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 5, 3, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 6, 3, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 3, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 8, 3, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 3, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 5, 2, 5, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 6, 2, 5, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 1, 7, 4, 1, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 4, 7, 4, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 3, 4, 7, 3, 4, Block.bookShelf.blockID, Block.bookShelf.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 7, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 0), 7, 1, 3, par3StructureBoundingBox);
        int var6 = this.getMetadataWithOffset(Block.stairsWoodOak.blockID, 3);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var6, 6, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var6, 5, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var6, 4, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.stairsWoodOak.blockID, var6, 3, 1, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 6, 1, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, 6, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 1, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, 4, 2, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.workbench.blockID, 0, 7, 1, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 1, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 2, 0, par3StructureBoundingBox);
        this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, this.getMetadataWithOffset(Block.doorWood.blockID, 1));
        if (this.getBlockIdAtCurrentPosition(par1World, 1, 0, -1, par3StructureBoundingBox) == 0 && this.getBlockIdAtCurrentPosition(par1World, 1, -1, -1, par3StructureBoundingBox) != 0) {
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 0, -1, par3StructureBoundingBox);
        }
        for (int var7 = 0; var7 < 6; ++var7) {
            for (int var8 = 0; var8 < 9; ++var8) {
                this.clearCurrentPositionBlocksUpwards(par1World, var8, 9, var7, par3StructureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, var8, -1, var7, par3StructureBoundingBox);
            }
        }
        this.spawnVillagers(par1World, par3StructureBoundingBox, 2, 1, 2, 1);
        return true;
    }
    
    @Override
    protected int getVillagerType(final int par1) {
        return 1;
    }
}
