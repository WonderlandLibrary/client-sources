package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.util.Random;
import java.util.List;
import com.google.common.base.Predicate;

public class BlockStairs extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyEnum à¢;
    public static final PropertyEnum ŠÂµà;
    private static final int[][] ¥à;
    private final Block Âµà;
    private final IBlockState Ç;
    private boolean È;
    private int áŠ;
    private static final String ˆáŠ = "CL_00000314";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyEnum.HorizonCode_Horizon_È("half", HorizonCode_Horizon_È.class);
        ŠÂµà = PropertyEnum.HorizonCode_Horizon_È("shape", Â.class);
        ¥à = new int[][] { { 4, 5 }, { 5, 7 }, { 6, 7 }, { 4, 6 }, { 0, 1 }, { 1, 3 }, { 2, 3 }, { 0, 2 } };
    }
    
    protected BlockStairs(final IBlockState modelState) {
        super(modelState.Ý().É);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockStairs.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockStairs.à¢, HorizonCode_Horizon_È.Â).HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.HorizonCode_Horizon_È));
        this.Âµà = modelState.Ý();
        this.Ç = modelState;
        this.Ý(this.Âµà.µÕ);
        this.Â(this.Âµà.Æ / 3.0f);
        this.HorizonCode_Horizon_È(this.Âµà.ˆá);
        this.Ø­áŒŠá(255);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        if (this.È) {
            this.HorizonCode_Horizon_È(0.5f * (this.áŠ % 2), 0.5f * (this.áŠ / 4 % 2), 0.5f * (this.áŠ / 2 % 2), 0.5f + 0.5f * (this.áŠ % 2), 0.5f + 0.5f * (this.áŠ / 4 % 2), 0.5f + 0.5f * (this.áŠ / 2 % 2));
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
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
    
    public void Âµá€(final IBlockAccess worldIn, final BlockPos pos) {
        if (worldIn.Â(pos).HorizonCode_Horizon_È(BlockStairs.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            this.HorizonCode_Horizon_È(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    public static boolean Ý(final Block p_150148_0_) {
        return p_150148_0_ instanceof BlockStairs;
    }
    
    public static boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state) {
        final IBlockState var3 = worldIn.Â(pos);
        final Block var4 = var3.Ý();
        return Ý(var4) && var3.HorizonCode_Horizon_È(BlockStairs.à¢) == state.HorizonCode_Horizon_È(BlockStairs.à¢) && var3.HorizonCode_Horizon_È(BlockStairs.Õ) == state.HorizonCode_Horizon_È(BlockStairs.Õ);
    }
    
    public int Ó(final IBlockAccess p_176307_1_, final BlockPos p_176307_2_) {
        final IBlockState var3 = p_176307_1_.Â(p_176307_2_);
        final EnumFacing var4 = (EnumFacing)var3.HorizonCode_Horizon_È(BlockStairs.Õ);
        final HorizonCode_Horizon_È var5 = (HorizonCode_Horizon_È)var3.HorizonCode_Horizon_È(BlockStairs.à¢);
        final boolean var6 = var5 == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        if (var4 == EnumFacing.Ó) {
            final IBlockState var7 = p_176307_1_.Â(p_176307_2_.áŒŠÆ());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.à(), var3)) {
                    return var6 ? 1 : 2;
                }
                if (var9 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.Ó(), var3)) {
                    return var6 ? 2 : 1;
                }
            }
        }
        else if (var4 == EnumFacing.Âµá€) {
            final IBlockState var7 = p_176307_1_.Â(p_176307_2_.Ø());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.à(), var3)) {
                    return var6 ? 2 : 1;
                }
                if (var9 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.Ó(), var3)) {
                    return var6 ? 1 : 2;
                }
            }
        }
        else if (var4 == EnumFacing.Ø­áŒŠá) {
            final IBlockState var7 = p_176307_1_.Â(p_176307_2_.à());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.áŒŠÆ(), var3)) {
                    return var6 ? 2 : 1;
                }
                if (var9 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.Ø(), var3)) {
                    return var6 ? 1 : 2;
                }
            }
        }
        else if (var4 == EnumFacing.Ý) {
            final IBlockState var7 = p_176307_1_.Â(p_176307_2_.Ó());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.áŒŠÆ(), var3)) {
                    return var6 ? 1 : 2;
                }
                if (var9 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176307_1_, p_176307_2_.Ø(), var3)) {
                    return var6 ? 2 : 1;
                }
            }
        }
        return 0;
    }
    
    public int à(final IBlockAccess p_176305_1_, final BlockPos p_176305_2_) {
        final IBlockState var3 = p_176305_1_.Â(p_176305_2_);
        final EnumFacing var4 = (EnumFacing)var3.HorizonCode_Horizon_È(BlockStairs.Õ);
        final HorizonCode_Horizon_È var5 = (HorizonCode_Horizon_È)var3.HorizonCode_Horizon_È(BlockStairs.à¢);
        final boolean var6 = var5 == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        if (var4 == EnumFacing.Ó) {
            final IBlockState var7 = p_176305_1_.Â(p_176305_2_.Ø());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.Ó(), var3)) {
                    return var6 ? 1 : 2;
                }
                if (var9 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.à(), var3)) {
                    return var6 ? 2 : 1;
                }
            }
        }
        else if (var4 == EnumFacing.Âµá€) {
            final IBlockState var7 = p_176305_1_.Â(p_176305_2_.áŒŠÆ());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.Ó(), var3)) {
                    return var6 ? 2 : 1;
                }
                if (var9 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.à(), var3)) {
                    return var6 ? 1 : 2;
                }
            }
        }
        else if (var4 == EnumFacing.Ø­áŒŠá) {
            final IBlockState var7 = p_176305_1_.Â(p_176305_2_.Ó());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.Ø(), var3)) {
                    return var6 ? 2 : 1;
                }
                if (var9 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.áŒŠÆ(), var3)) {
                    return var6 ? 1 : 2;
                }
            }
        }
        else if (var4 == EnumFacing.Ý) {
            final IBlockState var7 = p_176305_1_.Â(p_176305_2_.à());
            final Block var8 = var7.Ý();
            if (Ý(var8) && var5 == var7.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var9 = (EnumFacing)var7.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var9 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.Ø(), var3)) {
                    return var6 ? 1 : 2;
                }
                if (var9 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176305_1_, p_176305_2_.áŒŠÆ(), var3)) {
                    return var6 ? 2 : 1;
                }
            }
        }
        return 0;
    }
    
    public boolean Ø(final IBlockAccess p_176306_1_, final BlockPos p_176306_2_) {
        final IBlockState var3 = p_176306_1_.Â(p_176306_2_);
        final EnumFacing var4 = (EnumFacing)var3.HorizonCode_Horizon_È(BlockStairs.Õ);
        final HorizonCode_Horizon_È var5 = (HorizonCode_Horizon_È)var3.HorizonCode_Horizon_È(BlockStairs.à¢);
        final boolean var6 = var5 == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if (var6) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 1.0f;
        float var11 = 0.0f;
        float var12 = 0.5f;
        boolean var13 = true;
        if (var4 == EnumFacing.Ó) {
            var9 = 0.5f;
            var12 = 1.0f;
            final IBlockState var14 = p_176306_1_.Â(p_176306_2_.áŒŠÆ());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.à(), var3)) {
                    var12 = 0.5f;
                    var13 = false;
                }
                else if (var16 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.Ó(), var3)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var4 == EnumFacing.Âµá€) {
            var10 = 0.5f;
            var12 = 1.0f;
            final IBlockState var14 = p_176306_1_.Â(p_176306_2_.Ø());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.à(), var3)) {
                    var12 = 0.5f;
                    var13 = false;
                }
                else if (var16 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.Ó(), var3)) {
                    var11 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var4 == EnumFacing.Ø­áŒŠá) {
            var11 = 0.5f;
            var12 = 1.0f;
            final IBlockState var14 = p_176306_1_.Â(p_176306_2_.à());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.áŒŠÆ(), var3)) {
                    var10 = 0.5f;
                    var13 = false;
                }
                else if (var16 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.Ø(), var3)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        else if (var4 == EnumFacing.Ý) {
            final IBlockState var14 = p_176306_1_.Â(p_176306_2_.Ó());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.áŒŠÆ(), var3)) {
                    var10 = 0.5f;
                    var13 = false;
                }
                else if (var16 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176306_1_, p_176306_2_.Ø(), var3)) {
                    var9 = 0.5f;
                    var13 = false;
                }
            }
        }
        this.HorizonCode_Horizon_È(var9, var7, var11, var10, var8, var12);
        return var13;
    }
    
    public boolean áŒŠÆ(final IBlockAccess p_176304_1_, final BlockPos p_176304_2_) {
        final IBlockState var3 = p_176304_1_.Â(p_176304_2_);
        final EnumFacing var4 = (EnumFacing)var3.HorizonCode_Horizon_È(BlockStairs.Õ);
        final HorizonCode_Horizon_È var5 = (HorizonCode_Horizon_È)var3.HorizonCode_Horizon_È(BlockStairs.à¢);
        final boolean var6 = var5 == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        float var7 = 0.5f;
        float var8 = 1.0f;
        if (var6) {
            var7 = 0.0f;
            var8 = 0.5f;
        }
        float var9 = 0.0f;
        float var10 = 0.5f;
        float var11 = 0.5f;
        float var12 = 1.0f;
        boolean var13 = false;
        if (var4 == EnumFacing.Ó) {
            final IBlockState var14 = p_176304_1_.Â(p_176304_2_.Ø());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.Ó(), var3)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                }
                else if (var16 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.à(), var3)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var4 == EnumFacing.Âµá€) {
            final IBlockState var14 = p_176304_1_.Â(p_176304_2_.áŒŠÆ());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                var9 = 0.5f;
                var10 = 1.0f;
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Ý && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.Ó(), var3)) {
                    var11 = 0.0f;
                    var12 = 0.5f;
                    var13 = true;
                }
                else if (var16 == EnumFacing.Ø­áŒŠá && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.à(), var3)) {
                    var11 = 0.5f;
                    var12 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var4 == EnumFacing.Ø­áŒŠá) {
            final IBlockState var14 = p_176304_1_.Â(p_176304_2_.Ó());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                var11 = 0.0f;
                var12 = 0.5f;
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.Ø(), var3)) {
                    var13 = true;
                }
                else if (var16 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.áŒŠÆ(), var3)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        else if (var4 == EnumFacing.Ý) {
            final IBlockState var14 = p_176304_1_.Â(p_176304_2_.à());
            final Block var15 = var14.Ý();
            if (Ý(var15) && var5 == var14.HorizonCode_Horizon_È(BlockStairs.à¢)) {
                final EnumFacing var16 = (EnumFacing)var14.HorizonCode_Horizon_È(BlockStairs.Õ);
                if (var16 == EnumFacing.Âµá€ && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.Ø(), var3)) {
                    var13 = true;
                }
                else if (var16 == EnumFacing.Ó && !HorizonCode_Horizon_È(p_176304_1_, p_176304_2_.áŒŠÆ(), var3)) {
                    var9 = 0.5f;
                    var10 = 1.0f;
                    var13 = true;
                }
            }
        }
        if (var13) {
            this.HorizonCode_Horizon_È(var9, var7, var11, var10, var8, var12);
        }
        return var13;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.Âµá€((IBlockAccess)worldIn, pos);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        final boolean var7 = this.Ø((IBlockAccess)worldIn, pos);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        if (var7 && this.áŒŠÆ(worldIn, pos)) {
            super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        }
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        this.Âµà.Ý(worldIn, pos, state, rand);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
        this.Âµà.HorizonCode_Horizon_È(worldIn, pos, playerIn);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµà.Â(worldIn, pos, state);
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos) {
        return this.Âµà.Â(worldIn, pos);
    }
    
    @Override
    public float HorizonCode_Horizon_È(final Entity exploder) {
        return this.Âµà.HorizonCode_Horizon_È(exploder);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return this.Âµà.µà();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return this.Âµà.HorizonCode_Horizon_È(worldIn);
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        return this.Âµà.Ý(worldIn, pos);
    }
    
    @Override
    public Vec3 HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn, final Vec3 motion) {
        return this.Âµà.HorizonCode_Horizon_È(worldIn, pos, entityIn, motion);
    }
    
    @Override
    public boolean £à() {
        return this.Âµà.£à();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockState state, final boolean p_176209_2_) {
        return this.Âµà.HorizonCode_Horizon_È(state, p_176209_2_);
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return this.Âµà.Ø­áŒŠá(worldIn, pos);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.HorizonCode_Horizon_È(worldIn, pos, this.Ç, Blocks.Â);
        this.Âµà.Ý(worldIn, pos, this.Ç);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµà.Ø­áŒŠá(worldIn, pos, this.Ç);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn) {
        this.Âµà.HorizonCode_Horizon_È(worldIn, pos, entityIn);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        this.Âµà.Â(worldIn, pos, state, rand);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        return this.Âµà.HorizonCode_Horizon_È(worldIn, pos, this.Ç, playerIn, EnumFacing.HorizonCode_Horizon_È, 0.0f, 0.0f, 0.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Explosion explosionIn) {
        this.Âµà.HorizonCode_Horizon_È(worldIn, pos, explosionIn);
    }
    
    @Override
    public MapColor Â(final IBlockState state) {
        return this.Âµà.Â(this.Ç);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState var9 = super.HorizonCode_Horizon_È(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        var9 = var9.HorizonCode_Horizon_È(BlockStairs.Õ, placer.ˆà¢()).HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.HorizonCode_Horizon_È);
        return (facing != EnumFacing.HorizonCode_Horizon_È && (facing == EnumFacing.Â || hitY <= 0.5)) ? var9.HorizonCode_Horizon_È(BlockStairs.à¢, HorizonCode_Horizon_È.Â) : var9.HorizonCode_Horizon_È(BlockStairs.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Vec3 start, final Vec3 end) {
        final MovingObjectPosition[] var5 = new MovingObjectPosition[8];
        final IBlockState var6 = worldIn.Â(pos);
        final int var7 = ((EnumFacing)var6.HorizonCode_Horizon_È(BlockStairs.Õ)).Ý();
        final boolean var8 = var6.HorizonCode_Horizon_È(BlockStairs.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        final int[] var9 = BlockStairs.¥à[var7 + (var8 ? 4 : 0)];
        this.È = true;
        for (int var10 = 0; var10 < 8; ++var10) {
            this.áŠ = var10;
            if (Arrays.binarySearch(var9, var10) < 0) {
                var5[var10] = super.HorizonCode_Horizon_È(worldIn, pos, start, end);
            }
        }
        final int[] var11 = var9;
        for (int var12 = var9.length, var13 = 0; var13 < var12; ++var13) {
            final int var14 = var11[var13];
            var5[var14] = null;
        }
        MovingObjectPosition var15 = null;
        double var16 = 0.0;
        final MovingObjectPosition[] var17 = var5;
        for (int var18 = var5.length, var19 = 0; var19 < var18; ++var19) {
            final MovingObjectPosition var20 = var17[var19];
            if (var20 != null) {
                final double var21 = var20.Ý.à(end);
                if (var21 > var16) {
                    var15 = var20;
                    var16 = var21;
                }
            }
        }
        return var15;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à().HorizonCode_Horizon_È(BlockStairs.à¢, ((meta & 0x4) > 0) ? HorizonCode_Horizon_È.HorizonCode_Horizon_È : HorizonCode_Horizon_È.Â);
        var2 = var2.HorizonCode_Horizon_È(BlockStairs.Õ, EnumFacing.HorizonCode_Horizon_È(5 - (meta & 0x3)));
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        if (state.HorizonCode_Horizon_È(BlockStairs.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var2 |= 0x4;
        }
        var2 |= 5 - ((EnumFacing)state.HorizonCode_Horizon_È(BlockStairs.Õ)).Â();
        return var2;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        if (this.Ø(worldIn, pos)) {
            switch (this.à(worldIn, pos)) {
                case 0: {
                    state = state.HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.HorizonCode_Horizon_È);
                    break;
                }
                case 1: {
                    state = state.HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.Ý);
                    break;
                }
                case 2: {
                    state = state.HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.Â);
                    break;
                }
            }
        }
        else {
            switch (this.Ó(worldIn, pos)) {
                case 0: {
                    state = state.HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.HorizonCode_Horizon_È);
                    break;
                }
                case 1: {
                    state = state.HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.Âµá€);
                    break;
                }
                case 2: {
                    state = state.HorizonCode_Horizon_È(BlockStairs.ŠÂµà, Â.Ø­áŒŠá);
                    break;
                }
            }
        }
        return state;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockStairs.Õ, BlockStairs.à¢, BlockStairs.ŠÂµà });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("TOP", 0, "TOP", 0, "top"), 
        Â("BOTTOM", 1, "BOTTOM", 1, "bottom");
        
        private final String Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002062";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45683_1_, final int p_i45683_2_, final String p_i45683_3_) {
            this.Ý = p_i45683_3_;
        }
        
        @Override
        public String toString() {
            return this.Ý;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ý;
        }
    }
    
    public enum Â implements IStringSerializable
    {
        HorizonCode_Horizon_È("STRAIGHT", 0, "STRAIGHT", 0, "straight"), 
        Â("INNER_LEFT", 1, "INNER_LEFT", 1, "inner_left"), 
        Ý("INNER_RIGHT", 2, "INNER_RIGHT", 2, "inner_right"), 
        Ø­áŒŠá("OUTER_LEFT", 3, "OUTER_LEFT", 3, "outer_left"), 
        Âµá€("OUTER_RIGHT", 4, "OUTER_RIGHT", 4, "outer_right");
        
        private final String Ó;
        private static final Â[] à;
        private static final String Ø = "CL_00002061";
        
        static {
            áŒŠÆ = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€ };
            à = new Â[] { Â.HorizonCode_Horizon_È, Â.Â, Â.Ý, Â.Ø­áŒŠá, Â.Âµá€ };
        }
        
        private Â(final String s, final int n, final String p_i45682_1_, final int p_i45682_2_, final String p_i45682_3_) {
            this.Ó = p_i45682_3_;
        }
        
        @Override
        public String toString() {
            return this.Ó;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ó;
        }
    }
}
