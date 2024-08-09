package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class AutoDuel extends Feature
{
    OptionSlider delay;
    
    public AutoDuel() {
        super("AutoDuel", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043a\u0438\u0434\u0430\u0435\u0442 \u0434\u0443\u044d\u043b\u044c \u0440\u0430\u043d\u0434\u043e\u043c\u043d\u043e\u043c\u0443 \u0438\u0433\u0440\u043e\u043a\u0443.", Category.ANOTHER);
        this.delay = new OptionSlider(this, "Delay", 5000.0f, 1000.0f, 10000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.delay);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.counter.hasReached(5000.0f)) {
            final aed aed = AutoDuel.mc.f.i.get(MathUtils.nextInt(0, AutoDuel.mc.f.i.size() - 1));
            this.counter.setLastMS();
        }
    }
}
