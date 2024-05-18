package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.util.Random;

public class BiomeGenMesa extends BiomeGenBase
{
    private IBlockState[] Ñ¢à;
    private long ÇªØ­;
    private NoiseGeneratorPerlin £áŒŠá;
    private NoiseGeneratorPerlin áˆº;
    private NoiseGeneratorPerlin Šà;
    private boolean áŒŠá€;
    private boolean ¥Ï;
    private static final String ˆà¢ = "CL_00000176";
    
    public BiomeGenMesa(final int p_i45380_1_, final boolean p_i45380_2_, final boolean p_i45380_3_) {
        super(p_i45380_1_);
        this.áŒŠá€ = p_i45380_2_;
        this.¥Ï = p_i45380_3_;
        this.Â();
        this.HorizonCode_Horizon_È(2.0f, 0.0f);
        this.ÇªÂµÕ.clear();
        this.Ï­Ï­Ï = Blocks.£á.¥à().HorizonCode_Horizon_È(BlockSand.Õ, BlockSand.HorizonCode_Horizon_È.Â);
        this.£Â = Blocks.Ø­Â.¥à();
        this.ˆÏ.Ñ¢á = -999;
        this.ˆÏ.Ê = 20;
        this.ˆÏ.ˆá = 3;
        this.ˆÏ.ÇŽÕ = 5;
        this.ˆÏ.ŒÏ = 0;
        this.ÇªÂµÕ.clear();
        if (p_i45380_3_) {
            this.ˆÏ.Ñ¢á = 5;
        }
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return this.Û;
    }
    
    @Override
    public int Ý(final BlockPos p_180625_1_) {
        return 10387789;
    }
    
    @Override
    public int Â(final BlockPos p_180627_1_) {
        return 9470285;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        if (this.Ñ¢à == null || this.ÇªØ­ != worldIn.Æ()) {
            this.HorizonCode_Horizon_È(worldIn.Æ());
        }
        if (this.£áŒŠá == null || this.áˆº == null || this.ÇªØ­ != worldIn.Æ()) {
            final Random var8 = new Random(this.ÇªØ­);
            this.£áŒŠá = new NoiseGeneratorPerlin(var8, 4);
            this.áˆº = new NoiseGeneratorPerlin(var8, 1);
        }
        this.ÇªØ­ = worldIn.Æ();
        double var9 = 0.0;
        if (this.áŒŠá€) {
            final int var10 = (p_180622_4_ & 0xFFFFFFF0) + (p_180622_5_ & 0xF);
            final int var11 = (p_180622_5_ & 0xFFFFFFF0) + (p_180622_4_ & 0xF);
            final double var12 = Math.min(Math.abs(p_180622_6_), this.£áŒŠá.HorizonCode_Horizon_È(var10 * 0.25, var11 * 0.25));
            if (var12 > 0.0) {
                final double var13 = 0.001953125;
                final double var14 = Math.abs(this.áˆº.HorizonCode_Horizon_È(var10 * var13, var11 * var13));
                var9 = var12 * var12 * 2.5;
                final double var15 = Math.ceil(var14 * 50.0) + 14.0;
                if (var9 > var15) {
                    var9 = var15;
                }
                var9 += 64.0;
            }
        }
        final int var10 = p_180622_4_ & 0xF;
        final int var11 = p_180622_5_ & 0xF;
        final boolean var16 = true;
        IBlockState var17 = Blocks.Ø­Â.¥à();
        IBlockState var18 = this.£Â;
        final int var19 = (int)(p_180622_6_ / 3.0 + 3.0 + p_180622_2_.nextDouble() * 0.25);
        final boolean var20 = Math.cos(p_180622_6_ / 3.0 * 3.141592653589793) > 0.0;
        int var21 = -1;
        boolean var22 = false;
        for (int var23 = 255; var23 >= 0; --var23) {
            if (p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10).Ý().Ó() == Material.HorizonCode_Horizon_È && var23 < (int)var9) {
                p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, Blocks.Ý.¥à());
            }
            if (var23 <= p_180622_2_.nextInt(5)) {
                p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, Blocks.áŒŠÆ.¥à());
            }
            else {
                final IBlockState var24 = p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10);
                if (var24.Ý().Ó() == Material.HorizonCode_Horizon_È) {
                    var21 = -1;
                }
                else if (var24.Ý() == Blocks.Ý) {
                    if (var21 == -1) {
                        var22 = false;
                        if (var19 <= 0) {
                            var17 = null;
                            var18 = Blocks.Ý.¥à();
                        }
                        else if (var23 >= 59 && var23 <= 64) {
                            var17 = Blocks.Ø­Â.¥à();
                            var18 = this.£Â;
                        }
                        if (var23 < 63 && (var17 == null || var17.Ý().Ó() == Material.HorizonCode_Horizon_È)) {
                            var17 = Blocks.ÂµÈ.¥à();
                        }
                        var21 = var19 + Math.max(0, var23 - 63);
                        if (var23 >= 62) {
                            if (this.¥Ï && var23 > 86 + var19 * 2) {
                                if (var20) {
                                    p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.Â));
                                }
                                else {
                                    p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, Blocks.Ø­áŒŠá.¥à());
                                }
                            }
                            else if (var23 > 66 + var19) {
                                IBlockState var25;
                                if (var23 >= 64 && var23 <= 127) {
                                    if (var20) {
                                        var25 = Blocks.Ø­.¥à();
                                    }
                                    else {
                                        var25 = this.HorizonCode_Horizon_È(p_180622_4_, var23, p_180622_5_);
                                    }
                                }
                                else {
                                    var25 = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Â);
                                }
                                p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, var25);
                            }
                            else {
                                p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, this.Ï­Ï­Ï);
                                var22 = true;
                            }
                        }
                        else {
                            p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, var18);
                            if (var18.Ý() == Blocks.Ø­Â) {
                                p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, var18.Ý().¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Â));
                            }
                        }
                    }
                    else if (var21 > 0) {
                        --var21;
                        if (var22) {
                            p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Â));
                        }
                        else {
                            final IBlockState var25 = this.HorizonCode_Horizon_È(p_180622_4_, var23, p_180622_5_);
                            p_180622_3_.HorizonCode_Horizon_È(var11, var23, var10, var25);
                        }
                    }
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final long p_150619_1_) {
        Arrays.fill(this.Ñ¢à = new IBlockState[64], Blocks.Ø­.¥à());
        final Random var3 = new Random(p_150619_1_);
        this.Šà = new NoiseGeneratorPerlin(var3, 1);
        for (int var4 = 0; var4 < 64; ++var4) {
            var4 += var3.nextInt(5) + 1;
            if (var4 < 64) {
                this.Ñ¢à[var4] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Â);
            }
        }
        for (int var4 = var3.nextInt(4) + 2, var5 = 0; var5 < var4; ++var5) {
            for (int var6 = var3.nextInt(3) + 1, var7 = var3.nextInt(64), var8 = 0; var7 + var8 < 64 && var8 < var6; ++var8) {
                this.Ñ¢à[var7 + var8] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Âµá€);
            }
        }
        for (int var5 = var3.nextInt(4) + 2, var6 = 0; var6 < var5; ++var6) {
            for (int var7 = var3.nextInt(3) + 2, var8 = var3.nextInt(64), var9 = 0; var8 + var9 < 64 && var9 < var7; ++var9) {
                this.Ñ¢à[var8 + var9] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.ˆÏ­);
            }
        }
        for (int var6 = var3.nextInt(4) + 2, var7 = 0; var7 < var6; ++var7) {
            for (int var8 = var3.nextInt(3) + 1, var9 = var3.nextInt(64), var10 = 0; var9 + var10 < 64 && var10 < var8; ++var10) {
                this.Ñ¢à[var9 + var10] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.Å);
            }
        }
        int var7 = var3.nextInt(3) + 3;
        int var8 = 0;
        for (int var9 = 0; var9 < var7; ++var9) {
            final byte var11 = 1;
            var8 += var3.nextInt(16) + 4;
            for (int var12 = 0; var8 + var12 < 64 && var12 < var11; ++var12) {
                this.Ñ¢à[var8 + var12] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.HorizonCode_Horizon_È);
                if (var8 + var12 > 1 && var3.nextBoolean()) {
                    this.Ñ¢à[var8 + var12 - 1] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.áŒŠÆ);
                }
                if (var8 + var12 < 63 && var3.nextBoolean()) {
                    this.Ñ¢à[var8 + var12 + 1] = Blocks.Ø­Â.¥à().HorizonCode_Horizon_È(BlockColored.Õ, EnumDyeColor.áŒŠÆ);
                }
            }
        }
    }
    
    private IBlockState HorizonCode_Horizon_È(final int p_180629_1_, final int p_180629_2_, final int p_180629_3_) {
        final int var4 = (int)Math.round(this.Šà.HorizonCode_Horizon_È(p_180629_1_ * 1.0 / 512.0, p_180629_1_ * 1.0 / 512.0) * 2.0);
        return this.Ñ¢à[(p_180629_2_ + var4 + 64) % 64];
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        final boolean var2 = this.ÇªÔ == BiomeGenBase.ÇŽá€.ÇªÔ;
        final BiomeGenMesa var3 = new BiomeGenMesa(p_180277_1_, var2, this.¥Ï);
        if (!var2) {
            var3.HorizonCode_Horizon_È(BiomeGenMesa.à);
            var3.HorizonCode_Horizon_È(String.valueOf(this.£Ï) + " M");
        }
        else {
            var3.HorizonCode_Horizon_È(String.valueOf(this.£Ï) + " (Bryce)");
        }
        var3.HorizonCode_Horizon_È(this.Ø­á, true);
        return var3;
    }
}
