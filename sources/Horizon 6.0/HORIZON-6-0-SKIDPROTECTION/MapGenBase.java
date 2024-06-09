package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class MapGenBase
{
    protected int HorizonCode_Horizon_È;
    protected Random Â;
    protected World Ý;
    private static final String Ø­áŒŠá = "CL_00000394";
    
    public MapGenBase() {
        this.HorizonCode_Horizon_È = 8;
        this.Â = new Random();
    }
    
    public void HorizonCode_Horizon_È(final IChunkProvider p_175792_1_, final World worldIn, final int p_175792_3_, final int p_175792_4_, final ChunkPrimer p_175792_5_) {
        final int var6 = this.HorizonCode_Horizon_È;
        this.Ý = worldIn;
        this.Â.setSeed(worldIn.Æ());
        final long var7 = this.Â.nextLong();
        final long var8 = this.Â.nextLong();
        for (int var9 = p_175792_3_ - var6; var9 <= p_175792_3_ + var6; ++var9) {
            for (int var10 = p_175792_4_ - var6; var10 <= p_175792_4_ + var6; ++var10) {
                final long var11 = var9 * var7;
                final long var12 = var10 * var8;
                this.Â.setSeed(var11 ^ var12 ^ worldIn.Æ());
                this.HorizonCode_Horizon_È(worldIn, var9, var10, p_175792_3_, p_175792_4_, p_175792_5_);
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final World worldIn, final int p_180701_2_, final int p_180701_3_, final int p_180701_4_, final int p_180701_5_, final ChunkPrimer p_180701_6_) {
    }
}
