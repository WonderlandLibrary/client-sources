package ez.h.features.movement;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class HighJump extends Feature
{
    public static OptionSlider force;
    public static OptionMode mode;
    public static OptionSlider height;
    int y;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (HighJump.mode.isMode("ReallyWorld")) {
            return;
        }
        if (HighJump.mode.isMode("Matrix") && HighJump.mc.h.t == 0.33319999363422365) {
            final bud h = HighJump.mc.h;
            h.t *= HighJump.force.getNum();
        }
    }
    
    public HighJump() {
        super("HighJump", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u043f\u0440\u044b\u0433\u0430\u0442\u044c \u0432\u044b\u0448\u0435.", Category.MOVEMENT);
        HighJump.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "ReallyWorld", "Default" }, 0);
        HighJump.height = new OptionSlider(this, "Height", 0.42f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        HighJump.force = new OptionSlider(this, "Force", 1.26f, 1.0f, 3.0f, OptionSlider.SliderType.NULL);
        this.addOptions(HighJump.mode, HighJump.height, HighJump.force);
    }
    
    @Override
    public void onEnable() {
        if (HighJump.mode.isMode("ReallyWorld")) {
            if (HighJump.mc.h.z) {
                HighJump.mc.h.cu();
            }
            new Thread(() -> {
                HighJump.mc.h.t = 9.0;
                try {
                    Thread.sleep(260L);
                }
                catch (Exception ex) {}
                HighJump.mc.h.t = 8.742;
                this.toggle();
                return;
            }).start();
        }
        super.onEnable();
    }
    
    @Override
    public void updateElements() {
        HighJump.height.display = HighJump.mode.isMode("Default");
        HighJump.force.display = HighJump.mode.isMode("Matrix");
        super.updateElements();
    }
    
    @Override
    public void onDisable() {
        HighJump.mc.Y.speed = 1.0f;
        this.y = 0;
        super.onDisable();
    }
}
