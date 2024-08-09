package ez.h.features.combat;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class FastBow extends Feature
{
    OptionSlider delay;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (FastBow.mc.h.cG() && FastBow.mc.h.cL() >= this.delay.getNum() && (FastBow.mc.h.co().c() == air.g || FastBow.mc.h.cp().c() == air.g)) {
            FastBow.mc.h.d.a((ht)new lp(lp.a.f, et.a, FastBow.mc.h.bt()));
            FastBow.mc.h.d.a((ht)new mb(FastBow.mc.h.cH()));
            FastBow.mc.h.cM();
        }
    }
    
    public FastBow() {
        super("FastBow", "\u0411\u044b\u0441\u0442\u0440\u043e \u0441\u0442\u0440\u0435\u043b\u044f\u0435\u0442 \u0438\u0437 \u043b\u0443\u043a\u0430.", Category.COMBAT);
        this.delay = new OptionSlider(this, "Delay", 3.0f, 0.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.delay);
    }
}
