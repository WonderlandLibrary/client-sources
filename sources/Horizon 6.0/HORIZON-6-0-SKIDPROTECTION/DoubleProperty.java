package HORIZON-6-0-SKIDPROTECTION;

public class DoubleProperty extends Property<Double>
{
    private double Â;
    private double Ý;
    
    public DoubleProperty(final String label, final Double value, final double min, final double max) {
        super(label, value);
        this.Â = min;
        this.Ý = max;
    }
    
    @Override
    public final void HorizonCode_Horizon_È(Double value) {
        if (value > this.Ý) {
            value = this.Ý;
        }
        else if (value < this.Â) {
            value = this.Â;
        }
        this.HorizonCode_Horizon_È = (T)value;
    }
}
