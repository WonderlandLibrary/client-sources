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

public interface MoveCameraListener extends Listener {
    void onMoveCamera(final float tickDelta);

    class MoveCameraEvent extends AbstractEvent<MoveCameraListener> {
        private final EventExecutor<MoveCameraListener> eventExecutor;

        public MoveCameraEvent(final float tickDelta) {
            this.eventExecutor = listener -> listener.onMoveCamera(tickDelta);
        }

        @Override
        public EventExecutor<MoveCameraListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<MoveCameraListener> getListenerType() {
            return MoveCameraListener.class;
        }
    }
}
