package us.dev.dvent;

import us.dev.dvent.filter.Filter;
import us.dev.dvent.internal.util.LinkTargetResolver;

/**
 * @author Foundry
 */
public final class Link<T> implements LinkBody<T>, Comparable<Link<?>> {
    public static final int VERY_LOW_PRIORITY = 20;
    public static final int LOW_PRIORITY = 15;
    public static final int NORMAL_PRIORITY = 10;
    public static final int HIGH_PRIORITY = 7;
    public static final int VERY_HIGH_PRIORITY = 4;

    private final LinkBody<T> link;
    private final Class<T> eventTarget;
    private final int priority;
    private final Filter<T>[] filters;

    @SafeVarargs
    public Link(LinkBody<T> link, Filter<T>... filters) {
        this(link, NORMAL_PRIORITY, filters);
    }

    @SafeVarargs
    public Link(LinkBody<T> link, int priority, Filter<T>... filters) {
        this.link = link;
        this.eventTarget = LinkTargetResolver.resolveLinkTarget(link);
        this.priority = priority;
        this.filters = filters;
    }

    @Override
    public void invoke(T event) {
        link.invoke(event);
    }

    public LinkBody<T> getLink() {
        return this.link;
    }

    public Class<T> getEventClass() {
        return this.eventTarget;
    }

    public int getPriority() {
        return this.priority;
    }

    public Filter<T>[] getFilters() {
        return this.filters;
    }

    @Override
    public int compareTo(Link<?> link) {
        final int discriminant = Integer.compare(this.priority, link.getPriority());
        return discriminant == 0 ? System.identityHashCode(link) - System.identityHashCode(this) : discriminant;
    }
}
