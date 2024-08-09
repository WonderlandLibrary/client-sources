package dev.luvbeeq.baritone.utils.schematic;

import dev.luvbeeq.baritone.api.command.registry.Registry;
import dev.luvbeeq.baritone.api.schematic.ISchematicSystem;
import dev.luvbeeq.baritone.api.schematic.format.ISchematicFormat;
import dev.luvbeeq.baritone.utils.schematic.format.DefaultSchematicFormats;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Brady
 * @since 12/24/2019
 */
public enum SchematicSystem implements ISchematicSystem {
    INSTANCE;

    private final Registry<ISchematicFormat> registry = new Registry<>();

    SchematicSystem() {
        Arrays.stream(DefaultSchematicFormats.values()).forEach(this.registry::register);
    }

    @Override
    public Registry<ISchematicFormat> getRegistry() {
        return this.registry;
    }

    @Override
    public Optional<ISchematicFormat> getByFile(File file) {
        return this.registry.stream().filter(format -> format.isFileType(file)).findFirst();
    }
}
