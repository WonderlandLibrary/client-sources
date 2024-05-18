package net.minecraft.src;

import java.util.*;

public class ComponentStrongholdStairsStraight extends ComponentStronghold
{
    private final EnumDoor doorType;
    
    public ComponentStrongholdStairsStraight(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.doorType = this.getRandomDoor(par2Random);
        this.boundingBox = par3StructureBoundingBox;
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        this.getNextComponentNormal((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 1);
    }
    
    public static ComponentStrongholdStairsStraight findValidPlacement(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 8, par5);
        return (ComponentStronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentStrongholdStairsStraight(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        }
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 7, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.doorType, 1, 7, 0);
        this.placeDoor(par1World, par2Random, par3StructureBoundingBox, EnumDoor.OPENING, 1, 1, 7);
        final int var4 = this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 2);
        for (int var5 = 0; var5 < 6; ++var5) {
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, var4, 1, 6 - var5, 1 + var5, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, var4, 2, 6 - var5, 1 + var5, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, var4, 3, 6 - var5, 1 + var5, par3StructureBoundingBox);
            if (var5 < 5) {
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 1, 5 - var5, 1 + var5, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 2, 5 - var5, 1 + var5, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, 3, 5 - var5, 1 + var5, par3StructureBoundingBox);
            }
        }
        return true;
    }
}
