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

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface PreTickRaytraceListener extends Listener {
    void onPreTickRaytrace();

    class PreTickRaytraceEvent extends AbstractEvent<PreTickRaytraceListener> {
        private final EventExecutor<PreTickRaytraceListener> eventExecutor = PreTickRaytraceListener::onPreTickRaytrace;

        @Override
        public EventExecutor<PreTickRaytraceListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<PreTickRaytraceListener> getListenerType() {
            return PreTickRaytraceListener.class;
        }
    }
}
