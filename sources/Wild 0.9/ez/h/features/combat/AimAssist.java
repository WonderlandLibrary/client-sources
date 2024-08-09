package ez.h.features.combat;

import ez.h.event.events.*;
import ez.h.utils.*;
import org.lwjgl.input.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import java.util.*;
import java.util.function.*;

public class AimAssist extends Feature
{
    OptionSlider range;
    List<aed> targets;
    OptionSlider speed;
    OptionSlider random;
    aed target;
    OptionMode mouseButton;
    OptionSlider fov;
    OptionBoolean yaw;
    OptionBoolean pitch;
    
    void collectTargets() {
        this.targets.clear();
        for (final aed aed : AimAssist.mc.f.i) {
            if (this.isValid(aed)) {
                this.targets.add(aed);
            }
        }
    }
    
    @EventTarget
    public void onEvent(final EventRender3D eventRender3D) {
        if (AimAssist.mc.h == null || AimAssist.mc.f == null) {
            return;
        }
        this.collectTargets();
        this.sortTargets();
        final float n = (this.random.getNum() != 0.0f) ? MathUtils.nextFloat(-this.random.getNum(), this.random.getNum()) : 0.0f;
        this.target = (this.targets.isEmpty() ? null : this.targets.get(0));
        if (this.target == null || AimAssist.mc.m != null) {
            return;
        }
        if (!MathUtils.canSeeEntityAtFov((vg)this.target, this.fov.getNum())) {
            return;
        }
        if ((this.mouseButton.isMode("Left") && Mouse.isButtonDown(0)) || (this.mouseButton.isMode("Right") && Mouse.isButtonDown(1))) {
            if (this.yaw.enabled) {
                AimAssist.mc.h.v = MathUtils.lerp(AimAssist.mc.h.v, MathUtils.Rotations.getDefaultRotations(this.target)[0], (this.speed.getNum() + n / 5.0f) / 200.0f);
            }
            if (this.pitch.enabled) {
                AimAssist.mc.h.w = MathUtils.lerp(AimAssist.mc.h.w, MathUtils.Rotations.getDefaultRotations(this.target)[1], (this.speed.getNum() + n / 5.0f) / 200.0f);
            }
        }
    }
    
    @Override
    public void updateElements() {
    }
    
    public AimAssist() {
        super("AimAssist", "\u041f\u043b\u0430\u0432\u043d\u0430\u044f \u043d\u0430\u0432\u043e\u0434\u043a\u0430 \u043d\u0430 \u0431\u043b\u0438\u0436\u0430\u0439\u0448\u0435\u0433\u043e \u0438\u0433\u0440\u043e\u043a\u0430.", Category.COMBAT);
        this.targets = new ArrayList<aed>();
        this.mouseButton = new OptionMode(this, "Mouse Button", "Left", new String[] { "Left", "Right" }, 0);
        this.yaw = new OptionBoolean(this, "Yaw", true);
        this.pitch = new OptionBoolean(this, "Pitch", true);
        this.fov = new OptionSlider(this, "FOV", 360.0f, 0.0f, 360.0f, OptionSlider.SliderType.NULLINT);
        this.range = new OptionSlider(this, "Range", 3.2f, 0.01f, 6.0f, OptionSlider.SliderType.M);
        this.random = new OptionSlider(this, "Random", 1.5f, 0.01f, 5.0f, OptionSlider.SliderType.NULL);
        this.speed = new OptionSlider(this, "Aim Speed", 1.0f, 0.01f, 4.0f, OptionSlider.SliderType.NULL);
        this.addOptions(this.mouseButton, this.yaw, this.pitch, this.fov, this.range, this.random, this.speed);
    }
    
    boolean isValid(final aed aed) {
        return aed != AimAssist.mc.h && !aed.F && aed.getDistance((vg)AimAssist.mc.h) <= this.range.getNum() && aed.cd() != 0.0f && !aed.aX();
    }
    
    void sortTargets() {
        this.targets.sort(Comparator.comparingDouble((ToDoubleFunction<? super aed>)MathUtils::getAngleChange));
    }
}
