package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import net.minecraft.entity.Entity;

public interface TargetPickListener extends Listener {

    void onPickTarget(TargetPickEvent event);

    class TargetPickEvent extends AbstractEvent<TargetPickListener> {

        private final EventExecutor<TargetPickListener> eventExecutor = listener -> listener.onPickTarget(this);

        private Entity target;

        public TargetPickEvent(Entity target) {
            this.target = target;
        }

        public Entity getTarget() {
            return target;
        }

        public void setTarget(Entity target) {
            this.target = target;
        }

        @Override
        public EventExecutor<TargetPickListener> getEventExecutor() {
            return eventExecutor;
        }

        @Override
        public Class<TargetPickListener> getListenerType() {
            return TargetPickListener.class;
        }
    }


}
