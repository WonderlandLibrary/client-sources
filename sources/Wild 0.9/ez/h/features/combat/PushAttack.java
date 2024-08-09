package ez.h.features.combat;

import ez.h.utils.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class PushAttack extends Feature
{
    String[] modes;
    OptionMode mode;
    OptionBoolean release;
    Counter shieldFixerTimer;
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        if (PushAttack.mc.h.cO() && this.shieldFixerTimer.hasReached(200.0f) && PushAttack.mc.h.b(ub.b).c() instanceof ajm && this.release.enabled) {
            PushAttack.mc.h.d.a((ht)new lp(lp.a.f, new et(228 + 556 - 255 + 371, 211 + 224 - 394 + 859, 77 + 379 - 389 + 833), fa.b));
            PushAttack.mc.c.a((aed)PushAttack.mc.h, (amu)PushAttack.mc.f, ub.b);
            this.shieldFixerTimer.reset();
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (PushAttack.mc.h.b(ub.b).isEmpty()) {
            return;
        }
        if (PushAttack.mc.h.cG() && PushAttack.mc.h.n(1.0f) == 1.0f && PushAttack.mc.t.ae.i) {
            if (this.mode.getMode().equalsIgnoreCase("Click")) {
                PushAttack.mc.aA();
            }
            else {
                if (PushAttack.mc.s != null && PushAttack.mc.s.d instanceof vp) {
                    PushAttack.mc.c.a((aed)PushAttack.mc.h, PushAttack.mc.s.d);
                }
                PushAttack.mc.h.a(ub.a);
                PushAttack.mc.h.ds();
            }
        }
    }
    
    public PushAttack() {
        super("PushAttack", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u0431\u0438\u0442\u044c \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u0435\u0434\u044b/\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f \u0449\u0438\u0442\u043e\u043c.", Category.COMBAT);
        this.modes = new String[] { "Click", "Packet" };
        this.shieldFixerTimer = new Counter();
        this.mode = new OptionMode(this, "Mode", "Click", this.modes, 0);
        this.release = new OptionBoolean(this, "Release", true);
        this.addOptions(this.mode, this.release);
    }
}
