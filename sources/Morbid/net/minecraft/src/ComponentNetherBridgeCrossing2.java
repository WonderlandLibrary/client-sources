package net.minecraft.src;

import java.util.*;

public class ComponentNetherBridgeCrossing2 extends ComponentNetherBridgePiece
{
    public ComponentNetherBridgeCrossing2(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.boundingBox = par3StructureBoundingBox;
    }
    
    @Override
    public void buildComponent(final StructureComponent par1StructureComponent, final List par2List, final Random par3Random) {
        this.getNextComponentNormal((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 1, 0, true);
        this.getNextComponentX((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 0, 1, true);
        this.getNextComponentZ((ComponentNetherBridgeStartPiece)par1StructureComponent, par2List, par3Random, 0, 1, true);
    }
    
    public static ComponentNetherBridgeCrossing2 createValidComponent(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, 0, 0, 5, 7, 5, par5);
        return (ComponentNetherBridgePiece.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentNetherBridgeCrossing2(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 1, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 4, 5, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 0, 0, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 2, 0, 4, 5, 0, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, 4, 0, 5, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 2, 4, 4, 5, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 6, 0, 4, 6, 4, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        for (int var4 = 0; var4 <= 4; ++var4) {
            for (int var5 = 0; var5 <= 4; ++var5) {
                this.fillCurrentPositionBlocksDownwards(par1World, Block.netherBrick.blockID, 0, var4, -1, var5, par3StructureBoundingBox);
            }
        }
        return true;
    }
}
