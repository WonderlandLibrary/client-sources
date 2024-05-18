package HORIZON-6-0-SKIDPROTECTION;

public class Rotations
{
    protected final float HorizonCode_Horizon_È;
    protected final float Â;
    protected final float Ý;
    private static final String Ø­áŒŠá = "CL_00002316";
    
    public Rotations(final float p_i46009_1_, final float p_i46009_2_, final float p_i46009_3_) {
        this.HorizonCode_Horizon_È = p_i46009_1_;
        this.Â = p_i46009_2_;
        this.Ý = p_i46009_3_;
    }
    
    public Rotations(final NBTTagList p_i46010_1_) {
        this.HorizonCode_Horizon_È = p_i46010_1_.Âµá€(0);
        this.Â = p_i46010_1_.Âµá€(1);
        this.Ý = p_i46010_1_.Âµá€(2);
    }
    
    public NBTTagList HorizonCode_Horizon_È() {
        final NBTTagList var1 = new NBTTagList();
        var1.HorizonCode_Horizon_È(new NBTTagFloat(this.HorizonCode_Horizon_È));
        var1.HorizonCode_Horizon_È(new NBTTagFloat(this.Â));
        var1.HorizonCode_Horizon_È(new NBTTagFloat(this.Ý));
        return var1;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof Rotations)) {
            return false;
        }
        final Rotations var2 = (Rotations)p_equals_1_;
        return this.HorizonCode_Horizon_È == var2.HorizonCode_Horizon_È && this.Â == var2.Â && this.Ý == var2.Ý;
    }
    
    public float Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public float Ý() {
        return this.Â;
    }
    
    public float Ø­áŒŠá() {
        return this.Ý;
    }
}
