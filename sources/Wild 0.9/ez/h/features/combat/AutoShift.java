package ez.h.features.combat;

import ez.h.utils.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.*;
import ez.h.event.events.*;

public class AutoShift extends Feature
{
    Counter counter;
    OptionSlider delay;
    boolean shiftState;
    
    public AutoShift() {
        super("AutoShift", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u0440\u0438\u0441\u0435\u0434\u0430\u0435\u0442 \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u0430\u0442\u0430\u043a\u0438.", Category.COMBAT);
        this.counter = new Counter();
        this.shiftState = false;
        this.delay = new OptionSlider(this, "Delay", 150.0f, 1.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.delay);
    }
    
    @EventTarget
    public void onUpdate(final EventLivingUpdate eventLivingUpdate) {
        if (this.counter.isDelay((long)this.delay.getNum())) {
            if (this.shiftState) {
                AutoShift.mc.t.Y.i = false;
                this.shiftState = false;
            }
            this.counter.setLastMS();
        }
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (!(eventPacketSend.getPacket() instanceof li)) {
            return;
        }
        if (!AutoShift.mc.h.z && !AutoShift.mc.h.ao() && ((li)eventPacketSend.getPacket()).a() == li.a.b && !AutoShift.mc.h.aU()) {
            AutoShift.mc.t.Y.i = true;
            this.shiftState = true;
            this.counter.setLastMS();
        }
    }
}
