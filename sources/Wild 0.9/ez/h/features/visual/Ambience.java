package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.*;
import ez.h.event.events.*;

public class Ambience extends Feature
{
    OptionMode time;
    
    public Ambience() {
        super("Ambience", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c \u0432\u0440\u0435\u043c\u044f \u0432 \u043c\u0438\u0440\u0435.", Category.VISUAL);
        this.addOptions(this.time = new OptionMode(this, "Time", "Morning", new String[] { "Morning", "Day", "Evening", "Night" }, 0));
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof ko) {
            eventPacketReceive.setCancelled(true);
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.time.getMode());
        long n = 0L;
        final String mode = this.time.getMode();
        switch (mode) {
            case "Morning": {
                n = 23500L;
                break;
            }
            case "Day": {
                n = 5000L;
                break;
            }
            case "Evening": {
                n = 13188L;
                break;
            }
            case "Night": {
                n = 18000L;
                break;
            }
        }
        Ambience.mc.f.b(n);
    }
}
