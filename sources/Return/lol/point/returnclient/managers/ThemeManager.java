package lol.point.returnclient.managers;

import lol.point.returnclient.theme.Theme;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Set;

public class ThemeManager {

    public final ArrayList<Theme> themes = new ArrayList<>();

    public ThemeManager() {
        Reflections reflections = new Reflections("lol.point.returnclient.theme.impl");
        Set<Class<? extends Theme>> moduleClasses = reflections.getSubTypesOf(Theme.class);

        for (Class<? extends Theme> themeClass : moduleClasses) {
            try {
                Theme theme = themeClass.getDeclaredConstructor().newInstance();
                themes.add(theme);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Theme getThemeByName(String name) {
        return themes.stream().filter(theme -> theme.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public <V extends Theme> V getByClass(final Class<V> clazz) {
        final Theme feature = this.themes.stream().filter(t -> t.getClass().equals(clazz)).findFirst().orElse(null);
        if (feature == null) return null;
        return clazz.cast(feature);
    }

}
