package HORIZON-6-0-SKIDPROTECTION;

public class Property<T> implements Labeled
{
    private String Â;
    protected T HorizonCode_Horizon_È;
    
    protected Property(final String label, final T value) {
        this.Â = label;
        this.HorizonCode_Horizon_È = value;
    }
    
    @Override
    public final String HorizonCode_Horizon_È() {
        return this.Â;
    }
    
    public void HorizonCode_Horizon_È(final T value) {
        this.HorizonCode_Horizon_È = value;
    }
    
    public T Â() {
        return this.HorizonCode_Horizon_È;
    }
}
