/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;

public interface RaytraceListener extends Listener {
    void onRaytrace(final RaytraceEvent event);

    class RaytraceEvent extends CancellableEvent<RaytraceListener> {
        private final EventExecutor<RaytraceListener> eventExecutor = listener -> listener.onRaytrace(this);
        public float tickDelta;

        public RaytraceEvent(final float tickDelta) {
            this.tickDelta = tickDelta;
        }

        @Override
        public EventExecutor<RaytraceListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<RaytraceListener> getListenerType() {
            return RaytraceListener.class;
        }
    }
}
