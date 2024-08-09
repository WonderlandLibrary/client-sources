package dev.luvbeeq.baritone.api.schematic.format;

import dev.luvbeeq.baritone.api.schematic.ISchematic;
import dev.luvbeeq.baritone.api.schematic.IStaticSchematic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * The base of a {@link ISchematic} file format
 *
 * @author Brady
 * @since 12/23/2019
 */
public interface ISchematicFormat {

    /**
     * @return The parser for creating schematics of this format
     */
    IStaticSchematic parse(InputStream input) throws IOException;

    /**
     * @param file The file to check against
     * @return Whether or not the specified file matches this schematic format
     */
    boolean isFileType(File file);
}
