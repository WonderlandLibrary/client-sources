package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockRedstoneOre extends Block
{
    private final boolean Õ;
    private static final String à¢ = "CL_00000294";
    
    public BlockRedstoneOre(final boolean p_i45420_1_) {
        super(Material.Âµá€);
        if (p_i45420_1_) {
            this.HorizonCode_Horizon_È(true);
        }
        this.Õ = p_i45420_1_;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 30;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
        this.áŒŠÆ(worldIn, pos);
        super.HorizonCode_Horizon_È(worldIn, pos, playerIn);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn) {
        this.áŒŠÆ(worldIn, pos);
        super.HorizonCode_Horizon_È(worldIn, pos, entityIn);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        this.áŒŠÆ(worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
    }
    
    private void áŒŠÆ(final World worldIn, final BlockPos p_176352_2_) {
        this.áˆºÑ¢Õ(worldIn, p_176352_2_);
        if (this == Blocks.Ñ¢à) {
            worldIn.Â(p_176352_2_, Blocks.ÇªØ­.¥à());
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this == Blocks.ÇªØ­) {
            worldIn.Â(pos, Blocks.Ñ¢à.¥à());
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ÇŽá;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int fortune, final Random random) {
        return this.HorizonCode_Horizon_È(random) + random.nextInt(fortune + 1);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 4 + random.nextInt(2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, chance, fortune);
        if (this.HorizonCode_Horizon_È(state, worldIn.Å, fortune) != Item_1028566121.HorizonCode_Horizon_È(this)) {
            final int var6 = 1 + worldIn.Å.nextInt(5);
            this.HorizonCode_Horizon_È(worldIn, pos, var6);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this.Õ) {
            this.áˆºÑ¢Õ(worldIn, pos);
        }
    }
    
    private void áˆºÑ¢Õ(final World worldIn, final BlockPos p_180691_2_) {
        final Random var3 = worldIn.Å;
        final double var4 = 0.0625;
        for (int var5 = 0; var5 < 6; ++var5) {
            double var6 = p_180691_2_.HorizonCode_Horizon_È() + var3.nextFloat();
            double var7 = p_180691_2_.Â() + var3.nextFloat();
            double var8 = p_180691_2_.Ý() + var3.nextFloat();
            if (var5 == 0 && !worldIn.Â(p_180691_2_.Ø­áŒŠá()).Ý().Å()) {
                var7 = p_180691_2_.Â() + var4 + 1.0;
            }
            if (var5 == 1 && !worldIn.Â(p_180691_2_.Âµá€()).Ý().Å()) {
                var7 = p_180691_2_.Â() - var4;
            }
            if (var5 == 2 && !worldIn.Â(p_180691_2_.à()).Ý().Å()) {
                var8 = p_180691_2_.Ý() + var4 + 1.0;
            }
            if (var5 == 3 && !worldIn.Â(p_180691_2_.Ó()).Ý().Å()) {
                var8 = p_180691_2_.Ý() - var4;
            }
            if (var5 == 4 && !worldIn.Â(p_180691_2_.áŒŠÆ()).Ý().Å()) {
                var6 = p_180691_2_.HorizonCode_Horizon_È() + var4 + 1.0;
            }
            if (var5 == 5 && !worldIn.Â(p_180691_2_.Ø()).Ý().Å()) {
                var6 = p_180691_2_.HorizonCode_Horizon_È() - var4;
            }
            if (var6 < p_180691_2_.HorizonCode_Horizon_È() || var6 > p_180691_2_.HorizonCode_Horizon_È() + 1 || var7 < 0.0 || var7 > p_180691_2_.Â() + 1 || var8 < p_180691_2_.Ý() || var8 > p_180691_2_.Ý() + 1) {
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÉ, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        return new ItemStack(Blocks.Ñ¢à);
    }
}
