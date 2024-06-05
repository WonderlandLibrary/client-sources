package net.minecraft.src;

import java.util.*;

public class ComponentVillageField2 extends ComponentVillage
{
    private int averageGroundLevel;
    private int cropTypeA;
    private int cropTypeB;
    
    public ComponentVillageField2(final ComponentVillageStartPiece par1ComponentVillageStartPiece, final int par2, final Random par3Random, final StructureBoundingBox par4StructureBoundingBox, final int par5) {
        super(par1ComponentVillageStartPiece, par2);
        this.averageGroundLevel = -1;
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
        this.cropTypeA = this.pickRandomCrop(par3Random);
        this.cropTypeB = this.pickRandomCrop(par3Random);
    }
    
    private int pickRandomCrop(final Random par1Random) {
        switch (par1Random.nextInt(5)) {
            case 0: {
                return Block.carrot.blockID;
            }
            case 1: {
                return Block.potato.blockID;
            }
            default: {
                return Block.crops.blockID;
            }
        }
    }
    
    public static ComponentVillageField2 func_74902_a(final ComponentVillageStartPiece par0ComponentVillageStartPiece, final List par1List, final Random par2Random, final int par3, final int par4, final int par5, final int par6, final int par7) {
        final StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 7, 4, 9, par6);
        return (ComponentVillage.canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null) ? new ComponentVillageField2(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.averageGroundLevel < 0) {
            this.averageGroundLevel = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);
            if (this.averageGroundLevel < 0) {
                return true;
            }
            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 4 - 1, 0);
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 6, 4, 8, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 0, 1, 5, 0, 7, Block.tilledField.blockID, Block.tilledField.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 0, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 6, 0, 0, 6, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 0, 5, 0, 0, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 8, 5, 0, 8, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 0, 1, 3, 0, 7, Block.waterMoving.blockID, Block.waterMoving.blockID, false);
        for (int var4 = 1; var4 <= 7; ++var4) {
            this.placeBlockAtCurrentPosition(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 1, 1, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, this.cropTypeA, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 2, 1, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 4, 1, var4, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, this.cropTypeB, MathHelper.getRandomIntegerInRange(par2Random, 2, 7), 5, 1, var4, par3StructureBoundingBox);
        }
        for (int var4 = 0; var4 < 9; ++var4) {
            for (int var5 = 0; var5 < 7; ++var5) {
                this.clearCurrentPositionBlocksUpwards(par1World, var5, 4, var4, par3StructureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(par1World, Block.dirt.blockID, 0, var5, -1, var4, par3StructureBoundingBox);
            }
        }
        return true;
    }
}
