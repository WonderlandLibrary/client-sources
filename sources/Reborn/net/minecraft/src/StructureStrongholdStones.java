package net.minecraft.src;

import java.util.*;

class StructureStrongholdStones extends StructurePieceBlockSelector
{
    private StructureStrongholdStones() {
    }
    
    @Override
    public void selectBlocks(final Random par1Random, final int par2, final int par3, final int par4, final boolean par5) {
        if (par5) {
            this.selectedBlockId = Block.stoneBrick.blockID;
            final float var6 = par1Random.nextFloat();
            if (var6 < 0.2f) {
                this.selectedBlockMetaData = 2;
            }
            else if (var6 < 0.5f) {
                this.selectedBlockMetaData = 1;
            }
            else if (var6 < 0.55f) {
                this.selectedBlockId = Block.silverfish.blockID;
                this.selectedBlockMetaData = 2;
            }
            else {
                this.selectedBlockMetaData = 0;
            }
        }
        else {
            this.selectedBlockId = 0;
            this.selectedBlockMetaData = 0;
        }
    }
    
    StructureStrongholdStones(final StructureStrongholdPieceWeight2 par1StructureStrongholdPieceWeight2) {
        this();
    }
}
