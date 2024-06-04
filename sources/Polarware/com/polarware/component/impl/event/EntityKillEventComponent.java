package com.polarware.component.impl.event;

import com.polarware.Client;
import com.polarware.component.Component;
import com.polarware.event.bus.Listener;
import com.polarware.event.Priority;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.event.impl.other.KillEvent;
import com.polarware.event.impl.other.WorldChangeEvent;
import net.minecraft.entity.Entity;

//@Priority(priority = -100) /* Must be run before all modules */
public class EntityKillEventComponent extends Component {

    Entity target = null;

    @EventLink(value = Priority.LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        threadPool.execute(() -> {
            if (target != null && !mc.theWorld.loadedEntityList.contains(target)) {
                target = null;
                Client.INSTANCE.getEventBus().handle(new KillEvent(target));
            }
        });
    };

    @EventLink(value = Priority.LOW)
    public final Listener<AttackEvent> onAttackEvent = event -> {
        target = event.getTarget();
    };

    @EventLink(value = Priority.LOW)
    public final Listener<WorldChangeEvent> onWorldChange = event -> {
        target = null;
    };
}
