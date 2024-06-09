package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class BlockRedstoneDiode extends BlockDirectional
{
    protected final boolean ¥à;
    private static final String Õ = "CL_00000226";
    
    protected BlockRedstoneDiode(final boolean p_i45400_1_) {
        super(Material.µà);
        this.¥à = p_i45400_1_;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && super.Ø­áŒŠá(worldIn, pos);
    }
    
    public boolean áŒŠÆ(final World worldIn, final BlockPos p_176409_2_) {
        return World.HorizonCode_Horizon_È(worldIn, p_176409_2_.Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!this.Â((IBlockAccess)worldIn, pos, state)) {
            final boolean var5 = this.Âµá€(worldIn, pos, state);
            if (this.¥à && !var5) {
                worldIn.HorizonCode_Horizon_È(pos, this.ÂµÈ(state), 2);
            }
            else if (!this.¥à) {
                worldIn.HorizonCode_Horizon_È(pos, this.áˆºÑ¢Õ(state), 2);
                if (!var5) {
                    worldIn.HorizonCode_Horizon_È(pos, this.áˆºÑ¢Õ(state).Ý(), this.ˆÏ­(state), -1);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return side.á() != EnumFacing.HorizonCode_Horizon_È.Â;
    }
    
    protected boolean á(final IBlockState p_176406_1_) {
        return this.¥à;
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return this.HorizonCode_Horizon_È(worldIn, pos, state, side);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return this.á(state) ? ((state.HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà) == side) ? this.HorizonCode_Horizon_È(worldIn, pos, state) : 0) : 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (this.áŒŠÆ(worldIn, pos)) {
            this.à(worldIn, pos, state);
        }
        else {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
            for (final EnumFacing var8 : EnumFacing.values()) {
                worldIn.Â(pos.HorizonCode_Horizon_È(var8), this);
            }
        }
    }
    
    protected void à(final World worldIn, final BlockPos p_176398_2_, final IBlockState p_176398_3_) {
        if (!this.Â((IBlockAccess)worldIn, p_176398_2_, p_176398_3_)) {
            final boolean var4 = this.Âµá€(worldIn, p_176398_2_, p_176398_3_);
            if (((this.¥à && !var4) || (!this.¥à && var4)) && !worldIn.Ø­áŒŠá(p_176398_2_, this)) {
                byte var5 = -1;
                if (this.áŒŠÆ(worldIn, p_176398_2_, p_176398_3_)) {
                    var5 = -3;
                }
                else if (this.¥à) {
                    var5 = -2;
                }
                worldIn.HorizonCode_Horizon_È(p_176398_2_, this, this.áŒŠÆ(p_176398_3_), var5);
            }
        }
    }
    
    public boolean Â(final IBlockAccess p_176405_1_, final BlockPos p_176405_2_, final IBlockState p_176405_3_) {
        return false;
    }
    
    protected boolean Âµá€(final World worldIn, final BlockPos p_176404_2_, final IBlockState p_176404_3_) {
        return this.Ó(worldIn, p_176404_2_, p_176404_3_) > 0;
    }
    
    protected int Ó(final World worldIn, final BlockPos p_176397_2_, final IBlockState p_176397_3_) {
        final EnumFacing var4 = (EnumFacing)p_176397_3_.HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà);
        final BlockPos var5 = p_176397_2_.HorizonCode_Horizon_È(var4);
        final int var6 = worldIn.Ý(var5, var4);
        if (var6 >= 15) {
            return var6;
        }
        final IBlockState var7 = worldIn.Â(var5);
        return Math.max(var6, (var7.Ý() == Blocks.Œ) ? ((int)var7.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà)) : 0);
    }
    
    protected int Ý(final IBlockAccess p_176407_1_, final BlockPos p_176407_2_, final IBlockState p_176407_3_) {
        final EnumFacing var4 = (EnumFacing)p_176407_3_.HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà);
        final EnumFacing var5 = var4.Ó();
        final EnumFacing var6 = var4.à();
        return Math.max(this.Ý(p_176407_1_, p_176407_2_.HorizonCode_Horizon_È(var5), var5), this.Ý(p_176407_1_, p_176407_2_.HorizonCode_Horizon_È(var6), var6));
    }
    
    protected int Ý(final IBlockAccess p_176401_1_, final BlockPos p_176401_2_, final EnumFacing p_176401_3_) {
        final IBlockState var4 = p_176401_1_.Â(p_176401_2_);
        final Block var5 = var4.Ý();
        return (int)(this.Ý(var5) ? ((var5 == Blocks.Œ) ? var4.HorizonCode_Horizon_È(BlockRedstoneWire.Âµà) : p_176401_1_.HorizonCode_Horizon_È(p_176401_2_, p_176401_3_)) : 0);
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà, placer.ˆà¢().Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        if (this.Âµá€(worldIn, pos, state)) {
            worldIn.HorizonCode_Horizon_È(pos, this, 1);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ø(worldIn, pos, state);
    }
    
    protected void Ø(final World worldIn, final BlockPos p_176400_2_, final IBlockState p_176400_3_) {
        final EnumFacing var4 = (EnumFacing)p_176400_3_.HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà);
        final BlockPos var5 = p_176400_2_.HorizonCode_Horizon_È(var4.Âµá€());
        worldIn.Ý(var5, this);
        worldIn.HorizonCode_Horizon_È(var5, this, var4);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this.¥à) {
            for (final EnumFacing var7 : EnumFacing.values()) {
                worldIn.Â(pos.HorizonCode_Horizon_È(var7), this);
            }
        }
        super.Â(worldIn, pos, state);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    protected boolean Ý(final Block p_149908_1_) {
        return p_149908_1_.áŒŠà();
    }
    
    protected int HorizonCode_Horizon_È(final IBlockAccess p_176408_1_, final BlockPos p_176408_2_, final IBlockState p_176408_3_) {
        return 15;
    }
    
    public static boolean Ø­áŒŠá(final Block p_149909_0_) {
        return Blocks.áŒŠá.Âµá€(p_149909_0_) || Blocks.ÐƒÇŽà.Âµá€(p_149909_0_);
    }
    
    public boolean Âµá€(final Block p_149907_1_) {
        return p_149907_1_ == this.áˆºÑ¢Õ(this.¥à()).Ý() || p_149907_1_ == this.ÂµÈ(this.¥à()).Ý();
    }
    
    public boolean áŒŠÆ(final World worldIn, final BlockPos p_176402_2_, final IBlockState p_176402_3_) {
        final EnumFacing var4 = ((EnumFacing)p_176402_3_.HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà)).Âµá€();
        final BlockPos var5 = p_176402_2_.HorizonCode_Horizon_È(var4);
        return Ø­áŒŠá(worldIn.Â(var5).Ý()) && worldIn.Â(var5).HorizonCode_Horizon_È(BlockRedstoneDiode.ŠÂµà) != var4;
    }
    
    protected int ˆÏ­(final IBlockState p_176399_1_) {
        return this.áŒŠÆ(p_176399_1_);
    }
    
    protected abstract int áŒŠÆ(final IBlockState p0);
    
    protected abstract IBlockState áˆºÑ¢Õ(final IBlockState p0);
    
    protected abstract IBlockState ÂµÈ(final IBlockState p0);
    
    @Override
    public boolean Â(final Block other) {
        return this.Âµá€(other);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
}
