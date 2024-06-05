package pw.stamina.causam.publish.listen.decorate;

import pw.stamina.causam.publish.listen.*;

public interface ListenerDecorator<T>
{
    Listener<T> decorate(final Listener<T> p0);
    
    default <T> ListenerDecorator<T> synchronizing() {
        return new SynchronizingListenerDecorator<T>();
    }
}
