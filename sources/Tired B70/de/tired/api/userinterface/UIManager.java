package de.tired.api.userinterface;


import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UIManager {

    private static final List<UI> uiList = new ArrayList<>();

    public UIManager() {
        final Reflections reflections = new Reflections("beta.tiredb56.ui.userinterface");
        final Set<Class<?>> classes = reflections.getTypesAnnotatedWith(UIAnnotation.class);
        for (Class<?> aClass : classes) {
            try {
                final UI ui = (UI) aClass.newInstance();
                uiList.add(ui);

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void setupOver() {

    }

    public static <T extends UI> T getUI(Class<T> clazz) {
        return (T) uiList.stream().filter(module -> module.getClass().equals(clazz)).findAny().orElse(null);
    }

}
