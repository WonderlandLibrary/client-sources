package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class NoRotateSet extends Feature
{
    OptionMode mode;
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        this.setSuffix(this.mode.getMode());
        if (NoRotateSet.mc.h == null) {
            return;
        }
        if (this.mode.isMode("Default")) {
            if (eventPacketReceive.getPacket() instanceof jq) {
                if (eventPacketReceive.getPacket() == null) {
                    return;
                }
                ((jq)eventPacketReceive.getPacket()).d = NoRotateSet.mc.h.v;
                ((jq)eventPacketReceive.getPacket()).e = NoRotateSet.mc.h.w;
            }
        }
        else if (eventPacketReceive.getPacket() instanceof kt) {
            final byte e = (byte)(NoRotateSet.mc.h.v * 256.0f / 360.0f);
            final byte f = (byte)(NoRotateSet.mc.h.w * 256.0f / 360.0f);
            ((kt)eventPacketReceive.getPacket()).e = e;
            ((kt)eventPacketReceive.getPacket()).f = f;
        }
    }
    
    public NoRotateSet() {
        super("NoRotateSet", "\u0421\u0435\u0440\u0432\u0435\u0440 \u043d\u0435 \u0441\u043c\u043e\u0436\u0435\u0442 \u043f\u043e\u0432\u0435\u0440\u043d\u0443\u0442\u044c \u0432\u0430\u043c \u0433\u043e\u043b\u043e\u0432\u0443.", Category.ANOTHER);
        this.mode = new OptionMode(this, "Mode", "Default", new String[] { "Default", "Intave" }, 0);
        this.addOptions(this.mode);
    }
}
