package net.minecraft.src;

import java.util.*;

public class ComponentStrongholdStraight extends ComponentStronghold
{
    private final EnumDoor doorType;
    private final boolean expandsX;
    private final boolean expandsZ;
    
    public ComponentStrongholdStraight(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.doorType = this.getRandomDoor(par2Random);
        this.boundingBox = par3StructureBoundingBox;
        this.expandsX = (par2Random.nextInt(2) == 0);
        this.expandsZ = (par2Random.nextInt(2) == 0);
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        this.getNextComponentNormal((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 1);
        if (this.expandsX) {
            this.getNextComponentX((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 2);
        }
        if (this.expandsZ) {
            this.getNextComponentZ((ComponentStrongholdStairs2)par1StructureComponent, par2List, par3Random, 1, 2);
        }
    }
    
    public static ComponentStrongholdStraight findValidPlacement(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
        return (ComponentStronghold.canStrongholdGoDeeper(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentStrongholdStraight(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox)) {
            return false;
        }
        this.fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureStrongholdPieces.getStrongholdStones());
        this.placeDoor(par1World, par2Random, par3StructureBoundingBox, this.doorType, 1, 1, 0);
        this.placeDoor(par1World, par2Random, par3StructureBoundingBox, EnumDoor.OPENING, 1, 1, 6);
        this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1f, 1, 2, 1, Block.torchWood.blockID, 0);
        this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1f, 3, 2, 1, Block.torchWood.blockID, 0);
        this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1f, 1, 2, 5, Block.torchWood.blockID, 0);
        this.randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.1f, 3, 2, 5, Block.torchWood.blockID, 0);
        if (this.expandsX) {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 2, 0, 3, 4, 0, 0, false);
        }
        if (this.expandsZ) {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 2, 4, 3, 4, 0, 0, false);
        }
        return true;
    }
}
