package us.dev.dvent.internal.util.registry;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Foundry
 */
public class StrongListenerRegistry<T> extends AbstractListenerRegistry<T> {
    public StrongListenerRegistry() {
        super(new ConcurrentHashMap<>());
    }
}
