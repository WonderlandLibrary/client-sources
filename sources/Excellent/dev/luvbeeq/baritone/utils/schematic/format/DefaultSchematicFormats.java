package dev.luvbeeq.baritone.utils.schematic.format;

import dev.luvbeeq.baritone.api.schematic.IStaticSchematic;
import dev.luvbeeq.baritone.api.schematic.format.ISchematicFormat;
import dev.luvbeeq.baritone.utils.schematic.format.defaults.LitematicaSchematic;
import dev.luvbeeq.baritone.utils.schematic.format.defaults.MCEditSchematic;
import dev.luvbeeq.baritone.utils.schematic.format.defaults.SpongeSchematic;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Default implementations of {@link ISchematicFormat}
 *
 * @author Brady
 * @since 12/13/2019
 */
public enum DefaultSchematicFormats implements ISchematicFormat {

    /**
     * The MCEdit schematic specification. Commonly denoted by the ".schematic" file extension.
     */
    MCEDIT("schematic") {
        @Override
        public IStaticSchematic parse(InputStream input) throws IOException {
            return new MCEditSchematic(CompressedStreamTools.readCompressed(input));
        }
    },

    /**
     * The SpongePowered Schematic Specification. Commonly denoted by the ".schem" file extension.
     *
     * @see <a href="https://github.com/SpongePowered/Schematic-Specification">Sponge Schematic Specification</a>
     */
    SPONGE("schem") {
        @Override
        public IStaticSchematic parse(InputStream input) throws IOException {
            CompoundNBT nbt = CompressedStreamTools.readCompressed(input);
            int version = nbt.getInt("Version");
            switch (version) {
                case 1:
                case 2:
                    return new SpongeSchematic(nbt);
                default:
                    throw new UnsupportedOperationException("Unsupported Version of a Sponge Schematic");
            }
        }
    },

    /**
     * The Litematica schematic specification. Commonly denoted by the ".litematic" file extension.
     */
    LITEMATICA("litematic") {
        @Override
        public IStaticSchematic parse(InputStream input) throws IOException {
            CompoundNBT nbt = CompressedStreamTools.readCompressed(input);
            int version = nbt.getInt("Version");
            switch (version) {
                case 4: //1.12
                    throw new UnsupportedOperationException("This litematic Version is too old.");
                case 5: //1.13-1.17
                    return new LitematicaSchematic(nbt, false);
                case 6: //1.18+
                    throw new UnsupportedOperationException("This litematic Version is too new.");
                default:
                    throw new UnsupportedOperationException("Unsuported Version of a Litematica Schematic");
            }
        }
    };

    private final String extension;

    DefaultSchematicFormats(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean isFileType(File file) {
        return this.extension.equalsIgnoreCase(FilenameUtils.getExtension(file.getAbsolutePath()));
    }
}
