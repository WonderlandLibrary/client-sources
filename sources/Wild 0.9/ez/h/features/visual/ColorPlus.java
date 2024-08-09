package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class ColorPlus extends Feature
{
    OptionSlider saturation;
    
    public ColorPlus() {
        super("ColorPlus", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u043d\u0430\u0441\u0442\u0440\u0430\u0438\u0432\u0430\u0442\u044c \u043d\u0430\u0441\u044b\u0449\u0435\u043d\u043d\u043e\u0441\u0442\u044c \u0446\u0432\u0435\u0442\u043e\u0432 \u0432 \u0438\u0433\u0440\u0435.", Category.VISUAL);
        this.saturation = new OptionSlider(this, "Saturation", 1.9f, -1.0f, 5.0f, OptionSlider.SliderType.NULL);
        this.addOptions(this.saturation);
    }
    
    @Override
    public void onDisable() {
        if (ColorPlus.mc.o.af != null) {
            ColorPlus.mc.o.af.a();
        }
        super.onDisable();
    }
    
    @EventTarget
    public void onRender2D(final EventRender2D eventRender2D) {
        RenderUtils.Desaturate.desaturate(this.saturation.getNum());
    }
}
