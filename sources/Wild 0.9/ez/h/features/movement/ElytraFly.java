package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class ElytraFly extends Feature
{
    OptionBoolean disabler;
    OptionSlider vSpeed;
    OptionSlider speed;
    
    public ElytraFly() {
        super("ElytraFly", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u043b\u0435\u0442\u0430\u0442\u044c \u043d\u0430 \u044d\u043b\u0438\u0442\u0440\u0435 \u0431\u0435\u0441\u043a\u043e\u043d\u0435\u0447\u043d\u043e.", Category.MOVEMENT);
        this.disabler = new OptionBoolean(this, "Disabler", true);
        this.speed = new OptionSlider(this, "Speed", 3.0f, 0.01f, 4.0f, OptionSlider.SliderType.BPS);
        this.vSpeed = new OptionSlider(this, "VSpeed", 0.07f, 0.01f, 0.1f, OptionSlider.SliderType.BPS);
        this.addOptions(this.speed, this.disabler, this.vSpeed);
    }
    
    @EventTarget
    public void motion(final EventMotion eventMotion) {
        if (!ElytraFly.mc.h.cP()) {
            return;
        }
        Utils.setMotion(this.speed.getNum());
        if (!ElytraFly.mc.h.cP() && ElytraFly.mc.h.T % 5 == 0 && this.disabler.enabled) {
            ElytraFly.mc.h.d.a((ht)new lq((vg)ElytraFly.mc.h, lq.a.i));
        }
        ElytraFly.mc.h.t = -0.004000000189989805;
        if (ElytraFly.mc.t.X.i) {
            final bud h = ElytraFly.mc.h;
            h.t += this.vSpeed.getNum();
        }
        if (ElytraFly.mc.t.Y.i) {
            final bud h2 = ElytraFly.mc.h;
            h2.t -= this.vSpeed.getNum();
        }
    }
}
