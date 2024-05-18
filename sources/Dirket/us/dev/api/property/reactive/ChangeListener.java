package us.dev.api.property.reactive;

import java.util.function.BiConsumer;

/**
 * @author Foundry
 */
@FunctionalInterface
public interface ChangeListener<T> extends BiConsumer<T, T> {
    @Override
    void accept(T oldValue, T newValue);
}
