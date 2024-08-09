package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class CustomWeather extends Feature
{
    public OptionSlider rainStrength;
    public OptionBoolean fancy;
    
    public CustomWeather() {
        super("CustomWeather", "\u0412\u044b\u0431\u043e\u0440 \u043f\u043e\u0433\u043e\u0434\u044b.", Category.VISUAL);
        this.rainStrength = new OptionSlider(this, "Rain Strength", 1.0f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        this.fancy = new OptionBoolean(this, "Fancy", true);
        this.addOptions(this.rainStrength, this.fancy);
    }
}
