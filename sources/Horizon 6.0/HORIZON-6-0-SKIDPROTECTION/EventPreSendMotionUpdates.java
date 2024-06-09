package HORIZON-6-0-SKIDPROTECTION;

public final class EventPreSendMotionUpdates extends Event implements Cancellable
{
    private boolean HorizonCode_Horizon_È;
    private float Â;
    private float Ý;
    
    public EventPreSendMotionUpdates(final float yaw, final float pitch) {
        this.Â = yaw;
        this.Ý = pitch;
    }
    
    public float Ý() {
        return this.Ý;
    }
    
    public float Ø­áŒŠá() {
        return this.Â;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean cancel) {
        this.HorizonCode_Horizon_È = cancel;
    }
    
    public void HorizonCode_Horizon_È(final float pitch) {
        this.Ý = pitch;
    }
    
    public void Â(final float yaw) {
        this.Â = yaw;
    }
}
