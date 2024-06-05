package net.minecraft.src;

import java.util.*;

public class BlockRedstoneRepeater extends BlockRedstoneLogic
{
    public static final double[] repeaterTorchOffset;
    private static final int[] repeaterState;
    
    static {
        repeaterTorchOffset = new double[] { -0.0625, 0.0625, 0.1875, 0.3125 };
        repeaterState = new int[] { 1, 2, 3, 4 };
    }
    
    protected BlockRedstoneRepeater(final int par1, final boolean par2) {
        super(par1, par2);
    }
    
    @Override
    public boolean onBlockActivated(final World par1World, final int par2, final int par3, final int par4, final EntityPlayer par5EntityPlayer, final int par6, final float par7, final float par8, final float par9) {
        final int var10 = par1World.getBlockMetadata(par2, par3, par4);
        int var11 = (var10 & 0xC) >> 2;
        var11 = (var11 + 1 << 2 & 0xC);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 | (var10 & 0x3), 3);
        return true;
    }
    
    @Override
    protected int func_94481_j_(final int par1) {
        return BlockRedstoneRepeater.repeaterState[(par1 & 0xC) >> 2] * 2;
    }
    
    @Override
    protected BlockRedstoneLogic func_94485_e() {
        return Block.redstoneRepeaterActive;
    }
    
    @Override
    protected BlockRedstoneLogic func_94484_i() {
        return Block.redstoneRepeaterIdle;
    }
    
    @Override
    public int idDropped(final int par1, final Random par2Random, final int par3) {
        return Item.redstoneRepeater.itemID;
    }
    
    @Override
    public int idPicked(final World par1World, final int par2, final int par3, final int par4) {
        return Item.redstoneRepeater.itemID;
    }
    
    @Override
    public int getRenderType() {
        return 15;
    }
    
    @Override
    public boolean func_94476_e(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        return this.func_94482_f(par1IBlockAccess, par2, par3, par4, par5) > 0;
    }
    
    @Override
    protected boolean func_94477_d(final int par1) {
        return BlockRedstoneLogic.isRedstoneRepeaterBlockID(par1);
    }
    
    @Override
    public void randomDisplayTick(final World par1World, final int par2, final int par3, final int par4, final Random par5Random) {
        if (this.isRepeaterPowered) {
            final int var6 = par1World.getBlockMetadata(par2, par3, par4);
            final int var7 = BlockDirectional.getDirection(var6);
            final double var8 = par2 + 0.5f + (par5Random.nextFloat() - 0.5f) * 0.2;
            final double var9 = par3 + 0.4f + (par5Random.nextFloat() - 0.5f) * 0.2;
            final double var10 = par4 + 0.5f + (par5Random.nextFloat() - 0.5f) * 0.2;
            double var11 = 0.0;
            double var12 = 0.0;
            if (par5Random.nextInt(2) == 0) {
                switch (var7) {
                    case 0: {
                        var12 = -0.3125;
                        break;
                    }
                    case 1: {
                        var11 = 0.3125;
                        break;
                    }
                    case 2: {
                        var12 = 0.3125;
                        break;
                    }
                    case 3: {
                        var11 = -0.3125;
                        break;
                    }
                }
            }
            else {
                final int var13 = (var6 & 0xC) >> 2;
                switch (var7) {
                    case 0: {
                        var12 = BlockRedstoneRepeater.repeaterTorchOffset[var13];
                        break;
                    }
                    case 1: {
                        var11 = -BlockRedstoneRepeater.repeaterTorchOffset[var13];
                        break;
                    }
                    case 2: {
                        var12 = -BlockRedstoneRepeater.repeaterTorchOffset[var13];
                        break;
                    }
                    case 3: {
                        var11 = BlockRedstoneRepeater.repeaterTorchOffset[var13];
                        break;
                    }
                }
            }
            par1World.spawnParticle("reddust", var8 + var11, var9, var10 + var12, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        this.func_94483_i_(par1World, par2, par3, par4);
    }
}
