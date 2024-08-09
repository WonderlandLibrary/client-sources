package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class Gamma extends Feature
{
    float startGamma;
    OptionSlider gamma;
    
    public Gamma() {
        super("Gamma", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0438\u0434\u0435\u0442\u044c \u0432 \u0442\u0435\u043c\u043d\u043e\u0442\u0435.", Category.VISUAL);
        this.gamma = new OptionSlider(this, "Gamma", 3.0f, -5.0f, 5.0f, OptionSlider.SliderType.NULL);
        this.startGamma = Gamma.mc.t.aE;
        this.addOptions(this.gamma);
    }
    
    @Override
    public void onDisable() {
        Gamma.mc.t.aE = this.startGamma;
        super.onDisable();
    }
    
    @EventTarget
    public void onTick(final EventMotion eventMotion) {
        Gamma.mc.t.aE = this.gamma.getNum();
    }
}
