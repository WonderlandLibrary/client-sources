package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockPane extends Block
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    private final boolean Âµà;
    private static final String Ç = "CL_00000322";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("north");
        à¢ = PropertyBool.HorizonCode_Horizon_È("east");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("south");
        ¥à = PropertyBool.HorizonCode_Horizon_È("west");
    }
    
    protected BlockPane(final Material p_i45675_1_, final boolean p_i45675_2_) {
        super(p_i45675_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPane.Õ, false).HorizonCode_Horizon_È(BlockPane.à¢, false).HorizonCode_Horizon_È(BlockPane.ŠÂµà, false).HorizonCode_Horizon_È(BlockPane.¥à, false));
        this.Âµà = p_i45675_2_;
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockPane.Õ, this.Ý(worldIn.Â(pos.Ó()).Ý())).HorizonCode_Horizon_È(BlockPane.ŠÂµà, this.Ý(worldIn.Â(pos.à()).Ý())).HorizonCode_Horizon_È(BlockPane.¥à, this.Ý(worldIn.Â(pos.Ø()).Ý())).HorizonCode_Horizon_È(BlockPane.à¢, this.Ý(worldIn.Â(pos.áŒŠÆ()).Ý()));
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return this.Âµà ? super.HorizonCode_Horizon_È(state, rand, fortune) : null;
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
        return worldIn.Â(pos).Ý() != this && super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        final boolean var7 = this.Ý(worldIn.Â(pos.Ó()).Ý());
        final boolean var8 = this.Ý(worldIn.Â(pos.à()).Ý());
        final boolean var9 = this.Ý(worldIn.Â(pos.Ø()).Ý());
        final boolean var10 = this.Ý(worldIn.Â(pos.áŒŠÆ()).Ý());
        if ((!var9 || !var10) && (var9 || var10 || var7 || var8)) {
            if (var9) {
                this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.4375f, 0.5f, 1.0f, 0.5625f);
                super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
            }
            else if (var10) {
                this.HorizonCode_Horizon_È(0.5f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
                super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
            }
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
        if ((!var7 || !var8) && (var9 || var10 || var7 || var8)) {
            if (var7) {
                this.HorizonCode_Horizon_È(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 0.5f);
                super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
            }
            else if (var8) {
                this.HorizonCode_Horizon_È(0.4375f, 0.0f, 0.5f, 0.5625f, 1.0f, 1.0f);
                super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
            }
        }
        else {
            this.HorizonCode_Horizon_È(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 1.0f);
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
    }
    
    @Override
    public void ŠÄ() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        float var3 = 0.4375f;
        float var4 = 0.5625f;
        float var5 = 0.4375f;
        float var6 = 0.5625f;
        final boolean var7 = this.Ý(access.Â(pos.Ó()).Ý());
        final boolean var8 = this.Ý(access.Â(pos.à()).Ý());
        final boolean var9 = this.Ý(access.Â(pos.Ø()).Ý());
        final boolean var10 = this.Ý(access.Â(pos.áŒŠÆ()).Ý());
        if ((!var9 || !var10) && (var9 || var10 || var7 || var8)) {
            if (var9) {
                var3 = 0.0f;
            }
            else if (var10) {
                var4 = 1.0f;
            }
        }
        else {
            var3 = 0.0f;
            var4 = 1.0f;
        }
        if ((!var7 || !var8) && (var9 || var10 || var7 || var8)) {
            if (var7) {
                var5 = 0.0f;
            }
            else if (var8) {
                var6 = 1.0f;
            }
        }
        else {
            var5 = 0.0f;
            var6 = 1.0f;
        }
        this.HorizonCode_Horizon_È(var3, 0.0f, var5, var4, 1.0f, var6);
    }
    
    public final boolean Ý(final Block p_150098_1_) {
        return p_150098_1_.HorizonCode_Horizon_È() || p_150098_1_ == this || p_150098_1_ == Blocks.Ï­Ðƒà || p_150098_1_ == Blocks.ÐƒáŒŠÂµÐƒÕ || p_150098_1_ == Blocks.Ø­áƒ || p_150098_1_ instanceof BlockPane;
    }
    
    @Override
    protected boolean Ñ¢á() {
        return true;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Â;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPane.Õ, BlockPane.à¢, BlockPane.¥à, BlockPane.ŠÂµà });
    }
}
