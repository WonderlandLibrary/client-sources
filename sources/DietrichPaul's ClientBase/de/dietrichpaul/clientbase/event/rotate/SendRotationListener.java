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

public interface SendRotationListener extends Listener {
    void onSendYaw(final SendRotationEvent event);
    void onSendPitch(final SendRotationEvent event);

    class SendRotationEvent extends AbstractEvent<SendRotationListener> {
        private final EventExecutor<SendRotationListener> eventExecutor;
        public float value;

        public SendRotationEvent(final float value, final Type type) {
            if (type == Type.YAW) {
                this.eventExecutor = listener -> listener.onSendYaw(this);
            } else {
                this.eventExecutor = listener -> listener.onSendPitch(this);
            }
            this.value = value;
        }

        @Override
        public EventExecutor<SendRotationListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<SendRotationListener> getListenerType() {
            return SendRotationListener.class;
        }
    }

    enum Type {
        YAW, PITCH
    }
}
