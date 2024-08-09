package dev.excellent.impl.value.mode;

import dev.excellent.Excellent;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.api.interfaces.module.IToggleable;
import dev.excellent.impl.value.Value;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public abstract class Mode<T> implements IMinecraft, IToggleable {
    private final String name;
    private final T parent;
    private final List<Value<?>> values = new ArrayList<>();

    public boolean is(final String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public boolean isNot(final String name) {
        return !this.name.equalsIgnoreCase(name);
    }

    public final void register() {
        Excellent.getInst().getEventBus().register(this);
        this.onEnable();
    }

    public final void unregister() {
        Excellent.getInst().getEventBus().unregister(this);
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