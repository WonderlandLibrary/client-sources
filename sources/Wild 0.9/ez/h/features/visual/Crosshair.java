package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;

public class Crosshair extends Feature
{
    public static OptionColor color;
    public static OptionSlider radius;
    
    public Crosshair() {
        super("Crosshair", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0432\u0438\u0434 \u0432\u0430\u0448\u0435\u0433\u043e \u043f\u0440\u0438\u0446\u0435\u043b\u0430.", Category.VISUAL);
        Crosshair.color = new OptionColor(this, "Color", new Color(-10630722, true));
        Crosshair.radius = new OptionSlider(this, "Radius", 3.0f, 1.0f, 8.0f, OptionSlider.SliderType.NULL);
        this.addOptions(Crosshair.color, Crosshair.radius);
    }
}
