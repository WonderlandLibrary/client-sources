package HORIZON-6-0-SKIDPROTECTION;

public class ModelChest extends ModelBase
{
    public ModelRenderer HorizonCode_Horizon_È;
    public ModelRenderer Â;
    public ModelRenderer Ý;
    private static final String Ø­áŒŠá = "CL_00000834";
    
    public ModelChest() {
        (this.HorizonCode_Horizon_È = new ModelRenderer(this, 0, 0).Â(64, 64)).HorizonCode_Horizon_È(0.0f, -5.0f, -14.0f, 14, 5, 14, 0.0f);
        this.HorizonCode_Horizon_È.Ý = 1.0f;
        this.HorizonCode_Horizon_È.Ø­áŒŠá = 7.0f;
        this.HorizonCode_Horizon_È.Âµá€ = 15.0f;
        (this.Ý = new ModelRenderer(this, 0, 0).Â(64, 64)).HorizonCode_Horizon_È(-1.0f, -2.0f, -15.0f, 2, 4, 1, 0.0f);
        this.Ý.Ý = 8.0f;
        this.Ý.Ø­áŒŠá = 7.0f;
        this.Ý.Âµá€ = 15.0f;
        (this.Â = new ModelRenderer(this, 0, 19).Â(64, 64)).HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 14, 10, 14, 0.0f);
        this.Â.Ý = 1.0f;
        this.Â.Ø­áŒŠá = 6.0f;
        this.Â.Âµá€ = 1.0f;
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ý.Ó = this.HorizonCode_Horizon_È.Ó;
        this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(0.0625f);
        this.Ý.HorizonCode_Horizon_È(0.0625f);
        this.Â.HorizonCode_Horizon_È(0.0625f);
    }
}
