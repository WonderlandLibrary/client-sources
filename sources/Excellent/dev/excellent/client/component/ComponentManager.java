package dev.excellent.client.component;

import dev.excellent.Excellent;
import dev.excellent.client.component.impl.*;
import i.gishreloaded.protection.annotation.Native;
import i.gishreloaded.protection.annotation.Protect;

import java.util.ArrayList;

public final class ComponentManager extends ArrayList<Component> {

    @Protect(Protect.Type.VIRTUALIZATION)
    @Native
    public void init() {
        add(new DragComponent());
        add(new AuraComponent());
        add(new ParticleRangeComponent());
        add(new SmartTimerComponent());
        add(new GPSComponent());
        add(new LastConnectionComponent());
        add(new ArrowsComponent());

        iterate();
    }

    @Native
    private void iterate() {
        for (Component component : this) {
            Excellent.getInst().getEventBus().register(component);
        }
    }

    public <T extends Component> T get(final Class<T> clazz) {
        return this.stream()
                .filter(component -> component.getClass() == clazz)
                .map(component -> (T) component)
                .findAny()
                .orElse(null);
    }
}