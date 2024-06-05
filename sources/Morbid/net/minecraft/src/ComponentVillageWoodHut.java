package net.minecraft.src;

import java.util.*;

public class ComponentVillageWoodHut extends ComponentVillage
{
    private int averageGroundLevel;
    private final boolean isTallHouse;
    private final int tablePosition;
    
    public ComponentVillageWoodHut(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final int par2, final Random par3Random, final StructureBoundingBox par4StructureBoundingBox, final int par5) {
        super(par1ComponentVillageStartPiece, par2);
        this.averageGroundLevel = -1;
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
        this.isTallHouse = par3Random.nextBoolean();
        this.tablePosition = par3Random.nextInt(3);
    }
    
    public static ComponentVillageWoodHut func_74908_a(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 4, 6, 5, par6);
        return (ComponentVillage.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null) ? new ComponentVillageWoodHut(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 6 - 1, 0);
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 5, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 3, 0, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 3, Block.dirt.blockID, Block.dirt.blockID, false);
        if (this.isTallHouse) {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 1, 2, 4, 3, Block.wood.blockID, Block.wood.blockID, false);
        }
        else {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 2, 5, 3, Block.wood.blockID, Block.wood.blockID, false);
        }
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 1, 4, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 2, 4, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 1, 4, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 2, 4, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 3, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 4, 3, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 1, 3, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 3, 2, 2, par3StructureBoundingBox);
        if (this.tablePosition > 0) {
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, this.tablePosition, 1, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, this.tablePosition, 2, 3, par3StructureBoundingBox);
        }
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 1, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 2, 0, par3StructureBoundingBox);
        this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, this.getMetadataWithOffset(Block.doorWood.blockID, 1));
        if (this.getBlockIdAtCurrentPosition(par1World, 1, 0, -1, par3StructureBoundingBox) == 0 && this.getBlockIdAtCurrentPosition(par1World, 1, -1, -1, par3StructureBoundingBox) != 0) {
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 0, -1, par3StructureBoundingBox);
        }
        for (int var4 = 0; var4 < 5; ++var4) {
            for (int var5 = 0; var5 < 4; ++var5) {
                this.clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, var5, -1, var4, par3StructureBoundingBox);
            }
        }
        this.spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
