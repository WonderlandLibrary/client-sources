package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class DamageFly extends Feature
{
    float delta;
    OptionMode mode;
    boolean damage;
    int motion;
    
    float getSpeed() {
        return (float)Math.sqrt(DamageFly.mc.h.s * DamageFly.mc.h.s + DamageFly.mc.h.u * DamageFly.mc.h.u);
    }
    
    public DamageFly() {
        super("DamageFly", "\u041f\u043e\u043b\u0451\u0442 \u0432\u043e \u0432\u0440\u0435\u0438\u044f \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u0438\u044f \u0443\u0440\u043e\u043d\u0430.", Category.MOVEMENT);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Matrix2", "Jump" }, 0);
        this.addOptions(this.mode);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        final String mode = this.mode.getMode();
        switch (mode) {
            case "Matrix": {
                if (DamageFly.mc.h.ay == 9 && DamageFly.mc.h.z) {
                    DamageFly.mc.h.bT = 0.2f;
                    this.damage = true;
                }
                if (this.damage) {
                    DamageFly.mc.h.t = 0.3610000014305115;
                    DamageFly.mc.h.aR = 0.42f;
                    Utils.setMotion(Math.hypot(DamageFly.mc.h.s, DamageFly.mc.h.u));
                    ++this.motion;
                }
                if (this.motion >= (0xB9 ^ 0xA2)) {
                    DamageFly.mc.h.aR = 0.02f;
                    DamageFly.mc.h.bT = 0.02f;
                    this.damage = false;
                    this.motion = 0;
                    this.toggle();
                    break;
                }
                break;
            }
            case "Matrix2": {
                if (DamageFly.mc.h.ay > 0) {
                    DamageFly.mc.h.t = 0.4000000059604645;
                    DamageFly.mc.h.bT = 0.65f;
                    break;
                }
                DamageFly.mc.h.t = -0.019999999552965164;
                break;
            }
            case "Jump": {
                if (DamageFly.mc.h.ay != 0) {
                    final bud h = DamageFly.mc.h;
                    h.t += 0.20000000298023224;
                    final bud h2 = DamageFly.mc.h;
                    h2.t *= 1.100000023841858;
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void onDisable() {
        DamageFly.mc.h.aR = 0.02f;
        DamageFly.mc.h.bT = 0.02f;
        DamageFly.mc.Y.speed = 1.0f;
        this.motion = 0;
        this.damage = false;
        DamageFly.mc.h.bO.b = false;
        DamageFly.mc.h.bO.c = false;
        super.onDisable();
    }
}
