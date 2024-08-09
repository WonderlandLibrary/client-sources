package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import java.awt.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class CustomFog extends Feature
{
    public static OptionSlider distance;
    public static OptionColor color;
    
    public CustomFog() {
        super("CustomFog", "\u041f\u043e\u043b\u043d\u0430\u044f \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 \u0442\u0443\u043c\u0430\u043d\u0430.", Category.VISUAL);
        this.addOptions(CustomFog.color = new OptionColor(this, "Fog Color", new Color(0, 129 + 179 - 180 + 72, 179 + 117 - 102 + 6)), CustomFog.distance = new OptionSlider(this, "Distance", 0.8f, 0.0f, 1.0f, OptionSlider.SliderType.NULL));
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        CustomFog.mc.o.R = (float)CustomFog.color.getColor().getRed();
        CustomFog.mc.o.S = (float)CustomFog.color.getColor().getGreen();
        CustomFog.mc.o.T = (float)CustomFog.color.getColor().getBlue();
        CustomFog.mc.o.V = 1.0f;
    }
}
