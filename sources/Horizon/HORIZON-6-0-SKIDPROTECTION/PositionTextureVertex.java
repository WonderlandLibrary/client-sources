package HORIZON-6-0-SKIDPROTECTION;

public class PositionTextureVertex
{
    public Vec3 HorizonCode_Horizon_È;
    public float Â;
    public float Ý;
    private static final String Ø­áŒŠá = "CL_00000862";
    
    public PositionTextureVertex(final float p_i1158_1_, final float p_i1158_2_, final float p_i1158_3_, final float p_i1158_4_, final float p_i1158_5_) {
        this(new Vec3(p_i1158_1_, p_i1158_2_, p_i1158_3_), p_i1158_4_, p_i1158_5_);
    }
    
    public PositionTextureVertex HorizonCode_Horizon_È(final float p_78240_1_, final float p_78240_2_) {
        return new PositionTextureVertex(this, p_78240_1_, p_78240_2_);
    }
    
    public PositionTextureVertex(final PositionTextureVertex p_i46363_1_, final float p_i46363_2_, final float p_i46363_3_) {
        this.HorizonCode_Horizon_È = p_i46363_1_.HorizonCode_Horizon_È;
        this.Â = p_i46363_2_;
        this.Ý = p_i46363_3_;
    }
    
    public PositionTextureVertex(final Vec3 p_i1160_1_, final float p_i1160_2_, final float p_i1160_3_) {
        this.HorizonCode_Horizon_È = p_i1160_1_;
        this.Â = p_i1160_2_;
        this.Ý = p_i1160_3_;
    }
}
