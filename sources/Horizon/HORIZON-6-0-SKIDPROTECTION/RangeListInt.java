package HORIZON-6-0-SKIDPROTECTION;

public class RangeListInt
{
    private RangeInt[] HorizonCode_Horizon_È;
    
    public RangeListInt() {
        this.HorizonCode_Horizon_È = new RangeInt[0];
    }
    
    public void HorizonCode_Horizon_È(final RangeInt ri) {
        this.HorizonCode_Horizon_È = (RangeInt[])Config.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, ri);
    }
    
    public boolean HorizonCode_Horizon_È(final int val) {
        for (int i = 0; i < this.HorizonCode_Horizon_È.length; ++i) {
            final RangeInt ri = this.HorizonCode_Horizon_È[i];
            if (ri.HorizonCode_Horizon_È(val)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        for (int i = 0; i < this.HorizonCode_Horizon_È.length; ++i) {
            final RangeInt ri = this.HorizonCode_Horizon_È[i];
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(ri.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
