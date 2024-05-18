package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public abstract class BlockButton extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    private final boolean ŠÂµà;
    private static final String ¥à = "CL_00000209";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing");
        à¢ = PropertyBool.HorizonCode_Horizon_È("powered");
    }
    
    protected BlockButton(final boolean wooden) {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockButton.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockButton.à¢, false));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
        this.ŠÂµà = wooden;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return this.ŠÂµà ? 30 : 20;
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
        return worldIn.Â(pos.HorizonCode_Horizon_È(side.Âµá€())).Ý().Ø();
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        for (final EnumFacing var6 : EnumFacing.values()) {
            if (worldIn.Â(pos.HorizonCode_Horizon_È(var6)).Ý().Ø()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return worldIn.Â(pos.HorizonCode_Horizon_È(facing.Âµá€())).Ý().Ø() ? this.¥à().HorizonCode_Horizon_È(BlockButton.Õ, facing).HorizonCode_Horizon_È(BlockButton.à¢, false) : this.¥à().HorizonCode_Horizon_È(BlockButton.Õ, EnumFacing.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockButton.à¢, false);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (this.Âµá€(worldIn, pos, state)) {
            final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockButton.Õ);
            if (!worldIn.Â(pos.HorizonCode_Horizon_È(var5.Âµá€())).Ý().Ø()) {
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
                worldIn.Ø(pos);
            }
        }
    }
    
    private boolean Âµá€(final World worldIn, final BlockPos p_176583_2_, final IBlockState p_176583_3_) {
        if (!this.Ø­áŒŠá(worldIn, p_176583_2_)) {
            this.HorizonCode_Horizon_È(worldIn, p_176583_2_, p_176583_3_, 0);
            worldIn.Ø(p_176583_2_);
            return false;
        }
        return true;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.áŒŠÆ(access.Â(pos));
    }
    
    private void áŒŠÆ(final IBlockState p_180681_1_) {
        final EnumFacing var2 = (EnumFacing)p_180681_1_.HorizonCode_Horizon_È(BlockButton.Õ);
        final boolean var3 = (boolean)p_180681_1_.HorizonCode_Horizon_È(BlockButton.à¢);
        final float var4 = 0.25f;
        final float var5 = 0.375f;
        final float var6 = (var3 ? 1 : 2) / 16.0f;
        final float var7 = 0.125f;
        final float var8 = 0.1875f;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[var2.ordinal()]) {
            case 1: {
                this.HorizonCode_Horizon_È(0.0f, 0.375f, 0.3125f, var6, 0.625f, 0.6875f);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(1.0f - var6, 0.375f, 0.3125f, 1.0f, 0.625f, 0.6875f);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(0.3125f, 0.375f, 0.0f, 0.6875f, 0.625f, var6);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(0.3125f, 0.375f, 1.0f - var6, 0.6875f, 0.625f, 1.0f);
                break;
            }
            case 5: {
                this.HorizonCode_Horizon_È(0.3125f, 0.0f, 0.375f, 0.6875f, 0.0f + var6, 0.625f);
                break;
            }
            case 6: {
                this.HorizonCode_Horizon_È(0.3125f, 1.0f - var6, 0.375f, 0.6875f, 1.0f, 0.625f);
                break;
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (state.HorizonCode_Horizon_È(BlockButton.à¢)) {
            return true;
        }
        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockButton.à¢, true), 3);
        worldIn.Â(pos, pos);
        worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "random.click", 0.3f, 0.6f);
        this.Â(worldIn, pos, (EnumFacing)state.HorizonCode_Horizon_È(BlockButton.Õ));
        worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
        return true;
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (state.HorizonCode_Horizon_È(BlockButton.à¢)) {
            this.Â(worldIn, pos, (EnumFacing)state.HorizonCode_Horizon_È(BlockButton.Õ));
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockButton.à¢) ? 15 : 0;
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockButton.à¢) ? ((state.HorizonCode_Horizon_È(BlockButton.Õ) == side) ? 15 : 0) : 0;
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ && (boolean)state.HorizonCode_Horizon_È(BlockButton.à¢)) {
            if (this.ŠÂµà) {
                this.Ó(worldIn, pos, state);
            }
            else {
                worldIn.Â(pos, state.HorizonCode_Horizon_È(BlockButton.à¢, false));
                this.Â(worldIn, pos, (EnumFacing)state.HorizonCode_Horizon_È(BlockButton.Õ));
                worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "random.click", 0.3f, 0.5f);
                worldIn.Â(pos, pos);
            }
        }
    }
    
    @Override
    public void ŠÄ() {
        final float var1 = 0.1875f;
        final float var2 = 0.125f;
        final float var3 = 0.125f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.5f - var2, 0.5f - var3, 0.5f + var1, 0.5f + var2, 0.5f + var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (!worldIn.ŠÄ && this.ŠÂµà && !(boolean)state.HorizonCode_Horizon_È(BlockButton.à¢)) {
            this.Ó(worldIn, pos, state);
        }
    }
    
    private void Ó(final World worldIn, final BlockPos p_180680_2_, final IBlockState p_180680_3_) {
        this.áŒŠÆ(p_180680_3_);
        final List var4 = worldIn.HorizonCode_Horizon_È(EntityArrow.class, new AxisAlignedBB(p_180680_2_.HorizonCode_Horizon_È() + this.ŠÄ, p_180680_2_.Â() + this.Ñ¢á, p_180680_2_.Ý() + this.ŒÏ, p_180680_2_.HorizonCode_Horizon_È() + this.Çªà¢, p_180680_2_.Â() + this.Ê, p_180680_2_.Ý() + this.ÇŽÉ));
        final boolean var5 = !var4.isEmpty();
        final boolean var6 = (boolean)p_180680_3_.HorizonCode_Horizon_È(BlockButton.à¢);
        if (var5 && !var6) {
            worldIn.Â(p_180680_2_, p_180680_3_.HorizonCode_Horizon_È(BlockButton.à¢, true));
            this.Â(worldIn, p_180680_2_, (EnumFacing)p_180680_3_.HorizonCode_Horizon_È(BlockButton.Õ));
            worldIn.Â(p_180680_2_, p_180680_2_);
            worldIn.HorizonCode_Horizon_È(p_180680_2_.HorizonCode_Horizon_È() + 0.5, p_180680_2_.Â() + 0.5, p_180680_2_.Ý() + 0.5, "random.click", 0.3f, 0.6f);
        }
        if (!var5 && var6) {
            worldIn.Â(p_180680_2_, p_180680_3_.HorizonCode_Horizon_È(BlockButton.à¢, false));
            this.Â(worldIn, p_180680_2_, (EnumFacing)p_180680_3_.HorizonCode_Horizon_È(BlockButton.Õ));
            worldIn.Â(p_180680_2_, p_180680_2_);
            worldIn.HorizonCode_Horizon_È(p_180680_2_.HorizonCode_Horizon_È() + 0.5, p_180680_2_.Â() + 0.5, p_180680_2_.Ý() + 0.5, "random.click", 0.3f, 0.5f);
        }
        if (var5) {
            worldIn.HorizonCode_Horizon_È(p_180680_2_, this, this.HorizonCode_Horizon_È(worldIn));
        }
    }
    
    private void Â(final World worldIn, final BlockPos p_176582_2_, final EnumFacing p_176582_3_) {
        worldIn.Â(p_176582_2_, this);
        worldIn.Â(p_176582_2_.HorizonCode_Horizon_È(p_176582_3_.Âµá€()), this);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing var2 = null;
        switch (meta & 0x7) {
            case 0: {
                var2 = EnumFacing.HorizonCode_Horizon_È;
                break;
            }
            case 1: {
                var2 = EnumFacing.Ó;
                break;
            }
            case 2: {
                var2 = EnumFacing.Âµá€;
                break;
            }
            case 3: {
                var2 = EnumFacing.Ø­áŒŠá;
                break;
            }
            case 4: {
                var2 = EnumFacing.Ý;
                break;
            }
            default: {
                var2 = EnumFacing.Â;
                break;
            }
        }
        return this.¥à().HorizonCode_Horizon_È(BlockButton.Õ, var2).HorizonCode_Horizon_È(BlockButton.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((EnumFacing)state.HorizonCode_Horizon_È(BlockButton.Õ)).ordinal()]) {
            case 1: {
                var2 = 1;
                break;
            }
            case 2: {
                var2 = 2;
                break;
            }
            case 3: {
                var2 = 3;
                break;
            }
            case 4: {
                var2 = 4;
                break;
            }
            default: {
                var2 = 5;
                break;
            }
            case 6: {
                var2 = 0;
                break;
            }
        }
        if (state.HorizonCode_Horizon_È(BlockButton.à¢)) {
            var2 |= 0x8;
        }
        return var2;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockButton.Õ, BlockButton.à¢ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002131";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockButton.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
