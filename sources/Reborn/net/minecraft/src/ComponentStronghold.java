package net.minecraft.src;

import java.util.*;

abstract class ComponentStronghold extends StructureComponent
{
    protected ComponentStronghold(final int par1) {
        super(par1);
    }
    
    protected void placeDoor(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final EnumDoor par4EnumDoor, final int par5, final int par6, final int par7) {
        switch (EnumDoorHelper.doorEnum[par4EnumDoor.ordinal()]) {
            default: {
                this.fillWithBlocks(par1World, par3StructureBoundingBox, par5, par6, par7, par5 + 3 - 1, par6 + 3 - 1, par7, 0, 0, false);
                break;
            }
            case 2: {
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.doorWood.blockID, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.doorWood.blockID, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                break;
            }
            case 3: {
                this.placeBlockAtCurrentPosition(par1World, 0, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, 0, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.fenceIron.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                break;
            }
            case 4: {
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneBrick.blockID, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.doorIron.blockID, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneButton.blockID, this.getMetadataWithOffset(Block.stoneButton.blockID, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
                this.placeBlockAtCurrentPosition(par1World, Block.stoneButton.blockID, this.getMetadataWithOffset(Block.stoneButton.blockID, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
                break;
            }
        }
    }
    
    protected EnumDoor getRandomDoor(final Random par1Random) {
        final int var2 = par1Random.nextInt(5);
        switch (var2) {
            default: {
                return EnumDoor.OPENING;
            }
            case 2: {
                return EnumDoor.WOOD_DOOR;
            }
            case 3: {
                return EnumDoor.GRATES;
            }
            case 4: {
                return EnumDoor.IRON_DOOR;
            }
        }
    }
    
    protected StructureComponent getNextComponentNormal(final ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, final List par2List, final Random par3Random, final int par4, final int par5) {
        switch (this.coordBaseMode) {
            case 0: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.maxZ + 1, this.coordBaseMode, this.getComponentType());
            }
            case 1: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType());
            }
            case 2: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par4, this.boundingBox.minY + par5, this.boundingBox.minZ - 1, this.coordBaseMode, this.getComponentType());
            }
            case 3: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par5, this.boundingBox.minZ + par4, this.coordBaseMode, this.getComponentType());
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructureComponent getNextComponentX(final ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, final List par2List, final Random par3Random, final int par4, final int par5) {
        switch (this.coordBaseMode) {
            case 0: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
            }
            case 1: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
            }
            case 2: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 1, this.getComponentType());
            }
            case 3: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.minZ - 1, 2, this.getComponentType());
            }
            default: {
                return null;
            }
        }
    }
    
    protected StructureComponent getNextComponentZ(final ComponentStrongholdStairs2 par1ComponentStrongholdStairs2, final List par2List, final Random par3Random, final int par4, final int par5) {
        switch (this.coordBaseMode) {
            case 0: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
            }
            case 1: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }
            case 2: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY + par4, this.boundingBox.minZ + par5, 3, this.getComponentType());
            }
            case 3: {
                return StructureStrongholdPieces.getNextValidComponentAccess(par1ComponentStrongholdStairs2, par2List, par3Random, this.boundingBox.minX + par5, this.boundingBox.minY + par4, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }
            default: {
                return null;
            }
        }
    }
    
    protected static boolean canStrongholdGoDeeper(final StructureBoundingBox par0StructureBoundingBox) {
        return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
    }
}
