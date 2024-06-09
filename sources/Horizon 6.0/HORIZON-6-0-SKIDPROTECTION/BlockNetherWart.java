package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockNetherWart extends BlockBush
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000274";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 3);
    }
    
    protected BlockNetherWart() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockNetherWart.Õ, 0));
        this.HorizonCode_Horizon_È(true);
        final float var1 = 0.5f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
        this.HorizonCode_Horizon_È((CreativeTabs)null);
    }
    
    @Override
    protected boolean Ý(final Block ground) {
        return ground == Blocks.ŠÕ;
    }
    
    @Override
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        return this.Ý(worldIn.Â(p_180671_2_.Âµá€()).Ý());
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, IBlockState state, final Random rand) {
        final int var5 = (int)state.HorizonCode_Horizon_È(BlockNetherWart.Õ);
        if (var5 < 3 && rand.nextInt(10) == 0) {
            state = state.HorizonCode_Horizon_È(BlockNetherWart.Õ, var5 + 1);
            worldIn.HorizonCode_Horizon_È(pos, state, 2);
        }
        super.Â(worldIn, pos, state, rand);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.ŠÄ) {
            int var6 = 1;
            if ((int)state.HorizonCode_Horizon_È(BlockNetherWart.Õ) >= 3) {
                var6 = 2 + worldIn.Å.nextInt(3);
                if (fortune > 0) {
                    var6 += worldIn.Å.nextInt(fortune + 1);
                }
            }
            for (int var7 = 0; var7 < var6; ++var7) {
                Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Items.Œá));
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.Œá;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockNetherWart.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockNetherWart.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockNetherWart.Õ });
    }
}
