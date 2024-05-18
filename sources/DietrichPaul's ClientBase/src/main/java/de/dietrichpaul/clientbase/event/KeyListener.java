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

public interface KeyListener extends Listener {
    void onKey(final int key, final int scan, final int action, final int modifiers);

    class KeyEvent extends AbstractEvent<KeyListener> {
        private final EventExecutor<KeyListener> eventExecutor;

        public KeyEvent(final int key, final int scan, final int action, final int modifiers) {
            this.eventExecutor = listener -> listener.onKey(key, scan, action, modifiers);
        }

        @Override
        public EventExecutor<KeyListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<KeyListener> getListenerType() {
            return KeyListener.class;
        }
    }
}
