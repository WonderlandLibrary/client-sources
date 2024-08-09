package ez.h.ui.clickgui.options;

import java.text.*;
import java.math.*;
import ez.h.features.*;

public class OptionSlider extends Option
{
    public float max;
    public float min;
    public float value;
    String name;
    public SliderType slidertype;
    
    public float getNum() {
        final DecimalFormat decimalFormat = new DecimalFormat("##.##");
        final DecimalFormat decimalFormat2 = new DecimalFormat("##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        decimalFormat2.setRoundingMode(RoundingMode.DOWN);
        return (this.slidertype == SliderType.MS || this.slidertype == SliderType.NULLINT) ? ((float)Integer.parseInt(decimalFormat2.format(this.value))) : Float.parseFloat(decimalFormat.format(this.value));
    }
    
    @Override
    public void setName(final String name) {
        this.name = name;
    }
    
    public OptionSlider(final Feature feature, final String name, final float value, final float min, final float max, final SliderType slidertype) {
        super(feature, name, value);
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.slidertype = slidertype;
        this.setValue(value);
    }
    
    public String getDes() {
        return this.name;
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
    public void setNum(final float value) {
        this.value = value;
    }
    
    public enum SliderType
    {
        NULLINT, 
        MS, 
        NULL, 
        BPS, 
        M;
    }
}
