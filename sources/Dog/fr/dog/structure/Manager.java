package fr.dog.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Manager<T> {
    protected final List<T> objects;

    public Manager() {
        this.objects = new ArrayList<>();
    }

    public void register(final T object) {
        this.objects.add(object);
    }

    @SafeVarargs
    public final void register(final T... objects) {
        this.objects.addAll(Arrays.asList(objects));
    }

    public void unregister(final T object) {
        this.objects.remove(object);
    }

    @SafeVarargs
    public final void unregister(final T... objects) {
        this.objects.removeAll(Arrays.asList(objects));
    }

    public void unregister(final Predicate<? super T> predicate) {
        this.objects.removeIf(predicate);
    }

    public final List<T> getMultipleBy(final Predicate<? super T> predicate) {
        return this.objects.stream().filter(predicate).collect(Collectors.toList());
    }

    public final T getBy(final Predicate<? super T> predicate) {
        return this.objects.stream().filter(predicate).findFirst().orElse(null);
    }

    public final List<T> getObjects() {
        return Collections.unmodifiableList(this.objects);
    }
}