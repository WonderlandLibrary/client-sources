package io.github.liticane.monoxide.component;

import de.florianmichael.rclasses.storage.Storage;
import lombok.Getter;
import org.reflections.Reflections;
import io.github.liticane.monoxide.listener.handling.EventHandling;
import io.github.liticane.monoxide.component.api.Component;
import io.github.liticane.monoxide.component.api.ComponentInfo;

import java.lang.reflect.InvocationTargetException;

public class ComponentManager extends Storage<Component> {

    @Getter
    private static ComponentManager instance;

    @Override
    public void init() {
        EventHandling.getInstance().registerListener(this);
        final Reflections reflections = new Reflections("io.github.liticane.monoxide");
        reflections.getTypesAnnotatedWith(ComponentInfo.class).forEach(aClass -> {
            try {
                Component component = (Component) aClass.getDeclaredConstructor().newInstance();
                this.add(component);
                component.launch();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public <V extends Component> V getByClass(final Class<V> clazz) {
        final Component component = this.getList().stream().filter(m -> m.getClass().equals(clazz)).findFirst().orElse(null);
        if (component == null) return null;
        return clazz.cast(component);
    }

    public static void setInstance(ComponentManager instance) {
        ComponentManager.instance = instance;
    }

}
