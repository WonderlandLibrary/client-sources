package net.minecraft.src;

import java.util.*;

public class BlockRedstoneTorch extends BlockTorch
{
    private boolean torchActive;
    private static Map redstoneUpdateInfoCache;
    
    static {
        BlockRedstoneTorch.redstoneUpdateInfoCache = new HashMap();
    }
    
    private boolean checkForBurnout(final World par1World, final int par2, final int par3, final int par4, final boolean par5) {
        if (!BlockRedstoneTorch.redstoneUpdateInfoCache.containsKey(par1World)) {
            BlockRedstoneTorch.redstoneUpdateInfoCache.put(par1World, new ArrayList());
        }
        final List var6 = BlockRedstoneTorch.redstoneUpdateInfoCache.get(par1World);
        if (par5) {
            var6.add(new RedstoneUpdateInfo(par2, par3, par4, par1World.getTotalWorldTime()));
        }
        int var7 = 0;
        for (int var8 = 0; var8 < var6.size(); ++var8) {
            final RedstoneUpdateInfo var9 = var6.get(var8);
            if (var9.x == par2 && var9.y == par3 && var9.z == par4 && ++var7 >= 8) {
                return true;
            }
        }
        return false;
    }
    
    protected BlockRedstoneTorch(final int par1, final boolean par2) {
        super(par1);
        this.torchActive = false;
        this.torchActive = par2;
        this.setTickRandomly(true);
        this.setCreativeTab(null);
    }
    
    @Override
    public int tickRate(final World par1World) {
        return 2;
    }
    
    @Override
    public void onBlockAdded(final World par1World, final int par2, final int par3, final int par4) {
        if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
            super.onBlockAdded(par1World, par2, par3, par4);
        }
        if (this.torchActive) {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        if (this.torchActive) {
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        }
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        if (!this.torchActive) {
            return 0;
        }
        final int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        return (var6 == 5 && par5 == 1) ? 0 : ((var6 == 3 && par5 == 3) ? 0 : ((var6 == 4 && par5 == 2) ? 0 : ((var6 == 1 && par5 == 5) ? 0 : ((var6 == 2 && par5 == 4) ? 0 : 15))));
    }
    
    private boolean isIndirectlyPowered(final World par1World, final int par2, final int par3, final int par4) {
        final int var5 = par1World.getBlockMetadata(par2, par3, par4);
        return (var5 == 5 && par1World.getIndirectPowerOutput(par2, par3 - 1, par4, 0)) || (var5 == 3 && par1World.getIndirectPowerOutput(par2, par3, par4 - 1, 2)) || (var5 == 4 && par1World.getIndirectPowerOutput(par2, par3, par4 + 1, 3)) || (var5 == 1 && par1World.getIndirectPowerOutput(par2 - 1, par3, par4, 4)) || (var5 == 2 && par1World.getIndirectPowerOutput(par2 + 1, par3, par4, 5));
    }
    
    @Override
    public void updateTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        final boolean var6 = this.isIndirectlyPowered(par1World, par2, par3, par4);
        final List var7 = BlockRedstoneTorch.redstoneUpdateInfoCache.get(par1World);
        while (var7 != null && !var7.isEmpty() && par1World.getTotalWorldTime() - var7.get(0).updateTime > 60L) {
            var7.remove(0);
        }
        if (this.torchActive) {
            if (var6) {
                par1World.setBlock(par2, par3, par4, Block.torchRedstoneIdle.blockID, par1World.getBlockMetadata(par2, par3, par4), 3);
                if (this.checkForBurnout(par1World, par2, par3, par4, true)) {
                    par1World.playSoundEffect(par2 + 0.5f, par3 + 0.5f, par4 + 0.5f, "random.fizz", 0.5f, 2.6f + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8f);
                    for (int var8 = 0; var8 < 5; ++var8) {
                        final double var9 = par2 + par5Random.nextDouble() * 0.6 + 0.2;
                        final double var10 = par3 + par5Random.nextDouble() * 0.6 + 0.2;
                        final double var11 = par4 + par5Random.nextDouble() * 0.6 + 0.2;
                        par1World.spawnParticle("smoke", var9, var10, var11, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
        else if (!var6 && !this.checkForBurnout(par1World, par2, par3, par4, false)) {
            par1World.setBlock(par2, par3, par4, Block.torchRedstoneActive.blockID, par1World.getBlockMetadata(par2, par3, par4), 3);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World par1World, final int par2, final int par3, final int par4, final int par5) {
        if (!this.func_94397_d(par1World, par2, par3, par4, par5)) {
            final boolean var6 = this.isIndirectlyPowered(par1World, par2, par3, par4);
            if ((this.torchActive && var6) || (!this.torchActive && !var6)) {
                par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
            }
        }
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return (par5 == 0) ? this.isProvidingWeakPower(par1IBlockAccess, par2, par3, par4, par5) : 0;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Block.torchRedstoneActive.blockID;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.torchActive) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final double var7 = par2 + 0.5f + (par5Random.nextFloat() - 0.5f) * 0.2;
            final double var8 = par3 + 0.7f + (par5Random.nextFloat() - 0.5f) * 0.2;
            final double var9 = par4 + 0.5f + (par5Random.nextFloat() - 0.5f) * 0.2;
            final double var10 = 0.2199999988079071;
            final double var11 = 0.27000001072883606;
            if (var6 == 1) {
                par1World.spawnParticle("reddust", var7 - var11, var8 + var10, var9, 0.0, 0.0, 0.0);
            }
            else if (var6 == 2) {
                par1World.spawnParticle("reddust", var7 + var11, var8 + var10, var9, 0.0, 0.0, 0.0);
            }
            else if (var6 == 3) {
                par1World.spawnParticle("reddust", var7, var8 + var10, var9 - var11, 0.0, 0.0, 0.0);
            }
            else if (var6 == 4) {
                par1World.spawnParticle("reddust", var7, var8 + var10, var9 + var11, 0.0, 0.0, 0.0);
            }
            else {
                par1World.spawnParticle("reddust", var7, var8, var9, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Block.torchRedstoneActive.blockID;
    }
    
    @Override
    public boolean isAssociatedBlockID(final int par1) {
        return par1 == Block.torchRedstoneIdle.blockID || par1 == Block.torchRedstoneActive.blockID;
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        if (this.torchActive) {
            this.blockIcon = par1IconRegister.registerIcon("redtorch_lit");
        }
        else {
            this.blockIcon = par1IconRegister.registerIcon("redtorch");
        }
    }
}
