package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class AutoPlay extends Feature
{
    OptionMode mode;
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (!AutoPlay.mc.C().b.contains("stormhvh")) {
            return;
        }
        if (AutoPlay.mc.h != null && Debug.getOrCreateVar("counter2222", new Counter()).isDelay(8000L) && this.mode.isMode("StormHVH")) {
            Debug.getOrCreateVar("counter2222", new Counter()).setLastMS();
            AutoPlay.mc.h.g("/deluxemenu open unranked");
        }
        final ht<?> packet = eventPacketReceive.getPacket();
        if (packet instanceof iu) {
            final iu iu = (iu)packet;
            if (this.mode.isMode("StormHVH")) {
                final int b = iu.b();
                iu.c();
                if (b == 0) {
                    Debug.executeLaterNotStack(200L, () -> {
                        AutoPlay.mc.h.d.a((ht)new lv(b));
                        Debug.executeLaterNotStack(200L, () -> AutoPlay.mc.h.d.a((ht)new mb(ub.a)));
                        return;
                    });
                }
            }
        }
        if (packet instanceof is) {
            final is is = (is)packet;
            if (this.mode.isMode("StormHVH") && is.b().size() > (0x88 ^ 0xA8)) {
                final aip aip;
                int i = 0;
                final is is2;
                Debug.executeLaterNotStack(200L, () -> {
                    aip = is.b().get(0xE ^ 0x2E);
                    while (i < 3) {
                        AutoPlay.mc.h.d.a((ht)new lf(is2.a(), 0x32 ^ 0x12, 1, afw.a, aip, (short)0));
                        ++i;
                    }
                });
            }
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    public AutoPlay() {
        super("AutoPlay", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0430\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u043e\u0434\u043a\u043b\u044e\u0447\u0430\u0442\u044c\u0441\u044f \u043a \u0438\u0433\u0440\u0430\u043c", Category.ANOTHER);
        this.mode = new OptionMode(this, "Mode", "StormHVH", new String[] { "StormHVH" }, 0);
        this.addOptions(this.mode);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
