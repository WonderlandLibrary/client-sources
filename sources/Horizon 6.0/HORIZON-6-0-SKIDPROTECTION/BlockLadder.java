package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.base.Predicate;

public class BlockLadder extends Block
{
    public static final PropertyDirection Õ;
    private static final String à¢ = "CL_00000262";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
    }
    
    protected BlockLadder() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockLadder.Õ, EnumFacing.Ý));
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, state);
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.Ý(worldIn, pos);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final IBlockState var3 = access.Â(pos);
        if (var3.Ý() == this) {
            final float var4 = 0.125f;
            switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((EnumFacing)var3.HorizonCode_Horizon_È(BlockLadder.Õ)).ordinal()]) {
                case 1: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 1.0f - var4, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 2: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, var4);
                    break;
                }
                case 3: {
                    this.HorizonCode_Horizon_È(1.0f - var4, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                default: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, var4, 1.0f, 1.0f);
                    break;
                }
            }
        }
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
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return worldIn.Â(pos.Ø()).Ý().Ø() || worldIn.Â(pos.áŒŠÆ()).Ý().Ø() || worldIn.Â(pos.Ó()).Ý().Ø() || worldIn.Â(pos.à()).Ý().Ø();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        if (facing.á().Ø­áŒŠá() && this.Â(worldIn, pos, facing)) {
            return this.¥à().HorizonCode_Horizon_È(BlockLadder.Õ, facing);
        }
        for (final EnumFacing var10 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (this.Â(worldIn, pos, var10)) {
                return this.¥à().HorizonCode_Horizon_È(BlockLadder.Õ, var10);
            }
        }
        return this.¥à();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockLadder.Õ);
        if (!this.Â(worldIn, pos, var5)) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
    }
    
    protected boolean Â(final World worldIn, final BlockPos p_176381_2_, final EnumFacing p_176381_3_) {
        return worldIn.Â(p_176381_2_.HorizonCode_Horizon_È(p_176381_3_.Âµá€())).Ý().Ø();
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(meta);
        if (var2.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var2 = EnumFacing.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockLadder.Õ, var2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumFacing)state.HorizonCode_Horizon_È(BlockLadder.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockLadder.Õ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002104";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockLadder.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockLadder.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockLadder.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockLadder.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
