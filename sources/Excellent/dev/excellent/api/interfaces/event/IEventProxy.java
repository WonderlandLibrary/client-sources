package dev.excellent.api.interfaces.event;

public interface IEventProxy {

    void register(Object object);

    void unregister(Object object);

    void updateCallbacks();

}
