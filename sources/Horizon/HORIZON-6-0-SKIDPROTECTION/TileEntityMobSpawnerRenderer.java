package HORIZON-6-0-SKIDPROTECTION;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer
{
    private static final String HorizonCode_Horizon_È = "CL_00000968";
    
    public void HorizonCode_Horizon_È(final TileEntityMobSpawner p_180539_1_, final double p_180539_2_, final double p_180539_4_, final double p_180539_6_, final float p_180539_8_, final int p_180539_9_) {
        GlStateManager.Çªà¢();
        GlStateManager.Â((float)p_180539_2_ + 0.5f, (float)p_180539_4_, (float)p_180539_6_ + 0.5f);
        HorizonCode_Horizon_È(p_180539_1_.Â(), p_180539_2_, p_180539_4_, p_180539_6_, p_180539_8_);
        GlStateManager.Ê();
    }
    
    public static void HorizonCode_Horizon_È(final MobSpawnerBaseLogic p_147517_0_, final double p_147517_1_, final double p_147517_3_, final double p_147517_5_, final float p_147517_7_) {
        final Entity var8 = p_147517_0_.HorizonCode_Horizon_È(p_147517_0_.HorizonCode_Horizon_È());
        if (var8 != null) {
            final float var9 = 0.4375f;
            GlStateManager.Â(0.0f, 0.4f, 0.0f);
            GlStateManager.Â((float)(p_147517_0_.Âµá€() + (p_147517_0_.Ø­áŒŠá() - p_147517_0_.Âµá€()) * p_147517_7_) * 10.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.Â(-30.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.Â(0.0f, -0.4f, 0.0f);
            GlStateManager.HorizonCode_Horizon_È(var9, var9, var9);
            var8.Â(p_147517_1_, p_147517_3_, p_147517_5_, 0.0f, 0.0f);
            Minecraft.áŒŠà().ÇªÓ().HorizonCode_Horizon_È(var8, 0.0, 0.0, 0.0, 0.0f, p_147517_7_);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final TileEntity p_180535_1_, final double p_180535_2_, final double p_180535_4_, final double p_180535_6_, final float p_180535_8_, final int p_180535_9_) {
        this.HorizonCode_Horizon_È((TileEntityMobSpawner)p_180535_1_, p_180535_2_, p_180535_4_, p_180535_6_, p_180535_8_, p_180535_9_);
    }
}
