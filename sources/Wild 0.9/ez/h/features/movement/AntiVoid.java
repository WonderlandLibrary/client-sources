package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class AntiVoid extends Feature
{
    float x;
    float z;
    OptionSlider maxFall;
    float y;
    
    public AntiVoid() {
        super("AntiVoid", "\u041d\u0435 \u043f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0443\u043f\u0430\u0441\u0442\u044c \u0432 \u043f\u0443\u0441\u0442\u043e\u0442\u0443.", Category.MOVEMENT);
        this.maxFall = new OptionSlider(this, "Fall Distance", 10.0f, 2.0f, 50.0f, OptionSlider.SliderType.M);
        this.addOptions(this.maxFall);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (AntiVoid.mc.h.z) {
            this.x = (float)AntiVoid.mc.h.p;
            this.y = (float)AntiVoid.mc.h.q;
            this.z = (float)AntiVoid.mc.h.r;
        }
        else if (AntiVoid.mc.h.L > this.maxFall.getNum()) {
            eventMotion.setX(this.x);
            eventMotion.setY(this.y);
            eventMotion.setZ(this.z);
            eventMotion.setOnGround(true);
        }
    }
}
