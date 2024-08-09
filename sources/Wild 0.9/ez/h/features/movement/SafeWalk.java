package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class SafeWalk extends Feature
{
    OptionBoolean cancelOnSneak;
    
    public SafeWalk() {
        super("SafeWalk", "\u041f\u0440\u0435\u0434\u043e\u0442\u0432\u0440\u0430\u0449\u0430\u0435\u0442 \u043f\u0430\u0434\u0435\u043d\u0438\u0435 \u0441 \u043a\u0440\u0430\u044f \u0431\u043b\u043e\u043a\u0430.", Category.MOVEMENT);
        this.cancelOnSneak = new OptionBoolean(this, "Cancel On Sneak", false);
        this.addOptions(this.cancelOnSneak);
    }
    
    @EventTarget
    public void onSafeWalk(final SafeWalkEvent safeWalkEvent) {
        safeWalkEvent.setSafe(SafeWalk.mc.h.z && (!this.cancelOnSneak.enabled || !SafeWalk.mc.h.aU()));
    }
}
