package HORIZON-6-0-SKIDPROTECTION;

public class HurtCam extends EventUpdate implements Cancellable
{
    boolean Ý;
    
    public HurtCam(final double x, final double y, final double z, final double oldX, final double oldY, final double oldZ, final float yaw, final float pitch, final boolean onGround) {
        super(x, y, z, oldX, oldY, oldZ, yaw, pitch, onGround);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final boolean shouldCancel) {
        this.Ý = shouldCancel;
    }
}
