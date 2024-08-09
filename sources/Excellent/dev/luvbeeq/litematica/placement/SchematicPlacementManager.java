package dev.luvbeeq.litematica.placement;

import java.util.ArrayList;
import java.util.List;

public class SchematicPlacementManager {
    private final List<SchematicPlacement> schematicPlacements = new ArrayList<>();

    //in case of a java.lang.NoSuchMethodError try change the name of this method to getAllSchematicPlacements()
    //there are inconsistencies in the litematica mod about the naming of this method
    public List<SchematicPlacement> getAllSchematicsPlacements() {
        return schematicPlacements;
    }
}