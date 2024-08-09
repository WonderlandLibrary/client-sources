package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class FastLadder extends Feature
{
    OptionSlider speed;
    OptionMode sneakAction;
    
    public FastLadder() {
        super("FastLadder", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0431\u044b\u0441\u0442\u0440\u043e \u0437\u0430\u0431\u0438\u0440\u0430\u0442\u044c\u0441\u044f \u043f\u043e \u043b\u0435\u0441\u0442\u043d\u0438\u0446\u0430\u043c.", Category.MOVEMENT);
        this.speed = new OptionSlider(this, "Speed", 0.1f, 0.01f, 3.0f, OptionSlider.SliderType.BPS);
        this.sneakAction = new OptionMode(this, "Sneak Action", "Stop", new String[] { "Stop", "Boost" }, 0);
        this.addOptions(this.speed, this.sneakAction);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (FastLadder.mc.h.m_()) {
            eventMotion.setOnGround(true);
            if (FastLadder.mc.h.aU()) {
                if (this.sneakAction.isMode("Stop")) {
                    FastLadder.mc.h.t = 0.0;
                    return;
                }
                final bud h = FastLadder.mc.h;
                h.t += this.speed.getNum();
            }
            final bud h2 = FastLadder.mc.h;
            h2.t += this.speed.getNum();
        }
    }
}
