package ez.h.features.movement;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.ui.*;
import ez.h.*;
import ez.h.event.*;

public class Timer extends Feature
{
    OptionBoolean autoDisable;
    public static OptionColor infoColor;
    OptionSlider speed;
    
    public Timer() {
        super("Timer", "\u0423\u0441\u043a\u043e\u0440\u044f\u0435\u0442 \u0442\u0435\u0447\u0435\u043d\u0438\u0435 \u0432\u0440\u0435\u043c\u0435\u043d\u0438 \u0438\u0433\u0440\u044b.", Category.MOVEMENT);
        this.speed = new OptionSlider(this, "Speed", 2.5f, 0.01f, 5.0f, OptionSlider.SliderType.BPS);
        this.autoDisable = new OptionBoolean(this, "Auto Disable", true);
        Timer.infoColor = new OptionColor(this, "Info Color", new Color(682620 + 280354 + 2847532 + 2284300));
        this.addOptions(this.speed, this.autoDisable, Timer.infoColor);
    }
    
    @Override
    public void onDisable() {
        Timer.mc.Y.speed = 1.0f;
        super.onDisable();
    }
    
    @EventTarget
    public void onMove(final EventMotion eventMotion) {
        if (!this.isEnabled()) {
            return;
        }
        final HUD hud = (HUD)Main.getFeatureByName("HUD");
        if (HUD.packetsCounter > (0x5 ^ 0x2D) && hud.isEnabled() && this.autoDisable.isEnabled()) {
            Timer.mc.Y.speed = 1.0f;
            this.setEnabled(false);
            return;
        }
        Timer.mc.Y.speed = this.speed.getNum();
    }
}
