package HORIZON-6-0-SKIDPROTECTION;

public final class TimeHelper
{
    private long HorizonCode_Horizon_È;
    
    public long HorizonCode_Horizon_È() {
        return System.nanoTime() / 1000000L;
    }
    
    public long Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public boolean HorizonCode_Horizon_È(final float f) {
        return this.HorizonCode_Horizon_È() - this.HorizonCode_Horizon_È >= f;
    }
    
    public void Ý() {
        this.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È(final long currentMS) {
        this.HorizonCode_Horizon_È = currentMS;
    }
    
    public boolean Â(final long delay) {
        return System.currentTimeMillis() - this.HorizonCode_Horizon_È >= delay;
    }
    
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È = System.currentTimeMillis();
    }
    
    public int HorizonCode_Horizon_È(final int perSecond) {
        return 1000 / perSecond;
    }
}
