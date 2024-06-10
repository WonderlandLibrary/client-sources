package me.dinozoid.strife.bind;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listenable;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.system.KeyEvent;
import me.dinozoid.strife.util.player.AltService;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BindRepository implements Listenable {

    private static final List<Bindable> BINDABLES = new ArrayList<>();

    public void init() {
        StrifeClient.INSTANCE.eventBus().subscribe(this);
    }

    @EventHandler
    private final Listener<KeyEvent> keyEvent = new Listener<>(event -> {
       BINDABLES.forEach(bindable -> {
           if(bindable.key() == event.key()) bindable.pressed();
       });
    });

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
