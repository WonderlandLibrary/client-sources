package ez.h.features.player;

import ez.h.features.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class NameProtect extends Feature
{
    public NameProtect() {
        super("NameProtect", "\u0428\u0438\u0444\u0440\u0443\u0435\u0442 \u0432\u0430\u0448\u0435 \u0438\u043c\u044f \u0438 \u0434\u0440\u0443\u0437\u0435\u0439.", Category.PLAYER);
    }
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
    }
}
