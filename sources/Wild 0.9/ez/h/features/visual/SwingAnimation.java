package ez.h.features.visual;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class SwingAnimation extends Feature
{
    public static OptionSlider speed;
    public static OptionMode mode;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(SwingAnimation.mode.getMode());
    }
    
    @Override
    public void updateElements() {
        SwingAnimation.speed.display = SwingAnimation.mode.isMode("Slow");
        super.updateElements();
    }
    
    public SwingAnimation() {
        super("SwingAnimation", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0430\u043d\u0438\u043c\u0430\u0446\u0438\u044e \u0430\u0442\u0430\u043a\u0438.", Category.VISUAL);
        SwingAnimation.mode = new OptionMode(this, "Mode", "Slow", new String[] { "Slow", "Bonk", "Spin", "Reverse" }, 0);
        SwingAnimation.speed = new OptionSlider(this, "Speed", 0.7f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        this.addOptions(SwingAnimation.mode, SwingAnimation.speed);
    }
}
