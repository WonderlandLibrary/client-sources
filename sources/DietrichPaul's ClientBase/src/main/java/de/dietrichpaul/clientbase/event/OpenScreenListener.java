package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;
import de.florianmichael.dietrichevents.types.CancellableEvent;
import net.minecraft.client.gui.screen.Screen;

public interface OpenScreenListener extends Listener {

    void onOpenScreen(OpenScreenEvent event);

    class OpenScreenEvent  extends CancellableEvent<OpenScreenListener> {

        private final Screen screen;

        private final EventExecutor<OpenScreenListener> executor = listener -> listener.onOpenScreen(this);

        public OpenScreenEvent(Screen screen) {
            this.screen = screen;
        }

        public Screen getScreen() {
            return screen;
        }

        @Override
        public EventExecutor<OpenScreenListener> getEventExecutor() {
            return executor;
        }
        @Override
        public Class<OpenScreenListener> getListenerType() {
            return OpenScreenListener.class;
        }

    }

}
