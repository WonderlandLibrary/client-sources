package ez.h.features.combat;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class SuperKnockback extends Feature
{
    OptionSlider chance;
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        if (Math.random() * 100.0 > this.chance.getNum()) {
            return;
        }
        if (SuperKnockback.mc.h.aV()) {
            SuperKnockback.mc.h.f(false);
        }
        SuperKnockback.mc.h.d.a((ht)new lq((vg)SuperKnockback.mc.h, lq.a.d));
        SuperKnockback.mc.h.cl = true;
    }
    
    public SuperKnockback() {
        super("SuperKnockback", "\u041f\u043e\u0432\u044b\u0448\u0430\u0435\u0442 \u0432\u0430\u0448\u0443 \u043e\u0442\u0434\u0430\u0447\u0443 \u043d\u0430 180%.", Category.COMBAT);
        this.chance = new OptionSlider(this, "Chance", 100.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.chance);
    }
}
