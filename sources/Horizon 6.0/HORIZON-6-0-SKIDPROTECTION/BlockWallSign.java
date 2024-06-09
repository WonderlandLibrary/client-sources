package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class BlockWallSign extends BlockSign
{
    public static final PropertyDirection Õ;
    private static final String à¢ = "CL_00002047";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
    }
    
    public BlockWallSign() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockWallSign.Õ, EnumFacing.Ý));
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final EnumFacing var3 = (EnumFacing)access.Â(pos).HorizonCode_Horizon_È(BlockWallSign.Õ);
        final float var4 = 0.28125f;
        final float var5 = 0.78125f;
        final float var6 = 0.0f;
        final float var7 = 1.0f;
        final float var8 = 0.125f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[var3.ordinal()]) {
            case 1: {
                this.HorizonCode_Horizon_È(var6, var4, 1.0f - var8, var7, var5, 1.0f);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(var6, var4, 0.0f, var7, var5, var8);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(1.0f - var8, var4, var6, 1.0f, var5, var7);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(0.0f, var4, var6, var8, var5, var7);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockWallSign.Õ);
        if (!worldIn.Â(pos.HorizonCode_Horizon_È(var5.Âµá€())).Ý().Ó().Â()) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(meta);
        if (var2.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var2 = EnumFacing.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockWallSign.Õ, var2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumFacing)state.HorizonCode_Horizon_È(BlockWallSign.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockWallSign.Õ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002046";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockWallSign.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockWallSign.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockWallSign.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockWallSign.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
