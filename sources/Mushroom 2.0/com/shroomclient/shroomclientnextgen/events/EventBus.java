package com.shroomclient.shroomclientnextgen.events;

import com.shroomclient.shroomclientnextgen.annotations.AlwaysPost;
import com.shroomclient.shroomclientnextgen.annotations.PostOutsideGame;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.util.C;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventBus {

    public static EventBus INSTANCE = new EventBus();
    private final ConcurrentHashMap<
        Class<? extends Event>,
        ConcurrentHashMap<Priority, ConcurrentLinkedQueue<Listener>>
    > listeners = new ConcurrentHashMap<>();

    public EventBus() {
        // $$$INSERT$$$___LICENSE-CHECK-2___$$$INSERT$$$
    }

    public void register(
        Method method,
        Object instance,
        Class<?> instanceClazz
    ) {
        Class<? extends Event> clazz = method
            .getParameterTypes()[0].asSubclass(Event.class);

        ConcurrentHashMap<Priority, ConcurrentLinkedQueue<Listener>> l;
        if (!listeners.containsKey(clazz)) {
            l = new ConcurrentHashMap<>();
        } else {
            l = listeners.get(clazz);
        }

        Priority p = method.getAnnotation(SubscribeEvent.class).value();
        ConcurrentLinkedQueue<Listener> q;
        if (!l.containsKey(p)) {
            q = new ConcurrentLinkedQueue<>();
        } else {
            q = l.get(p);
        }

        boolean isModule = Module.class.isAssignableFrom(instanceClazz);
        boolean onlyIfEnabled = !method.isAnnotationPresent(AlwaysPost.class);
        boolean onlyInGame = !method.isAnnotationPresent(PostOutsideGame.class);
        q.add(
            new Listener(method, instance, isModule, onlyIfEnabled, onlyInGame)
        );
        l.put(p, q);
        listeners.put(clazz, l);
    }

    public synchronized void post(Event ev)
        throws InvocationTargetException, IllegalAccessException {
        Class<? extends Event> clazz = ev.getClass();

        if (listeners.containsKey(clazz)) {
            ConcurrentHashMap<Priority, ConcurrentLinkedQueue<Listener>> l =
                listeners.get(clazz);
            boolean didMutate = false;
            for (Priority p : List.of(
                Priority.HIGHEST,
                Priority.HIGH,
                Priority.NORMAL,
                Priority.LOW,
                Priority.LOWEST
            )) {
                if (l.containsKey(p)) {
                    ConcurrentLinkedQueue<Listener> q = l.get(p);
                    ConcurrentLinkedQueue<Listener> n =
                        new ConcurrentLinkedQueue<>();
                    while (!q.isEmpty()) {
                        Listener ln = q.poll();
                        n.add(ln);

                        ln.call(ev);
                    }
                    l.put(p, n);
                    didMutate = true;
                }
            }
            if (didMutate) listeners.put(clazz, l);
        }
    }

    public enum Priority {
        LOWEST,
        LOW,
        NORMAL,
        HIGH,
        HIGHEST,
    }

    private record Listener(
        Method method,
        Object instance,
        boolean isModule,
        boolean onlyIfEnabled,
        boolean onlyInGame
    ) {
        public void call(Event ev) {
            if (isModule && onlyIfEnabled) {
                Module m = (Module) instance;
                if (!m.isEnabled()) return;
            }

            // buddie. (this breaks some events, packetrecieve / send, world load, etc prob
            //if (onlyInGame && !C.isInGame()) return;

            try {
                method.invoke(instance, ev);
            } catch (Throwable ex) {
                C.logger.error("Error calling listener");
                ex.printStackTrace();
            }
        }
    }
}
