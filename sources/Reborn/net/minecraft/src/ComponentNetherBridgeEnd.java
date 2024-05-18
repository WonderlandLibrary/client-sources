package net.minecraft.src;

import java.util.*;

public class ComponentNetherBridgeEnd extends ComponentNetherBridgePiece
{
    private int fillSeed;
    
    public ComponentNetherBridgeEnd(final int par1, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox, final int par4) {
        super(par1);
        this.coordBaseMode = par4;
        this.boundingBox = par3StructureBoundingBox;
        this.fillSeed = par2Random.nextInt();
    }
    
    public static ComponentNetherBridgeEnd func_74971_a(final List par0List, final Random par1Random, final int par2, final int par3, final int par4, final int par5, final int par6) {
        final StructureBoundingBox var7 = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -3, 0, 5, 10, 8, par5);
        return (ComponentNetherBridgePiece.isAboveGround(var7) && StructureComponent.findIntersecting(par0List, var7) == null) ? new ComponentNetherBridgeEnd(par6, par1Random, var7, par5) : null;
    }
    
    @Override
    public boolean addComponentParts(final World par1World, final Random par2Random, final StructureBoundingBox par3StructureBoundingBox) {
        final Random var4 = new Random(this.fillSeed);
        for (int var5 = 0; var5 <= 4; ++var5) {
            for (int var6 = 3; var6 <= 4; ++var6) {
                final int var7 = var4.nextInt(8);
                this.fillWithBlocks(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            }
        }
        int var5 = var4.nextInt(8);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 0, 5, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        var5 = var4.nextInt(8);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 0, 4, 5, var5, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        for (var5 = 0; var5 <= 4; ++var5) {
            final int var6 = var4.nextInt(5);
            this.fillWithBlocks(par1World, par3StructureBoundingBox, var5, 2, 0, var5, 2, var6, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
        }
        for (var5 = 0; var5 <= 4; ++var5) {
            for (int var6 = 0; var6 <= 1; ++var6) {
                final int var7 = var4.nextInt(3);
                this.fillWithBlocks(par1World, par3StructureBoundingBox, var5, var6, 0, var5, var6, var7, Block.netherBrick.blockID, Block.netherBrick.blockID, false);
            }
        }
        return true;
    }
}
