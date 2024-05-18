package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class BlockPumpkin extends BlockDirectional
{
    private BlockPattern Õ;
    private BlockPattern à¢;
    private BlockPattern ¥à;
    private BlockPattern Âµà;
    private static final String Ç = "CL_00000291";
    
    protected BlockPumpkin() {
        super(Material.Çªà¢);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPumpkin.ŠÂµà, EnumFacing.Ý));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ý(worldIn, pos, state);
        this.áˆºÑ¢Õ(worldIn, pos);
    }
    
    public boolean áŒŠÆ(final World worldIn, final BlockPos p_176390_2_) {
        return this.È().HorizonCode_Horizon_È(worldIn, p_176390_2_) != null || this.ˆáŠ().HorizonCode_Horizon_È(worldIn, p_176390_2_) != null;
    }
    
    private void áˆºÑ¢Õ(final World worldIn, final BlockPos p_180673_2_) {
        BlockPattern.Â var3;
        if ((var3 = this.áŠ().HorizonCode_Horizon_È(worldIn, p_180673_2_)) != null) {
            for (int var4 = 0; var4 < this.áŠ().HorizonCode_Horizon_È(); ++var4) {
                final BlockWorldState var5 = var3.HorizonCode_Horizon_È(0, var4, 0);
                worldIn.HorizonCode_Horizon_È(var5.Ý(), Blocks.Â.¥à(), 2);
            }
            final EntitySnowman var6 = new EntitySnowman(worldIn);
            final BlockPos var7 = var3.HorizonCode_Horizon_È(0, 2, 0).Ý();
            var6.Â(var7.HorizonCode_Horizon_È() + 0.5, var7.Â() + 0.05, var7.Ý() + 0.5, 0.0f, 0.0f);
            worldIn.HorizonCode_Horizon_È(var6);
            for (int var8 = 0; var8 < 120; ++var8) {
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÕ, var7.HorizonCode_Horizon_È() + worldIn.Å.nextDouble(), var7.Â() + worldIn.Å.nextDouble() * 2.5, var7.Ý() + worldIn.Å.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
            for (int var8 = 0; var8 < this.áŠ().HorizonCode_Horizon_È(); ++var8) {
                final BlockWorldState var9 = var3.HorizonCode_Horizon_È(0, var8, 0);
                worldIn.HorizonCode_Horizon_È(var9.Ý(), Blocks.Â);
            }
        }
        else if ((var3 = this.áŒŠ().HorizonCode_Horizon_È(worldIn, p_180673_2_)) != null) {
            for (int var4 = 0; var4 < this.áŒŠ().Â(); ++var4) {
                for (int var10 = 0; var10 < this.áŒŠ().HorizonCode_Horizon_È(); ++var10) {
                    worldIn.HorizonCode_Horizon_È(var3.HorizonCode_Horizon_È(var4, var10, 0).Ý(), Blocks.Â.¥à(), 2);
                }
            }
            final BlockPos var11 = var3.HorizonCode_Horizon_È(1, 2, 0).Ý();
            final EntityIronGolem var12 = new EntityIronGolem(worldIn);
            var12.á(true);
            var12.Â(var11.HorizonCode_Horizon_È() + 0.5, var11.Â() + 0.05, var11.Ý() + 0.5, 0.0f, 0.0f);
            worldIn.HorizonCode_Horizon_È(var12);
            for (int var8 = 0; var8 < 120; ++var8) {
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆá, var11.HorizonCode_Horizon_È() + worldIn.Å.nextDouble(), var11.Â() + worldIn.Å.nextDouble() * 3.9, var11.Ý() + worldIn.Å.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
            for (int var8 = 0; var8 < this.áŒŠ().Â(); ++var8) {
                for (int var13 = 0; var13 < this.áŒŠ().HorizonCode_Horizon_È(); ++var13) {
                    final BlockWorldState var14 = var3.HorizonCode_Horizon_È(var8, var13, 0);
                    worldIn.HorizonCode_Horizon_È(var14.Ý(), Blocks.Â);
                }
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return worldIn.Â(pos).Ý().É.áŒŠÆ() && World.HorizonCode_Horizon_È(worldIn, pos.Âµá€());
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockPumpkin.ŠÂµà, placer.ˆà¢().Âµá€());
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPumpkin.ŠÂµà, EnumFacing.Â(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumFacing)state.HorizonCode_Horizon_È(BlockPumpkin.ŠÂµà)).Ý();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPumpkin.ŠÂµà });
    }
    
    protected BlockPattern È() {
        if (this.Õ == null) {
            this.Õ = FactoryBlockPattern.HorizonCode_Horizon_È().HorizonCode_Horizon_È(" ", "#", "#").HorizonCode_Horizon_È('#', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.ˆà¢))).Â();
        }
        return this.Õ;
    }
    
    protected BlockPattern áŠ() {
        if (this.à¢ == null) {
            this.à¢ = FactoryBlockPattern.HorizonCode_Horizon_È().HorizonCode_Horizon_È("^", "#", "#").HorizonCode_Horizon_È('^', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.Ø­Æ))).HorizonCode_Horizon_È('#', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.ˆà¢))).Â();
        }
        return this.à¢;
    }
    
    protected BlockPattern ˆáŠ() {
        if (this.¥à == null) {
            this.¥à = FactoryBlockPattern.HorizonCode_Horizon_È().HorizonCode_Horizon_È("~ ~", "###", "~#~").HorizonCode_Horizon_È('#', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.áŒŠ))).HorizonCode_Horizon_È('~', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.Â))).Â();
        }
        return this.¥à;
    }
    
    protected BlockPattern áŒŠ() {
        if (this.Âµà == null) {
            this.Âµà = FactoryBlockPattern.HorizonCode_Horizon_È().HorizonCode_Horizon_È("~^~", "###", "~#~").HorizonCode_Horizon_È('^', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.Ø­Æ))).HorizonCode_Horizon_È('#', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.áŒŠ))).HorizonCode_Horizon_È('~', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.Â))).Â();
        }
        return this.Âµà;
    }
}
