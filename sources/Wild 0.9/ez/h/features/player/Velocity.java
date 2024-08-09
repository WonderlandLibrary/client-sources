package ez.h.features.player;

import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class Velocity extends Feature
{
    OptionSlider reverseFactor;
    OptionMode mode;
    OptionSlider vertical;
    OptionSlider horizontal;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.mode.isMode("Matrix")) {
            return;
        }
        if (Velocity.mc.h.ay <= 0) {
            return;
        }
        if (this.mode.isMode("Vanilla")) {
            final bud h = Velocity.mc.h;
            h.s *= this.horizontal.getNum() / 100.0f;
            final bud h2 = Velocity.mc.h;
            h2.u *= this.horizontal.getNum() / 100.0f;
            final bud h3 = Velocity.mc.h;
            h3.t *= this.vertical.getNum() / 100.0f;
        }
        if (this.mode.isMode("Nexus") && Velocity.mc.h.ay > 1 && Velocity.mc.h.ay < (0x22 ^ 0x28)) {
            final bud h4 = Velocity.mc.h;
            h4.s *= 0.7;
            final bud h5 = Velocity.mc.h;
            h5.u *= 0.7;
            if (!Velocity.mc.h.z && Velocity.mc.h.ay % 2 == 0) {
                Velocity.mc.h.s = MathUtils.lerp((float)Velocity.mc.h.s, 0.0f, 0.1f);
                Velocity.mc.h.u = MathUtils.lerp((float)Velocity.mc.h.u, 0.0f, 0.1f);
            }
        }
        if (this.mode.isMode("AAC") && Velocity.mc.h.ay < (0x40 ^ 0x4A)) {
            Velocity.mc.h.setMotion((Velocity.mc.h.T % 3 == 0) ? 0.6f : 0.5f);
        }
        if (this.mode.isMode("Reverse") && Velocity.mc.h.ay == 5) {
            if (Velocity.mc.h.z) {
                Velocity.mc.h.setMotion(0.5f);
            }
            else {
                final bud h6 = Velocity.mc.h;
                h6.u *= -this.reverseFactor.getNum();
                final bud h7 = Velocity.mc.h;
                h7.s *= -this.reverseFactor.getNum();
            }
        }
    }
    
    public Velocity() {
        super("Velocity", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u043f\u043e\u043b\u0443\u0447\u0430\u0435\u043c\u0443\u044e \u043e\u0442\u0434\u0430\u0447\u0443.", Category.PLAYER);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Reverse", "AAC", "Vanilla", "Nexus" }, 0);
        this.vertical = new OptionSlider(this, "Vertical", 0.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.horizontal = new OptionSlider(this, "Horizontal", 0.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.reverseFactor = new OptionSlider(this, "Factor", 1.0f, 0.0f, 3.0f, OptionSlider.SliderType.NULL);
        this.addOptions(this.mode, this.vertical, this.horizontal, this.reverseFactor);
    }
    
    @EventTarget
    public void onEvent(final EventPacketReceive eventPacketReceive) {
        this.setSuffix(this.mode.getMode());
        if (!this.mode.isMode("Matrix")) {
            return;
        }
        if (eventPacketReceive.getPacket() instanceof kf && ((kf)eventPacketReceive.getPacket()).a() == Velocity.mc.h.S()) {
            eventPacketReceive.setCancelled(true);
        }
    }
    
    @Override
    public void updateElements() {
        this.vertical.display = this.mode.isMode("Vanilla");
        this.horizontal.display = this.mode.isMode("Vanilla");
        this.reverseFactor.display = this.mode.isMode("Reverse");
    }
}
