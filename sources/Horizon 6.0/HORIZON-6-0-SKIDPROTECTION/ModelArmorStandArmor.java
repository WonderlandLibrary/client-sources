package HORIZON-6-0-SKIDPROTECTION;

public class ModelArmorStandArmor extends ModelBiped
{
    private static final String HorizonCode_Horizon_È = "CL_00002632";
    
    public ModelArmorStandArmor() {
        this(0.0f);
    }
    
    public ModelArmorStandArmor(final float p_i46307_1_) {
        this(p_i46307_1_, 64, 32);
    }
    
    protected ModelArmorStandArmor(final float p_i46308_1_, final int p_i46308_2_, final int p_i46308_3_) {
        super(p_i46308_1_, 0.0f, p_i46308_2_, p_i46308_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float p_78087_1_, final float p_78087_2_, final float p_78087_3_, final float p_78087_4_, final float p_78087_5_, final float p_78087_6_, final Entity p_78087_7_) {
        if (p_78087_7_ instanceof EntityArmorStand) {
            final EntityArmorStand var8 = (EntityArmorStand)p_78087_7_;
            this.ÂµÈ.Ó = 0.017453292f * var8.Ø­à().Â();
            this.ÂµÈ.à = 0.017453292f * var8.Ø­à().Ý();
            this.ÂµÈ.Ø = 0.017453292f * var8.Ø­à().Ø­áŒŠá();
            this.ÂµÈ.HorizonCode_Horizon_È(0.0f, 1.0f, 0.0f);
            this.ˆÏ­.Ó = 0.017453292f * var8.µÕ().Â();
            this.ˆÏ­.à = 0.017453292f * var8.µÕ().Ý();
            this.ˆÏ­.Ø = 0.017453292f * var8.µÕ().Ø­áŒŠá();
            this.Å.Ó = 0.017453292f * var8.Æ().Â();
            this.Å.à = 0.017453292f * var8.Æ().Ý();
            this.Å.Ø = 0.017453292f * var8.Æ().Ø­áŒŠá();
            this.£á.Ó = 0.017453292f * var8.Šáƒ().Â();
            this.£á.à = 0.017453292f * var8.Šáƒ().Ý();
            this.£á.Ø = 0.017453292f * var8.Šáƒ().Ø­áŒŠá();
            this.µà.Ó = 0.017453292f * var8.Ï­Ðƒà().Â();
            this.µà.à = 0.017453292f * var8.Ï­Ðƒà().Ý();
            this.µà.Ø = 0.017453292f * var8.Ï­Ðƒà().Ø­áŒŠá();
            this.µà.HorizonCode_Horizon_È(1.9f, 11.0f, 0.0f);
            this.£à.Ó = 0.017453292f * var8.Ñ¢á().Â();
            this.£à.à = 0.017453292f * var8.Ñ¢á().Ý();
            this.£à.Ø = 0.017453292f * var8.Ñ¢á().Ø­áŒŠá();
            this.£à.HorizonCode_Horizon_È(-1.9f, 11.0f, 0.0f);
            ModelBase.HorizonCode_Horizon_È(this.ÂµÈ, this.á);
        }
    }
}
