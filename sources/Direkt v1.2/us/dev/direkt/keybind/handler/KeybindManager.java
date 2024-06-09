package us.dev.direkt.keybind.handler;

import us.dev.direkt.Direkt;
import us.dev.direkt.event.internal.events.system.input.EventKeyInput;
import us.dev.direkt.keybind.Keybind;
import us.dev.dvent.Link;
import us.dev.dvent.Listener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Foundry
 */
public class KeybindManager {

    private final Set<Keybind> keybinds = new HashSet<>();

    public KeybindManager() {
        Direkt.getInstance().getEventManager().register(this);
    }

    @Listener
    protected Link<EventKeyInput> onKeyPress = new Link<>(event -> {
        this.keybinds.stream().filter(keybind -> event.getEventKey() == keybind.getKey()).forEach(Keybind::keyPressed);
    });

    public boolean register(Keybind keybind) {
        return keybinds.add(keybind);
    }

}
