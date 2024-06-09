package us.dev.api.registry;

import com.google.common.collect.Maps;
import us.dev.api.interfaces.Labeled;

import java.util.*;

/**
 * Created by Foundry on 11/15/2015.
 */
public class LabeledRegistry<T extends Labeled> implements Registry<T> {
    protected final Map<String, T> elements;

    public LabeledRegistry() {
        this.elements = Maps.newHashMap();
    }

    @Override
    public boolean register(final T key) {
        return this.elements.put(key.getLabel().toLowerCase().replace(" ", ""), key) != key;
    }

    @Override
    public boolean unregister(final T key) {
        return this.elements.remove(key.getLabel().toLowerCase().replace(" ", "")) == key;
    }

    @Override
    public boolean has(final String key) {
        return this.get(key.replace(" ", "")).isPresent();
    }

    @Override
    public Optional<T> get(final String key) {
        return Optional.ofNullable(this.elements.get(key.toLowerCase().replace(" ", "")));
    }

    @Override
    public Collection<T> getElements() {
        return Collections.unmodifiableCollection(this.elements.values());
    }
}
