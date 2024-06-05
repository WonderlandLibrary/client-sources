package io.github.nevalackin.radium.property;

@FunctionalInterface
public interface ValueChangeListener<T> {

    void onValueChange(T oldValue, T value);

}
