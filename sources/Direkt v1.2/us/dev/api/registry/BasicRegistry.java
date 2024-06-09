package us.dev.api.registry;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Foundry on 11/15/2015.
 */
public class BasicRegistry<T> implements Registry<T> {
    protected final List<T> elements;

    public BasicRegistry() {
        this.elements = Lists.newArrayList();
    }

    @Override
    public boolean register(final T key) {
        if (this.elements.contains(key)) {
            return false;
        }
        this.elements.add(key);
        return true;
    }

    @Override
    public boolean unregister(final T key) {
        if (!this.elements.contains(key)) {
            return false;
        }
        this.elements.remove(key);
        return true;
    }

    @Override
    public boolean has(final String key) {
        return false;
    }

    @Override
    public Optional<T> get(final String key) {
        return null;
    }

    public T get(final int index) {
        return this.elements.get(index);
    }

    @Override
    public Collection<T> getElements() {
        return this.elements;
    }
}
