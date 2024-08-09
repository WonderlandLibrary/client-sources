package ez.h.features.player;

import ez.h.event.events.*;
import ez.h.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class NoFall extends Feature
{
    OptionMode mode;
    boolean isInAir;
    OptionSlider reduceTicks;
    
    @Override
    public void updateElements() {
        this.reduceTicks.display = this.mode.isMode("Reduce");
        super.updateElements();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (Main.getFeatureByName("Glide").isEnabled()) {
            return;
        }
        if (this.mode.isMode("Matrix") && NoFall.mc.h.L >= 2.0f) {
            NoFall.mc.Y.speed = 0.01f;
            NoFall.mc.h.d.a((ht)new lk.a(NoFall.mc.h.p, NoFall.mc.h.q, NoFall.mc.h.r, false));
            NoFall.mc.h.d.a((ht)new lk.a(NoFall.mc.h.p, NoFall.mc.h.q, NoFall.mc.h.r, true));
            NoFall.mc.Y.speed = 1.0f;
            NoFall.mc.h.L = 0.0f;
        }
        if (this.mode.isMode("Reduce")) {
            if (NoFall.mc.h.B || NoFall.mc.h.z || !(NoFall.mc.f.o(NoFall.mc.h.c().b()).u() instanceof aom)) {
                NoFall.mc.h.L = 0.0f;
            }
            if (NoFall.mc.h.L > 2.81 && NoFall.mc.h.T % this.reduceTicks.getNum() == 0.0f) {
                NoFall.mc.h.d.a((ht)new lk.a(NoFall.mc.h.p, NoFall.mc.h.q, NoFall.mc.h.r, true));
            }
        }
        if (this.mode.isMode("AAC 5.2.0") && NoFall.mc.h.L > 3.35f) {
            NoFall.mc.h.t = 0.20000000298023224;
        }
    }
    
    public NoFall() {
        super("NoFall", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0443\u0440\u043e\u043d \u043e\u0442 \u043f\u0430\u0434\u0435\u043d\u0438\u044f.", Category.PLAYER);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Reduce", "AAC 5.2.0" }, 0);
        this.reduceTicks = new OptionSlider(this, "Reduce Ticks", 4.0f, 1.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.mode, this.reduceTicks);
    }
}
