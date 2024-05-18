package net.minecraft.src;

import java.util.*;

public class ComponentStrongholdPrison extends ComponentStronghold
{
    protected final EnumDoor doorType;
    
    public ComponentStrongholdPrison(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.doorType = this.getRandomDoor(par2Random);
        this.boundingBox = par3StructureBoundingBox;
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        this.getNextComponentNormal((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 1);
    }
    
    public static ComponentStrongholdPrison findValidPlacement(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 9, 5, 11, par5);
        return (ComponentStronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentStrongholdPrison(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        }
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 4, 10, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.doorType, 1, 1, 0);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 10, 3, 3, 10, 0, 0, false);
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 1, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 3, 4, 3, 3, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 7, 4, 3, 7, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 3, 9, false, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 4, 4, 3, 6, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 5, 7, 3, 5, Block.fenceIron.blockID, Block.fenceIron.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, 4, 3, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, 4, 3, 8, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, this.getMetadataWithOffset(Block.doorIron.blockID, 3), 4, 1, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, this.getMetadataWithOffset(Block.doorIron.blockID, 3) + 8, 4, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, this.getMetadataWithOffset(Block.doorIron.blockID, 3), 4, 1, 8, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, this.getMetadataWithOffset(Block.doorIron.blockID, 3) + 8, 4, 2, 8, par3StructureBoundingBox);
        return true;
    }
}
