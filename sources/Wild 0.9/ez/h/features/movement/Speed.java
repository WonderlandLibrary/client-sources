package ez.h.features.movement;

import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Speed extends Feature
{
    OptionSlider cmFallSpeed;
    OptionSlider cmSpeed;
    OptionSlider mulitpiler;
    OptionSlider timerBoostSpeed;
    OptionSlider motionY;
    int ticks;
    public OptionMode mode;
    OptionBoolean timerBoost;
    OptionSlider sunriseSpeed;
    float prevMotionY;
    public OptionSlider vanillaSpeed;
    
    @Override
    public void updateElements() {
        this.cmSpeed.display = this.mode.isMode("Custom");
        this.cmFallSpeed.display = this.mode.isMode("Custom");
        this.mulitpiler.display = this.mode.isMode("Custom");
        this.motionY.display = this.mode.isMode("Custom");
        this.vanillaSpeed.display = this.mode.isMode("VanillaHop");
        this.timerBoostSpeed.display = this.timerBoost.enabled;
        this.sunriseSpeed.display = this.mode.isMode("Sunrise");
    }
    
    @Override
    public void onDisable() {
        this.ticks = 0;
        Speed.mc.h.bT = 0.02f;
        Utils.setTimer(0.0f);
        Speed.mc.h.aR = 0.02f;
        super.onDisable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
        if (!this.isEnabled()) {
            return;
        }
        if (!Speed.mc.h.isMoving()) {
            if (this.timerBoost.enabled) {
                Utils.setTimer(0.0f);
            }
            return;
        }
        if (this.timerBoost.enabled) {
            Utils.setTimer(this.timerBoostSpeed.getNum());
        }
        this.direction(Speed.mc.h.v);
        final String mode = this.mode.getMode();
        switch (mode) {
            case "MatrixBHop": {
                if (!Speed.mc.h.isMoving() || Speed.mc.h.L >= 1.43f) {
                    break;
                }
                if (Speed.mc.h.z) {
                    Speed.mc.h.fixedJump();
                }
                if (Speed.mc.h.t == -0.22768848754498797) {
                    final bud h = Speed.mc.h;
                    h.s *= 1.0010000467300415;
                    final bud h2 = Speed.mc.h;
                    h2.u *= 1.0010000467300415;
                }
                if (Speed.mc.h.t == -0.4448259643949201) {
                    Speed.mc.h.t = -0.22768848754498797;
                    Utils.setMotion(Utils.getSpeed());
                    final bud h3 = Speed.mc.h;
                    h3.s *= 2.0;
                    final bud h4 = Speed.mc.h;
                    h4.u *= 2.0;
                    Speed.mc.h.b(Speed.mc.h.p - Math.sin(Math.toRadians(Speed.mc.h.v)) * 0.015, Speed.mc.h.q, Speed.mc.h.r + Math.cos(Math.toRadians(Speed.mc.h.v)) * 0.015);
                    break;
                }
                break;
            }
            case "MatrixGround": {
                if (Speed.mc.h.isMoving() && Speed.mc.h.z) {
                    Speed.mc.h.cu();
                    Speed.mc.h.t = 3.016261509042581E-11;
                    break;
                }
                break;
            }
            case "MatrixHop": {
                if (!Speed.mc.h.isMoving() || Speed.mc.h.L >= 1.43f) {
                    break;
                }
                if (Speed.mc.h.t == -0.4448259643949201) {
                    Utils.setMotion(this.getSpeed());
                }
                if (Speed.mc.h.z) {
                    Speed.mc.h.fixedJump();
                }
                if (Speed.mc.h.t == -0.4448259643949201) {
                    final bud h5 = Speed.mc.h;
                    h5.s *= 1.7999999523162842;
                    final bud h6 = Speed.mc.h;
                    h6.u *= 1.7999999523162842;
                    Speed.mc.h.d.a((ht)new lk(true));
                    Speed.mc.h.d.a((ht)new lk(false));
                    Speed.mc.h.b(Speed.mc.h.p - Math.sin(Math.toRadians(Speed.mc.h.v)) * 0.015, Speed.mc.h.q - 0.05, Speed.mc.h.r + Math.cos(Math.toRadians(Speed.mc.h.v)) * 0.015);
                    break;
                }
                break;
            }
            case "ReallyWorld": {
                if (Speed.mc.h.E || Speed.mc.h.m_() || Speed.mc.h.isInLiquid()) {
                    return;
                }
                if (Speed.mc.h.t == -0.4448259643949201) {
                    Utils.setMotion(this.getSpeed());
                }
                if (Speed.mc.h.t == -0.4448259643949201) {
                    final bud h7 = Speed.mc.h;
                    h7.s *= 2.0;
                    final bud h8 = Speed.mc.h;
                    h8.u *= 2.0;
                    Speed.mc.h.b(Speed.mc.h.p - Math.sin(Math.toRadians(Speed.mc.h.v)) * 0.015, Speed.mc.h.q - 0.1, Speed.mc.h.r + Math.cos(Math.toRadians(Speed.mc.h.v)) * 0.015);
                    break;
                }
                Utils.setMotion(this.getSpeed());
                break;
            }
            case "MatrixTickHop": {
                if (Debug.getOrCreateVar("canBoost", new Counter()).hasReached(1500.0f)) {
                    if (Speed.mc.f.o(Speed.mc.h.c().a(0.0, -1.2000000476837158, 0.0)).u() != aox.a || Speed.mc.h.A) {
                        Speed.mc.t.X.i = true;
                        Speed.mc.h.cu();
                        Utils.setMotion(Utils.getSpeed());
                        eventMotion.setOnGround(Speed.mc.h.z = true);
                    }
                    Debug.getOrCreateVar("canBoost", new Counter()).reset();
                    break;
                }
                Speed.mc.t.X.i = false;
                break;
            }
            case "YPort": {
                if (Speed.mc.h.z) {
                    Speed.mc.h.b(qs.w);
                    Speed.mc.h.cu();
                    Speed.mc.h.f(-Math.sin(Utils.getDirection()) * 0.05, -0.05000000074505806, Math.cos(Utils.getDirection()) * 0.05);
                    break;
                }
                Speed.mc.h.t = -1.0;
                break;
            }
            case "Sunrise": {
                if (Speed.mc.h.z || Speed.mc.h.ao()) {
                    Speed.mc.h.f(-Math.sin(Utils.getDirection()) * (0.25 * this.sunriseSpeed.getNum()), 0.0, Math.cos(Utils.getDirection()) * (0.25 * this.sunriseSpeed.getNum()));
                    break;
                }
                Speed.mc.h.f(-Math.sin(Utils.getDirection()) * (0.005 * this.sunriseSpeed.getNum()), 0.0, Math.cos(Utils.getDirection()) * (0.005 * this.sunriseSpeed.getNum()));
                break;
            }
            case "Custom": {
                Utils.setMotion(this.cmSpeed.getNum());
                Speed.mc.h.setMotion(this.mulitpiler.getNum());
                if (Speed.mc.h.z) {
                    Speed.mc.h.t = this.motionY.getNum();
                }
                if (Speed.mc.h.L > 0.0f) {
                    final bud h9 = Speed.mc.h;
                    h9.t -= this.cmFallSpeed.getNum();
                    break;
                }
                break;
            }
        }
    }
    
    float getSpeed() {
        return (float)Math.sqrt(Speed.mc.h.s * Speed.mc.h.s + Speed.mc.h.u * Speed.mc.h.u);
    }
    
    public Speed() {
        super("Speed", "\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0441\u043a\u043e\u0440\u043e\u0441\u0442\u044c \u043f\u0435\u0440\u0435\u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f \u0438\u0433\u0440\u043e\u043a\u0430.", Category.MOVEMENT);
        this.ticks = 0;
        this.mode = new OptionMode(this, "Mode", "MatrixBHop", new String[] { "MatrixBHop", "MatrixTickHop", "MatrixHop", "MatrixGround", "ReallyWorld", "Sunrise", "Custom", "YPort", "VanillaHop" }, 0);
        this.cmSpeed = new OptionSlider(this, "Speed", 0.25f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        this.cmFallSpeed = new OptionSlider(this, "Fall Speed", 0.1f, 0.0f, 1.0f, OptionSlider.SliderType.BPS);
        this.mulitpiler = new OptionSlider(this, "Multiplier", 1.01f, 0.0f, 2.0f, OptionSlider.SliderType.NULL);
        this.motionY = new OptionSlider(this, "MotionY", 0.42f, 0.0f, 1.0f, OptionSlider.SliderType.BPS);
        this.timerBoost = new OptionBoolean(this, "Timer Boost", false);
        this.timerBoostSpeed = new OptionSlider(this, "Timer Speed", 1.0f, 0.01f, 4.0f, OptionSlider.SliderType.NULL);
        this.vanillaSpeed = new OptionSlider(this, "Vanilla Speed", 0.2f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        this.sunriseSpeed = new OptionSlider(this, "Sunrise Speed", 1.0f, 1.0f, 5.0f, OptionSlider.SliderType.BPS);
        this.addOptions(this.mode, this.cmSpeed, this.cmFallSpeed, this.mulitpiler, this.motionY, this.vanillaSpeed, this.timerBoost, this.timerBoostSpeed, this.sunriseSpeed);
    }
    
    bhe direction(final float n) {
        return new bhe(Math.cos(Math.toRadians(n + 90.0f)), 0.0, Math.sin(Math.toRadians(n + 90.0f)));
    }
}
