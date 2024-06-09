package HORIZON-6-0-SKIDPROTECTION;

public class LongProperty extends Property<Long>
{
    private long Â;
    private long Ý;
    
    public LongProperty(final String label, final Long value, final long min, final long max) {
        super(label, value);
        this.Â = min;
        this.Ý = max;
    }
    
    @Override
    public final void HorizonCode_Horizon_È(Long value) {
        if (value > this.Ý) {
            value = this.Ý;
        }
        else if (value < this.Â) {
            value = this.Â;
        }
        this.HorizonCode_Horizon_È = (T)value;
    }
}
