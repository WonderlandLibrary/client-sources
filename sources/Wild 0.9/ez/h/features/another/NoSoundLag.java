package ez.h.features.another;

import ez.h.features.*;
import java.util.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class NoSoundLag extends Feature
{
    ArrayList<qe> blacklist;
    
    public NoSoundLag() {
        super("NoSoundLag", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0437\u0432\u0443\u043a\u0438 \u043a\u043e\u0442\u043e\u0440\u044b\u0435 \u0441\u043f\u043e\u0441\u043e\u0431\u0441\u0442\u0432\u0443\u044e\u0442 \u043b\u0430\u0433\u0430\u043c.", Category.ANOTHER);
        this.blacklist = new ArrayList<qe>(Arrays.asList(qf.a, qf.q, qf.o, qf.s, qf.r, qf.n, qf.t));
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof kq && ((kq)eventPacketReceive.getPacket()).b() == qg.h) {
            eventPacketReceive.setCancelled(true);
        }
    }
}
