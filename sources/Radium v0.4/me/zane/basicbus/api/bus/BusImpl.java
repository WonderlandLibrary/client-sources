package me.zane.basicbus.api.bus;

import me.zane.basicbus.api.annotations.Listener;
import me.zane.basicbus.api.annotations.Priority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Default implementation of {@link Bus}.
 *
 * @since 1.4.0
 */
public final class BusImpl<T> implements Bus<T> {

    private static final Site[] PLACEHOLDER = new Site[1];

    private final Map<Class<?>, List<Site>> map = new HashMap<>();

    /**
     * When an {@link Object} is subscribed, any method annotated with {@link Listener} will be called
     * if an instance of the class {@link Listener#value} is posted with {@link BusImpl#post}.
     * Note: Needs to be optimized is currently very slow in comparision to {@link BusImpl#post} and {@link BusImpl#unsubscribe}
     *
     * @param subscriber {@link Object} to be subscribed
     */
    @Override
    public void subscribe(Object subscriber) {
        for (final Method m : subscriber.getClass().getDeclaredMethods()) {
            final Listener l = m.getAnnotation(Listener.class);
            if (l != null) {
                final Class<?>[] params = m.getParameterTypes();
                if (params.length == 1) {
                    final Map<Class<?>, List<Site>> map = this.map;
                    Class<?> ecs = params[0];
                    final Site cl = new Site(subscriber, m, l.value());
                    // if the target event has had one of its subscribers subscribed before add to the existing ArrayList
                    if (map.containsKey(ecs)) {
                        List<Site> ss = map.get(ecs);
                        ss.add(cl);
                        ss.sort(Comparator.comparingInt(site -> site.p));
                    } else { // else create a new single-element ArrayList
                        // avoid new array creation
                        PLACEHOLDER[0] = cl;
                        // in the JDK ArrayList(E[] array) is package-private is it must be called via Arrays#asList
                        map.put(ecs, new ArrayList<>(Arrays.asList(PLACEHOLDER)));
                    }
                }
            }
        }
    }

    /**
     * Once a subscriber has been unsubscribed any method annotated with {@link Listener} no longer
     * will be called on {@link BusImpl#post}
     *
     * @param subscriber Any {@link Object} that has been subscribed using {@link BusImpl#subscribe}.
     */
    @Override
    public void unsubscribe(Object subscriber) {
        // using removeIf is noticeably faster than any alternative (open for discussion)
        for (List<Site> cls : this.map.values()) {
            cls.removeIf(c -> c.s == subscriber);
            cls.sort(Comparator.comparingInt(site -> site.p));
        }
    }

    /**
     * @param event Any {@link T}, when {@code event} is posted it invokes all
     *              methods annotated with {@link Listener} and with either 0 or 1 parameter(s),
     *              if 1 parameter is present it must be of matching type as the class specified
     *              in {@link Listener#value}. Only if an instance of the method's containing
     *              class has been subscribed using {@link BusImpl#subscribe} will it be invoked.
     */
    @Override
    public void post(T event) {
        final List<Site> cls = this.map.get(event.getClass());
        if (cls != null && !cls.isEmpty()) {
            for (Site site : cls) {
                try {
                    final Method m = site.m;
                    final Object sub = site.s;
                    m.invoke(sub, event);
                } catch (IllegalAccessException | InvocationTargetException | IndexOutOfBoundsException ignored) {
                }
            }
        }
    }

    static final class Site {

        final Object s;
        final Method m;
        final byte p;

        Site(Object s, Method m, Priority p) {
            this.s = s;
            if (!m.isAccessible())
                m.setAccessible(true);
            this.m = m;
            this.p = (byte) p.ordinal();
        }
    }

}
