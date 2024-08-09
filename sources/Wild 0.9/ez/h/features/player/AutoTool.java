package ez.h.features.player;

import ez.h.features.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class AutoTool extends Feature
{
    int prevSlot;
    
    public AutoTool() {
        super("AutoTool", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u043e\u0434\u0431\u0438\u0440\u0430\u0435\u0442 \u043d\u0443\u0436\u043d\u044b\u0439 \u0438\u043d\u0441\u0442\u0440\u0443\u043c\u0435\u043d\u0442 \u043f\u0440\u0438 \u043a\u043e\u043f\u0430\u043d\u0438\u0438.", Category.PLAYER);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        if (!AutoTool.mc.t.ae.i) {
            return;
        }
        if (AutoTool.mc.s == null) {
            return;
        }
        this.updateTool(AutoTool.mc.s.a());
    }
    
    void updateTool(final et et) {
        final awt o = AutoTool.mc.f.o(et);
        float a = 1.0f;
        int d = -1;
        for (int i = 0; i < 9; ++i) {
            final aip aip = (aip)AutoTool.mc.h.bv.a.get(i);
            if (aip.a(o) > a) {
                a = aip.a(o);
                d = i;
            }
        }
        if (d != -1 && AutoTool.mc.h.bv.d != d) {
            AutoTool.mc.h.bv.d = d;
        }
    }
}
