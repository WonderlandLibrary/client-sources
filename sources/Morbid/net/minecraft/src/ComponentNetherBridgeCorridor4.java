package net.minecraft.src;

import java.util.*;

public class ComponentNetherBridgeCorridor4 extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeCorridor4(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.boundingBox = par3StructureBoundingBox;
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        byte var4 = 1;
        if (this.coordBaseMode == 1 || this.coordBaseMode == 2) {
            var4 = 5;
        }
        this.getNextComponentX((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
        this.getNextComponentZ((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 0, var4, par3Random.nextInt(8) > 0);
    }
    
    public static ComponentNetherBridgeCorridor4 createValidComponent(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -3, 0, 0, 9, 7, 9, par5);
        return (ComponentNetherBridgePiece.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentNetherBridgeCorridor4(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 1, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 8, 5, 8, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 6, 0, 8, 6, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 2, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 6, 2, 0, 8, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 3, 0, 1, 4, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 7, 3, 0, 7, 4, 0, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 4, 8, 2, 8, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 2, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 6, 1, 4, 7, 2, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 8, 8, 3, 8, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 6, 0, 3, 7, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 3, 6, 8, 3, 7, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 5, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 8, 3, 4, 8, 5, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 3, 5, 2, 5, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 6, 3, 5, 7, 5, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 5, 1, 5, 5, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 7, 4, 5, 7, 5, 5, Block.netherFence.blockID, Block.netherFence.blockID, false);
        for (int var4 = 0; var4 <= 5; ++var4) {
            for (int var5 = 0; var5 <= 8; ++var5) {
                this.fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, var5, -1, var4, par3StructureBoundingBox);
            }
        }
        return true;
    }
}
