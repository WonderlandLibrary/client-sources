package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenSnow extends BiomeGenBase
{
    private boolean Ñ¢à;
    private WorldGenIceSpike ÇªØ­;
    private WorldGenIcePath £áŒŠá;
    private static final String áˆº = "CL_00000174";
    
    public BiomeGenSnow(final int p_i45378_1_, final boolean p_i45378_2_) {
        super(p_i45378_1_);
        this.ÇªØ­ = new WorldGenIceSpike();
        this.£áŒŠá = new WorldGenIcePath(4);
        this.Ñ¢à = p_i45378_2_;
        if (p_i45378_2_) {
            this.Ï­Ï­Ï = Blocks.ˆà¢.¥à();
        }
        this.ÇªÂµÕ.clear();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        if (this.Ñ¢à) {
            for (int var4 = 0; var4 < 3; ++var4) {
                final int var5 = p_180624_2_.nextInt(16) + 8;
                final int var6 = p_180624_2_.nextInt(16) + 8;
                this.ÇªØ­.HorizonCode_Horizon_È(worldIn, p_180624_2_, worldIn.£á(p_180624_3_.Â(var5, 0, var6)));
            }
            for (int var4 = 0; var4 < 2; ++var4) {
                final int var5 = p_180624_2_.nextInt(16) + 8;
                final int var6 = p_180624_2_.nextInt(16) + 8;
                this.£áŒŠá.HorizonCode_Horizon_È(worldIn, p_180624_2_, worldIn.£á(p_180624_3_.Â(var5, 0, var6)));
            }
        }
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return new WorldGenTaiga2(false);
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        final BiomeGenBase var2 = new BiomeGenSnow(p_180277_1_, true).HorizonCode_Horizon_È(13828095, true).HorizonCode_Horizon_È(String.valueOf(this.£Ï) + " Spikes").Ý().HorizonCode_Horizon_È(0.0f, 0.5f).HorizonCode_Horizon_È(new HorizonCode_Horizon_È(this.ˆÐƒØ­à + 0.1f, this.£Õ + 0.1f));
        var2.ˆÐƒØ­à = this.ˆÐƒØ­à + 0.3f;
        var2.£Õ = this.£Õ + 0.4f;
        return var2;
    }
}
