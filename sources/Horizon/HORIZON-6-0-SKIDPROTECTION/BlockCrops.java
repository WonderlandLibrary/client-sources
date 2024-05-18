package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockCrops extends BlockBush implements IGrowable
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000222";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 7);
    }
    
    protected BlockCrops() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCrops.Õ, 0));
        this.HorizonCode_Horizon_È(true);
        final float var1 = 0.5f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
        this.HorizonCode_Horizon_È((CreativeTabs)null);
        this.Ý(0.0f);
        this.HorizonCode_Horizon_È(BlockCrops.Ó);
        this.ÇŽÉ();
    }
    
    @Override
    protected boolean Ý(final Block ground) {
        return ground == Blocks.£Â;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.Â(worldIn, pos, state, rand);
        if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) >= 9) {
            final int var5 = (int)state.HorizonCode_Horizon_È(BlockCrops.Õ);
            if (var5 < 7) {
                final float var6 = HorizonCode_Horizon_È(this, worldIn, pos);
                if (rand.nextInt((int)(25.0f / var6) + 1) == 0) {
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockCrops.Õ, var5 + 1), 2);
                }
            }
        }
    }
    
    public void à(final World worldIn, final BlockPos p_176487_2_, final IBlockState p_176487_3_) {
        int var4 = (int)p_176487_3_.HorizonCode_Horizon_È(BlockCrops.Õ) + MathHelper.HorizonCode_Horizon_È(worldIn.Å, 2, 5);
        if (var4 > 7) {
            var4 = 7;
        }
        worldIn.HorizonCode_Horizon_È(p_176487_2_, p_176487_3_.HorizonCode_Horizon_È(BlockCrops.Õ, var4), 2);
    }
    
    protected static float HorizonCode_Horizon_È(final Block p_180672_0_, final World worldIn, final BlockPos p_180672_2_) {
        float var3 = 1.0f;
        final BlockPos var4 = p_180672_2_.Âµá€();
        for (int var5 = -1; var5 <= 1; ++var5) {
            for (int var6 = -1; var6 <= 1; ++var6) {
                float var7 = 0.0f;
                final IBlockState var8 = worldIn.Â(var4.Â(var5, 0, var6));
                if (var8.Ý() == Blocks.£Â) {
                    var7 = 1.0f;
                    if ((int)var8.HorizonCode_Horizon_È(BlockFarmland.Õ) > 0) {
                        var7 = 3.0f;
                    }
                }
                if (var5 != 0 || var6 != 0) {
                    var7 /= 4.0f;
                }
                var3 += var7;
            }
        }
        final BlockPos var9 = p_180672_2_.Ó();
        final BlockPos var10 = p_180672_2_.à();
        final BlockPos var11 = p_180672_2_.Ø();
        final BlockPos var12 = p_180672_2_.áŒŠÆ();
        final boolean var13 = p_180672_0_ == worldIn.Â(var11).Ý() || p_180672_0_ == worldIn.Â(var12).Ý();
        final boolean var14 = p_180672_0_ == worldIn.Â(var9).Ý() || p_180672_0_ == worldIn.Â(var10).Ý();
        if (var13 && var14) {
            var3 /= 2.0f;
        }
        else {
            final boolean var15 = p_180672_0_ == worldIn.Â(var11.Ó()).Ý() || p_180672_0_ == worldIn.Â(var12.Ó()).Ý() || p_180672_0_ == worldIn.Â(var12.à()).Ý() || p_180672_0_ == worldIn.Â(var11.à()).Ý();
            if (var15) {
                var3 /= 2.0f;
            }
        }
        return var3;
    }
    
    @Override
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        return (worldIn.á(p_180671_2_) >= 8 || worldIn.áˆºÑ¢Õ(p_180671_2_)) && this.Ý(worldIn.Â(p_180671_2_.Âµá€()).Ý());
    }
    
    protected Item_1028566121 È() {
        return Items.¥à;
    }
    
    protected Item_1028566121 áŠ() {
        return Items.Âµà;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, chance, 0);
        if (!worldIn.ŠÄ) {
            final int var6 = (int)state.HorizonCode_Horizon_È(BlockCrops.Õ);
            if (var6 >= 7) {
                for (int var7 = 3 + fortune, var8 = 0; var8 < var7; ++var8) {
                    if (worldIn.Å.nextInt(15) <= var6) {
                        Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(this.È(), 1, 0));
                    }
                }
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return ((int)state.HorizonCode_Horizon_È(BlockCrops.Õ) == 7) ? this.áŠ() : this.È();
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return this.È();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return (int)p_176473_3_.HorizonCode_Horizon_È(BlockCrops.Õ) < 7;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return true;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        this.à(worldIn, p_176474_3_, p_176474_4_);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCrops.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockCrops.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCrops.Õ });
    }
}
