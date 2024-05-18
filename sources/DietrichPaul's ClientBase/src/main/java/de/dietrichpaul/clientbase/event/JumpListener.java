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

public interface JumpListener extends Listener {
    void onJump(final JumpEvent event);

    class JumpEvent extends AbstractEvent<JumpListener> {
        private final EventExecutor<JumpListener> eventExecutor = listener -> listener.onJump(this);
        public float yaw;

        public JumpEvent(final float yaw) {
            this.yaw = yaw;
        }

        @Override
        public EventExecutor<JumpListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<JumpListener> getListenerType() {
            return JumpListener.class;
        }
    }
}
