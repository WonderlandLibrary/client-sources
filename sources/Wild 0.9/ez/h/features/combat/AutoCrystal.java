package ez.h.features.combat;

import ez.h.utils.*;
import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class AutoCrystal extends Feature
{
    private Counter counter;
    private OptionSlider range;
    private OptionSlider fov;
    private OptionSlider delay;
    
    private abc findEnderCrystal() {
        return (abc)AutoCrystal.mc.f.e.stream().filter(vg -> vg instanceof abc && MathUtils.canSeeEntityAtFov(vg, this.fov.getNum()) && AutoCrystal.mc.h.getDistance(vg) <= this.range.getNum()).findFirst().orElse(null);
    }
    
    private void changeRotation(final EventMotion eventMotion, final abc abc) {
        final float[] defaultRotations = MathUtils.Rotations.getDefaultRotations(abc.p, abc.q, abc.r);
        final float n = defaultRotations[0];
        final float n2 = defaultRotations[1];
        eventMotion.yaw = n;
        eventMotion.pitch = n2;
        AutoCrystal.mc.h.aP = n;
        AutoCrystal.mc.h.rotationPitchHead = n2;
        AutoCrystal.mc.h.aN = n;
        AutoCrystal.mc.h.aO = n;
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        final abc enderCrystal = this.findEnderCrystal();
        if (enderCrystal == null || !this.counter.isDelay((long)this.delay.getNum())) {
            return;
        }
        this.changeRotation(eventMotion, enderCrystal);
        this.attackCrystal(enderCrystal);
        this.counter.reset();
    }
    
    private void attackCrystal(final abc abc) {
        AutoCrystal.mc.c.a((aed)AutoCrystal.mc.h, (vg)abc);
        AutoCrystal.mc.h.a(ub.a);
    }
    
    public AutoCrystal() {
        super("AutoCrystal", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0432\u0437\u0440\u044b\u0432\u0430\u0435\u0442 \u043f\u043e\u0441\u0442\u0430\u0432\u043b\u0435\u043d\u043d\u044b\u0435 \u043a\u0440\u0438\u0441\u0442\u0430\u043b\u044b", Category.COMBAT);
        this.counter = new Counter();
        this.range = new OptionSlider(this, "Range", 3.0f, 3.0f, 6.0f, OptionSlider.SliderType.M);
        this.delay = new OptionSlider(this, "Delay", 150.0f, 50.0f, 1000.0f, OptionSlider.SliderType.NULLINT);
        this.fov = new OptionSlider(this, "FOV", 120.0f, 50.0f, 360.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.range, this.delay, this.fov);
    }
}
