package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockCocoa extends BlockDirectional implements IGrowable
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000216";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 2);
    }
    
    public BlockCocoa() {
        super(Material.ÂµÈ);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCocoa.ŠÂµà, EnumFacing.Ý).HorizonCode_Horizon_È(BlockCocoa.Õ, 0));
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!this.Âµá€(worldIn, pos, state)) {
            this.Ó(worldIn, pos, state);
        }
        else if (worldIn.Å.nextInt(5) == 0) {
            final int var5 = (int)state.HorizonCode_Horizon_È(BlockCocoa.Õ);
            if (var5 < 2) {
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockCocoa.Õ, var5 + 1), 2);
            }
        }
    }
    
    public boolean Âµá€(final World worldIn, BlockPos p_176499_2_, final IBlockState p_176499_3_) {
        p_176499_2_ = p_176499_2_.HorizonCode_Horizon_È((EnumFacing)p_176499_3_.HorizonCode_Horizon_È(BlockCocoa.ŠÂµà));
        final IBlockState var4 = worldIn.Â(p_176499_2_);
        return var4.Ý() == Blocks.¥Æ && var4.HorizonCode_Horizon_È(BlockPlanks.Õ) == BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
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
        final EnumFacing var4 = (EnumFacing)var3.HorizonCode_Horizon_È(BlockCocoa.ŠÂµà);
        final int var5 = (int)var3.HorizonCode_Horizon_È(BlockCocoa.Õ);
        final int var6 = 4 + var5 * 2;
        final int var7 = 5 + var5 * 2;
        final float var8 = var6 / 2.0f;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[var4.ordinal()]) {
            case 1: {
                this.HorizonCode_Horizon_È((8.0f - var8) / 16.0f, (12.0f - var7) / 16.0f, (15.0f - var6) / 16.0f, (8.0f + var8) / 16.0f, 0.75f, 0.9375f);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È((8.0f - var8) / 16.0f, (12.0f - var7) / 16.0f, 0.0625f, (8.0f + var8) / 16.0f, 0.75f, (1.0f + var6) / 16.0f);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(0.0625f, (12.0f - var7) / 16.0f, (8.0f - var8) / 16.0f, (1.0f + var6) / 16.0f, 0.75f, (8.0f + var8) / 16.0f);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È((15.0f - var6) / 16.0f, (12.0f - var7) / 16.0f, (8.0f - var8) / 16.0f, 0.9375f, 0.75f, (8.0f + var8) / 16.0f);
                break;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        final EnumFacing var6 = EnumFacing.HorizonCode_Horizon_È(placer.É);
        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockCocoa.ŠÂµà, var6), 2);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        if (!facing.á().Ø­áŒŠá()) {
            facing = EnumFacing.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockCocoa.ŠÂµà, facing.Âµá€()).HorizonCode_Horizon_È(BlockCocoa.Õ, 0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.Âµá€(worldIn, pos, state)) {
            this.Ó(worldIn, pos, state);
        }
    }
    
    private void Ó(final World worldIn, final BlockPos p_176500_2_, final IBlockState p_176500_3_) {
        worldIn.HorizonCode_Horizon_È(p_176500_2_, Blocks.Â.¥à(), 3);
        this.HorizonCode_Horizon_È(worldIn, p_176500_2_, p_176500_3_, 0);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        final int var6 = (int)state.HorizonCode_Horizon_È(BlockCocoa.Õ);
        byte var7 = 1;
        if (var6 >= 2) {
            var7 = 3;
        }
        for (int var8 = 0; var8 < var7; ++var8) {
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Items.áŒŠÔ, 1, EnumDyeColor.ˆÏ­.Ý()));
        }
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.áŒŠÔ;
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        return EnumDyeColor.ˆÏ­.Ý();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return (int)p_176473_3_.HorizonCode_Horizon_È(BlockCocoa.Õ) < 2;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return true;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        worldIn.HorizonCode_Horizon_È(p_176474_3_, p_176474_4_.HorizonCode_Horizon_È(BlockCocoa.Õ, (int)p_176474_4_.HorizonCode_Horizon_È(BlockCocoa.Õ) + 1), 2);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCocoa.ŠÂµà, EnumFacing.Â(meta)).HorizonCode_Horizon_È(BlockCocoa.Õ, (meta & 0xF) >> 2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockCocoa.ŠÂµà)).Ý();
        var3 |= (int)state.HorizonCode_Horizon_È(BlockCocoa.Õ) << 2;
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCocoa.ŠÂµà, BlockCocoa.Õ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002130";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockCocoa.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockCocoa.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockCocoa.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockCocoa.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
