package com.alan.clients.bindable;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.input.KeyboardInputEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BindableManager {
    
    public void init() {
        // Has to be a listener to handle the key presses
        Client.INSTANCE.getEventBus().register(this);
    }

    public List<Bindable> getBinds() {
        List<Bindable> bindableList = new ArrayList<>();

        bindableList.addAll(Client.INSTANCE.getModuleManager().getAll());
        bindableList.addAll(Client.INSTANCE.getConfigManager());
        
        return bindableList;
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<KeyboardInputEvent> onKey = event -> {
        if (event.getGuiScreen() != null || event.isCancelled()) return;

        getBinds().stream()
                .filter(bind -> bind.getKey() == event.getKeyCode())
                .forEach(Bindable::onKey);
    };

    public <T extends Bindable> T get(final String name) {
        // noinspection unchecked
        return (T) getBinds().stream()
                .filter(module -> Arrays.stream(module.getAliases()).anyMatch(alias ->
                        alias.replace(" ", "")
                                .equalsIgnoreCase(name.replace(" ", ""))))
                .findAny().orElse(null);
    }
}
