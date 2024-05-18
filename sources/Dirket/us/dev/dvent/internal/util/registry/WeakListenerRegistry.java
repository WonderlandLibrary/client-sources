package us.dev.dvent.internal.util.registry;

import com.google.common.collect.MapMaker;

/**
 * @author Foundry
 */
public class WeakListenerRegistry<T> extends AbstractListenerRegistry<T> {
    public WeakListenerRegistry() {
        super(new MapMaker().weakKeys().makeMap());
    }
}
