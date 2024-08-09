package ez.h.features.combat;

import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class Criticals extends Feature
{
    OptionMode mode;
    float hoverY;
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        this.hoverY = 1.0E-4f;
        if (!this.mode.isMode("Packet")) {
            return;
        }
        if (!Criticals.mc.h.z || Criticals.mc.h.m_() || Criticals.mc.h.E || Criticals.mc.h.ao() || Criticals.mc.h.bJ() != null) {
            return;
        }
        final float n = (float)Criticals.mc.h.p;
        final float n2 = (float)Criticals.mc.h.q;
        final float n3 = (float)Criticals.mc.h.r;
        Criticals.mc.h.d.a((ht)new lk.a((double)n, n2 + 0.0625, (double)n3, true));
        Criticals.mc.h.d.a((ht)new lk.a((double)n, (double)n2, (double)n3, false));
        Criticals.mc.h.d.a((ht)new lk.a((double)n, n2 + 1.1E-5, (double)n3, false));
        Criticals.mc.h.d.a((ht)new lk.a((double)n, (double)n2, (double)n3, false));
        Criticals.mc.h.b(eventAttack.attackEntity);
    }
    
    public Criticals() {
        super("Criticals", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u043d\u0430\u043d\u043e\u0441\u0438\u0442\u044c \u043a\u0440\u0438\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0435 \u0443\u0434\u0430\u0440\u044b \u0431\u0435\u0437 \u043f\u0440\u044b\u0436\u043a\u0430.", Category.COMBAT);
        this.hoverY = 9.0E-5f;
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Packet" }, 0);
        this.addOptions(this.mode);
    }
    
    @EventTarget
    public void onPostAttack(final EventPostAttack eventPostAttack) {
        this.hoverY = 9.0E-4f;
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (!this.mode.isMode("Matrix")) {
            return;
        }
        if (eventPacketSend.getPacket() instanceof lk) {
            final lk lk = (lk)eventPacketSend.getPacket();
            if (!Criticals.mc.t.X.i && Criticals.mc.h.L <= 0.0f) {
                lk.b = ((Criticals.mc.h.T % 2 == 0) ? (lk.b + 6.0E-4) : (lk.b + 2.0E-4));
                lk.f = false;
                Criticals.mc.h.z = false;
            }
        }
        if (eventPacketSend.getPacket() instanceof lq && ((lq)eventPacketSend.getPacket()).b() == lq.a.d) {
            eventPacketSend.setCancelled(true);
        }
    }
}
