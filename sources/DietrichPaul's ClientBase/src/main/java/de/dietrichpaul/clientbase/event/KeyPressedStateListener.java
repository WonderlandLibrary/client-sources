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
import net.minecraft.client.option.KeyBinding;

public interface KeyPressedStateListener extends Listener {
    void onKeyPressedState(final KeyPressedStateEvent keyPressedStateEvent);

    class KeyPressedStateEvent extends AbstractEvent<KeyPressedStateListener> {
        private final EventExecutor<KeyPressedStateListener> eventExecutor = listener -> listener.onKeyPressedState(this);
        public KeyBinding keyBinding;
        public boolean pressed;

        public KeyPressedStateEvent(final KeyBinding keyBinding, final boolean pressed) {
            this.keyBinding = keyBinding;
            this.pressed = pressed;
        }

        @Override
        public EventExecutor<KeyPressedStateListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<KeyPressedStateListener> getListenerType() {
            return KeyPressedStateListener.class;
        }
    }
}
