package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenSavanna extends BiomeGenBase
{
    private static final WorldGenSavannaTree Ñ¢à;
    private static final String ÇªØ­ = "CL_00000182";
    
    static {
        Ñ¢à = new WorldGenSavannaTree(false);
    }
    
    protected BiomeGenSavanna(final int p_i45383_1_) {
        super(p_i45383_1_);
        this.ÇªÂµÕ.add(new Â(EntityHorse.class, 1, 2, 6));
        this.ˆÏ.Ñ¢á = 1;
        this.ˆÏ.ŒÏ = 4;
        this.ˆÏ.Çªà¢ = 20;
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(5) > 0) ? BiomeGenSavanna.Ñ¢à : this.Û;
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        final HorizonCode_Horizon_È var2 = new HorizonCode_Horizon_È(p_180277_1_, this);
        var2.Ï­Ô = (this.Ï­Ô + 1.0f) * 0.5f;
        var2.ˆÐƒØ­à = this.ˆÐƒØ­à * 0.5f + 0.3f;
        var2.£Õ = this.£Õ * 0.5f + 1.2f;
        return var2;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        BiomeGenSavanna.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.Ý);
        for (int var4 = 0; var4 < 7; ++var4) {
            final int var5 = p_180624_2_.nextInt(16) + 8;
            final int var6 = p_180624_2_.nextInt(16) + 8;
            final int var7 = p_180624_2_.nextInt(worldIn.£á(p_180624_3_.Â(var5, 0, var6)).Â() + 32);
            BiomeGenSavanna.Œ.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var5, var7, var6));
        }
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
    }
    
    public static class HorizonCode_Horizon_È extends BiomeGenMutated
    {
        private static final String Ñ¢à = "CL_00000183";
        
        public HorizonCode_Horizon_È(final int p_i45382_1_, final BiomeGenBase p_i45382_2_) {
            super(p_i45382_1_, p_i45382_2_);
            this.ˆÏ.Ñ¢á = 2;
            this.ˆÏ.ŒÏ = 2;
            this.ˆÏ.Çªà¢ = 5;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
            this.Ï­Ï­Ï = Blocks.Ø­áŒŠá.¥à();
            this.£Â = Blocks.Âµá€.¥à();
            if (p_180622_6_ > 1.75) {
                this.Ï­Ï­Ï = Blocks.Ý.¥à();
                this.£Â = Blocks.Ý.¥à();
            }
            else if (p_180622_6_ > -0.5) {
                this.Ï­Ï­Ï = Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.Â);
            }
            this.Â(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
        }
        
        @Override
        public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
            this.ˆÏ.HorizonCode_Horizon_È(worldIn, p_180624_2_, this, p_180624_3_);
        }
    }
}
