package club.bluezenith.events;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.listeners.*;
import club.bluezenith.module.Module;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static club.bluezenith.core.data.preferences.Preferences.useSortingInEventManager;
import static java.lang.System.err;
import static java.util.Comparator.comparingInt;

public class EventManager {

    public EventManager() {
        register(new UtilityListener());
        register(new HypixelStatMeter());
        register(new PlaytimeMeter());
    }

    public final Map<Class<?>, CopyOnWriteArrayList<Subscription>> eventSubscriptionMap = Maps.newHashMap();

    public void register(final Object listener) {
        final Class<?> clazz = listener.getClass();
        for(Method method : clazz.getMethods()) {
            method.setAccessible(true);
            final Class<?>[] types = method.getParameterTypes();
            if(!method.isAnnotationPresent(Listener.class) || types.length != 1) continue;

            final Class<?> event = types[0];
            if(event.getSuperclass() != Event.class && event.getSuperclass() != MultiTypeEvent.class) continue;

            final Subscription sub = new Subscription(listener, method);

            List<Subscription> list;

            if(eventSubscriptionMap.containsKey(event)) {
                list = eventSubscriptionMap.get(event);
                list.add(sub);
            } else {
                final CopyOnWriteArrayList<Subscription> arrayList = Lists.newCopyOnWriteArrayList();
                list = arrayList;
                arrayList.add(sub);
                eventSubscriptionMap.put(event, arrayList);
            }
            if(useSortingInEventManager)
              list.sort(comparingInt(a -> 10 - a.getMethod().getAnnotation(Listener.class).value().getPriority()));
        }
    }

    public void unregister(final Object listener) {
        for(List<Subscription> list : eventSubscriptionMap.values()) {
            list.removeIf(subscription -> subscription.getParent().equals(listener));
        }
    }

    public void invoke(final Event event) {
        final List<Subscription> halal = eventSubscriptionMap.get(event.getClass());
        if(halal == null) return;
        for(final Subscription subscription : halal) {
            try {
                subscription.getMethod().invoke(subscription.getParent(), event);
            } catch(Exception ex) {
                err.printf("\n[Events] ### Failed to dispatch %s to subscriber %s! ###\nCause: %s\n",
                        event.getClass().getSimpleName(),
                        subscription.getParent().getClass().getSimpleName(), ex.getCause().getClass().getSimpleName());
                ex.getCause().printStackTrace();
                if(!BlueZenith.getBlueZenith().ignoreModuleErrors) {
                    if (subscription.getParent() instanceof Module) {
                        final Module mod = (Module) subscription.getParent();
                        mod.exceptionThreshold++;
                        if (mod.exceptionThreshold >= 5) {
                            mod.exceptionThreshold = 0;
                            unregister(subscription.getParent());
                            mod.setState(false);
                            BlueZenith.getBlueZenith().getNotificationPublisher().postError(
                                    BlueZenith.getBlueZenith().getName(),
                                    mod.getName() + " was disabled to prevent crashes.",
                                    3000
                            );
                        } else
                            BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(
                                    mod.getName(),
                                    "An error has occurred! Total errors: " + mod.exceptionThreshold,
                                    2000
                            );
                    }
                }
            }
        }
    }

    public void unregisterAll() {
        this.eventSubscriptionMap.clear();
    }

    private int getPriority(Subscription subscription) {
        return subscription.getMethod().getAnnotation(Listener.class).value().getPriority();
    }
}
