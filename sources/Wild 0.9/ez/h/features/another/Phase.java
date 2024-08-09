package ez.h.features.another;

import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class Phase extends Feature
{
    OptionMode mode;
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mode.isMode("MatrixDisabler")) {
            float[] array;
            new Thread(() -> {
                try {
                    array = new float[] { (float)(-Math.sin(Math.toRadians(Phase.mc.h.v))), (float)Math.cos(Math.toRadians(Phase.mc.h.v)) };
                    Phase.mc.h.d.a((ht)new lk.a(0.0, 0.0, 0.0, false));
                    Phase.mc.h.b(Phase.mc.h.p + array[0] * 0.003f, Phase.mc.h.q, Phase.mc.h.r + array[1] * 0.003f);
                    Phase.mc.h.d.a((ht)new lk.a(Phase.mc.h.p + array[0] * 0.003f, Phase.mc.h.q, Phase.mc.h.r + array[1] * 0.003f, true));
                    Phase.mc.h.d.a((ht)new lp(lp.a.a, Phase.mc.h.c(), fa.a));
                    Phase.mc.h.d.a((ht)new lq((vg)Phase.mc.h, lq.a.a));
                    Phase.mc.h.setMotion(0.0f);
                    Phase.mc.h.e.a();
                    Thread.sleep(50L);
                    Phase.mc.h.d.a((ht)new lk.a(0.0, 0.0, 0.0, false));
                    Phase.mc.h.b(Phase.mc.h.p + array[0] * 0.003f, Phase.mc.h.q, Phase.mc.h.r + array[1] * 0.003f);
                    Phase.mc.h.d.a((ht)new lk.a(Phase.mc.h.p + array[0] * 0.003f, Phase.mc.h.q, Phase.mc.h.r + array[1] * 0.003f, true));
                    Phase.mc.h.d.a((ht)new lq((vg)Phase.mc.h, lq.a.b));
                }
                catch (Exception ex) {}
                return;
            }).start();
        }
        if (this.mode.isMode("MatrixDisabler")) {
            this.toggle();
        }
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof ko) {}
    }
    
    public Phase() {
        super("Phase", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c\u0441\u044f \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b.", Category.ANOTHER);
        this.mode = new OptionMode(this, "Mode", "MatrixDisabler", new String[] { "MatrixDisabler", "Sunrise" }, 1);
        this.addOptions(this.mode);
    }
    
    @Override
    public void updateElements() {
        super.updateElements();
    }
    
    @EventTarget
    public void onEvent(final EventLivingUpdate eventLivingUpdate) {
        this.setSuffix(this.mode.getMode());
        if (this.mode.isMode("Sunrise")) {
            final float[] array = { (float)(-Math.sin(Math.toRadians(Phase.mc.h.v))), (float)Math.cos(Math.toRadians(Phase.mc.h.v)) };
            Phase.mc.h.Q = false;
            Phase.mc.h.setMotion(0.0f);
            Phase.mc.h.e.a = 0.0f;
            if (Phase.mc.h.A && this.counter.hasReached(150.0f)) {
                Phase.mc.h.setMotion(0.8f);
                Phase.mc.h.d.a((ht)new lk.a(0.0, 0.0, 0.0, false));
                Phase.mc.h.b(Phase.mc.h.p + array[0] * 0.003f, Phase.mc.h.q, Phase.mc.h.r + array[1] * 0.003f);
                Phase.mc.h.d.a((ht)new lk.a(Phase.mc.h.p + array[0] * 0.003f, Phase.mc.h.q, Phase.mc.h.r + array[1] * 0.003f, true));
                this.counter.reset();
            }
        }
    }
}
