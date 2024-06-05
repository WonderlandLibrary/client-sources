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

public interface StrafeInputListener extends Listener {
    void onStrafeInput(final StrafeInputEvent event);

    class StrafeInputEvent extends AbstractEvent<StrafeInputListener> {
        private final EventExecutor<StrafeInputListener> eventExecutor = listener -> listener.onStrafeInput(this);
        public int moveForward;
        public int moveSideways;
        public boolean jumping;
        public boolean sneaking;

        public StrafeInputEvent(int moveForward, int moveSideways, boolean jumping, boolean sneaking) {
            this.moveForward = moveForward;
            this.moveSideways = moveSideways;
            this.jumping = jumping;
            this.sneaking = sneaking;
        }

        @Override
        public EventExecutor<StrafeInputListener> getEventExecutor() {
            return this.eventExecutor;
        }

        @Override
        public Class<StrafeInputListener> getListenerType() {
            return StrafeInputListener.class;
        }
    }
}
