package net.minecraft.src;

import java.util.*;

public class ComponentNetherBridgeStraight extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeStraight(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.boundingBox = par3StructureBoundingBox;
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        this.getNextComponentNormal((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 1, 3, false);
    }
    
    public static ComponentNetherBridgeStraight createValidComponent(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 19, par5);
        return (ComponentNetherBridgePiece.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentNetherBridgeStraight(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 0, 4, 4, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 0, 3, 7, 18, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 2, 5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 13, 4, 2, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 3, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 15, 4, 1, 18, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        for (int var4 = 0; var4 <= 4; ++var4) {
            for (int var5 = 0; var5 <= 2; ++var5) {
                this.fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, var4, -1, var5, par3StructureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, var4, -1, 18 - var5, par3StructureBoundingBox);
            }
        }
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 4, 1, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 4, 0, 4, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 14, 0, 4, 14, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 17, 0, 4, 17, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 4, 1, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 4, 4, 4, 4, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 3, 14, 4, 4, 14, Block.netherFence.blockID, Block.netherFence.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 17, 4, 4, 17, Block.netherFence.blockID, Block.netherFence.blockID, false);
        return true;
    }
}
