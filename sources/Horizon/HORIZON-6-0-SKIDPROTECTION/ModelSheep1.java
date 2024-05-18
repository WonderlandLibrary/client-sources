package HORIZON-6-0-SKIDPROTECTION;

public class ModelSheep1 extends ModelQuadruped
{
    private float Å;
    private static final String £à = "CL_00000852";
    
    public ModelSheep1() {
        super(12, 0.0f);
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0)).HorizonCode_Horizon_È(-3.0f, -4.0f, -4.0f, 6, 6, 6, 0.6f);
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0f, 6.0f, -8.0f);
        (this.Â = new ModelRenderer(this, 28, 8)).HorizonCode_Horizon_È(-4.0f, -10.0f, -7.0f, 8, 16, 6, 1.75f);
        this.Â.HorizonCode_Horizon_È(0.0f, 5.0f, 2.0f);
        final float var1 = 0.5f;
        (this.Ý = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.Ý.HorizonCode_Horizon_È(-3.0f, 12.0f, 7.0f);
        (this.Ø­áŒŠá = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.Ø­áŒŠá.HorizonCode_Horizon_È(3.0f, 12.0f, 7.0f);
        (this.ÂµÈ = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.ÂµÈ.HorizonCode_Horizon_È(-3.0f, 12.0f, -5.0f);
        (this.á = new ModelRenderer(this, 0, 16)).HorizonCode_Horizon_È(-2.0f, 0.0f, -2.0f, 4, 6, 4, var1);
        this.á.HorizonCode_Horizon_È(3.0f, 12.0f, -5.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        super.HorizonCode_Horizon_È(p_78086_1_, p_78086_2_, p_78086_3_, p_78086_4_);
        this.HorizonCode_Horizon_È.Ø­áŒŠá = 6.0f + ((EntitySheep)p_78086_1_).£á(p_78086_4_) * 9.0f;
        this.Å = ((EntitySheep)p_78086_1_).Å(p_78086_4_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        super.HorizonCode_Horizon_È(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
        this.HorizonCode_Horizon_È.Ó = this.Å;
    }
}
