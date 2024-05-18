package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenSwamp extends BiomeGenBase
{
    private static final String Ñ¢à = "CL_00000185";
    
    protected BiomeGenSwamp(final int p_i1988_1_) {
        super(p_i1988_1_);
        this.ˆÏ.Ñ¢á = 2;
        this.ˆÏ.ŒÏ = 1;
        this.ˆÏ.Ê = 1;
        this.ˆÏ.ÇŽÉ = 8;
        this.ˆÏ.ˆá = 10;
        this.ˆÏ.á€ = 1;
        this.ˆÏ.ŠÄ = 4;
        this.ˆÏ.áƒ = 0;
        this.ˆÏ.É = 0;
        this.ˆÏ.Çªà¢ = 5;
        this.Ðƒá = 14745518;
        this.áˆºÇŽØ.add(new Â(EntitySlime.class, 1, 1, 1));
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return this.ÇŽá;
    }
    
    @Override
    public int Â(final BlockPos p_180627_1_) {
        final double var2 = BiomeGenSwamp.ˆáƒ.HorizonCode_Horizon_È(p_180627_1_.HorizonCode_Horizon_È() * 0.0225, p_180627_1_.Ý() * 0.0225);
        return (var2 < -0.1) ? 5011004 : 6975545;
    }
    
    @Override
    public int Ý(final BlockPos p_180625_1_) {
        return 6975545;
    }
    
    @Override
    public BlockFlower.Â HorizonCode_Horizon_È(final Random p_180623_1_, final BlockPos p_180623_2_) {
        return BlockFlower.Â.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        final double var8 = BiomeGenSwamp.ˆáƒ.HorizonCode_Horizon_È(p_180622_4_ * 0.25, p_180622_5_ * 0.25);
        if (var8 > 0.0) {
            final int var9 = p_180622_4_ & 0xF;
            final int var10 = p_180622_5_ & 0xF;
            int var11 = 255;
            while (var11 >= 0) {
                if (p_180622_3_.HorizonCode_Horizon_È(var10, var11, var9).Ý().Ó() != Material.HorizonCode_Horizon_È) {
                    if (var11 != 62 || p_180622_3_.HorizonCode_Horizon_È(var10, var11, var9).Ý() == Blocks.ÂµÈ) {
                        break;
                    }
                    p_180622_3_.HorizonCode_Horizon_È(var10, var11, var9, Blocks.ÂµÈ.¥à());
                    if (var8 < 0.12) {
                        p_180622_3_.HorizonCode_Horizon_È(var10, var11 + 1, var9, Blocks.Œá.¥à());
                        break;
                    }
                    break;
                }
                else {
                    --var11;
                }
            }
        }
        this.Â(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
}
