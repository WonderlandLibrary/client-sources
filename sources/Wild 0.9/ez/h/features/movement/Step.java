package ez.h.features.movement;

import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Step extends Feature
{
    OptionSlider height;
    OptionBoolean groundSpoof;
    OptionSlider clipheight;
    OptionMode mode;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.mode.isMode("Matrix")) {
            Step.mc.Y.speed = 1.0f;
            if (Step.mc.h.A) {
                if (Step.mc.h.z) {
                    Step.mc.h.cu();
                }
                else if (Step.mc.h.t < 0.04) {
                    final bud h = Step.mc.h;
                    h.t += 0.08;
                }
                if (Step.mc.h.t > 0.03) {
                    Step.mc.h.b(Step.mc.h.p, Step.mc.h.q - 0.001, Step.mc.h.r);
                    Step.mc.h.z = true;
                    if (Step.mc.h.bf > 0.0f) {
                        Utils.setMotion(0.25);
                    }
                }
            }
        }
        if (this.mode.isMode("Timer")) {
            if (Step.mc.h.A && Step.mc.h.be == 0.0f) {
                if (this.groundSpoof.enabled) {
                    eventMotion.setOnGround(true);
                }
                Step.mc.h.z = true;
                Step.mc.h.s = 0.0;
                Step.mc.h.u = 0.0;
                Utils.setTimer(3.0f);
                Step.mc.h.t = 0.42;
            }
            else {
                Utils.setTimer(0.0f);
            }
        }
        if (!Step.mc.h.A) {
            return;
        }
        if (this.groundSpoof.enabled) {
            eventMotion.setOnGround(true);
        }
        if (this.mode.isMode("Vanilla")) {
            Step.mc.h.P = this.height.getNum();
        }
        if (this.mode.isMode("Motion")) {
            if (!Step.mc.h.z) {
                Step.mc.h.t = 1.0;
                Utils.setMotion(0.20000000298023224);
            }
            else {
                Step.mc.h.setMotion(0.0f);
                final bud h2 = Step.mc.h;
                h2.t -= 0.20000000298023224;
            }
        }
        if (this.mode.isMode("Clip")) {
            Step.mc.h.a(Step.mc.h.p, Step.mc.h.q + this.clipheight.getNum(), Step.mc.h.r);
        }
    }
    
    public Step() {
        super("Step", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u043c\u043e\u043c\u0435\u043d\u0442\u0430\u043b\u044c\u043d\u043e \u0437\u0430\u0448\u0430\u0433\u0438\u0432\u0430\u0442\u044c \u043d\u0430 \u0431\u043b\u043e\u043a\u0438.", Category.MOVEMENT);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Timer", "Motion", "Clip", "Vanilla" }, 0);
        this.groundSpoof = new OptionBoolean(this, "Ground Spoof", true);
        this.height = new OptionSlider(this, "Height", 1.0f, 0.0f, 3.0f, OptionSlider.SliderType.M);
        this.clipheight = new OptionSlider(this, "Clip Height", 1.0f, 0.0f, 3.0f, OptionSlider.SliderType.M);
        this.addOptions(this.mode, this.groundSpoof, this.height, this.clipheight);
    }
    
    @Override
    public void updateElements() {
        this.height.display = this.mode.isMode("Vanilla");
        this.clipheight.display = this.mode.isMode("Clip");
        super.updateElements();
    }
}
