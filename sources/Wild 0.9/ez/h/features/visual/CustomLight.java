package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;

public class CustomLight extends Feature
{
    public static OptionColor color;
    
    public CustomLight() {
        super("CustomLight", "\u041d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0430 \u043e\u0441\u0432\u0435\u0449\u0435\u043d\u0438\u044f.", Category.VISUAL);
        CustomLight.color = new OptionColor(this, "Color", new Color(-1));
        this.addOptions(CustomLight.color);
    }
}
