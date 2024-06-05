package me.zane.basicbus.api.bus;

/**
 * @since 1.4.0
 */

public interface Bus<T> {

    void subscribe(Object subscriber);

    void unsubscribe(Object subscriber);

    void post(T event);
}
