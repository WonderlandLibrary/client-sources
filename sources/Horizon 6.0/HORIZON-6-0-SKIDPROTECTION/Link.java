package HORIZON-6-0-SKIDPROTECTION;

public class Link
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private Space Ý;
    
    public Link(final float px, final float py, final Space target) {
        this.HorizonCode_Horizon_È = px;
        this.Â = py;
        this.Ý = target;
    }
    
    public float HorizonCode_Horizon_È(final float tx, final float ty) {
        final float dx = tx - this.HorizonCode_Horizon_È;
        final float dy = ty - this.Â;
        return dx * dx + dy * dy;
    }
    
    public float HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public float Â() {
        return this.Â;
    }
    
    public Space Ý() {
        return this.Ý;
    }
}
