package pw.stamina.causam.publish.listen.decorate;

import pw.stamina.causam.publish.listen.*;
import pw.stamina.causam.publish.exception.*;

final class SynchronizingListenerDecorator<T> implements ListenerDecorator<T>
{
    @Override
    public Listener<T> decorate(final Listener<T> decorating) {
        return event -> {
            synchronized (this) {
                decorating.publish(event);
            }
        };
    }
}
