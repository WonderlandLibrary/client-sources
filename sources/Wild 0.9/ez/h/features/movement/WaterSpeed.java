package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class WaterSpeed extends Feature
{
    OptionSlider speed;
    OptionBoolean potionCheck;
    
    public WaterSpeed() {
        super("WaterSpeed", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0431\u044b\u0441\u0442\u0440\u043e \u0434\u0432\u0438\u0433\u0430\u0442\u044c\u0441\u044f \u0432 \u0432\u043e\u0434\u0435.", Category.MOVEMENT);
        this.speed = new OptionSlider(this, "Speed", 0.5f, 0.01f, 5.0f, OptionSlider.SliderType.BPS);
        this.potionCheck = new OptionBoolean(this, "Potion Check", true);
        this.addOptions(this.speed, this.potionCheck);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.speed.getNum() + "");
        if (!WaterSpeed.mc.h.ao() || !WaterSpeed.mc.h.isMoving()) {
            return;
        }
        if (this.potionCheck.enabled && !WaterSpeed.mc.h.a(uz.a(1))) {
            return;
        }
        Utils.setMotion(this.speed.getNum());
    }
}
