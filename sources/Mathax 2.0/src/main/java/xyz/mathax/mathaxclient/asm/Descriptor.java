package xyz.mathax.mathaxclient.asm;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class Descriptor {
    private final String[] components;

    public Descriptor(String... components) {
        this.components = components;
    }

    public String toString(boolean method, boolean map) {
        MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();
        StringBuilder stringBuilder = new StringBuilder();
        if (method) {
            stringBuilder.append('(');
        }

        for (int i = 0; i < components.length; i++) {
            if (method && i == components.length - 1) {
                stringBuilder.append(')');
            }

            String component = components[i];
            if (map && component.startsWith("L") && component.endsWith(";")) {
                stringBuilder.append('L').append(mappings.mapClassName("intermediary", component.substring(1, component.length() - 1).replace('/', '.')).replace('.', '/')).append(';');
            } else {
                stringBuilder.append(component);
            }
        }

        return stringBuilder.toString();
    }
}
