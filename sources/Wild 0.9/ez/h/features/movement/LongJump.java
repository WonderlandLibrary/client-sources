package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class LongJump extends Feature
{
    OptionMode mode;
    OptionBoolean autoDisable;
    OptionSlider disableAfter;
    OptionBoolean timerBoost;
    OptionSlider timer;
    OptionSlider boost;
    OptionBoolean autoJump;
    
    @Override
    public void onDisable() {
        LongJump.mc.h.bT = 0.02f;
        LongJump.mc.Y.speed = 1.0f;
        super.onDisable();
    }
    
    public LongJump() {
        super("LongJump", "\u0411\u043e\u043b\u0435\u0435 \u0434\u0430\u043b\u0451\u043a\u0438\u0439 \u043f\u0440\u044b\u0436\u043e\u043a.", Category.MOVEMENT);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Matrix2", "Normal" }, 0);
        this.boost = new OptionSlider(this, "Boost", 0.3f, 0.1f, 1.0f, OptionSlider.SliderType.BPS);
        this.disableAfter = new OptionSlider(this, "Disable After", 800.0f, 300.0f, 1500.0f, OptionSlider.SliderType.MS);
        this.autoJump = new OptionBoolean(this, "Auto Jump", false);
        this.autoDisable = new OptionBoolean(this, "Auto Disable", false);
        this.timerBoost = new OptionBoolean(this, "Timer Boost", false);
        this.timer = new OptionSlider(this, "Timer", 2.0f, 1.0f, 5.0f, OptionSlider.SliderType.NULL);
        this.addOptions(this.mode, this.boost, this.disableAfter, this.autoJump, this.autoDisable, this.timerBoost, this.timer);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (!this.isEnabled()) {
            return;
        }
        if (this.mode.isMode("Matrix")) {
            if (LongJump.mc.h.ay == 1 && this.autoDisable.enabled) {
                this.toggle();
            }
            if (LongJump.mc.h.ay > 1) {
                LongJump.mc.h.bT = this.boost.getNum();
                if (this.autoJump.enabled && LongJump.mc.h.z) {
                    LongJump.mc.h.cu();
                }
                if (this.timerBoost.enabled) {
                    LongJump.mc.Y.speed = this.timer.getNum();
                }
            }
        }
        if (this.mode.isMode("Matrix2") && LongJump.mc.h.ay > 0) {
            LongJump.mc.h.t = 0.3100000023841858;
            LongJump.mc.Y.b = 2.5f;
            LongJump.mc.h.aR = 1.0f;
        }
        if (this.mode.isMode("Normal")) {
            LongJump.mc.h.bT = this.boost.getNum();
            if (this.timerBoost.enabled) {
                LongJump.mc.Y.speed = this.timer.getNum();
            }
        }
        if (LongJump.mc.h.bT == this.boost.getNum() && this.counter.hasReached(this.disableAfter.getNum())) {
            LongJump.mc.h.bT = 0.02f;
            LongJump.mc.t.X.i = false;
            this.counter.reset();
        }
    }
    
    @Override
    public void updateElements() {
        this.timer.display = this.timerBoost.enabled;
        super.updateElements();
    }
}
