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
package de.dietrichpaul.clientbase.event.rotate;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface RotationGetListener extends Listener {
    void onGetRotation(final RotationGetEvent event);

    class RotationGetEvent extends AbstractEvent<RotationGetListener> {
        private final EventExecutor<RotationGetListener> eventExecutor = listener -> listener.onGetRotation(this);
        public float yaw;
        public float pitch;

        public RotationGetEvent(final float yaw, final float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }

        @Override
        public EventExecutor<RotationGetListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<RotationGetListener> getListenerType() {
            return RotationGetListener.class;
        }
    }
}
