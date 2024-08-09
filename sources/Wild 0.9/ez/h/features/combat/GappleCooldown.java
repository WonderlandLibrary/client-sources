package ez.h.features.combat;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class GappleCooldown extends Feature
{
    public static OptionSlider cooldown;
    
    public GappleCooldown() {
        super("GappleCooldown", "\u0417\u0430\u0434\u0430\u0451\u0442 \u0437\u0430\u0434\u0435\u0440\u0436\u043a\u0443 \u043c\u0435\u0436\u0434\u0443 \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435\u043c \u0437\u043e\u043b\u043e\u0442\u044b\u0445 \u044f\u0431\u043b\u043e\u043a.", Category.COMBAT);
        GappleCooldown.cooldown = new OptionSlider(this, "Cooldown", 65.0f, 20.0f, 200.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(GappleCooldown.cooldown);
    }
}
