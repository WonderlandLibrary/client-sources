package me.dinozoid.strife.property;

public interface ValueChangeListener<T> {
    void onValueChange(T oldValue, T value);
}