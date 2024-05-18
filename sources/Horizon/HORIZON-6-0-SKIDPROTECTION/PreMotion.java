package HORIZON-6-0-SKIDPROTECTION;

public class PreMotion extends Event implements Cancellable
{
    private boolean Ý;
    float HorizonCode_Horizon_È;
    float Â;
    
    public PreMotion(final float rotationYaw, final float rotationPitch) {
        this.Â = rotationPitch;
        this.HorizonCode_Horizon_È = rotationYaw;
    }
    
    public float Ý() {
        return this.Â;
    }
    
    public float Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean cancel) {
        this.Ý = cancel;
    }
    
    public void HorizonCode_Horizon_È(final float pitch) {
        this.Â = pitch;
    }
    
    public void Â(final float yaw) {
        this.HorizonCode_Horizon_È = yaw;
    }
}
