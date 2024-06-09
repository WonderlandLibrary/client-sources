package us.dev.direkt.event.internal.filters;

import us.dev.direkt.event.Cancellable;
import us.dev.direkt.event.Event;
import us.dev.dvent.Link;
import us.dev.dvent.filter.Filter;

/**
 * @author Foundry
 */
public class CancellableFilter<T extends Event & Cancellable> implements Filter<T> {
    @Override
    public boolean test(Link<T> link, T event) {
        return !event.isCancelled();
    }
}
