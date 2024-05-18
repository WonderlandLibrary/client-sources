package HORIZON-6-0-SKIDPROTECTION;

public class FloatProperty extends Property<Float>
{
    private float Â;
    private float Ý;
    
    public FloatProperty(final String label, final Float value, final float min, final float max) {
        super(label, value);
        this.Â = min;
        this.Ý = max;
    }
    
    @Override
    public final void HorizonCode_Horizon_È(Float value) {
        if (value > this.Ý) {
            value = this.Ý;
        }
        else if (value < this.Â) {
            value = this.Â;
        }
        this.HorizonCode_Horizon_È = (T)value;
    }
}
