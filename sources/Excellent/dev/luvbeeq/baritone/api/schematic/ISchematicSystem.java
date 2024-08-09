package dev.luvbeeq.baritone.api.schematic;

import dev.luvbeeq.baritone.api.command.registry.Registry;
import dev.luvbeeq.baritone.api.schematic.format.ISchematicFormat;

import java.io.File;
import java.util.Optional;

/**
 * @author Brady
 * @since 12/23/2019
 */
public interface ISchematicSystem {

    /**
     * @return The registry of supported schematic formats
     */
    Registry<ISchematicFormat> getRegistry();

    /**
     * Attempts to find an {@link ISchematicFormat} that supports the specified schematic file.
     *
     * @param file A schematic file
     * @return The corresponding format for the file, {@link Optional#empty()} if no candidates were found.
     */
    Optional<ISchematicFormat> getByFile(File file);
}
