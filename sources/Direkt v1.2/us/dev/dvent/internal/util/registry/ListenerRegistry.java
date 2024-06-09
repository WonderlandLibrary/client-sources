package us.dev.dvent.internal.util.registry;

import us.dev.dvent.Link;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author Foundry
 */
public interface ListenerRegistry<T> {
    boolean register(Object parent, Class<?>... events);

    boolean unregister(Object parent, Class<?>... events);

    Optional<Iterator<Link<T>>> findSubscribers(T event);
}
