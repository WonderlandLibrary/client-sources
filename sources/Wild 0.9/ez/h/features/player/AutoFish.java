package ez.h.features.player;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;

public class AutoFish extends Feature
{
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof kq) {
            final kq kq = (kq)eventPacketReceive.getPacket();
            if (kq.a() == qf.K && kq.b() == qg.g && AutoFish.mc.h.co().c() instanceof aih) {
                AutoFish.mc.h.d.a((ht)new mb(ub.a));
                AutoFish.mc.h.a(ub.a);
                AutoFish.mc.h.d.a((ht)new mb(ub.a));
                AutoFish.mc.h.a(ub.a);
            }
        }
    }
    
    public AutoFish() {
        super("AutoFish", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0430\u044f \u0440\u044b\u0431\u0430\u043b\u043a\u0430.", Category.PLAYER);
    }
}
