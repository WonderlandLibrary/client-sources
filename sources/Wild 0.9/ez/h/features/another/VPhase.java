package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class VPhase extends Feature
{
    OptionMode mode;
    
    @Override
    public void onEnable() {
        if (this.mode.isMode("Nexus")) {
            if (!VPhase.mc.h.aU()) {
                VPhase.mc.h.d.a((ht)new lq((vg)VPhase.mc.h, lq.a.a));
            }
            VPhase.mc.h.setMotion(0.0f);
            VPhase.mc.h.a(VPhase.mc.h.p, VPhase.mc.h.q - 0.029999999329447746, VPhase.mc.h.r);
            VPhase.mc.h.d.a((ht)new lq((vg)VPhase.mc.h, lq.a.b));
            this.toggle();
        }
        if (this.mode.isMode("ReallyWorld")) {
            for (int i = 50 + 146 + 34 + 25; i > 0; --i) {
                if (VPhase.mc.f.o(new et(VPhase.mc.h.p, (double)i, VPhase.mc.h.r)).u() != aox.a) {
                    VPhase.mc.h.b(VPhase.mc.h.p, (double)(i + 1), VPhase.mc.h.r);
                    VPhase.mc.h.d.a((ht)new lk.a(VPhase.mc.h.p, (double)(i + 1), VPhase.mc.h.r, true));
                    VPhase.mc.h.d.a((ht)new lk.a(VPhase.mc.h.p, (double)(i + 1), VPhase.mc.h.r, true));
                    break;
                }
            }
            this.toggle();
        }
        super.onEnable();
    }
    
    public VPhase() {
        super("VPhase", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u0432\u0435\u0440\u0442\u0438\u043a\u0430\u043b\u044c\u043d\u043e.", Category.ANOTHER);
        this.mode = new OptionMode(this, "Mode", "ReallyWorld", new String[] { "ReallyWorld", "Nexus" }, 0);
        this.addOptions(this.mode);
    }
}
