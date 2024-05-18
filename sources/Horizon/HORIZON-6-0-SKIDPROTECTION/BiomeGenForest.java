package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenForest extends BiomeGenBase
{
    private int áˆº;
    protected static final WorldGenForest Ñ¢à;
    protected static final WorldGenForest ÇªØ­;
    protected static final WorldGenCanopyTree £áŒŠá;
    private static final String Šà = "CL_00000170";
    
    static {
        Ñ¢à = new WorldGenForest(false, true);
        ÇªØ­ = new WorldGenForest(false, false);
        £áŒŠá = new WorldGenCanopyTree(false);
    }
    
    public BiomeGenForest(final int p_i45377_1_, final int p_i45377_2_) {
        super(p_i45377_1_);
        this.áˆº = p_i45377_2_;
        this.ˆÏ.Ñ¢á = 10;
        this.ˆÏ.Çªà¢ = 2;
        if (this.áˆº == 1) {
            this.ˆÏ.Ñ¢á = 6;
            this.ˆÏ.ŒÏ = 100;
            this.ˆÏ.Çªà¢ = 1;
        }
        this.HorizonCode_Horizon_È(5159473);
        this.HorizonCode_Horizon_È(0.7f, 0.8f);
        if (this.áˆº == 2) {
            this.ˆÉ = 353825;
            this.Ø­á = 3175492;
            this.HorizonCode_Horizon_È(0.6f, 0.6f);
        }
        if (this.áˆº == 0) {
            this.ÇªÂµÕ.add(new Â(EntityWolf.class, 5, 4, 4));
        }
        if (this.áˆº == 3) {
            this.ˆÏ.Ñ¢á = -999;
        }
    }
    
    @Override
    protected BiomeGenBase HorizonCode_Horizon_È(final int p_150557_1_, final boolean p_150557_2_) {
        if (this.áˆº == 2) {
            this.ˆÉ = 353825;
            this.Ø­á = p_150557_1_;
            if (p_150557_2_) {
                this.ˆÉ = (this.ˆÉ & 0xFEFEFE) >> 1;
            }
            return this;
        }
        return super.HorizonCode_Horizon_È(p_150557_1_, p_150557_2_);
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return (this.áˆº == 3 && p_150567_1_.nextInt(3) > 0) ? BiomeGenForest.£áŒŠá : ((this.áˆº != 2 && p_150567_1_.nextInt(5) != 0) ? this.Û : BiomeGenForest.ÇªØ­);
    }
    
    @Override
    public BlockFlower.Â HorizonCode_Horizon_È(final Random p_180623_1_, final BlockPos p_180623_2_) {
        if (this.áˆº == 1) {
            final double var3 = MathHelper.HorizonCode_Horizon_È((1.0 + BiomeGenForest.ˆáƒ.HorizonCode_Horizon_È(p_180623_2_.HorizonCode_Horizon_È() / 48.0, p_180623_2_.Ý() / 48.0)) / 2.0, 0.0, 0.9999);
            final BlockFlower.Â var4 = BlockFlower.Â.values()[(int)(var3 * BlockFlower.Â.values().length)];
            return (var4 == BlockFlower.Â.Ý) ? BlockFlower.Â.Â : var4;
        }
        return super.HorizonCode_Horizon_È(p_180623_1_, p_180623_2_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        if (this.áˆº == 3) {
            for (int var4 = 0; var4 < 4; ++var4) {
                for (int var5 = 0; var5 < 4; ++var5) {
                    final int var6 = var4 * 4 + 1 + 8 + p_180624_2_.nextInt(3);
                    final int var7 = var5 * 4 + 1 + 8 + p_180624_2_.nextInt(3);
                    final BlockPos var8 = worldIn.£á(p_180624_3_.Â(var6, 0, var7));
                    if (p_180624_2_.nextInt(20) == 0) {
                        final WorldGenBigMushroom var9 = new WorldGenBigMushroom();
                        var9.HorizonCode_Horizon_È(worldIn, p_180624_2_, var8);
                    }
                    else {
                        final WorldGenAbstractTree var10 = this.HorizonCode_Horizon_È(p_180624_2_);
                        var10.Âµá€();
                        if (var10.HorizonCode_Horizon_È(worldIn, p_180624_2_, var8)) {
                            var10.Â(worldIn, p_180624_2_, var8);
                        }
                    }
                }
            }
        }
        int var4 = p_180624_2_.nextInt(5) - 3;
        if (this.áˆº == 1) {
            var4 += 2;
        }
        for (int var5 = 0; var5 < var4; ++var5) {
            final int var6 = p_180624_2_.nextInt(3);
            if (var6 == 0) {
                BiomeGenForest.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.Â);
            }
            else if (var6 == 1) {
                BiomeGenForest.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.Âµá€);
            }
            else if (var6 == 2) {
                BiomeGenForest.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.Ó);
            }
            for (int var7 = 0; var7 < 5; ++var7) {
                final int var11 = p_180624_2_.nextInt(16) + 8;
                final int var12 = p_180624_2_.nextInt(16) + 8;
                final int var13 = p_180624_2_.nextInt(worldIn.£á(p_180624_3_.Â(var11, 0, var12)).Â() + 32);
                if (BiomeGenForest.Œ.HorizonCode_Horizon_È(worldIn, p_180624_2_, new BlockPos(p_180624_3_.HorizonCode_Horizon_È() + var11, var13, p_180624_3_.Ý() + var12))) {
                    break;
                }
            }
        }
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
    }
    
    @Override
    public int Â(final BlockPos p_180627_1_) {
        final int var2 = super.Â(p_180627_1_);
        return (this.áˆº == 3) ? ((var2 & 0xFEFEFE) + 2634762 >> 1) : var2;
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        if (this.ÇªÔ == BiomeGenBase.Ø­à.ÇªÔ) {
            final BiomeGenForest var2 = new BiomeGenForest(p_180277_1_, 1);
            var2.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(this.ˆÐƒØ­à, this.£Õ + 0.2f));
            var2.HorizonCode_Horizon_È("Flower Forest");
            var2.HorizonCode_Horizon_È(6976549, true);
            var2.HorizonCode_Horizon_È(8233509);
            return var2;
        }
        return (this.ÇªÔ != BiomeGenBase.È.ÇªÔ && this.ÇªÔ != BiomeGenBase.áŠ.ÇªÔ) ? new BiomeGenMutated(p_180277_1_, this) {
            private static final String £áŒŠá = "CL_00000172";
            
            @Override
            public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
                this.ÇªØ­.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
            }
        } : new BiomeGenMutated(p_180277_1_, this) {
            private static final String £áŒŠá = "CL_00001861";
            
            @Override
            public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
                return p_150567_1_.nextBoolean() ? BiomeGenForest.Ñ¢à : BiomeGenForest.ÇªØ­;
            }
        };
    }
}
