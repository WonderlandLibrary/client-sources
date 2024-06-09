package HORIZON-6-0-SKIDPROTECTION;

public class EventUpdate extends Event
{
    public float HorizonCode_Horizon_È;
    private float Ý;
    public float Â;
    private float Ø­áŒŠá;
    private double Âµá€;
    private double Ó;
    private double à;
    private double Ø;
    private double áŒŠÆ;
    private double áˆºÑ¢Õ;
    private HorizonCode_Horizon_È ÂµÈ;
    private boolean á;
    private boolean ˆÏ­;
    
    public EventUpdate(final double x, final double y, final double z, final double oldX, final double oldY, final double oldZ, final float yaw, final float pitch, final boolean onGround) {
        this.Âµá€ = x;
        this.à = y;
        this.áŒŠÆ = z;
        this.Ó = oldX;
        this.Ø = oldY;
        this.áˆºÑ¢Õ = oldZ;
        this.HorizonCode_Horizon_È = yaw;
        this.Â = pitch;
        this.á = onGround;
    }
    
    public Event HorizonCode_Horizon_È(final HorizonCode_Horizon_È type) {
        this.ÂµÈ = type;
        return super.Â();
    }
    
    public HorizonCode_Horizon_È Ý() {
        return this.ÂµÈ;
    }
    
    public void Â(final HorizonCode_Horizon_È type) {
        this.ÂµÈ = type;
    }
    
    public float Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final float yaw) {
        this.HorizonCode_Horizon_È = yaw;
    }
    
    public float Âµá€() {
        return this.Â;
    }
    
    public void Â(final float pitch) {
        this.Â = pitch;
    }
    
    public double Ó() {
        return this.Âµá€;
    }
    
    public void HorizonCode_Horizon_È(final double x) {
        this.Âµá€ = x;
    }
    
    public double à() {
        return this.à;
    }
    
    public void Â(final double y) {
        this.à = y;
    }
    
    public double Ø() {
        return this.áŒŠÆ;
    }
    
    public void Ý(final double z) {
        this.áŒŠÆ = z;
    }
    
    public boolean áŒŠÆ() {
        return this.á;
    }
    
    public void Â(final boolean onGround) {
        this.á = onGround;
    }
    
    public final float áˆºÑ¢Õ() {
        return this.Ý;
    }
    
    public final void Ý(final float oldYaw) {
        this.Ý = oldYaw;
    }
    
    public final float ÂµÈ() {
        return this.Ø­áŒŠá;
    }
    
    public final void Ø­áŒŠá(final float oldPitch) {
        this.Ø­áŒŠá = oldPitch;
    }
    
    public final double á() {
        return this.Ó;
    }
    
    public final void Ø­áŒŠá(final double oldX) {
        this.Ó = oldX;
    }
    
    public final double ˆÏ­() {
        return this.Ø;
    }
    
    public final void Âµá€(final double oldY) {
        this.Ø = oldY;
    }
    
    public final double £á() {
        return this.áˆºÑ¢Õ;
    }
    
    public final void Ó(final double oldZ) {
        this.áˆºÑ¢Õ = oldZ;
    }
    
    public final boolean Å() {
        return this.ˆÏ­;
    }
    
    public final void Ý(final boolean oldOnGround) {
        this.ˆÏ­ = oldOnGround;
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("PRE", 0, "PRE", 0), 
        Â("MID", 1, "MID", 1), 
        Ý("POST", 2, "POST", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        
        static {
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String var1, final int var2) {
        }
    }
}
