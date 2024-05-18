package HORIZON-6-0-SKIDPROTECTION;

public class IntegerProperty extends Property<Integer>
{
    private int Â;
    private int Ý;
    
    public IntegerProperty(final String label, final Integer value, final int min, final int max) {
        super(label, value);
        this.Â = min;
        this.Ý = max;
    }
    
    @Override
    public final void HorizonCode_Horizon_È(Integer value) {
        if (value > this.Ý) {
            value = this.Ý;
        }
        else if (value < this.Â) {
            value = this.Â;
        }
        this.HorizonCode_Horizon_È = (T)value;
    }
}
