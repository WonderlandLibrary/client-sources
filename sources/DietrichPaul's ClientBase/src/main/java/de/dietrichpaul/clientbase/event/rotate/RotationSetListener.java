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

public interface RotationSetListener extends Listener {
    void onSetRotation(final float yaw, final float pitch, final boolean isYaw, final boolean isPitch);

    class RotationSetEvent extends AbstractEvent<RotationSetListener> {
        private final EventExecutor<RotationSetListener> eventExecutor;

        public RotationSetEvent(final float yaw, final float pitch, final boolean isYaw, final boolean isPitch) {
            this.eventExecutor = listener -> listener.onSetRotation(yaw, pitch, isYaw, isPitch);
        }

        @Override
        public EventExecutor<RotationSetListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<RotationSetListener> getListenerType() {
            return RotationSetListener.class;
        }
    }
}
