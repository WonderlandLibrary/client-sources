package HORIZON-6-0-SKIDPROTECTION;

public class RenderMinecartMobSpawner extends RenderMinecart
{
    private static final String Âµá€ = "CL_00001014";
    
    public RenderMinecartMobSpawner(final RenderManager p_i46154_1_) {
        super(p_i46154_1_);
    }
    
    protected void HorizonCode_Horizon_È(final EntityMinecartMobSpawner p_177081_1_, final float p_177081_2_, final IBlockState p_177081_3_) {
        super.HorizonCode_Horizon_È(p_177081_1_, p_177081_2_, p_177081_3_);
        if (p_177081_3_.Ý() == Blocks.ÇªÓ) {
            TileEntityMobSpawnerRenderer.HorizonCode_Horizon_È(p_177081_1_.áŒŠÆ(), p_177081_1_.ŒÏ, p_177081_1_.Çªà¢, p_177081_1_.Ê, p_177081_2_);
        }
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final EntityMinecart p_180560_1_, final float p_180560_2_, final IBlockState p_180560_3_) {
        this.HorizonCode_Horizon_È((EntityMinecartMobSpawner)p_180560_1_, p_180560_2_, p_180560_3_);
    }
}
