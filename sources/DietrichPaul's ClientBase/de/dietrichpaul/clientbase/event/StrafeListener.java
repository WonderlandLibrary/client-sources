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

public interface StrafeListener extends Listener {
    void onStrafe(final StrafeEvent event);

    class StrafeEvent extends AbstractEvent<StrafeListener> {
        private final EventExecutor<StrafeListener> eventExecutor = listener -> listener.onStrafe(this);
        public float yaw;

        public StrafeEvent(final float yaw) {
            this.yaw = yaw;
        }

        @Override
        public EventExecutor<StrafeListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<StrafeListener> getListenerType() {
            return StrafeListener.class;
        }
    }
}
