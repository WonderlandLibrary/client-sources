package HORIZON-6-0-SKIDPROTECTION;

public class Location
{
    public double HorizonCode_Horizon_È;
    public double Â;
    public double Ý;
    
    public Location(final double x, final double y, final double z) {
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
        this.Ý = z;
    }
    
    public void HorizonCode_Horizon_È(final double xoff, final double yoff, final double zoff) {
        this.HorizonCode_Horizon_È += xoff;
        this.Â += yoff;
        this.Ý += zoff;
    }
    
    public void HorizonCode_Horizon_È(final float rot, final boolean x, final boolean y, final boolean z) {
        final double radians = Math.toRadians(rot);
        if (x) {
            this.Â = this.Â * Math.cos(radians) - this.Ý * Math.sin(radians);
            this.Ý = this.Â * Math.sin(radians) + this.Ý * Math.cos(radians);
        }
        else if (y) {
            this.Ý = this.Ý * Math.cos(radians) - this.HorizonCode_Horizon_È * Math.sin(radians);
            this.HorizonCode_Horizon_È = this.Ý * Math.sin(radians) + this.HorizonCode_Horizon_È * Math.cos(radians);
        }
        else if (z) {
            this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È * Math.cos(radians) - this.Â * Math.sin(radians);
            this.Â = this.HorizonCode_Horizon_È * Math.sin(radians) + this.Â * Math.cos(radians);
        }
    }
}
