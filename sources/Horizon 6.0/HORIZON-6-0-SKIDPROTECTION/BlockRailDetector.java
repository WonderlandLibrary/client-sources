package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;
import com.google.common.base.Predicate;

public class BlockRailDetector extends BlockRailBase
{
    public static final PropertyEnum Õ;
    public static final PropertyBool ŠÂµà;
    private static final String ¥à = "CL_00000225";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("shape", HorizonCode_Horizon_È.class, (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002126";
            
            public boolean HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_180344_1_) {
                return p_180344_1_ != BlockRailBase.HorizonCode_Horizon_È.áˆºÑ¢Õ && p_180344_1_ != BlockRailBase.HorizonCode_Horizon_È.áŒŠÆ && p_180344_1_ != BlockRailBase.HorizonCode_Horizon_È.à && p_180344_1_ != BlockRailBase.HorizonCode_Horizon_È.Ø;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((HorizonCode_Horizon_È)p_apply_1_);
            }
        });
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("powered");
    }
    
    public BlockRailDetector() {
        super(true);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà, false).HorizonCode_Horizon_È(BlockRailDetector.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 20;
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (!worldIn.ŠÄ && !(boolean)state.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà)) {
            this.Âµá€(worldIn, pos, state);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ && (boolean)state.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà)) {
            this.Âµá€(worldIn, pos, state);
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà) ? 15 : 0;
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà) ? ((side == EnumFacing.Â) ? 15 : 0) : 0;
    }
    
    private void Âµá€(final World worldIn, final BlockPos p_176570_2_, final IBlockState p_176570_3_) {
        final boolean var4 = (boolean)p_176570_3_.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà);
        boolean var5 = false;
        final List var6 = this.HorizonCode_Horizon_È(worldIn, p_176570_2_, EntityMinecart.class, new Predicate[0]);
        if (!var6.isEmpty()) {
            var5 = true;
        }
        if (var5 && !var4) {
            worldIn.HorizonCode_Horizon_È(p_176570_2_, p_176570_3_.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà, true), 3);
            worldIn.Â(p_176570_2_, this);
            worldIn.Â(p_176570_2_.Âµá€(), this);
            worldIn.Â(p_176570_2_, p_176570_2_);
        }
        if (!var5 && var4) {
            worldIn.HorizonCode_Horizon_È(p_176570_2_, p_176570_3_.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà, false), 3);
            worldIn.Â(p_176570_2_, this);
            worldIn.Â(p_176570_2_.Âµá€(), this);
            worldIn.Â(p_176570_2_, p_176570_2_);
        }
        if (var5) {
            worldIn.HorizonCode_Horizon_È(p_176570_2_, this, this.HorizonCode_Horizon_È(worldIn));
        }
        worldIn.Âµá€(p_176570_2_, this);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ý(worldIn, pos, state);
        this.Âµá€(worldIn, pos, state);
    }
    
    @Override
    public IProperty È() {
        return BlockRailDetector.Õ;
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        if (worldIn.Â(pos).HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà)) {
            final List var3 = this.HorizonCode_Horizon_È(worldIn, pos, EntityMinecartCommandBlock.class, new Predicate[0]);
            if (!var3.isEmpty()) {
                return var3.get(0).áŒŠÆ().à();
            }
            final List var4 = this.HorizonCode_Horizon_È(worldIn, pos, EntityMinecart.class, IEntitySelector.Ý);
            if (!var4.isEmpty()) {
                return Container.Â(var4.get(0));
            }
        }
        return 0;
    }
    
    protected List HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176571_2_, final Class p_176571_3_, final Predicate... p_176571_4_) {
        final AxisAlignedBB var5 = this.HorizonCode_Horizon_È(p_176571_2_);
        return (p_176571_4_.length != 1) ? worldIn.HorizonCode_Horizon_È(p_176571_3_, var5) : worldIn.HorizonCode_Horizon_È(p_176571_3_, var5, p_176571_4_[0]);
    }
    
    private AxisAlignedBB HorizonCode_Horizon_È(final BlockPos p_176572_1_) {
        final float var2 = 0.2f;
        return new AxisAlignedBB(p_176572_1_.HorizonCode_Horizon_È() + 0.2f, p_176572_1_.Â(), p_176572_1_.Ý() + 0.2f, p_176572_1_.HorizonCode_Horizon_È() + 1 - 0.2f, p_176572_1_.Â() + 1 - 0.2f, p_176572_1_.Ý() + 1 - 0.2f);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockRailDetector.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7)).HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockRailDetector.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockRailDetector.ŠÂµà)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockRailDetector.Õ, BlockRailDetector.ŠÂµà });
    }
}
