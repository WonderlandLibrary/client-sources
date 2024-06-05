package client.metaware.impl.managers;

import client.metaware.Metaware;
import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.bind.Bindable;
import client.metaware.impl.event.impl.system.KeyEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BindManager {

    private static final List<Bindable> BINDABLES = new ArrayList<>();

    public void init() {
        Metaware.INSTANCE.getEventBus().subscribe(this);
    }


    @EventHandler
    private final Listener<KeyEvent> keyEvent = event -> {
        BINDABLES.forEach(bindable -> {
            if(bindable.getKey() == event.getKey()) bindable.pressed();
        });
    };

    public void addBindables(Bindable... bindables) {
        Collections.addAll(BINDABLES, bindables);
    }

    public void addBindables(List<Bindable> bindables) {
        BINDABLES.addAll(bindables);
    }

    public void addBindable(Bindable bindable) {
        BINDABLES.add(bindable);
    }

    public static List<Bindable> bindables() {
        return BINDABLES;
    }
}