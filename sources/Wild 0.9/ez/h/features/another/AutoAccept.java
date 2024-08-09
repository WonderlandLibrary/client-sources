package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.managers.*;
import java.util.*;
import ez.h.event.*;
import ez.h.features.*;

public class AutoAccept extends Feature
{
    @EventTarget
    public void onChat(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof in) {
            final String c = ((in)eventPacketReceive.getPacket()).a().c();
            if (c.contains("/tpaccept") || c.contains("/tpyes") || c.contains("teleport request") || (c.contains("\u2713") && c.contains("\u2718")) || (c.contains("\u2714") && c.contains("\u2717"))) {
                final Iterator<String> iterator = FriendManager.friends.iterator();
                while (iterator.hasNext()) {
                    if (c.contains(iterator.next())) {
                        AutoAccept.mc.h.g("/tpaccept");
                    }
                }
            }
        }
    }
    
    public AutoAccept() {
        super("AutoAccept", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u0440\u0438\u043d\u0438\u043c\u0430\u0435\u0442 \u0442\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0430\u0446\u0438\u0438 \u043e\u0442 \u0434\u0440\u0443\u0437\u0435\u0439.", Category.ANOTHER);
    }
}
