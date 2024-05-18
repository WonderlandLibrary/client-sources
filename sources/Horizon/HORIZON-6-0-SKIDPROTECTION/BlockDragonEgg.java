package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockDragonEgg extends Block
{
    private static final String Õ = "CL_00000232";
    
    public BlockDragonEgg() {
        super(Material.Ê);
        this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 1.0f, 0.9375f);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        this.áŒŠÆ(worldIn, pos);
    }
    
    private void áŒŠÆ(final World worldIn, final BlockPos p_180683_2_) {
        if (BlockFalling.áˆºÑ¢Õ(worldIn, p_180683_2_.Âµá€()) && p_180683_2_.Â() >= 0) {
            final byte var3 = 32;
            if (!BlockFalling.ŠÂµà && worldIn.HorizonCode_Horizon_È(p_180683_2_.Â(-var3, -var3, -var3), p_180683_2_.Â(var3, var3, var3))) {
                worldIn.HorizonCode_Horizon_È(new EntityFallingBlock(worldIn, p_180683_2_.HorizonCode_Horizon_È() + 0.5f, p_180683_2_.Â(), p_180683_2_.Ý() + 0.5f, this.¥à()));
            }
            else {
                worldIn.Ø(p_180683_2_);
                BlockPos var4;
                for (var4 = p_180683_2_; BlockFalling.áˆºÑ¢Õ(worldIn, var4) && var4.Â() > 0; var4 = var4.Âµá€()) {}
                if (var4.Â() > 0) {
                    worldIn.HorizonCode_Horizon_È(var4, this.¥à(), 2);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        this.áˆºÑ¢Õ(worldIn, pos);
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
        this.áˆºÑ¢Õ(worldIn, pos);
    }
    
    private void áˆºÑ¢Õ(final World worldIn, final BlockPos p_180684_2_) {
        final IBlockState var3 = worldIn.Â(p_180684_2_);
        if (var3.Ý() == this) {
            for (int var4 = 0; var4 < 1000; ++var4) {
                final BlockPos var5 = p_180684_2_.Â(worldIn.Å.nextInt(16) - worldIn.Å.nextInt(16), worldIn.Å.nextInt(8) - worldIn.Å.nextInt(8), worldIn.Å.nextInt(16) - worldIn.Å.nextInt(16));
                if (worldIn.Â(var5).Ý().É == Material.HorizonCode_Horizon_È) {
                    if (worldIn.ŠÄ) {
                        for (int var6 = 0; var6 < 128; ++var6) {
                            final double var7 = worldIn.Å.nextDouble();
                            final float var8 = (worldIn.Å.nextFloat() - 0.5f) * 0.2f;
                            final float var9 = (worldIn.Å.nextFloat() - 0.5f) * 0.2f;
                            final float var10 = (worldIn.Å.nextFloat() - 0.5f) * 0.2f;
                            final double var11 = var5.HorizonCode_Horizon_È() + (p_180684_2_.HorizonCode_Horizon_È() - var5.HorizonCode_Horizon_È()) * var7 + (worldIn.Å.nextDouble() - 0.5) * 1.0 + 0.5;
                            final double var12 = var5.Â() + (p_180684_2_.Â() - var5.Â()) * var7 + worldIn.Å.nextDouble() * 1.0 - 0.5;
                            final double var13 = var5.Ý() + (p_180684_2_.Ý() - var5.Ý()) * var7 + (worldIn.Å.nextDouble() - 0.5) * 1.0 + 0.5;
                            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, var11, var12, var13, var8, var9, var10, new int[0]);
                        }
                    }
                    else {
                        worldIn.HorizonCode_Horizon_È(var5, var3, 2);
                        worldIn.Ø(p_180684_2_);
                    }
                    return;
                }
            }
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 5;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return null;
    }
}
