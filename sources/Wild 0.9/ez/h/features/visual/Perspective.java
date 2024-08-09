package ez.h.features.visual;

import ez.h.event.*;
import ez.h.features.*;
import ez.h.event.events.*;

public class Perspective extends Feature
{
    float prevYaw;
    float prevPitch;
    
    @Override
    public void onEnable() {
        if (Perspective.mc.h != null && Perspective.mc.f != null) {
            this.prevYaw = Perspective.mc.h.v;
            this.prevPitch = Perspective.mc.h.w;
        }
        super.onEnable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        eventMotion.yaw = this.prevYaw;
        eventMotion.pitch = this.prevPitch;
        Perspective.mc.t.aw = 2;
        Perspective.mc.h.aP = eventMotion.yaw;
        Perspective.mc.h.aN = eventMotion.yaw;
    }
    
    public Perspective() {
        super("Perspective", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0441\u043c\u043e\u0442\u0440\u0435\u0442\u044c \u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u0430 \u043e\u0442 \u0442\u0440\u0435\u0442\u044c\u0435\u0433\u043e \u043b\u0438\u0446\u0430 \u0431\u0435\u0437 \u043f\u043e\u0432\u043e\u0440\u043e\u0442\u0430 \u0433\u043e\u043b\u043e\u0432\u044b.", Category.VISUAL);
    }
    
    @EventTarget
    public void onPitchHead(final EventPitchHead eventPitchHead) {
        eventPitchHead.pitchHead = this.prevPitch;
    }
    
    @Override
    public void onDisable() {
        Perspective.mc.t.aw = 0;
        if (Perspective.mc.h != null && Perspective.mc.f != null) {
            this.prevYaw = Perspective.mc.h.v;
            this.prevPitch = Perspective.mc.h.w;
            super.onDisable();
            return;
        }
        super.onDisable();
    }
}
