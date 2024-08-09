package ez.h.features.movement;

import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Spider extends Feature
{
    aip prevItem;
    OptionMode mode;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (!Spider.mc.h.A) {
            return;
        }
        if (this.mode.isMode("Matrix") && Spider.mc.h.t < 0.20000000298023224) {
            eventMotion.onGround = true;
            Spider.mc.h.cu();
        }
        if (this.mode.isMode("Sunrise")) {
            if (!(Spider.mc.h.co().c() instanceof ahb)) {
                this.prevItem = Spider.mc.h.co();
                for (int i = 0xA9 ^ 0x8D; i < (0xEF ^ 0xC2); ++i) {
                    if (Spider.mc.h.bx.a(i).d().c() instanceof ahb) {
                        Spider.mc.v().a((ht)new lv(Spider.mc.h.bv.b(Spider.mc.h.bx.a(i).d())));
                        break;
                    }
                }
            }
            if (Spider.mc.h.T % 2 == 0 && Spider.mc.h.A) {
                final bhc rayTrace = MathUtils.rayTrace((vg)Spider.mc.h, Spider.mc.h.v, -65.3f, 2.0, Spider.mc.aj());
                Spider.mc.v().a((ht)new ma(rayTrace.a(), rayTrace.b, ub.a, (float)rayTrace.c.x, (float)rayTrace.c.y, (float)rayTrace.c.z));
                Spider.mc.v().a((ht)new ly(ub.a));
                Spider.mc.c.b(MathUtils.rayTrace((vg)Spider.mc.h, Spider.mc.h.v, -90.0f, 1.0, Spider.mc.aj()).a(), rayTrace.b);
                Spider.mc.h.cu();
            }
        }
        if (this.mode.isMode("ReallyWorld") && Spider.mc.h.isMoving() && this.counter.hasReached(100.0f)) {
            eventMotion.onGround = true;
            Spider.mc.h.cu();
            this.counter.reset();
        }
        if (this.mode.isMode("Intave13")) {
            Spider.mc.h.cu();
            Spider.mc.h.b(Spider.mc.h.p, Spider.mc.h.q + 9.234000231117534E-7, Spider.mc.h.r);
            final bud h = Spider.mc.h;
            h.q -= 9.234E-7;
        }
    }
    
    public Spider() {
        super("Spider", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u0437\u0430\u0431\u0438\u0440\u0430\u0442\u044c\u0441\u044f \u043f\u043e \u0441\u0442\u0435\u043d\u0430\u043c.", Category.MOVEMENT);
        this.prevItem = aip.EMPTY;
        this.mode = new OptionMode(this, "Mode", "Sunrise", new String[] { "Sunrise", "Matrix", "ReallyWorld", "Intave13" }, 0);
        this.addOptions(this.mode);
    }
}
