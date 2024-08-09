package ez.h.features.player;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class Sprint extends Feature
{
    OptionBoolean onlyMoving;
    OptionBoolean multiDirectBypass;
    OptionMode mode;
    
    public Sprint() {
        super("Sprint", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0437\u0430\u0436\u0438\u043c\u0430\u0435\u0442 \u043a\u043d\u043e\u043f\u043a\u0443 \u0431\u0435\u0433\u0430.", Category.PLAYER);
        this.mode = new OptionMode(this, "Mode", "Legit", new String[] { "Legit", "Rage" }, 0);
        this.onlyMoving = new OptionBoolean(this, "Only Moving", true);
        this.addOptions(this.mode, this.onlyMoving);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (Sprint.mc.h.cG()) {
            return;
        }
        this.setSuffix(this.mode.getMode());
        if (this.onlyMoving.enabled && !Sprint.mc.h.isMoving()) {
            return;
        }
        if (this.mode.isMode("Rage")) {
            Sprint.mc.h.f(true);
        }
        if (this.mode.isMode("Legit")) {
            Sprint.mc.t.Z.i = true;
        }
    }
}
