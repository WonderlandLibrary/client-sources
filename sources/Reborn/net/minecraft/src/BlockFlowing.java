package net.minecraft.src;

import java.util.*;

public class BlockFlowing extends BlockFluid
{
    int numAdjacentSources;
    boolean[] isOptimalFlowDirection;
    int[] flowCost;
    
    protected BlockFlowing(final int par1, final Material par2Material) {
        super(par1, par2Material);
        this.numAdjacentSources = 0;
        this.isOptimalFlowDirection = new boolean[4];
        this.flowCost = new int[4];
    }
    
    private void updateFlow(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlock(par2, par3, par4, this.blockID + 1, var5, 2);
    }
    
    @Override
    public boolean getBlocksMovement(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
        return this.blockMaterial != Material.lava;
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        int var6 = this.getFlowDecay(par1World, par2, par3, par4);
        byte var7 = 1;
        if (this.blockMaterial == Material.lava && !par1World.provider.isHellWorld) {
            var7 = 2;
        }
        boolean var8 = true;
        if (var6 > 0) {
            final byte var9 = -100;
            this.numAdjacentSources = 0;
            int var10 = this.getSmallestFlowDecay(par1World, par2 - 1, par3, par4, var9);
            var10 = this.getSmallestFlowDecay(par1World, par2 + 1, par3, par4, var10);
            var10 = this.getSmallestFlowDecay(par1World, par2, par3, par4 - 1, var10);
            var10 = this.getSmallestFlowDecay(par1World, par2, par3, par4 + 1, var10);
            int var11 = var10 + var7;
            if (var11 >= 8 || var10 < 0) {
                var11 = -1;
            }
            if (this.getFlowDecay(par1World, par2, par3 + 1, par4) >= 0) {
                final int var12 = this.getFlowDecay(par1World, par2, par3 + 1, par4);
                if (var12 >= 8) {
                    var11 = var12;
                }
                else {
                    var11 = var12 + 8;
                }
            }
            if (this.numAdjacentSources >= 2 && this.blockMaterial == Material.water) {
                if (par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid()) {
                    var11 = 0;
                }
                else if (par1World.getBlockMaterial(par2, par3 - 1, par4) == this.blockMaterial && par1World.getBlockMetadata(par2, par3 - 1, par4) == 0) {
                    var11 = 0;
                }
            }
            if (this.blockMaterial == Material.lava && var6 < 8 && var11 < 8 && var11 > var6 && par5Random.nextInt(4) != 0) {
                var11 = var6;
                var8 = false;
            }
            if (var11 == var6) {
                if (var8) {
                    this.updateFlow(par1World, par2, par3, par4);
                }
            }
            else if ((var6 = var11) < 0) {
                par1World.setBlockToAir(par2, par3, par4);
            }
            else {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 2);
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
                par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
            }
        }
        else {
            this.updateFlow(par1World, par2, par3, par4);
        }
        if (this.liquidCanDisplaceBlock(par1World, par2, par3 - 1, par4)) {
            if (this.blockMaterial == Material.lava && par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water) {
                par1World.setBlock(par2, par3 - 1, par4, Block.stone.blockID);
                this.triggerLavaMixEffects(par1World, par2, par3 - 1, par4);
                return;
            }
            if (var6 >= 8) {
                this.flowIntoBlock(par1World, par2, par3 - 1, par4, var6);
            }
            else {
                this.flowIntoBlock(par1World, par2, par3 - 1, par4, var6 + 8);
            }
        }
        else if (var6 >= 0 && (var6 == 0 || this.blockBlocksFlow(par1World, par2, par3 - 1, par4))) {
            final boolean[] var13 = this.getOptimalFlowDirections(par1World, par2, par3, par4);
            int var11 = var6 + var7;
            if (var6 >= 8) {
                var11 = 1;
            }
            if (var11 >= 8) {
                return;
            }
            if (var13[0]) {
                this.flowIntoBlock(par1World, par2 - 1, par3, par4, var11);
            }
            if (var13[1]) {
                this.flowIntoBlock(par1World, par2 + 1, par3, par4, var11);
            }
            if (var13[2]) {
                this.flowIntoBlock(par1World, par2, par3, par4 - 1, var11);
            }
            if (var13[3]) {
                this.flowIntoBlock(par1World, par2, par3, par4 + 1, var11);
            }
        }
    }
    
    private void flowIntoBlock(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (this.liquidCanDisplaceBlock(par1World, par2, par3, par4)) {
            final int var6 = par1World.getBlockId(par2, par3, par4);
            if (var6 > 0) {
                if (this.blockMaterial == Material.lava) {
                    this.triggerLavaMixEffects(par1World, par2, par3, par4);
                }
                else {
                    Block.blocksList[var6].dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
                }
            }
            par1World.setBlock(par2, par3, par4, this.blockID, par5, 3);
        }
    }
    
    private int calculateFlowCost(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        int var7 = 1000;
        for (int var8 = 0; var8 < 4; ++var8) {
            if ((var8 != 0 || par6 != 1) && (var8 != 1 || par6 != 0) && (var8 != 2 || par6 != 3) && (var8 != 3 || par6 != 2)) {
                int var9 = par2;
                int var10 = par4;
                if (var8 == 0) {
                    var9 = par2 - 1;
                }
                if (var8 == 1) {
                    ++var9;
                }
                if (var8 == 2) {
                    var10 = par4 - 1;
                }
                if (var8 == 3) {
                    ++var10;
                }
                if (!this.blockBlocksFlow(par1World, var9, par3, var10) && (par1World.getBlockMaterial(var9, par3, var10) != this.blockMaterial || par1World.getBlockMetadata(var9, par3, var10) != 0)) {
                    if (!this.blockBlocksFlow(par1World, var9, par3 - 1, var10)) {
                        return par5;
                    }
                    if (par5 < 4) {
                        final int var11 = this.calculateFlowCost(par1World, var9, par3, var10, par5 + 1, var8);
                        if (var11 < var7) {
                            var7 = var11;
                        }
                    }
                }
            }
        }
        return var7;
    }
    
    private boolean[] getOptimalFlowDirections(final World par1World, final int par2, final int par3, final int par4) {
        for (int var5 = 0; var5 < 4; ++var5) {
            this.flowCost[var5] = 1000;
            int var6 = par2;
            int var7 = par4;
            if (var5 == 0) {
                var6 = par2 - 1;
            }
            if (var5 == 1) {
                ++var6;
            }
            if (var5 == 2) {
                var7 = par4 - 1;
            }
            if (var5 == 3) {
                ++var7;
            }
            if (!this.blockBlocksFlow(par1World, var6, par3, var7) && (par1World.getBlockMaterial(var6, par3, var7) != this.blockMaterial || par1World.getBlockMetadata(var6, par3, var7) != 0)) {
                if (this.blockBlocksFlow(par1World, var6, par3 - 1, var7)) {
                    this.flowCost[var5] = this.calculateFlowCost(par1World, var6, par3, var7, 1, var5);
                }
                else {
                    this.flowCost[var5] = 0;
                }
            }
        }
        int var5 = this.flowCost[0];
        for (int var6 = 1; var6 < 4; ++var6) {
            if (this.flowCost[var6] < var5) {
                var5 = this.flowCost[var6];
            }
        }
        for (int var6 = 0; var6 < 4; ++var6) {
            this.isOptimalFlowDirection[var6] = (this.flowCost[var6] == var5);
        }
        return this.isOptimalFlowDirection;
    }
    
    private boolean blockBlocksFlow(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockId(par2, par3, par4);
        if (var5 == Block.doorWood.blockID || var5 == Block.doorIron.blockID || var5 == Block.signPost.blockID || var5 == Block.ladder.blockID || var5 == Block.reed.blockID) {
            return true;
        }
        if (var5 == 0) {
            return false;
        }
        final Material var6 = Block.blocksList[var5].blockMaterial;
        return var6 == Material.portal || var6.blocksMovement();
    }
    
    protected int getSmallestFlowDecay(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        int var6 = this.getFlowDecay(par1World, par2, par3, par4);
        if (var6 < 0) {
            return par5;
        }
        if (var6 == 0) {
            ++this.numAdjacentSources;
        }
        if (var6 >= 8) {
            var6 = 0;
        }
        return (par5 >= 0 && var6 >= par5) ? par5 : var6;
    }
    
    private boolean liquidCanDisplaceBlock(final World par1World, final int par2, final int par3, final int par4) {
        final Material var5 = par1World.getBlockMaterial(par2, par3, par4);
        return var5 != this.blockMaterial && var5 != Material.lava && !this.blockBlocksFlow(par1World, par2, par3, par4);
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        if (par1World.getBlockId(par2, par3, par4) == this.blockID) {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }
    
    @Override
    public boolean func_82506_l() {
        return false;
    }
}
