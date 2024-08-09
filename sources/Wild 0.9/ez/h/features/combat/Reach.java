package ez.h.features.combat;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Reach extends Feature
{
    public static OptionSlider distance;
    
    public Reach() {
        super("Reach", "\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0440\u0430\u0434\u0438\u0443\u0441 \u0434\u043e\u0441\u044f\u0433\u0430\u0435\u043c\u043e\u0441\u0442\u0438 \u0434\u043e \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439.", Category.COMBAT);
        Reach.distance = new OptionSlider(this, "Distance", 4.2f, 0.0f, 6.0f, OptionSlider.SliderType.M);
        this.addOptions(Reach.distance);
    }
}
