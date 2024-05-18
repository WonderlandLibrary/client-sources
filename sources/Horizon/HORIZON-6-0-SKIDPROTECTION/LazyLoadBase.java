package HORIZON-6-0-SKIDPROTECTION;

public abstract class LazyLoadBase
{
    private Object HorizonCode_Horizon_È;
    private boolean Â;
    private static final String Ý = "CL_00002263";
    
    public LazyLoadBase() {
        this.Â = false;
    }
    
    public Object Ý() {
        if (!this.Â) {
            this.Â = true;
            this.HorizonCode_Horizon_È = this.Â();
        }
        return this.HorizonCode_Horizon_È;
    }
    
    protected abstract Object Â();
}
