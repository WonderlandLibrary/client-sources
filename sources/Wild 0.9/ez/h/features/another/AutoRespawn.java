package ez.h.features.another;

import ez.h.event.events.*;
import java.awt.*;
import ez.h.event.*;
import ez.h.features.*;

public class AutoRespawn extends Feature
{
    @EventTarget
    public void onDeath(final EventDeath eventDeath) {
        AutoRespawn.mc.h.cY();
        Notifications.addNotification("Detected death", "Respawned", new Color(0, 166 + 84 - 225 + 175, 123 + 20 - 78 + 135), new Color(-1));
    }
    
    public AutoRespawn() {
        super("AutoRespawn", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u043e\u0435 \u0432\u043e\u0437\u0440\u043e\u0436\u0434\u0435\u043d\u0438\u0435 \u0438\u0433\u0440\u043e\u043a\u0430.", Category.ANOTHER);
    }
}
