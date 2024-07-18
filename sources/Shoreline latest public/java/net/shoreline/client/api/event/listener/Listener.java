package net.shoreline.client.api.event.listener;

import net.shoreline.client.Shoreline;
import net.shoreline.client.api.Invokable;
import net.shoreline.client.api.event.Event;
import net.shoreline.client.api.event.handler.EventHandler;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Event} Listener that creates an {@link Invokable} and runs
 * {@link #invokeSubscriber(Event)} when the event is dispatched by
 * the {@link EventHandler}.
 *
 * <p>The invoker is created using {@link LambdaMetafactory} which is nearly
 * as fast as direct access; This method of invocation allows for blazing
 * fast event handling. Event Listeners can be created with the
 * {@link EventListener} annotation, for example:<p/>
 * <pre>{@code
 * @EventListener
 * public void onEvent(Event e)
 * {
 *     // your code ...
 * }
 * }</pre>
 *
 * @author linus
 * @see Event
 * @see EventHandler
 * @see EventListener
 * @since 1.0
 */
public class Listener implements Comparable<Listener> {
    // subscriber invoker cache for each listener method
    private static final Map<Method, Invokable<Object>> INVOKE_CACHE = new HashMap<>();
    // the MethodHandler lookup
    private static final Lookup LOOKUP = MethodHandles.lookup();
    // The EventListener method which contains the code to invoke when the
    // listener is invoked.
    private final Method method;
    // The object that contains the EventListener. This object must be
    // subscribed to the EventHandler in order for this Listener to be invoked.
    private final Object subscriber;
    //
    private final boolean receiveCanceled;
    //
    private final int priority;
    // The Listener invoker created by the LambdaMetaFactory which invokes the
    // code from the Listener method.
    private Invokable<Object> invoker;

    /**
     * @param method
     * @param subscriber
     * @param receiveCanceled
     * @param priority
     */
    @SuppressWarnings("unchecked")
    public Listener(Method method, Object subscriber,
                    boolean receiveCanceled, int priority) {
        this.method = method;
        this.subscriber = subscriber;
        this.receiveCanceled = receiveCanceled;
        this.priority = priority;
        // lambda at runtime to call the method
        try {
            if (!INVOKE_CACHE.containsKey(method)) {
                MethodType methodType = MethodType.methodType(Invokable.class);
                CallSite callSite = LambdaMetafactory.metafactory(
                        LOOKUP, "invoke",
                        methodType.appendParameterTypes(subscriber.getClass()),
                        MethodType.methodType(void.class, Object.class),
                        LOOKUP.unreflect(method),
                        MethodType.methodType(void.class,
                                method.getParameterTypes()[0])
                );
                invoker = (Invokable<Object>) callSite.getTarget().invoke(subscriber);
                INVOKE_CACHE.put(method, invoker);
            } else {
                invoker = INVOKE_CACHE.get(method);
            }
        } catch (Throwable e) {
            Shoreline.error("Failed to build invoker for {}!", method.getName());
            e.printStackTrace();
        }
    }

    /**
     * @param event
     * @throws NullPointerException
     * @see Invokable#invoke(Object)
     */
    public void invokeSubscriber(Event event) {
        if (event != null) {
            invoker.invoke(event);
        }
        // throw new NullPointerException("Failed to invoke subscriber!");
    }

    /**
     * Returns a negative integer, zero, or a positive integer as this
     * {@link Listener} has less than, equal to, or greater priority than the
     * specified listener.
     *
     * @param other The comparing listener
     * @see #getPriority()
     */
    @Override
    public int compareTo(Listener other) {
        return Integer.compare(other.getPriority(), getPriority());
    }

    /**
     * @return
     */
    public Method getMethod() {
        return method;
    }

    /**
     * @return
     */
    public Object getSubscriber() {
        return subscriber;
    }

    /**
     * @return
     */
    public boolean isReceiveCanceled() {
        return receiveCanceled;
    }

    /**
     * @return
     */
    public int getPriority() {
        return priority;
    }
}
