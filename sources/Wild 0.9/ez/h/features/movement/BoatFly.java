package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;

public class BoatFly extends Feature
{
    OptionSlider downSpeed;
    OptionSlider upSpeed;
    OptionSlider boost;
    OptionSlider speed;
    OptionBoolean nexus;
    
    public BoatFly() {
        super("BoatFly", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u043b\u0435\u0442\u0430\u0442\u044c \u043d\u0430 \u043b\u043e\u0434\u043a\u0435.", Category.MOVEMENT);
        this.speed = new OptionSlider(this, "Speed", 2.0f, 0.01f, 4.0f, OptionSlider.SliderType.BPS);
        this.boost = new OptionSlider(this, "Boost", 1.5f, 0.0f, 3.0f, OptionSlider.SliderType.NULL);
        this.upSpeed = new OptionSlider(this, "Up Speed", 1.0f, 0.01f, 2.0f, OptionSlider.SliderType.BPS);
        this.downSpeed = new OptionSlider(this, "Down Speed", 0.15f, 0.01f, 2.0f, OptionSlider.SliderType.BPS);
        this.nexus = new OptionBoolean(this, "Nexus", true);
        this.addOptions(this.speed, this.boost, this.upSpeed, this.downSpeed, this.nexus);
    }
    
    @EventTarget
    public void onLiving(final EventLivingUpdate eventLivingUpdate) {
        if (BoatFly.mc.h == null || BoatFly.mc.f == null) {
            return;
        }
        if (this.nexus.enabled) {
            final vg vg3 = (vg)BoatFly.mc.f.e.stream().filter(vg -> vg instanceof afd).filter(vg2 -> BoatFly.mc.h.getDistance(vg2) < 6.0f).findAny().orElse(null);
            if (vg3 != null && BoatFly.mc.h.bJ() == null) {
                BoatFly.mc.h.d.a((ht)new li(vg3, ub.a));
            }
        }
        if (BoatFly.mc.h.bJ() != null) {
            final float[] array = { (float)(-Math.sin(Utils.getDirection())), (float)Math.cos(Utils.getDirection()) };
            BoatFly.mc.h.bJ().t = 0.0;
            if (BoatFly.mc.t.X.i) {
                BoatFly.mc.h.bJ().t = this.upSpeed.getNum();
            }
            if (BoatFly.mc.t.Y.i) {
                BoatFly.mc.h.bJ().t = -this.downSpeed.getNum();
            }
            final float n = BoatFly.mc.t.Z.i ? (this.speed.getNum() * this.boost.getNum()) : this.speed.getNum();
            if (BoatFly.mc.h.isMoving()) {
                BoatFly.mc.h.bJ().s = array[0] * n;
                BoatFly.mc.h.bJ().u = array[1] * n;
            }
        }
    }
}
