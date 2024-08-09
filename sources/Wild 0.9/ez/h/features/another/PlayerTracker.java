package ez.h.features.another;

import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class PlayerTracker extends Feature
{
    public OptionSlider radius;
    
    @EventTarget
    public void onFindReceive(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof ij) {
            final ij ij = (ij)eventPacketReceive.getPacket();
            this.radius.setNum(10000.0f);
            PlayerTracker.mc.h.a((hh)new ho(a.m + "[PlayerTracker] Player found on > " + ij.b().p() + " " + ij.b().r()));
        }
    }
    
    @EventTarget
    public void onFind(final EventMotion eventMotion) {
        PlayerTracker.mc.h.d.a((ht)new lp(lp.a.a, new et(MathUtils.nextInt((int)(-this.radius.getNum()), (int)this.radius.getNum()), 0, MathUtils.nextInt((int)(-this.radius.getNum()), (int)this.radius.getNum())), fa.a));
    }
    
    public PlayerTracker() {
        super("PlayerTracker", "\u041f\u043e\u0438\u0441\u043a \u0438\u0433\u0440\u043e\u043a\u043e\u0432 \u043f\u043e \u043c\u0438\u0440\u0443.", Category.ANOTHER);
        this.radius = new OptionSlider(this, "Radius", 0.0f, 100.0f, 5000.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.radius);
    }
}
