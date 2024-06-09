package us.dev.dvent.internal.util.registry;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableSet;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;
import us.dev.dvent.internal.util.FieldFactory;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;

/**
 * @author Foundry
 */
abstract class AbstractListenerRegistry<T> implements ListenerRegistry<T> {
     private static final LoadingCache<Object, ImmutableSet<Link<?>>> parentLinkCache = CacheBuilder.newBuilder()
            .weakKeys()
            .build(CacheLoader.from(object -> Arrays.stream(object.getClass().getDeclaredFields())
                    .filter(field -> Link.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(Listener.class))
                    .map(field -> (Link<?>) FieldFactory.create(object, field, Link.class))
                    .collect(toImmutableSet())));

    private final ConcurrentMap<Class<?>, SortedSet<Link<?>>> backingMap;

    AbstractListenerRegistry(ConcurrentMap<Class<?>, SortedSet<Link<?>>> backingMap) {
        this.backingMap = backingMap;
    }

    @Override
    public boolean register(Object parent, Class<?>... events) {
        boolean added = false;
        try {
            for (Link<?> link : parentLinkCache.get(Objects.requireNonNull(parent, "Object to be registered cannot be null"))) {
                if (events.length > 0) {
                    for (Class<?> clazz : events) {
                        if (link.getEventClass() == clazz) {
                            this.backingMap.computeIfAbsent(link.getEventClass(), l -> new ConcurrentSkipListSet<>()).add(link);
                            added = true;
                            break;
                        }
                    }
                } else {
                    this.backingMap.computeIfAbsent(link.getEventClass(), l -> new ConcurrentSkipListSet<>()).add(link);
                    added = true;
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return added;
    }

    @Override
    public boolean unregister(Object parent, Class<?>... events) {
        boolean removed = false;
        try {
            for (Link<?> link : parentLinkCache.get(Objects.requireNonNull(parent, "Object to be unregistered cannot be null"))) {
                final SortedSet<Link<?>> links = backingMap.get(link.getEventClass());
                if (links != null) {
                    if (events.length > 0) {
                        for (Class<?> clazz : events) {
                            if (link.getEventClass() == clazz) {
                                removed |= links.remove(link);
                                break;
                            }
                        }
                    } else {
                        removed |= links.remove(link);
                    }
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }
        return removed;
    }

    @Override
    public Optional<Iterator<Link<T>>> findSubscribers(T event) {
        @SuppressWarnings("unchecked")
        final SortedSet<Link<T>> listeners = (SortedSet<Link<T>>) (SortedSet) backingMap.get(Objects.requireNonNull(event, "Event to be called cannot be null").getClass());
        return Optional.ofNullable(listeners).filter(set -> !set.isEmpty()).map(Set::iterator);
    }

    private static <T> Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> toImmutableSet() {
        return Collector.of(ImmutableSet.Builder<T>::new,
                ImmutableSet.Builder<T>::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSet.Builder<T>::build);
    }

}
