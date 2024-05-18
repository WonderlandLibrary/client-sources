package HORIZON-6-0-SKIDPROTECTION;

public class RangeInt
{
    private int HorizonCode_Horizon_È;
    private int Â;
    
    public RangeInt(final int min, final int max) {
        this.HorizonCode_Horizon_È = -1;
        this.Â = -1;
        this.HorizonCode_Horizon_È = min;
        this.Â = max;
    }
    
    public boolean HorizonCode_Horizon_È(final int val) {
        return (this.HorizonCode_Horizon_È < 0 || val >= this.HorizonCode_Horizon_È) && (this.Â < 0 || val <= this.Â);
    }
    
    @Override
    public String toString() {
        return "min: " + this.HorizonCode_Horizon_È + ", max: " + this.Â;
    }
}
