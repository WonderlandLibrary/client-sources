package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import java.awt.*;
import ez.h.event.*;

public class AutoLeave extends Feature
{
    OptionSlider radius;
    
    public AutoLeave() {
        super("AutoLeave", "\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0442 \u0432\u0430\u0441 \u043d\u0430 \u0441\u043f\u0430\u0432\u043d \u043a\u043e\u0433\u0434\u0430 \u0432\u0430\u0441 \u043d\u0430\u0445\u043e\u0434\u0438\u0442 \u0438\u0433\u0440\u043e\u043a \u0432 \u043e\u043f\u0440\u0435\u0434\u0435\u043b\u0451\u043d\u043d\u043e\u043c \u0440\u0430\u0434\u0438\u0443\u0441\u0435.", Category.ANOTHER);
        this.radius = new OptionSlider(this, "Radius", 25.0f, 1.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.radius);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(Utils.format("##", this.radius.getNum()));
        if (AutoLeave.mc.f.i.stream().anyMatch(bud -> bud != AutoLeave.mc.h && AutoLeave.mc.h.getDistance((vg)bud) <= this.radius.getNum())) {
            AutoLeave.mc.h.g("/spawn");
            Notifications.addNotification("Detected player in radius", "Teleported to spawn", new Color(0, 141 + 95 - 207 + 171, 2 + 116 - 80 + 162), new Color(-1));
            this.toggle();
        }
    }
}
