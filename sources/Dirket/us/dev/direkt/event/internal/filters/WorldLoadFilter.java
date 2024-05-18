package us.dev.direkt.event.internal.filters;

import us.dev.direkt.Wrapper;
import us.dev.direkt.event.Event;
import us.dev.dvent.Link;
import us.dev.dvent.filter.Filter;

/**
 * @author Foundry
 */
public class WorldLoadFilter<T extends Event> implements Filter<T> {

    @Override
    public boolean test(Link<T> link, T event) {
        return Wrapper.getWorld() != null;
    }
}
