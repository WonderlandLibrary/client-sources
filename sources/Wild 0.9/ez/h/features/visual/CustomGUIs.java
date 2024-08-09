package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class CustomGUIs extends Feature
{
    public static OptionSlider blurStrength;
    public static OptionBoolean snow;
    public static OptionBoolean blur;
    
    @Override
    public void updateElements() {
        CustomGUIs.blurStrength.display = CustomGUIs.blur.enabled;
        super.updateElements();
    }
    
    public CustomGUIs() {
        super("CustomGUIs", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0437\u0430\u0434\u043d\u0438\u0439 \u0444\u043e\u043d \u0438\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441\u043e\u0432.", Category.VISUAL);
        CustomGUIs.blur = new OptionBoolean(this, "Blur", true);
        CustomGUIs.blurStrength = new OptionSlider(this, "Blur Strength", 3.0f, 1.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        CustomGUIs.snow = new OptionBoolean(this, "Snow", true);
        this.addOptions(CustomGUIs.blur, CustomGUIs.blurStrength, CustomGUIs.snow);
    }
}
