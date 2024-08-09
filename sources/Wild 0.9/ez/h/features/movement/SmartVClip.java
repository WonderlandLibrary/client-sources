package ez.h.features.movement;

import ez.h.features.*;

public class SmartVClip extends Feature
{
    public SmartVClip() {
        super("SmartVClip", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043a\u043b\u0438\u043f\u0430\u0435\u0442 \u0432\u0430\u0441 \u043d\u0430 \u043d\u0443\u0436\u043d\u0443\u044e Y-\u043a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u0443 \u0435\u0441\u043b\u0438 \u0432\u044b \u0432 \u0431\u043b\u043e\u043a\u0430\u0445.", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        if (!SmartVClip.mc.t.Y.i) {
            for (int i = 35 + 56 - 81 + 245; i > 0; --i) {
                if (SmartVClip.mc.f.o(new et(SmartVClip.mc.h.p, (double)i, SmartVClip.mc.h.r)).u() != aox.a) {
                    SmartVClip.mc.h.b(SmartVClip.mc.h.p, (double)(i + 1), SmartVClip.mc.h.r);
                    SmartVClip.mc.h.d.a((ht)new lk.a(SmartVClip.mc.h.p, (double)(i + 1), SmartVClip.mc.h.r, true));
                    SmartVClip.mc.h.d.a((ht)new lk.a(SmartVClip.mc.h.p, (double)(i + 1), SmartVClip.mc.h.r, true));
                    break;
                }
            }
        }
        else {
            SmartVClip.mc.h.b(SmartVClip.mc.h.p, -5.0, SmartVClip.mc.h.r);
            SmartVClip.mc.h.d.a((ht)new lk.a(SmartVClip.mc.h.p, -5.0, SmartVClip.mc.h.r, true));
            SmartVClip.mc.h.d.a((ht)new lk.a(SmartVClip.mc.h.p, -5.0, SmartVClip.mc.h.r, true));
        }
        this.toggle();
        super.onEnable();
    }
}
