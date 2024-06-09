package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Objects;
import java.util.Iterator;
import com.google.common.base.Predicate;

public class BlockTripWireHook extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    private static final String Âµà = "CL_00000329";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyBool.HorizonCode_Horizon_È("powered");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("attached");
        ¥à = PropertyBool.HorizonCode_Horizon_È("suspended");
    }
    
    public BlockTripWireHook() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockTripWireHook.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockTripWireHook.à¢, false).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, false).HorizonCode_Horizon_È(BlockTripWireHook.¥à, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockTripWireHook.¥à, !World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()));
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
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return side.á().Ø­áŒŠá() && worldIn.Â(pos.HorizonCode_Horizon_È(side.Âµá€())).Ý().Ø();
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        for (final EnumFacing var4 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (worldIn.Â(pos.HorizonCode_Horizon_È(var4)).Ý().Ø()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState var9 = this.¥à().HorizonCode_Horizon_È(BlockTripWireHook.à¢, false).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, false).HorizonCode_Horizon_È(BlockTripWireHook.¥à, false);
        if (facing.á().Ø­áŒŠá()) {
            var9 = var9.HorizonCode_Horizon_È(BlockTripWireHook.Õ, facing);
        }
        return var9;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        this.HorizonCode_Horizon_È(worldIn, pos, state, false, false, -1, null);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (neighborBlock != this && this.Âµá€(worldIn, pos, state)) {
            final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockTripWireHook.Õ);
            if (!worldIn.Â(pos.HorizonCode_Horizon_È(var5.Âµá€())).Ý().Ø()) {
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
                worldIn.Ø(pos);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176260_2_, final IBlockState p_176260_3_, final boolean p_176260_4_, final boolean p_176260_5_, final int p_176260_6_, final IBlockState p_176260_7_) {
        final EnumFacing var8 = (EnumFacing)p_176260_3_.HorizonCode_Horizon_È(BlockTripWireHook.Õ);
        final boolean var9 = (boolean)p_176260_3_.HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà);
        final boolean var10 = (boolean)p_176260_3_.HorizonCode_Horizon_È(BlockTripWireHook.à¢);
        final boolean var11 = !World.HorizonCode_Horizon_È(worldIn, p_176260_2_.Âµá€());
        boolean var12 = !p_176260_4_;
        boolean var13 = false;
        int var14 = 0;
        final IBlockState[] var15 = new IBlockState[42];
        int var16 = 1;
        while (var16 < 42) {
            final BlockPos var17 = p_176260_2_.HorizonCode_Horizon_È(var8, var16);
            IBlockState var18 = worldIn.Â(var17);
            if (var18.Ý() == Blocks.ˆÂ) {
                if (var18.HorizonCode_Horizon_È(BlockTripWireHook.Õ) == var8.Âµá€()) {
                    var14 = var16;
                    break;
                }
                break;
            }
            else {
                if (var18.Ý() != Blocks.áŒŠÈ && var16 != p_176260_6_) {
                    var15[var16] = null;
                    var12 = false;
                }
                else {
                    if (var16 == p_176260_6_) {
                        var18 = (IBlockState)Objects.firstNonNull((Object)p_176260_7_, (Object)var18);
                    }
                    final boolean var19 = !(boolean)var18.HorizonCode_Horizon_È(BlockTripWire.¥à);
                    final boolean var20 = (boolean)var18.HorizonCode_Horizon_È(BlockTripWire.Õ);
                    final boolean var21 = (boolean)var18.HorizonCode_Horizon_È(BlockTripWire.à¢);
                    var12 &= (var21 == var11);
                    var13 |= (var19 && var20);
                    var15[var16] = var18;
                    if (var16 == p_176260_6_) {
                        worldIn.HorizonCode_Horizon_È(p_176260_2_, this, this.HorizonCode_Horizon_È(worldIn));
                        var12 &= var19;
                    }
                }
                ++var16;
            }
        }
        var12 &= (var14 > 1);
        var13 &= var12;
        final IBlockState var22 = this.¥à().HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, var12).HorizonCode_Horizon_È(BlockTripWireHook.à¢, var13);
        if (var14 > 0) {
            final BlockPos var17 = p_176260_2_.HorizonCode_Horizon_È(var8, var14);
            final EnumFacing var23 = var8.Âµá€();
            worldIn.HorizonCode_Horizon_È(var17, var22.HorizonCode_Horizon_È(BlockTripWireHook.Õ, var23), 3);
            this.Â(worldIn, var17, var23);
            this.HorizonCode_Horizon_È(worldIn, var17, var12, var13, var9, var10);
        }
        this.HorizonCode_Horizon_È(worldIn, p_176260_2_, var12, var13, var9, var10);
        if (!p_176260_4_) {
            worldIn.HorizonCode_Horizon_È(p_176260_2_, var22.HorizonCode_Horizon_È(BlockTripWireHook.Õ, var8), 3);
            if (p_176260_5_) {
                this.Â(worldIn, p_176260_2_, var8);
            }
        }
        if (var9 != var12) {
            for (int var24 = 1; var24 < var14; ++var24) {
                final BlockPos var25 = p_176260_2_.HorizonCode_Horizon_È(var8, var24);
                final IBlockState var26 = var15[var24];
                if (var26 != null && worldIn.Â(var25).Ý() != Blocks.Â) {
                    worldIn.HorizonCode_Horizon_È(var25, var26.HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, var12), 3);
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        this.HorizonCode_Horizon_È(worldIn, pos, state, false, true, -1, null);
    }
    
    private void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180694_2_, final boolean p_180694_3_, final boolean p_180694_4_, final boolean p_180694_5_, final boolean p_180694_6_) {
        if (p_180694_4_ && !p_180694_6_) {
            worldIn.HorizonCode_Horizon_È(p_180694_2_.HorizonCode_Horizon_È() + 0.5, p_180694_2_.Â() + 0.1, p_180694_2_.Ý() + 0.5, "random.click", 0.4f, 0.6f);
        }
        else if (!p_180694_4_ && p_180694_6_) {
            worldIn.HorizonCode_Horizon_È(p_180694_2_.HorizonCode_Horizon_È() + 0.5, p_180694_2_.Â() + 0.1, p_180694_2_.Ý() + 0.5, "random.click", 0.4f, 0.5f);
        }
        else if (p_180694_3_ && !p_180694_5_) {
            worldIn.HorizonCode_Horizon_È(p_180694_2_.HorizonCode_Horizon_È() + 0.5, p_180694_2_.Â() + 0.1, p_180694_2_.Ý() + 0.5, "random.click", 0.4f, 0.7f);
        }
        else if (!p_180694_3_ && p_180694_5_) {
            worldIn.HorizonCode_Horizon_È(p_180694_2_.HorizonCode_Horizon_È() + 0.5, p_180694_2_.Â() + 0.1, p_180694_2_.Ý() + 0.5, "random.bowhit", 0.4f, 1.2f / (worldIn.Å.nextFloat() * 0.2f + 0.9f));
        }
    }
    
    private void Â(final World worldIn, final BlockPos p_176262_2_, final EnumFacing p_176262_3_) {
        worldIn.Â(p_176262_2_, this);
        worldIn.Â(p_176262_2_.HorizonCode_Horizon_È(p_176262_3_.Âµá€()), this);
    }
    
    private boolean Âµá€(final World worldIn, final BlockPos p_176261_2_, final IBlockState p_176261_3_) {
        if (!this.Ø­áŒŠá(worldIn, p_176261_2_)) {
            this.HorizonCode_Horizon_È(worldIn, p_176261_2_, p_176261_3_, 0);
            worldIn.Ø(p_176261_2_);
            return false;
        }
        return true;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final float var3 = 0.1875f;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((EnumFacing)access.Â(pos).HorizonCode_Horizon_È(BlockTripWireHook.Õ)).ordinal()]) {
            case 1: {
                this.HorizonCode_Horizon_È(0.0f, 0.2f, 0.5f - var3, var3 * 2.0f, 0.8f, 0.5f + var3);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(1.0f - var3 * 2.0f, 0.2f, 0.5f - var3, 1.0f, 0.8f, 0.5f + var3);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(0.5f - var3, 0.2f, 0.0f, 0.5f + var3, 0.8f, var3 * 2.0f);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(0.5f - var3, 0.2f, 1.0f - var3 * 2.0f, 0.5f + var3, 0.8f, 1.0f);
                break;
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final boolean var4 = (boolean)state.HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà);
        final boolean var5 = (boolean)state.HorizonCode_Horizon_È(BlockTripWireHook.à¢);
        if (var4 || var5) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, true, false, -1, null);
        }
        if (var5) {
            worldIn.Â(pos, this);
            worldIn.Â(pos.HorizonCode_Horizon_È(((EnumFacing)state.HorizonCode_Horizon_È(BlockTripWireHook.Õ)).Âµá€()), this);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockTripWireHook.à¢) ? 15 : 0;
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockTripWireHook.à¢) ? ((state.HorizonCode_Horizon_È(BlockTripWireHook.Õ) == side) ? 15 : 0) : 0;
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Â;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockTripWireHook.Õ, EnumFacing.Â(meta & 0x3)).HorizonCode_Horizon_È(BlockTripWireHook.à¢, (meta & 0x8) > 0).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, (meta & 0x4) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockTripWireHook.Õ)).Ý();
        if (state.HorizonCode_Horizon_È(BlockTripWireHook.à¢)) {
            var3 |= 0x8;
        }
        if (state.HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà)) {
            var3 |= 0x4;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockTripWireHook.Õ, BlockTripWireHook.à¢, BlockTripWireHook.ŠÂµà, BlockTripWireHook.¥à });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002050";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockTripWireHook.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockTripWireHook.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockTripWireHook.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockTripWireHook.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
