package HORIZON-6-0-SKIDPROTECTION;

public class EventMovementSpeed extends Event
{
    private double HorizonCode_Horizon_È;
    private double Â;
    private double Ý;
    
    public Event HorizonCode_Horizon_È(final double x, final double y, final double z) {
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
        this.Ý = z;
        return super.Â();
    }
    
    public double Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final double x) {
        this.HorizonCode_Horizon_È = x;
    }
    
    public double Ø­áŒŠá() {
        return this.Â;
    }
    
    public void Â(final double y) {
        this.Â = y;
    }
    
    public double Âµá€() {
        return this.Ý;
    }
    
    public void Ý(final double z) {
        this.Ý = z;
    }
    
    public void Ø­áŒŠá(final double h) {
        this.HorizonCode_Horizon_È *= h;
        this.Ý *= h;
    }
    
    public void Âµá€(final double v) {
        this.Â *= v;
    }
}
