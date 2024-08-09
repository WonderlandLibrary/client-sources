package dev.luvbeeq.baritone.utils.schematic.schematica;

import dev.luvbeeq.baritone.api.schematic.IStaticSchematic;
import dev.luvbeeq.schematica.Schematica;
import dev.luvbeeq.schematica.proxy.ClientProxy;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public enum SchematicaHelper {
    ;

    public static boolean isSchematicaPresent() {
        try {
            Class.forName(Schematica.class.getName());
            return true;
        } catch(ClassNotFoundException | NoClassDefFoundError ex) {
            return false;
        }
    }

    public static Optional<Tuple<IStaticSchematic, BlockPos>> getOpenSchematic() {
        return Optional.ofNullable(ClientProxy.schematic)
                .map(world -> new Tuple<>(new SchematicAdapter(world), world.position));
    }

}
