package ez.h.features.movement;

import java.util.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Flight extends Feature
{
    OptionMode mode;
    OptionSlider matrixSpeed;
    Random random;
    OptionSlider vspeed;
    
    @Override
    public void updateElements() {
        this.vspeed.display = this.mode.isMode("Vanilla");
        this.matrixSpeed.display = this.mode.isMode("Matrix");
        super.updateElements();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
        if (!this.isEnabled()) {
            return;
        }
        final String mode = this.mode.getMode();
        switch (mode) {
            case "StormHVH": {
                if (Flight.mc.t.X.i) {
                    Flight.mc.h.cu();
                    break;
                }
                break;
            }
            case "Matrix": {
                if (Flight.mc.t.X.i) {
                    Flight.mc.h.t = 0.42;
                    Flight.mc.h.z = true;
                    Utils.setMotion(Utils.getSpeed() * (1.0f + this.matrixSpeed.getNum()));
                    eventMotion.setOnGround(true);
                    break;
                }
                break;
            }
            case "Vanilla": {
                Flight.mc.h.H = 10.0f;
                Flight.mc.h.bO.a(this.vspeed.getNum());
                Flight.mc.h.bO.b = true;
                Flight.mc.h.bO.c = true;
                break;
            }
            case "RTP": {
                if (((Flight.mc.h.A || Flight.mc.h.B) && Flight.mc.h.C) || Flight.mc.h.ao() || Flight.mc.h.au()) {
                    break;
                }
                Flight.mc.h.f(false);
                if (Flight.mc.h.t < -0.037) {
                    Flight.mc.h.t = -(this.random.nextDouble() / 50.0 + 0.005149);
                    Utils.setMotion(6.275000095367432);
                    Flight.mc.h.bO.a(6.275f);
                }
                if (Flight.mc.h.t > 0.0) {
                    Flight.mc.h.s = 0.0;
                    Utils.setMotion(Flight.mc.h.u = 0.0);
                    break;
                }
                break;
            }
        }
    }
    
    public Flight() {
        super("Flight", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u043b\u0435\u0442\u0430\u0442\u044c.", Category.MOVEMENT);
        this.random = new Random();
        this.mode = new OptionMode(this, "Mode", "RTP", new String[] { "RTP", "StormHVH", "Matrix", "PikaNetwork", "Vanilla" }, 0);
        this.vspeed = new OptionSlider(this, "Vanilla Speed", 0.2f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        this.matrixSpeed = new OptionSlider(this, "Matrix Speed", 0.0f, 0.0f, 1.0f, OptionSlider.SliderType.BPS);
        this.addOptions(this.mode, this.vspeed, this.matrixSpeed);
    }
    
    @Override
    public void onDisable() {
        Flight.mc.h.bO.c = false;
        Flight.mc.h.bO.b = false;
        Flight.mc.h.bO.a(0.2f);
        Flight.mc.h.aR = 0.02f;
        Flight.mc.h.bT = 0.02f;
        Flight.mc.h.d(false);
        super.onDisable();
    }
}
