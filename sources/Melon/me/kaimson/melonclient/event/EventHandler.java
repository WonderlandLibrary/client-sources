package me.kaimson.melonclient.event;

import java.lang.annotation.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.common.collect.*;

public class EventHandler
{
    private static final Map<Class<? extends Event>, List<MethodData>> bus;
    
    public static void register(final Object o) {
        for (final Method method : o.getClass().getDeclaredMethods()) {
            if (method.getParameterTypes().length == 1) {
                if (method.isAnnotationPresent(TypeEvent.class)) {
                    register(method, o);
                }
            }
        }
    }
    
    private static void register(final Method m, final Object s) {
        final Class<? extends Event> c = (Class<? extends Event>)m.getParameterTypes()[0];
        final MethodData d = new MethodData(s, m);
        if (EventHandler.bus.containsKey(c)) {
            if (!EventHandler.bus.get(c).contains(d)) {
                EventHandler.bus.get(c).add(d);
            }
        }
        else {
            EventHandler.bus.put(c, new CopyOnWriteArrayList<MethodData>() {
                private static final long serialVersionUID = 666L;
                
                {
                    this.add(d);
                }
            });
        }
    }
    
    public static void unregister(final Object object) {
        for (final List<MethodData> dataList : EventHandler.bus.values()) {
            dataList.removeIf(data -> data.getSource().equals(object));
        }
    }
    
    public static void unregister(final Object object, final Class<? extends Event> eventClass) {
        if (EventHandler.bus.containsKey(eventClass)) {
            EventHandler.bus.get(eventClass).removeIf(data -> data.getSource().equals(object));
        }
    }
    
    public static boolean call(final Event e) {
        final List<MethodData> list = EventHandler.bus.get(e.getClass());
        if (list != null) {
            for (final MethodData m : list) {
                invoke(m, e);
            }
        }
        return e instanceof Cancellable && ((Cancellable)e).isCancelled();
    }
    
    private static void invoke(final MethodData data, final Event argument) {
        try {
            data.getTarget().setAccessible(true);
            data.getTarget().invoke(data.getSource(), argument);
        }
        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex2) {
            final Exception ex;
            final Exception e = ex;
            e.printStackTrace();
        }
    }
    
    static {
        bus = Maps.newHashMap();
    }
    
    private static final class MethodData
    {
        Object source;
        Method target;
        
        public MethodData(final Object source, final Method target) {
            this.source = source;
            this.target = target;
        }
        
        public Object getSource() {
            return this.source;
        }
        
        public Method getTarget() {
            return this.target;
        }
    }
}
