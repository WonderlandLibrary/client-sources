package ez.h.features.movement;

import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class Jesus extends Feature
{
    OptionSlider tickSpeed;
    OptionSlider zoomSpeed;
    OptionSlider speed;
    OptionSlider clipSpeed;
    OptionMode mode;
    
    @Override
    public void updateElements() {
        this.clipSpeed.display = this.mode.isMode("ReallyWorld2");
        this.zoomSpeed.display = this.mode.isMode("Zoom");
        this.tickSpeed.display = this.mode.isMode("Tick");
        this.speed.display = this.mode.isMode("ReallyWorld");
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (!this.isEnabled()) {
            return;
        }
        this.setSuffix(this.mode.getMode());
        final String mode = this.mode.getMode();
        switch (mode) {
            case "ReallyWorld": {
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q + 0.05, Jesus.mc.h.r)).u() == aox.j) {
                    Jesus.mc.h.t = 0.05000000074505806;
                    break;
                }
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q - 0.01, Jesus.mc.h.r)).u() != aox.j) {
                    break;
                }
                Jesus.mc.h.t = 0.0;
                eventMotion.y += ((Jesus.mc.h.T % 2 == 0) ? 0.01 : -0.01);
                if (Jesus.mc.h.T % 2 == 0) {
                    Utils.setMotion(this.speed.getNum());
                }
                else {
                    Utils.setMotion(0.10000000149011612);
                }
                Jesus.mc.h.aR = 0.0f;
                if (Jesus.mc.h.A) {
                    Jesus.mc.h.bT = 0.020021f;
                    Jesus.mc.h.t = 0.10000000149011612;
                    final bud h = Jesus.mc.h;
                    h.t *= 1.0499999523162842;
                    break;
                }
                break;
            }
            case "ReallyWorld2": {
                final float[] array = { (float)(-Math.sin(Math.toRadians(Jesus.mc.h.v))), (float)Math.cos(Math.toRadians(Jesus.mc.h.v)) };
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p + array[0] * this.clipSpeed.getNum() * 1.2f, Jesus.mc.h.q, Jesus.mc.h.r + array[1] * this.clipSpeed.getNum() * 1.2f)).u() != aox.j) {
                    break;
                }
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q + 0.05, Jesus.mc.h.r)).u() == aox.j) {
                    Jesus.mc.h.t = 0.10000000149011612;
                    break;
                }
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q, Jesus.mc.h.r)).u() == aox.j) {
                    Jesus.mc.h.t = 0.0;
                    eventMotion.y += ((Jesus.mc.h.T % 2 == 0) ? 0.01 : -0.01);
                    final float[] array2 = { (float)(-Math.sin(Math.toRadians(Jesus.mc.h.v))), (float)Math.cos(Math.toRadians(Jesus.mc.h.v)) };
                    Jesus.mc.h.b(Jesus.mc.h.p + array2[0] * this.clipSpeed.getNum(), Jesus.mc.h.q, Jesus.mc.h.r + array2[1] * this.clipSpeed.getNum());
                    Jesus.mc.h.aR = 0.0f;
                    Jesus.mc.h.bT = 0.0f;
                    Jesus.mc.h.aG = 0.9f;
                    break;
                }
                break;
            }
            case "Tick": {
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q, Jesus.mc.h.r)).u() != aox.j) {
                    break;
                }
                Jesus.mc.h.h(0.0, 0.0395, 0.0);
                Jesus.mc.h.bT = this.tickSpeed.getNum() / 1.345f;
                Jesus.mc.h.aR = 0.0f;
                if (Jesus.mc.h.A) {
                    Jesus.mc.h.bT = 0.020021f;
                    Jesus.mc.h.t = 0.10000000149011612;
                    final bud h2 = Jesus.mc.h;
                    h2.t *= 1.0499999523162842;
                    break;
                }
                break;
            }
            case "Zoom": {
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q - 0.15, Jesus.mc.h.r)).u() != aox.j) {
                    break;
                }
                Utils.setMotion(this.zoomSpeed.getNum());
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q + 1.0E-7, Jesus.mc.h.r)).u() != aow.c(9)) {
                    break;
                }
                Jesus.mc.h.L = 0.0f;
                Jesus.mc.h.s = 0.0;
                Jesus.mc.h.t = 0.05999999865889549;
                Jesus.mc.h.u = 0.0;
                if (Jesus.mc.h.T % (0xA9 ^ 0xBD) == 0) {
                    Jesus.mc.Y.b = 50.0f;
                    break;
                }
                break;
            }
            case "Float": {
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q + 1.0, Jesus.mc.h.r)).u() == aox.j) {
                    Jesus.mc.h.t = 0.18000000715255737;
                    break;
                }
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q + 1.0E-6, Jesus.mc.h.r)).u() == aox.j) {
                    Jesus.mc.h.L = 0.0f;
                    Jesus.mc.h.setMotion(0.0f);
                    Jesus.mc.h.t = 0.10000000149011612;
                    Jesus.mc.h.aR = 1.04f;
                    final bud h3 = Jesus.mc.h;
                    h3.q += 0.1;
                    final bud h4 = Jesus.mc.h;
                    h4.q -= 0.1;
                    break;
                }
                break;
            }
            case "Jump": {
                if (Jesus.mc.h.ao()) {
                    Jesus.mc.h.cu();
                    break;
                }
                break;
            }
            case "Ground": {
                if (Jesus.mc.f.o(new et(Jesus.mc.h.p, Jesus.mc.h.q - 0.10000000149011612, Jesus.mc.h.r)).u() == aox.j) {
                    Jesus.mc.h.t = -1.0E-6;
                    eventMotion.setOnGround(true);
                    eventMotion.y += 1.0E-4;
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public void onDisable() {
        Jesus.mc.h.d(false);
        Jesus.mc.h.aR = 0.02f;
        Jesus.mc.h.bT = 0.02f;
        Jesus.mc.Y.speed = 1.0f;
        super.onDisable();
    }
    
    public Jesus() {
        super("Jesus", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0445\u043e\u0434\u0438\u0442\u044c \u043f\u043e \u0432\u043e\u0434\u0435.", Category.MOVEMENT);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "ReallyWorld", "ReallyWorld2", "Tick", "Zoom", "Float", "Jump", "Ground" }, 0);
        this.speed = new OptionSlider(this, "Speed", 1.1f, 0.1f, 1.1f, OptionSlider.SliderType.BPS);
        this.clipSpeed = new OptionSlider(this, "Clip Speed", 1.0f, 0.0f, 1.0f, OptionSlider.SliderType.BPS);
        this.zoomSpeed = new OptionSlider(this, "Zoom Speed", 5.0f, 1.0f, 6.0f, OptionSlider.SliderType.BPS);
        this.tickSpeed = new OptionSlider(this, "Tick Speed", 5.0f, 1.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.mode, this.speed, this.tickSpeed, this.zoomSpeed, this.clipSpeed);
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
    }
}
