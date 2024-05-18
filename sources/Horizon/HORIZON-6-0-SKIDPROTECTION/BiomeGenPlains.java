package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenPlains extends BiomeGenBase
{
    protected boolean Ñ¢à;
    private static final String ÇªØ­ = "CL_00000180";
    
    protected BiomeGenPlains(final int p_i1986_1_) {
        super(p_i1986_1_);
        this.HorizonCode_Horizon_È(0.8f, 0.4f);
        this.HorizonCode_Horizon_È(BiomeGenPlains.Âµá€);
        this.ÇªÂµÕ.add(new Â(EntityHorse.class, 5, 2, 6));
        this.ˆÏ.Ñ¢á = -999;
        this.ˆÏ.ŒÏ = 4;
        this.ˆÏ.Çªà¢ = 10;
    }
    
    @Override
    public BlockFlower.Â HorizonCode_Horizon_È(final Random p_180623_1_, final BlockPos p_180623_2_) {
        final double var3 = BiomeGenPlains.ˆáƒ.HorizonCode_Horizon_È(p_180623_2_.HorizonCode_Horizon_È() / 200.0, p_180623_2_.Ý() / 200.0);
        if (var3 < -0.8) {
            final int var4 = p_180623_1_.nextInt(4);
            switch (var4) {
                case 0: {
                    return BlockFlower.Â.à;
                }
                case 1: {
                    return BlockFlower.Â.Ó;
                }
                case 2: {
                    return BlockFlower.Â.áŒŠÆ;
                }
                default: {
                    return BlockFlower.Â.Ø;
                }
            }
        }
        else {
            if (p_180623_1_.nextInt(3) > 0) {
                final int var4 = p_180623_1_.nextInt(3);
                return (var4 == 0) ? BlockFlower.Â.Â : ((var4 == 1) ? BlockFlower.Â.Âµá€ : BlockFlower.Â.áˆºÑ¢Õ);
            }
            return BlockFlower.Â.HorizonCode_Horizon_È;
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        final double var4 = BiomeGenPlains.ˆáƒ.HorizonCode_Horizon_È((p_180624_3_.HorizonCode_Horizon_È() + 8) / 200.0, (p_180624_3_.Ý() + 8) / 200.0);
        if (var4 < -0.8) {
            this.ˆÏ.ŒÏ = 15;
            this.ˆÏ.Çªà¢ = 5;
        }
        else {
            this.ˆÏ.ŒÏ = 4;
            this.ˆÏ.Çªà¢ = 10;
            BiomeGenPlains.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.Ý);
            for (int var5 = 0; var5 < 7; ++var5) {
                final int var6 = p_180624_2_.nextInt(16) + 8;
                final int var7 = p_180624_2_.nextInt(16) + 8;
                final int var8 = p_180624_2_.nextInt(worldIn.£á(p_180624_3_.Â(var6, 0, var7)).Â() + 32);
                BiomeGenPlains.Œ.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var6, var8, var7));
            }
        }
        if (this.Ñ¢à) {
            BiomeGenPlains.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.HorizonCode_Horizon_È);
            for (int var5 = 0; var5 < 10; ++var5) {
                final int var6 = p_180624_2_.nextInt(16) + 8;
                final int var7 = p_180624_2_.nextInt(16) + 8;
                final int var8 = p_180624_2_.nextInt(worldIn.£á(p_180624_3_.Â(var6, 0, var7)).Â() + 32);
                BiomeGenPlains.Œ.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var6, var8, var7));
            }
        }
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        final BiomeGenPlains var2 = new BiomeGenPlains(p_180277_1_);
        var2.HorizonCode_Horizon_È("Sunflower Plains");
        var2.Ñ¢à = true;
        var2.Â(9286496);
        var2.ˆÉ = 14273354;
        return var2;
    }
}
