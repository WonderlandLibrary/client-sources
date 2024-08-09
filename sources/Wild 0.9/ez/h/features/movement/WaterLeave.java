package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class WaterLeave extends Feature
{
    OptionBoolean autoStuck;
    OptionBoolean autoDisable;
    boolean goStuck;
    OptionSlider pulse;
    OptionBoolean matrixYPort;
    
    public WaterLeave() {
        super("WaterLeave", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0432\u044b\u0441\u043a\u043e\u0433\u043e \u043f\u0440\u044b\u0433\u0430\u0442\u044c \u0441 \u0432\u043e\u0434\u044b.", Category.MOVEMENT);
        this.matrixYPort = new OptionBoolean(this, "Matrix YPort", true);
        this.autoStuck = new OptionBoolean(this, "Auto Stack", true);
        this.pulse = new OptionSlider(this, "Pulse", 10.0f, 0.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.autoDisable = new OptionBoolean(this, "Auto Disable", true);
        this.addOptions(this.matrixYPort, this.autoStuck, this.pulse, this.autoDisable);
    }
    
    @Override
    public void onDisable() {
        this.goStuck = false;
        WaterLeave.mc.Y.speed = 1.0f;
        WaterLeave.mc.h.F = false;
        super.onDisable();
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        final double p = WaterLeave.mc.h.p;
        final double q = WaterLeave.mc.h.q;
        final double r = WaterLeave.mc.h.r;
        if (WaterLeave.mc.f.o(new et(p, q - 0.0, r)).u() == aox.a && (WaterLeave.mc.f.o(new et(p, q - 1.0000001, r)).u() == aox.j || WaterLeave.mc.f.o(new et(p, q - 1.0000001, r)).u() == aox.l)) {
            final float num = this.pulse.getNum();
            if (WaterLeave.mc.h.L == 0.0f) {
                WaterLeave.mc.h.t = num - 0.2f;
                if (this.matrixYPort.enabled) {
                    WaterLeave.mc.Y.speed = 19.9f;
                }
                this.goStuck = this.autoStuck.enabled;
                if (this.autoDisable.enabled && !this.autoStuck.enabled) {
                    if (this.matrixYPort.enabled) {
                        WaterLeave.mc.Y.speed = 1.0f;
                    }
                    this.setEnabled(false);
                }
            }
        }
        if (WaterLeave.mc.h.L != 0.0f && this.autoStuck.enabled && this.goStuck) {
            WaterLeave.mc.h.F = true;
        }
        if (this.autoDisable.enabled && this.autoStuck.enabled && this.goStuck && WaterLeave.mc.h.F) {
            this.setEnabled(WaterLeave.mc.h.F = false);
            WaterLeave.mc.Y.speed = 1.0f;
        }
        if (WaterLeave.mc.h.L != 0.0f && this.matrixYPort.enabled) {
            WaterLeave.mc.Y.speed = 1.0f;
        }
        if (WaterLeave.mc.h.ao() || WaterLeave.mc.h.au()) {
            if (this.matrixYPort.enabled) {
                WaterLeave.mc.Y.speed = 9.99f;
            }
            WaterLeave.mc.t.X.i = false;
            WaterLeave.mc.h.t = 0.1899999976158142;
            WaterLeave.mc.h.z = true;
            WaterLeave.mc.h.s = 0.0;
            WaterLeave.mc.h.u = 0.0;
            WaterLeave.mc.h.aR = 0.0f;
            if (WaterLeave.mc.f.o(new et(p, q + 1.0, r)).u() == aox.a) {
                WaterLeave.mc.h.cu();
            }
        }
    }
}
