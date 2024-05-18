package de.dietrichpaul.clientbase.event;

import de.florianmichael.dietrichevents.AbstractEvent;
import de.florianmichael.dietrichevents.handle.EventExecutor;
import de.florianmichael.dietrichevents.handle.Listener;

public interface PostUpdateListener extends Listener {

    void onPostUpdate();

    class PostUpdateEvent extends AbstractEvent<PostUpdateListener> {

        public static final PostUpdateEvent INSTANCE = new PostUpdateEvent();
        private static final EventExecutor<PostUpdateListener> EXECUTOR = PostUpdateListener::onPostUpdate;

        @Override
        public EventExecutor<PostUpdateListener> getEventExecutor() {
            return EXECUTOR;
        }

        @Override
        public Class<PostUpdateListener> getListenerType() {
            return PostUpdateListener.class;
        }
    }

}
