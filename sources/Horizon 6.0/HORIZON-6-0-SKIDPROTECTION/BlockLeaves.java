package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class BlockLeaves extends BlockLeavesBase
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    int[] ŠÂµà;
    protected int ¥à;
    protected boolean Âµà;
    private static final String È = "CL_00000263";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("decayable");
        à¢ = PropertyBool.HorizonCode_Horizon_È("check_decay");
    }
    
    public BlockLeaves() {
        super(Material.áˆºÑ¢Õ, false);
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.Ý(0.2f);
        this.Ø­áŒŠá(1);
        this.HorizonCode_Horizon_È(BlockLeaves.Ó);
    }
    
    @Override
    public int Ï­Ðƒà() {
        return ColorizerFoliage.HorizonCode_Horizon_È(0.5, 1.0);
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        return ColorizerFoliage.Ý();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return BiomeColorHelper.Â(worldIn, pos);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final byte var4 = 1;
        final int var5 = var4 + 1;
        final int var6 = pos.HorizonCode_Horizon_È();
        final int var7 = pos.Â();
        final int var8 = pos.Ý();
        if (worldIn.HorizonCode_Horizon_È(new BlockPos(var6 - var5, var7 - var5, var8 - var5), new BlockPos(var6 + var5, var7 + var5, var8 + var5))) {
            for (int var9 = -var4; var9 <= var4; ++var9) {
                for (int var10 = -var4; var10 <= var4; ++var10) {
                    for (int var11 = -var4; var11 <= var4; ++var11) {
                        final BlockPos var12 = pos.Â(var9, var10, var11);
                        final IBlockState var13 = worldIn.Â(var12);
                        if (var13.Ý().Ó() == Material.áˆºÑ¢Õ && !(boolean)var13.HorizonCode_Horizon_È(BlockLeaves.à¢)) {
                            worldIn.HorizonCode_Horizon_È(var12, var13.HorizonCode_Horizon_È(BlockLeaves.à¢, true), 4);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ && (boolean)state.HorizonCode_Horizon_È(BlockLeaves.à¢) && (boolean)state.HorizonCode_Horizon_È(BlockLeaves.Õ)) {
            final byte var5 = 4;
            final int var6 = var5 + 1;
            final int var7 = pos.HorizonCode_Horizon_È();
            final int var8 = pos.Â();
            final int var9 = pos.Ý();
            final byte var10 = 32;
            final int var11 = var10 * var10;
            final int var12 = var10 / 2;
            if (this.ŠÂµà == null) {
                this.ŠÂµà = new int[var10 * var10 * var10];
            }
            if (worldIn.HorizonCode_Horizon_È(new BlockPos(var7 - var6, var8 - var6, var9 - var6), new BlockPos(var7 + var6, var8 + var6, var9 + var6))) {
                for (int var13 = -var5; var13 <= var5; ++var13) {
                    for (int var14 = -var5; var14 <= var5; ++var14) {
                        for (int var15 = -var5; var15 <= var5; ++var15) {
                            final Block var16 = worldIn.Â(new BlockPos(var7 + var13, var8 + var14, var9 + var15)).Ý();
                            if (var16 != Blocks.¥Æ && var16 != Blocks.Ø­à) {
                                if (var16.Ó() == Material.áˆºÑ¢Õ) {
                                    this.ŠÂµà[(var13 + var12) * var11 + (var14 + var12) * var10 + var15 + var12] = -2;
                                }
                                else {
                                    this.ŠÂµà[(var13 + var12) * var11 + (var14 + var12) * var10 + var15 + var12] = -1;
                                }
                            }
                            else {
                                this.ŠÂµà[(var13 + var12) * var11 + (var14 + var12) * var10 + var15 + var12] = 0;
                            }
                        }
                    }
                }
                for (int var13 = 1; var13 <= 4; ++var13) {
                    for (int var14 = -var5; var14 <= var5; ++var14) {
                        for (int var15 = -var5; var15 <= var5; ++var15) {
                            for (int var17 = -var5; var17 <= var5; ++var17) {
                                if (this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12) * var10 + var17 + var12] == var13 - 1) {
                                    if (this.ŠÂµà[(var14 + var12 - 1) * var11 + (var15 + var12) * var10 + var17 + var12] == -2) {
                                        this.ŠÂµà[(var14 + var12 - 1) * var11 + (var15 + var12) * var10 + var17 + var12] = var13;
                                    }
                                    if (this.ŠÂµà[(var14 + var12 + 1) * var11 + (var15 + var12) * var10 + var17 + var12] == -2) {
                                        this.ŠÂµà[(var14 + var12 + 1) * var11 + (var15 + var12) * var10 + var17 + var12] = var13;
                                    }
                                    if (this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12 - 1) * var10 + var17 + var12] == -2) {
                                        this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12 - 1) * var10 + var17 + var12] = var13;
                                    }
                                    if (this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12 + 1) * var10 + var17 + var12] == -2) {
                                        this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12 + 1) * var10 + var17 + var12] = var13;
                                    }
                                    if (this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12) * var10 + (var17 + var12 - 1)] == -2) {
                                        this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12) * var10 + (var17 + var12 - 1)] = var13;
                                    }
                                    if (this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12) * var10 + var17 + var12 + 1] == -2) {
                                        this.ŠÂµà[(var14 + var12) * var11 + (var15 + var12) * var10 + var17 + var12 + 1] = var13;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int var13 = this.ŠÂµà[var12 * var11 + var12 * var10 + var12];
            if (var13 >= 0) {
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockLeaves.à¢, false), 4);
            }
            else {
                this.áŒŠÆ(worldIn, pos);
            }
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (worldIn.ŒÏ(pos.Ø­áŒŠá()) && !World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) && rand.nextInt(15) == 1) {
            final double var5 = pos.HorizonCode_Horizon_È() + rand.nextFloat();
            final double var6 = pos.Â() - 0.05;
            final double var7 = pos.Ý() + rand.nextFloat();
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.¥Æ, var5, var6, var7, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    private void áŒŠÆ(final World worldIn, final BlockPos p_176235_2_) {
        this.HorizonCode_Horizon_È(worldIn, p_176235_2_, worldIn.Â(p_176235_2_), 0);
        worldIn.Ø(p_176235_2_);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return (random.nextInt(20) == 0) ? 1 : 0;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.ŠÄ) {
            int var6 = this.áŒŠÆ(state);
            if (fortune > 0) {
                var6 -= 2 << fortune;
                if (var6 < 10) {
                    var6 = 10;
                }
            }
            if (worldIn.Å.nextInt(var6) == 0) {
                final Item_1028566121 var7 = this.HorizonCode_Horizon_È(state, worldIn.Å, fortune);
                Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(var7, 1, this.Ø­áŒŠá(state)));
            }
            var6 = 200;
            if (fortune > 0) {
                var6 -= 10 << fortune;
                if (var6 < 40) {
                    var6 = 40;
                }
            }
            this.Â(worldIn, pos, state, var6);
        }
    }
    
    protected void Â(final World worldIn, final BlockPos p_176234_2_, final IBlockState p_176234_3_, final int p_176234_4_) {
    }
    
    protected int áŒŠÆ(final IBlockState p_176232_1_) {
        return 20;
    }
    
    @Override
    public boolean Å() {
        return !this.Ç;
    }
    
    public void Â(final boolean p_150122_1_) {
        this.Âµà = p_150122_1_;
        this.Ç = p_150122_1_;
        this.¥à = (p_150122_1_ ? 0 : 1);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return this.Âµà ? EnumWorldBlockLayer.Â : EnumWorldBlockLayer.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean áŒŠÆ() {
        return false;
    }
    
    public abstract BlockPlanks.HorizonCode_Horizon_È Âµá€(final int p0);
}
