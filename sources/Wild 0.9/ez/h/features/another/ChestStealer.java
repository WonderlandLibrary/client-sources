package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class ChestStealer extends Feature
{
    OptionSlider delay;
    
    public ChestStealer() {
        super("ChestStealer", "\u0417\u0430\u0431\u0438\u0440\u0430\u0435\u0442 \u0432\u0441\u0435 \u0432\u0435\u0449\u0438 \u0438\u0437 \u043e\u0442\u043a\u0440\u044b\u0442\u043e\u0433\u043e \u0441\u0443\u043d\u0434\u0443\u043a\u0430.", Category.ANOTHER);
        this.delay = new OptionSlider(this, "Delay", 100.0f, 1.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.delay);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        if (ChestStealer.mc.h.by instanceof afv) {
            final afv afv = (afv)ChestStealer.mc.h.by;
            for (int i = 0; i < afv.e().w_(); ++i) {
                final agr agr = afv.c.get(i);
                if (this.counter.hasReached((float)(long)this.delay.getNum()) && !agr.d().isEmpty()) {
                    ChestStealer.mc.c.a(afv.d, agr.e, 0, afw.b, (aed)ChestStealer.mc.h);
                    this.counter.reset();
                }
            }
        }
    }
}
