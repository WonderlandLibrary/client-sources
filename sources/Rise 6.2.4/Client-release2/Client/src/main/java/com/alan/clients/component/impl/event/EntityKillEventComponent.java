package com.alan.clients.component.impl.event;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.other.KillEvent;
import com.alan.clients.event.impl.other.WorldChangeEvent;
import com.alan.clients.util.interfaces.ThreadAccess;
import net.minecraft.entity.Entity;

public class EntityKillEventComponent extends Component implements ThreadAccess {

    Entity target = null;

    @EventLink(value = Priorities.LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (target != null && !mc.theWorld.loadedEntityList.contains(target)) {
            Client.INSTANCE.getEventBus().handle(new KillEvent(target));
            target = null;
        }
    };

    @EventLink(value = Priorities.LOW)
    public final Listener<AttackEvent> onAttackEvent = event -> {
        target = event.getTarget();
    };

    @EventLink(value = Priorities.LOW)
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        target = null;
    };
}
