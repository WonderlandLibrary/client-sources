package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Wings extends Feature
{
    public static OptionMode mode;
    public static OptionSlider scale;
    
    public Wings() {
        super("Wings", "\u0420\u0438\u0441\u0443\u0435\u0442 \u043a\u0440\u044b\u043b\u044c\u044f \u0443 \u0438\u0433\u0440\u043e\u043a\u0430.", Category.VISUAL);
        Wings.mode = new OptionMode(this, "Mode", "White", new String[] { "White", "Dark" }, 0);
        Wings.scale = new OptionSlider(this, "Scale", 1.0f, 0.1f, 3.0f, OptionSlider.SliderType.NULL);
        this.addOptions(Wings.mode, Wings.scale);
    }
}
