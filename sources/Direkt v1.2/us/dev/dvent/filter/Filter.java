package us.dev.dvent.filter;

import us.dev.dvent.Link;

import java.util.function.BiPredicate;

/**
 * @author Foundry
 */
@FunctionalInterface
public interface Filter<T> extends BiPredicate<Link<T>, T> {
    @Override
    boolean test(Link<T> link, T event);
}
