package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class BlockBasePressurePlate extends Block
{
    private static final String Õ = "CL_00000194";
    
    protected BlockBasePressurePlate(final Material materialIn) {
        super(materialIn);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.áŒŠÆ(access.Â(pos));
    }
    
    protected void áŒŠÆ(final IBlockState p_180668_1_) {
        final boolean var2 = this.áˆºÑ¢Õ(p_180668_1_) > 0;
        final float var3 = 0.0625f;
        if (var2) {
            this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.03125f, 0.9375f);
        }
        else {
            this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.0625f, 0.9375f);
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 20;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
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
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return true;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return this.ÂµÈ(worldIn, pos.Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.ÂµÈ(worldIn, pos.Âµá€())) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
    }
    
    private boolean ÂµÈ(final World worldIn, final BlockPos pos) {
        return World.HorizonCode_Horizon_È(worldIn, pos) || worldIn.Â(pos).Ý() instanceof BlockFence;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ) {
            final int var5 = this.áˆºÑ¢Õ(state);
            if (var5 > 0) {
                this.Â(worldIn, pos, state, var5);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (!worldIn.ŠÄ) {
            final int var5 = this.áˆºÑ¢Õ(state);
            if (var5 == 0) {
                this.Â(worldIn, pos, state, var5);
            }
        }
    }
    
    protected void Â(final World worldIn, final BlockPos pos, IBlockState state, final int oldRedstoneStrength) {
        final int var5 = this.áˆºÑ¢Õ(worldIn, pos);
        final boolean var6 = oldRedstoneStrength > 0;
        final boolean var7 = var5 > 0;
        if (oldRedstoneStrength != var5) {
            state = this.HorizonCode_Horizon_È(state, var5);
            worldIn.HorizonCode_Horizon_È(pos, state, 2);
            this.áŒŠÆ(worldIn, pos);
            worldIn.Â(pos, pos);
        }
        if (!var7 && var6) {
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.1, pos.Ý() + 0.5, "random.click", 0.3f, 0.5f);
        }
        else if (var7 && !var6) {
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.1, pos.Ý() + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (var7) {
            worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
        }
    }
    
    protected AxisAlignedBB HorizonCode_Horizon_È(final BlockPos pos) {
        final float var2 = 0.125f;
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + 0.125f, pos.Â(), pos.Ý() + 0.125f, pos.HorizonCode_Horizon_È() + 1 - 0.125f, pos.Â() + 0.25, pos.Ý() + 1 - 0.125f);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this.áˆºÑ¢Õ(state) > 0) {
            this.áŒŠÆ(worldIn, pos);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    protected void áŒŠÆ(final World worldIn, final BlockPos pos) {
        worldIn.Â(pos, this);
        worldIn.Â(pos.Âµá€(), this);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return this.áˆºÑ¢Õ(state);
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return (side == EnumFacing.Â) ? this.áˆºÑ¢Õ(state) : 0;
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public void ŠÄ() {
        final float var1 = 0.5f;
        final float var2 = 0.125f;
        final float var3 = 0.5f;
        this.HorizonCode_Horizon_È(0.0f, 0.375f, 0.0f, 1.0f, 0.625f, 1.0f);
    }
    
    @Override
    public int ˆá() {
        return 1;
    }
    
    protected abstract int áˆºÑ¢Õ(final World p0, final BlockPos p1);
    
    protected abstract int áˆºÑ¢Õ(final IBlockState p0);
    
    protected abstract IBlockState HorizonCode_Horizon_È(final IBlockState p0, final int p1);
}
