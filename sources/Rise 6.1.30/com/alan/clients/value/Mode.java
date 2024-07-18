package com.alan.clients.value;

import com.alan.clients.Client;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.interfaces.ThreadAccess;
import com.alan.clients.util.interfaces.Toggleable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Rewritten from Patricks old version
 */
@Getter
@RequiredArgsConstructor
public abstract class Mode<T> implements Accessor, Toggleable, ThreadAccess {
    private final String name;
    private final T parent;
    private final List<Value<?>> values = new ArrayList<>();

    public void register() {
        Client.INSTANCE.getEventBus().register(this);
        this.onEnable();
    }

    public void unregister() {
        Client.INSTANCE.getEventBus().unregister(this);
        this.onDisable();
    }

    @Override
    public void toggle() {
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }
}