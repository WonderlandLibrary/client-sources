package mathax.client.systems.modules.render.marker;

import mathax.client.systems.modules.Modules;

import java.util.HashMap;
import java.util.Map;

public class MarkerFactory {
    private interface Factory {
        BaseMarker create();
    }

    private final Map<String, Factory> factories;
    private final String[] names;

    public MarkerFactory() {
        factories = new HashMap<>();
        factories.put(CuboidMarker.type, CuboidMarker::new);
        factories.put(Sphere2DMarker.type, Sphere2DMarker::new);

        names = new String[factories.size()];
        int i = 0;
        for (String key : factories.keySet()) names[i++] = key;
    }

    public String[] getNames() {
        return names;
    }

    public BaseMarker createMarker(String name) {
        if (factories.containsKey(name)) {
            BaseMarker marker = factories.get(name).create();
            marker.settings.registerColorSettings(Modules.get().get(Marker.class));

            return marker;
        }

        return null;
    }
}
